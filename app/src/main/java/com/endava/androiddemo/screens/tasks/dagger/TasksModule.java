package com.endava.androiddemo.screens.tasks.dagger;

import com.endava.androiddemo.api.TaskApi;
import com.endava.androiddemo.database.repository.TaskRepository;
import com.endava.androiddemo.screens.tasks.TasksActivity;
import com.endava.androiddemo.screens.tasks.core.TasksModel;
import com.endava.androiddemo.screens.tasks.core.TasksPresenter;
import com.endava.androiddemo.screens.tasks.core.TasksView;
import com.endava.androiddemo.utils.rx.RxSchedulers;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class TasksModule {

  private final TasksActivity activity;

  public TasksModule(TasksActivity activity) {
    this.activity = activity;
  }

  @Provides
  @TasksScope
  public TasksView view() {
    return new TasksView(activity);
  }

  @Provides
  @TasksScope
  public TasksPresenter presenter(TasksView view, TasksModel model, RxSchedulers rxSchedulers) {
    CompositeSubscription compositeSubscription = new CompositeSubscription();
    return new TasksPresenter(view, model, compositeSubscription, rxSchedulers);
  }

  @Provides
  @TasksScope
  TasksModel model(TaskApi taskApi, TaskRepository taskRepository) {
    return new TasksModel(activity, taskApi, taskRepository);
  }
}