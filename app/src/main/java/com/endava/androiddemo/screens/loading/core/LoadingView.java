package com.endava.androiddemo.screens.loading.core;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import butterknife.ButterKnife;
import com.endava.androiddemo.R;
import com.endava.androiddemo.screens.loading.LoadingActivity;

public class LoadingView {

  private View view;

  public LoadingView(LoadingActivity activity) {
    FrameLayout frameLayout = new FrameLayout(activity);
    frameLayout.setLayoutParams(
      new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    view = LayoutInflater.from(activity).inflate(R.layout.activity_loading, frameLayout, true);

    ButterKnife.bind(this, view);
  }

  public View getView() {
    return this.view;
  }
}