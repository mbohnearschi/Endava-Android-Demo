package com.endava.androiddemo.application;

import android.app.Application;
import com.endava.androiddemo.BuildConfig;
import com.endava.androiddemo.application.builder.AppComponent;
import com.endava.androiddemo.application.builder.AppModule;
import com.endava.androiddemo.application.builder.DaggerAppComponent;
import com.squareup.leakcanary.LeakCanary;
import timber.log.Timber;

public class AppController extends Application {

  private static AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
    initComponent();

    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }

    if (LeakCanary.isInAnalyzerProcess(this)) {
      return;
    }

    LeakCanary.install(this);
  }

  public static AppComponent getAppComponent() {
    return appComponent;
  }

  protected void initComponent() {
    appComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
  }
}