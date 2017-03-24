package com.endava.androiddemo.application.builder;

import android.content.Context;
import com.endava.androiddemo.api.TaskApi;
import com.endava.androiddemo.database.repository.TaskRepository;
import com.endava.androiddemo.utils.rx.RxSchedulers;
import dagger.Component;

@AppScope
@Component(modules = {
  AppModule.class, NetworkModule.class, RestServiceModule.class, GsonModule.class, DataModule.class,
  RxModule.class
})
public interface AppComponent {

  Context context();

  TaskApi taskApi();

  TaskRepository taskRepository();

  RxSchedulers rxSchedulers();
}