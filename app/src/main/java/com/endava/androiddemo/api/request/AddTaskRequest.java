package com.endava.androiddemo.api.request;

import com.endava.androiddemo.database.domain.Task;

public class AddTaskRequest {

  private Task task;

  public AddTaskRequest(Task task) {
    this.task = task;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(Task task) {
    this.task = task;
  }
}