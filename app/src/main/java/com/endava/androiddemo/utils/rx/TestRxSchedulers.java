package com.endava.androiddemo.utils.rx;

import rx.Scheduler;
import rx.schedulers.Schedulers;

public class TestRxSchedulers implements RxSchedulers {

  @Override
  public Scheduler androidUI() {
    return Schedulers.immediate();
  }

  @SuppressWarnings("PMD.ShortMethodName")
  @Override
  public Scheduler io() {
    return Schedulers.immediate();
  }

  @Override
  public Scheduler computation() {
    return Schedulers.immediate();
  }

  @Override
  public Scheduler background() {
    return Schedulers.immediate();
  }

  @Override
  public Scheduler network() {
    return Schedulers.immediate();
  }

  @Override
  public Scheduler immediate() {
    return Schedulers.immediate();
  }
}