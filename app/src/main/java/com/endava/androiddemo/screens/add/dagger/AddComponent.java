package com.endava.androiddemo.screens.add.dagger;

import com.endava.androiddemo.application.builder.AppComponent;
import com.endava.androiddemo.screens.add.AddActivity;
import dagger.Component;

@AddScope
@Component(modules = AddModule.class, dependencies = AppComponent.class)
public interface AddComponent {

  void inject(AddActivity activity);
}