package com.endava.androiddemo.database.repository;

import com.endava.androiddemo.database.dao.DaoSession;
import com.endava.androiddemo.database.domain.Task;
import java.util.List;

public class TaskRepository {

  private final DaoSession daoSession;

  public TaskRepository(DaoSession daoSession) {
    this.daoSession = daoSession;
  }

  public void saveAll(List<Task> incomes) {
    this.daoSession.getTaskDao().saveInTx(incomes);
  }

  public void deleteAll() {
    this.daoSession.getTaskDao().deleteAll();
  }

  public List<Task> getAll() {
    return this.daoSession.getTaskDao().loadAll();
  }
}
