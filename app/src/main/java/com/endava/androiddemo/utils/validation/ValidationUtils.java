package com.endava.androiddemo.utils.validation;

import android.text.TextUtils;
import com.endava.androiddemo.api.request.AddTaskRequest;
import com.endava.androiddemo.database.domain.Task;

public class ValidationUtils {

  public static final int TASK_NAME_FAIL = 0;
  public static final int TASK_DESCRIPTION_FAIL = 1;
  public static final int TASK_ASSIGNEE_FAIL = 2;
  public static final int TASK_DATE_FAIL = 3;

  private ValidationUtils() {
    //no-op
  }

  public static ValidationResponse validateAddTaskRequest(AddTaskRequest request) {
    ValidationResponse response = new ValidationResponse();

    if (null == request) {
      response.setSuccess(false);
      response.setFailReason("Missing request object!");
      return response;
    }

    if (null == request.getTask()) {
      response.setSuccess(false);
      response.setFailReason("Missing task!");
      return response;
    }

    Task task = request.getTask();
    if (TextUtils.isEmpty(task.getName())) {
      response.setSuccess(false);
      response.setFailReason("Task Name empty!");
      response.setFailCode(TASK_NAME_FAIL);
      return response;
    }

    if (TextUtils.isEmpty(task.getDescription())) {
      response.setSuccess(false);
      response.setFailReason("Task Description empty!");
      response.setFailCode(TASK_DESCRIPTION_FAIL);
      return response;
    }

    if (TextUtils.isEmpty(task.getAssignee())) {
      response.setSuccess(false);
      response.setFailReason("Task Assignee empty!");
      response.setFailCode(TASK_ASSIGNEE_FAIL);
      return response;
    }

    if (null == task.getDate()) {
      response.setSuccess(false);
      response.setFailReason("Task Date empty!");
      response.setFailCode(TASK_DATE_FAIL);
      return response;
    }

    response.setSuccess(true);
    return response;
  }
}
