package com.endava.androiddemo.utils.rx;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AppRxSchedulers implements RxSchedulers {

  private static final Executor NETWORK_EXECUTOR = Executors.newCachedThreadPool();
  private static final Scheduler NETWORK_SCHEDULER = Schedulers.from(NETWORK_EXECUTOR);
  private static final Executor BACKGROUND_EXECUTOR = Executors.newCachedThreadPool();
  private static final Scheduler BACKGROUND_SCHEDULER = Schedulers.from(BACKGROUND_EXECUTOR);

  @Override
  public Scheduler androidUI() {
    return AndroidSchedulers.mainThread();
  }

  @Override
  public Scheduler io() {
    return Schedulers.io();
  }

  @Override
  public Scheduler computation() {
    return Schedulers.computation();
  }

  @Override
  public Scheduler network() {
    return NETWORK_SCHEDULER;
  }

  @Override
  public Scheduler background() {
    return BACKGROUND_SCHEDULER;
  }

  @Override
  public Scheduler immediate() {
    return Schedulers.immediate();
  }
}