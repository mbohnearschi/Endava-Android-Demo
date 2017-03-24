package com.endava.androiddemo.screens.tasks.core;

import com.endava.androiddemo.R;
import com.endava.androiddemo.utils.UiUtils;
import com.endava.androiddemo.utils.rx.RxSchedulers;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class TasksPresenter {

  private final TasksView view;
  private final TasksModel model;
  private final CompositeSubscription subscriptions;
  private final RxSchedulers rxSchedulers;

  public TasksPresenter(TasksView view, TasksModel model, CompositeSubscription subscriptions,
    RxSchedulers rxSchedulers) {
    this.view = view;
    this.model = model;
    this.subscriptions = subscriptions;
    this.rxSchedulers = rxSchedulers;
  }

  public void onCreate() {
    view.setupView(model.getString(R.string.toolbar_tasks));
    subscriptions.add(addTaskClicksSubscription());
    subscriptions.add(loadTasksSubscription());
    subscriptions.add(refreshCalledSubscription());
  }

  public void onResume() {
    subscriptions.add(loadNetworkTasksSubscription());
  }

  public void onDestroy() {
    subscriptions.clear();
  }

  private Subscription addTaskClicksSubscription() {
    return view.addTaskClicks().subscribe(aVoid -> model.showAddScreen());
  }

  private Subscription loadTasksSubscription() {
    return Observable.just(model.getTasks())
      .subscribeOn(rxSchedulers.background())
      .observeOn(rxSchedulers.androidUI())
      .retry()
      .subscribe(view::setTasks, UiUtils::handleThrowable);
  }

  private Subscription loadNetworkTasksSubscription() {
    return model.networkAvailable()
      .doOnNext(view::setLoading)
      .filter(networkAvailable -> networkAvailable)
      .observeOn(rxSchedulers.network())
      .flatMap(networkAvailable -> model.loadNetworkTasks())
      .observeOn(rxSchedulers.androidUI())
      .subscribe(tasks -> {
        view.setLoading(false);
        model.saveTasks(tasks);
        view.setTasks(tasks);
      }, throwable -> {
        UiUtils.handleThrowable(throwable);
        view.setLoading(false);
      });
  }

  private Subscription refreshCalledSubscription() {
    return view.refreshCalled().subscribe(aVoid -> subscriptions.add(loadNetworkTasksSubscription()));
  }
}