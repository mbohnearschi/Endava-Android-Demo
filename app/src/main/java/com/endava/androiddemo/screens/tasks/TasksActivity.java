package com.endava.androiddemo.screens.tasks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import com.endava.androiddemo.application.AppController;
import com.endava.androiddemo.screens.tasks.core.TasksPresenter;
import com.endava.androiddemo.screens.tasks.core.TasksView;
import com.endava.androiddemo.screens.tasks.dagger.DaggerTasksComponent;
import com.endava.androiddemo.screens.tasks.dagger.TasksModule;
import javax.inject.Inject;

public class TasksActivity extends AppCompatActivity {

  @Inject
  TasksView view;

  @Inject
  TasksPresenter presenter;

  public static void start(Context context) {
    Intent intent = new Intent(context, TasksActivity.class);
    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DaggerTasksComponent.builder()
      .appComponent(AppController.getAppComponent())
      .tasksModule(new TasksModule(this))
      .build()
      .inject(this);

    setContentView(view.getView());
    presenter.onCreate();
  }

  @Override
  protected void onResume() {
    super.onResume();
    presenter.onResume();
  }

  @Override
  protected void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }
}