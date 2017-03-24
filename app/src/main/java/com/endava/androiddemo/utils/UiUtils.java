package com.endava.androiddemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import timber.log.Timber;

public class UiUtils {

  private static final String EMPTY_STRING = "";

  private UiUtils() {
    //No-OP
  }

  public static int getColor(@NonNull Context context, @ColorRes int resource) {
    return ContextCompat.getColor(context, resource);
  }

  public static void handleThrowable(Throwable throwable) {
    Timber.e(throwable, throwable.toString());
  }

  public static void showSnackbar(View view, String message, int length) {
    Snackbar.make(view, message, length).setAction("Action", null).show();
  }

  public static void playFailureAnimation(View view) {
    YoYo.with(Techniques.Wobble).duration(700).playOn(view);
  }

  @SuppressWarnings("ConstantConditions")
  public static String getInputText(Object input) {
    if (null == input) {
      return EMPTY_STRING;
    }

    if (input instanceof TextInputLayout) {
      TextInputLayout inputLayout = (TextInputLayout) input;
      return inputLayout.getEditText().getText().toString();
    } else if (input instanceof TextView) {
      TextView textView = (TextView) input;
      return textView.getText().toString();
    }

    return EMPTY_STRING;
  }

  public static Drawable getDrawable(Context context, int drawableId) {
    return ContextCompat.getDrawable(context, drawableId);
  }

  public static void hideKeyboard(Activity activity) {
    View focusedView = activity.getWindow().getCurrentFocus();
    if (focusedView != null && focusedView.isFocused()) {
      InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
      imm.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }
  }

  public static void setupBackToolbar(AppCompatActivity activity, Toolbar toolbar) {
    activity.setSupportActionBar(toolbar);

    if (null != activity.getSupportActionBar()) {
      activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
      activity.getSupportActionBar().setDisplayShowHomeEnabled(true);
    }
  }

  public static void setupSimpleToolbar(AppCompatActivity activity, Toolbar toolbar) {
    activity.setSupportActionBar(toolbar);

    if (null != activity.getSupportActionBar()) {
      activity.getSupportActionBar().setDisplayHomeAsUpEnabled(false);
      activity.getSupportActionBar().setDisplayShowHomeEnabled(false);
    }
  }
}