package com.endava.androiddemo.screens.loading.dagger;

import com.endava.androiddemo.application.builder.AppComponent;
import com.endava.androiddemo.screens.loading.LoadingActivity;
import dagger.Component;

@LoadingScope
@Component(modules = LoadingModule.class, dependencies = AppComponent.class)
public interface LoadingComponent {

  void inject(LoadingActivity activity);
}