package com.endava.androiddemo.screens.loading.dagger;

import com.endava.androiddemo.api.TaskApi;
import com.endava.androiddemo.database.repository.TaskRepository;
import com.endava.androiddemo.screens.loading.LoadingActivity;
import com.endava.androiddemo.screens.loading.core.LoadingModel;
import com.endava.androiddemo.screens.loading.core.LoadingPresenter;
import com.endava.androiddemo.screens.loading.core.LoadingView;
import com.endava.androiddemo.utils.rx.RxSchedulers;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class LoadingModule {

  private final LoadingActivity activity;

  public LoadingModule(LoadingActivity activity) {
    this.activity = activity;
  }

  @Provides
  @LoadingScope
  public LoadingView view() {
    return new LoadingView(activity);
  }

  @Provides
  @LoadingScope
  public LoadingPresenter presenter(LoadingModel model, RxSchedulers rxSchedulers) {
    CompositeSubscription compositeSubscription = new CompositeSubscription();
    return new LoadingPresenter(model, compositeSubscription, rxSchedulers);
  }

  @Provides
  @LoadingScope
  LoadingModel model(TaskApi taskApi, TaskRepository taskRepository) {
    return new LoadingModel(activity, taskApi, taskRepository);
  }
}
