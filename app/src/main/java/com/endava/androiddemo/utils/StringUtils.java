package com.endava.androiddemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class StringUtils {

  private StringUtils() {
    //no-op
  }

  public static Long getMillis(String stringDate) {
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.US);
      Date date = dateFormat.parse(stringDate);

      return date.getTime();
    } catch (Exception throwable) {
      UiUtils.handleThrowable(throwable);
      return null;
    }
  }

  public static Long getDisplayMillis(String stringDate) {
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.US);
      Date date = dateFormat.parse(stringDate);

      return date.getTime();
    } catch (Exception throwable) {
      UiUtils.handleThrowable(throwable);
      return null;
    }
  }

  public static String getDisplayDate(Long millis) {
    try {
      SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
      return dateFormat.format(millis);
    } catch (Exception throwable) {
      UiUtils.handleThrowable(throwable);
      return null;
    }
  }
}
