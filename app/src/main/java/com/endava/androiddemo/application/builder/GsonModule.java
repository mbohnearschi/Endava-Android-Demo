package com.endava.androiddemo.application.builder;

import com.fatboyindustrial.gsonjodatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dagger.Module;
import dagger.Provides;

@Module
class GsonModule {

  @AppScope
  @Provides
  Gson gson() {
    GsonBuilder builder = new GsonBuilder();
    Converters.registerAll(builder);
    return builder.create();
  }
}