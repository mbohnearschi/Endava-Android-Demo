package com.endava.androiddemo.screens.add.dagger;

import android.app.ProgressDialog;
import com.endava.androiddemo.api.TaskApi;
import com.endava.androiddemo.screens.add.AddActivity;
import com.endava.androiddemo.screens.add.core.AddModel;
import com.endava.androiddemo.screens.add.core.AddPresenter;
import com.endava.androiddemo.screens.add.core.AddView;
import com.endava.androiddemo.utils.rx.RxSchedulers;
import dagger.Module;
import dagger.Provides;
import rx.subscriptions.CompositeSubscription;

@Module
public class AddModule {

  private final AddActivity activity;

  public AddModule(AddActivity activity) {
    this.activity = activity;
  }

  @Provides
  @AddScope
  public AddView view() {
    ProgressDialog progressDialog = new ProgressDialog(activity);
    progressDialog.setCancelable(false);
    return new AddView(activity, progressDialog);
  }

  @Provides
  @AddScope
  public AddPresenter presenter(AddView view, AddModel model, RxSchedulers rxSchedulers) {
    CompositeSubscription compositeSubscription = new CompositeSubscription();
    return new AddPresenter(view, model, compositeSubscription, rxSchedulers);
  }

  @Provides
  @AddScope
  AddModel model(TaskApi taskApi) {
    return new AddModel(activity, taskApi);
  }
}