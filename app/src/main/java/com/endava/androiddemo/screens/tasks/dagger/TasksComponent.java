package com.endava.androiddemo.screens.tasks.dagger;

import com.endava.androiddemo.application.builder.AppComponent;
import com.endava.androiddemo.screens.tasks.TasksActivity;
import dagger.Component;

@TasksScope
@Component(modules = TasksModule.class, dependencies = AppComponent.class)
public interface TasksComponent {

  void inject(TasksActivity activity);
}