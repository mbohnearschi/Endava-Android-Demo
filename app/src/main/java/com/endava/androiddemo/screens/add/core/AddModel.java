package com.endava.androiddemo.screens.add.core;

import com.endava.androiddemo.api.TaskApi;
import com.endava.androiddemo.api.request.AddTaskRequest;
import com.endava.androiddemo.api.response.AddTaskResponse;
import com.endava.androiddemo.screens.add.AddActivity;
import com.endava.androiddemo.utils.NetworkUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import java.util.Calendar;
import rx.Observable;

public class AddModel {

  private static final String DATE_PICKER_TAG = "datePickerTag";

  private final AddActivity activity;
  private final TaskApi taskApi;

  public AddModel(AddActivity activity, TaskApi taskApi) {
    this.activity = activity;
    this.taskApi = taskApi;
  }

  void finish() {
    this.activity.finish();
  }

  String getString(int stringResourceId) {
    return activity.getString(stringResourceId);
  }

  Observable<AddTaskResponse> addTask(AddTaskRequest request) {
    return taskApi.addTask(request);
  }

  void showDatePicker(DatePickerDialog.OnDateSetListener dateSetListener) {
    Calendar now = Calendar.getInstance();
    DatePickerDialog datePickerDialog =
      DatePickerDialog.newInstance(dateSetListener, now.get(Calendar.YEAR), now.get(Calendar.MONTH),
        now.get(Calendar.DAY_OF_MONTH));
    datePickerDialog.show(activity.getSupportFragmentManager(), DATE_PICKER_TAG);
  }

  Observable<Boolean> networkAvailable() {
    return NetworkUtils.networkAvailable(activity);
  }
}