package com.endava.androiddemo.screens.add;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import com.endava.androiddemo.R;
import com.endava.androiddemo.application.AppController;
import com.endava.androiddemo.screens.add.core.AddPresenter;
import com.endava.androiddemo.screens.add.core.AddView;
import com.endava.androiddemo.screens.add.dagger.AddModule;
import com.endava.androiddemo.screens.add.dagger.DaggerAddComponent;
import javax.inject.Inject;

public class AddActivity extends AppCompatActivity {

  @Inject
  AddView view;

  @Inject
  AddPresenter presenter;

  public static void start(Context context) {
    Intent intent = new Intent(context, AddActivity.class);
    context.startActivity(intent);
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    DaggerAddComponent.builder()
      .appComponent(AppController.getAppComponent())
      .addModule(new AddModule(this))
      .build()
      .inject(this);

    setContentView(view.getView());
    presenter.onCreate();
  }

  @Override
  protected void onDestroy() {
    presenter.onDestroy();
    super.onDestroy();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.menu_add_task, menu);
    return true;
  }
}
