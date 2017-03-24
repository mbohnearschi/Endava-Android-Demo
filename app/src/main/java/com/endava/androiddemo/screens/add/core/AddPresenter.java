package com.endava.androiddemo.screens.add.core;

import android.support.design.widget.Snackbar;
import com.endava.androiddemo.R;
import com.endava.androiddemo.api.request.AddTaskRequest;
import com.endava.androiddemo.api.response.AddTaskResponse;
import com.endava.androiddemo.utils.StringUtils;
import com.endava.androiddemo.utils.UiUtils;
import com.endava.androiddemo.utils.rx.RxSchedulers;
import com.endava.androiddemo.utils.validation.ValidationResponse;
import com.endava.androiddemo.utils.validation.ValidationUtils;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class AddPresenter implements DatePickerDialog.OnDateSetListener {
  private final AddView view;
  private final AddModel model;
  private final CompositeSubscription subscriptions;
  private final RxSchedulers rxSchedulers;

  public AddPresenter(AddView view, AddModel model, CompositeSubscription subscriptions,
    RxSchedulers rxSchedulers) {
    this.view = view;
    this.model = model;
    this.subscriptions = subscriptions;
    this.rxSchedulers = rxSchedulers;
  }

  public void onCreate() {
    view.setupView(model.getString(R.string.toolbar_add_task));
    subscriptions.add(toolbarNavigationSubscription());
    subscriptions.add(actionItemSelectedSubscription());
    subscriptions.add(inputDateClicksSubscription());
  }

  public void onDestroy() {
    subscriptions.clear();
  }

  @Override
  public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
    String date = dayOfMonth + "." + (monthOfYear + 1) + "." + year;
    this.view.setDate(StringUtils.getDisplayDate(StringUtils.getMillis(date)));
  }

  private Subscription toolbarNavigationSubscription() {
    return view.toolbarNavigation().subscribe(aVoid -> model.finish());
  }

  private Subscription actionItemSelectedSubscription() {
    return view.observeActionItem()
      .filter(actionItem -> actionItem == R.id.addTask)
      .flatMap(actionId -> validateRequest())
      .flatMap(validationResponse -> validateNetwork())
      .doOnNext(hasNetwork -> view.showLoadingDialog(model.getString(R.string.loading_add_task)))
      .flatMap(hasNetwork -> addTask())
      .subscribe(addTaskResponse -> {
        view.hideLoadingDialog();
        if (addTaskResponse.getSuccess()) {
          model.finish();
        } else {
          String errorMessage =
            null == addTaskResponse.getFailReason() ? model.getString(R.string.error_add_task)
              : addTaskResponse.getFailReason();
          UiUtils.showSnackbar(view.getView(), errorMessage, Snackbar.LENGTH_SHORT);
        }
      }, throwable -> {
        view.hideLoadingDialog();
        UiUtils.handleThrowable(throwable);
        UiUtils.showSnackbar(view.getView(), model.getString(R.string.error_add_task), Snackbar.LENGTH_SHORT);
      });
  }

  private Subscription inputDateClicksSubscription() {
    return view.taskDateClicks().subscribe(aVoid -> model.showDatePicker(this));
  }

  private AddTaskRequest getAddTaskRequest() {
    return new AddTaskRequest(view.getTask());
  }

  private Observable<ValidationResponse> validateRequest() {
    return Observable.just(ValidationUtils.validateAddTaskRequest(getAddTaskRequest()))
      .subscribeOn(rxSchedulers.background())
      .observeOn(rxSchedulers.androidUI())
      .doOnNext(validationResponse -> {
        if (!validationResponse.getSuccess()) {
          UiUtils.showSnackbar(view.getView(), validationResponse.getFailReason(), Snackbar.LENGTH_SHORT);
          view.handleErrorCode(validationResponse.getFailCode());
        }
      })
      .filter(ValidationResponse::getSuccess);
  }

  private Observable<Boolean> validateNetwork() {
    return model.networkAvailable()
      .subscribeOn(rxSchedulers.network())
      .observeOn(rxSchedulers.androidUI())
      .doOnNext(networkAvailable -> {
        if (!networkAvailable) {
          UiUtils.showSnackbar(view.getView(), model.getString(R.string.error_no_internet),
            Snackbar.LENGTH_SHORT);
        }
      })
      .filter(networkAvailable -> networkAvailable);
  }

  private Observable<AddTaskResponse> addTask() {
    return model.addTask(getAddTaskRequest())
      .subscribeOn(rxSchedulers.network())
      .observeOn(rxSchedulers.androidUI());
  }
}
