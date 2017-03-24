package com.endava.androiddemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import rx.Observable;

public class NetworkUtils {

  private NetworkUtils() {
    //No-OP
  }

  private static boolean isNetworkAvailable(Context context) {
    ConnectivityManager connectivityManager =
      (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
    return activeNetworkInfo != null && activeNetworkInfo.isConnected();
  }

  public static Observable<Boolean> networkAvailable(Context context) {
    return Observable.just(NetworkUtils.isNetworkAvailable(context));
  }
}