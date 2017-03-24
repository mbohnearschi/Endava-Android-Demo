package com.endava.androiddemo.application.builder;

import com.endava.androiddemo.utils.rx.AppRxSchedulers;
import com.endava.androiddemo.utils.rx.RxSchedulers;
import dagger.Module;
import dagger.Provides;

@Module
class RxModule {

  @AppScope
  @Provides
  RxSchedulers provideRxSchedulers() {
    return new AppRxSchedulers();
  }
}