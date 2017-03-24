package com.endava.androiddemo.screens.add.core;

import android.app.ProgressDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.endava.androiddemo.R;
import com.endava.androiddemo.database.domain.Task;
import com.endava.androiddemo.screens.add.AddActivity;
import com.endava.androiddemo.utils.StringUtils;
import com.endava.androiddemo.utils.UiUtils;
import com.jakewharton.rxbinding.support.v7.widget.RxToolbar;
import com.jakewharton.rxbinding.view.RxView;
import rx.Observable;

import static com.endava.androiddemo.utils.UiUtils.getInputText;
import static com.endava.androiddemo.utils.UiUtils.playFailureAnimation;
import static com.endava.androiddemo.utils.validation.ValidationUtils.TASK_ASSIGNEE_FAIL;
import static com.endava.androiddemo.utils.validation.ValidationUtils.TASK_DATE_FAIL;
import static com.endava.androiddemo.utils.validation.ValidationUtils.TASK_DESCRIPTION_FAIL;
import static com.endava.androiddemo.utils.validation.ValidationUtils.TASK_NAME_FAIL;

public class AddView {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.taskName)
  TextInputLayout taskName;
  @BindView(R.id.taskDescription)
  TextInputLayout taskDescription;
  @BindView(R.id.taskAssignee)
  TextInputLayout taskAssignee;
  @BindView(R.id.taskDate)
  TextView taskDate;

  private final View view;
  private final AddActivity activity;
  private final ProgressDialog progressDialog;

  public AddView(AddActivity activity, ProgressDialog progressDialog) {
    this.activity = activity;
    this.progressDialog = progressDialog;
    FrameLayout frameLayout = new FrameLayout(activity);
    frameLayout.setLayoutParams(
      new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    this.view = LayoutInflater.from(activity).inflate(R.layout.activity_add, frameLayout, true);

    ButterKnife.bind(this, view);
  }

  public View getView() {
    return this.view;
  }

  void setupView(String toolbarTitle) {
    toolbar.setTitle(toolbarTitle);
    UiUtils.setupBackToolbar(activity, toolbar);
  }

  void showLoadingDialog(String loadingText) {
    progressDialog.setMessage(loadingText);
    progressDialog.show();
  }

  void hideLoadingDialog() {
    progressDialog.hide();
  }

  Observable<Void> toolbarNavigation() {
    return RxToolbar.navigationClicks(toolbar);
  }

  Observable<Integer> observeActionItem() {
    return RxToolbar.itemClicks(toolbar).map(MenuItem::getItemId);
  }

  Observable<Void> taskDateClicks() {
    return RxView.clicks(taskDate);
  }

  void setDate(String date) {
    this.taskDate.setText(date);
  }

  Task getTask() {
    Task task = new Task();

    task.setName(getInputText(taskName));
    task.setDescription(getInputText(taskDescription));
    task.setAssignee(getInputText(taskAssignee));
    task.setDate(StringUtils.getDisplayMillis(getInputText(taskDate)));

    return task;
  }

  void handleErrorCode(Integer errorCode) {
    if (null == errorCode) {
      return;
    }

    switch (errorCode) {
      case TASK_NAME_FAIL:
        playFailureAnimation(taskName);
        break;

      case TASK_DESCRIPTION_FAIL:
        playFailureAnimation(taskDescription);
        break;

      case TASK_ASSIGNEE_FAIL:
        playFailureAnimation(taskAssignee);
        break;

      case TASK_DATE_FAIL:
        playFailureAnimation(taskDate);
        break;

      default:
        //no-op
        break;
    }
  }
}