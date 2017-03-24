package com.endava.androiddemo.application.builder;

import android.content.Context;
import dagger.Module;
import dagger.Provides;
import java.io.File;
import javax.inject.Named;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import timber.log.Timber;

@Module
class NetworkModule {

  @AppScope
  @Provides
  OkHttpClient okHttpClient(HttpLoggingInterceptor loggingInterceptor, Cache cache) {
    return new OkHttpClient.Builder().addNetworkInterceptor(loggingInterceptor).cache(cache).build();
  }

  @AppScope
  @Provides
  Cache cache(Context context, @Named("OkHttpCacheDir") String cacheDir,
    @Named("OkHttpCacheSize") int cacheSize) {
    return new Cache(new File(context.getFilesDir(), cacheDir), cacheSize);
  }

  @AppScope
  @Provides
  @Named("OkHttpCacheDir")
  String cacheDir() {
    return "OkHttpCache";
  }

  @AppScope
  @Provides
  @Named("OkHttpCacheSize")
  int cacheSize() {
    return 10 * 1024 * 1024; //10MB cache
  }

  @AppScope
  @Provides
  HttpLoggingInterceptor httpLoggingInterceptor() {
    return new HttpLoggingInterceptor(Timber::i).setLevel(HttpLoggingInterceptor.Level.BODY);
  }
}