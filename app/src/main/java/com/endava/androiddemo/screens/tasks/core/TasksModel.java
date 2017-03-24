package com.endava.androiddemo.screens.tasks.core;

import com.endava.androiddemo.api.TaskApi;
import com.endava.androiddemo.database.domain.Task;
import com.endava.androiddemo.database.repository.TaskRepository;
import com.endava.androiddemo.screens.add.AddActivity;
import com.endava.androiddemo.screens.tasks.TasksActivity;
import com.endava.androiddemo.utils.NetworkUtils;
import java.util.List;
import rx.Observable;

public class TasksModel {

  private final TasksActivity activity;
  private final TaskApi taskApi;
  private final TaskRepository taskRepository;

  public TasksModel(TasksActivity activity, TaskApi taskApi, TaskRepository taskRepository) {
    this.activity = activity;
    this.taskApi = taskApi;
    this.taskRepository = taskRepository;
  }

  String getString(int stringResourceId) {
    return activity.getString(stringResourceId);
  }

  List<Task> getTasks() {
    return taskRepository.getAll();
  }

  Observable<List<Task>> loadNetworkTasks() {
    return taskApi.getTasks();
  }

  Observable<Boolean> networkAvailable() {
    return NetworkUtils.networkAvailable(activity);
  }

  void showAddScreen() {
    AddActivity.start(activity);
  }

  void saveTasks(List<Task> tasks) {
    taskRepository.deleteAll();
    taskRepository.saveAll(tasks);
  }
}