package com.endava.androiddemo.screens.loading.core;

import com.endava.androiddemo.utils.UiUtils;
import com.endava.androiddemo.utils.rx.RxSchedulers;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class LoadingPresenter {

  private final LoadingModel model;
  private final CompositeSubscription subscriptions;
  private final RxSchedulers rxSchedulers;

  public LoadingPresenter(LoadingModel model, CompositeSubscription subscriptions,
    RxSchedulers rxSchedulers) {
    this.model = model;
    this.subscriptions = subscriptions;
    this.rxSchedulers = rxSchedulers;
  }

  public void onCreate() {
    subscriptions.add(loadTasksSubscription());
  }

  public void onDestroy() {
    subscriptions.clear();
  }

  private Subscription loadTasksSubscription() {
    return model.networkAvailable()
      .doOnNext(networkAvailable -> {
        if (!networkAvailable) {
          model.showTasksScreen();
        }
      })
      .filter(networkAvailable -> networkAvailable)
      .flatMap(networkAvailable -> model.loadNetworkTasks())
      .subscribeOn(rxSchedulers.network())
      .observeOn(rxSchedulers.androidUI())
      .subscribe(tasks -> {
        model.saveTasks(tasks);
        model.showTasksScreen();
      }, throwable -> {
        UiUtils.handleThrowable(throwable);
        model.showTasksScreen();
      });
  }
}
