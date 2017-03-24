package com.endava.androiddemo.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import com.endava.androiddemo.database.dao.DaoMaster;
import com.endava.androiddemo.database.dao.TaskDao;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.StandardDatabase;
import org.greenrobot.greendao.internal.DaoConfig;
import timber.log.Timber;

public final class MigrationUtils {

  private static final String SQLITE_MASTER = "sqlite_master";
  private static final String SQLITE_TEMP_MASTER = "sqlite_temp_master";

  private MigrationUtils() {
    //No-OP
  }

  public static class MyOpenHelper extends DaoMaster.OpenHelper {
    public MyOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
      super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database database, int oldVersion, int newVersion) {
      //not meant to be implemented here
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
      printLog("greenDAO: Upgrading schema from version "
        + oldVersion
        + " to "
        + newVersion
        + " by migrating all tables data");

      migrate(sqLiteDatabase, TaskDao.class);
    }
  }

  @SafeVarargs
  private static void migrate(SQLiteDatabase sqLiteDatabase,
    Class<? extends AbstractDao<?, ?>>... daoClasses) {
    Database database = new StandardDatabase(sqLiteDatabase);

    printLog("【The Old Database Version】" + sqLiteDatabase.getVersion());
    printLog("【Generate temp table】start");
    generateTempTables(database, daoClasses);
    printLog("【Generate temp table】complete");

    dropAllTables(database, true, daoClasses);
    createAllTables(database, false, daoClasses);

    printLog("【Restore data】start");
    restoreData(database, daoClasses);
    printLog("【Restore data】complete");
  }

  @SafeVarargs
  private static void generateTempTables(Database database,
    Class<? extends AbstractDao<?, ?>>... daoClasses) {
    for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
      String tempTableName = null;

      DaoConfig daoConfig = new DaoConfig(database, daoClass);
      String tableName = daoConfig.tablename;
      if (!isTableExists(database, false, tableName)) {
        printLog("【New Table】" + tableName);
        continue;
      }
      try {
        tempTableName = daoConfig.tablename.concat("_TEMP");
        database.execSQL("DROP TABLE IF EXISTS " + tempTableName + ";");

        String insertTableStringBuilder = "CREATE TEMPORARY TABLE " + tempTableName +
          " AS SELECT * FROM " + tableName + ";";
        database.execSQL(insertTableStringBuilder);
        printLog("【Table】" + tableName + "\n ---Columns-->" + getColumnsStr(daoConfig));
        printLog("【Generate temp table】" + tempTableName);
      } catch (Exception throwable) {
        Timber.e("【Failed to generate temp table】" + tempTableName);
        UiUtils.handleThrowable(throwable);
      }
    }
  }

  private static boolean isTableExists(Database database, boolean isTemp, String tableName) {
    if (database == null || TextUtils.isEmpty(tableName)) {
      return false;
    }
    String dbName = isTemp ? SQLITE_TEMP_MASTER : SQLITE_MASTER;
    String sql = "SELECT COUNT(*) FROM " + dbName + " WHERE type = ? AND name = ?";
    Cursor cursor = null;
    int count = 0;
    try {
      cursor = database.rawQuery(sql, new String[] { "table", tableName });
      if (cursor == null || !cursor.moveToFirst()) {
        return false;
      }
      count = cursor.getInt(0);
    } catch (Exception throwable) {
      UiUtils.handleThrowable(throwable);
    } finally {
      if (cursor != null) {
        cursor.close();
      }
    }
    return count > 0;
  }

  private static String getColumnsStr(DaoConfig daoConfig) {
    if (daoConfig == null) {
      return "no columns";
    }
    StringBuilder builder = new StringBuilder();
    for (int i = 0; i < daoConfig.allColumns.length; i++) {
      builder.append(daoConfig.allColumns[i]);
      builder.append(',');
    }
    if (builder.length() > 0) {
      builder.deleteCharAt(builder.length() - 1);
    }
    return builder.toString();
  }

  @SafeVarargs
  private static void dropAllTables(Database database, boolean ifExists,
    @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
    reflectMethod(database, "dropTable", ifExists, daoClasses);
    printLog("【Drop all table】");
  }

  @SafeVarargs
  private static void createAllTables(Database database, boolean ifNotExists,
    @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
    reflectMethod(database, "createTable", ifNotExists, daoClasses);
    printLog("【Create all table】");
  }

  /**
   * dao class already define the sql exec method, so just invoke it
   */
  @SafeVarargs
  private static void reflectMethod(Database database, String methodName, boolean isExists,
    @NonNull Class<? extends AbstractDao<?, ?>>... daoClasses) {
    if (daoClasses.length < 1) {
      return;
    }
    try {
      for (Class cls : daoClasses) {
        Method method = cls.getDeclaredMethod(methodName, Database.class, boolean.class);
        method.invoke(null, database, isExists);
      }
    } catch (Exception throwable) {
      UiUtils.handleThrowable(throwable);
    }
  }

  @SafeVarargs
  private static void restoreData(Database database, Class<? extends AbstractDao<?, ?>>... daoClasses) {
    for (Class<? extends AbstractDao<?, ?>> daoClass : daoClasses) {
      DaoConfig daoConfig = new DaoConfig(database, daoClass);
      String tableName = daoConfig.tablename;
      String tempTableName = daoConfig.tablename.concat("_TEMP");

      if (!isTableExists(database, true, tempTableName)) {
        continue;
      }

      try {
        // get all columns from tempTable, take careful to use the columns list
        List<String> columns = getColumns(database, tempTableName);
        ArrayList<String> properties = new ArrayList<>(columns.size());
        for (int j = 0; j < daoConfig.properties.length; j++) {
          String columnName = daoConfig.properties[j].columnName;
          if (columns.contains(columnName)) {
            properties.add(columnName);
          }
        }
        if (!properties.isEmpty()) {
          final String columnSQL = TextUtils.join(",", properties);

          String insertTableStringBuilder = "INSERT INTO " + tableName + " (" +
            columnSQL +
            ") SELECT " +
            columnSQL +
            " FROM " + tempTableName + ";";
          database.execSQL(insertTableStringBuilder);
          printLog("【Restore data】 to " + tableName);
        }
        database.execSQL("DROP TABLE " + tempTableName);
        printLog("【Drop temp table】" + tempTableName);
      } catch (Exception throwable) {
        Timber.e("【Failed to restore data from temp table 】" + tempTableName);
        UiUtils.handleThrowable(throwable);
      }
    }
  }

  private static List<String> getColumns(Database database, String tableName) {
    List<String> columns = null;
    Cursor cursor = null;
    try {
      cursor = database.rawQuery("SELECT * FROM " + tableName + " limit 0", null);
      if (null != cursor && cursor.getColumnCount() > 0) {
        columns = Arrays.asList(cursor.getColumnNames());
      }
    } catch (Exception throwable) {
      UiUtils.handleThrowable(throwable);
    } finally {
      if (cursor != null) {
        cursor.close();
      }
      if (null == columns) {
        columns = new ArrayList<>();
      }
    }
    return columns;
  }

  private static void printLog(String info) {
    Timber.i(info);
  }
}