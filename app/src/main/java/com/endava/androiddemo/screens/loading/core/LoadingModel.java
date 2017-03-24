package com.endava.androiddemo.screens.loading.core;

import com.endava.androiddemo.api.TaskApi;
import com.endava.androiddemo.database.domain.Task;
import com.endava.androiddemo.database.repository.TaskRepository;
import com.endava.androiddemo.screens.loading.LoadingActivity;
import com.endava.androiddemo.screens.tasks.TasksActivity;
import com.endava.androiddemo.utils.NetworkUtils;
import java.util.List;
import rx.Observable;

public class LoadingModel {

  private final LoadingActivity activity;
  private final TaskApi taskApi;
  private final TaskRepository taskRepository;

  public LoadingModel(LoadingActivity activity, TaskApi taskApi, TaskRepository taskRepository) {
    this.activity = activity;
    this.taskApi = taskApi;
    this.taskRepository = taskRepository;
  }

  Observable<Boolean> networkAvailable() {
    return NetworkUtils.networkAvailable(activity);
  }

  Observable<List<Task>> loadNetworkTasks() {
    return taskApi.getTasks();
  }

  void showTasksScreen() {
    TasksActivity.start(activity);
  }

  void saveTasks(List<Task> tasks) {
    taskRepository.deleteAll();
    taskRepository.saveAll(tasks);
  }
}
