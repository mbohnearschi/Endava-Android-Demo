package com.endava.androiddemo.application.builder;

import com.endava.androiddemo.api.TaskApi;
import com.endava.androiddemo.utils.rx.RxSchedulers;
import com.google.gson.Gson;
import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
class RestServiceModule {

  private static final String BASE_URL = "https://endava-demo.herokuapp.com/";

  @AppScope
  @Provides
  public TaskApi taskApi(Gson gson, OkHttpClient okHttpClient, RxSchedulers rxSchedulers) {
    Retrofit retrofit = new Retrofit.Builder().addCallAdapterFactory(
      RxJavaCallAdapterFactory.createWithScheduler(rxSchedulers.network()))
      .addConverterFactory(GsonConverterFactory.create(gson))
      .baseUrl(BASE_URL)
      .client(okHttpClient)
      .build();
    return retrofit.create(TaskApi.class);
  }
}