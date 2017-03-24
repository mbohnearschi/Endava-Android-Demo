package com.endava.androiddemo.screens.tasks.core;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.endava.androiddemo.R;
import com.endava.androiddemo.database.domain.Task;
import com.endava.androiddemo.screens.tasks.TasksActivity;
import com.endava.androiddemo.screens.tasks.list.TaskAdapter;
import com.endava.androiddemo.utils.UiUtils;
import com.getbase.floatingactionbutton.FloatingActionButton;
import com.jakewharton.rxbinding.support.v4.widget.RxSwipeRefreshLayout;
import com.jakewharton.rxbinding.view.RxView;
import java.util.List;
import rx.Observable;

public class TasksView {

  @BindView(R.id.toolbar)
  Toolbar toolbar;
  @BindView(R.id.list)
  RecyclerView list;
  @BindView(R.id.swipeRefreshLayout)
  SwipeRefreshLayout swipeRefreshLayout;
  @BindView(R.id.addTask)
  FloatingActionButton addTask;

  private View view;
  private TasksActivity activity;
  private TaskAdapter adapter;

  public TasksView(TasksActivity activity) {
    this.activity = activity;
    FrameLayout frameLayout = new FrameLayout(activity);
    frameLayout.setLayoutParams(
      new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
    view = LayoutInflater.from(activity).inflate(R.layout.activity_tasks, frameLayout, true);

    ButterKnife.bind(this, view);

    adapter = new TaskAdapter();
    list.setAdapter(adapter);
    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(activity);
    list.setLayoutManager(mLayoutManager);
  }

  public View getView() {
    return this.view;
  }

  void setLoading(boolean showLoading) {
    swipeRefreshLayout.setRefreshing(showLoading);
  }

  void setupView(String toolbarTitle) {
    toolbar.setTitle(toolbarTitle);
    UiUtils.setupSimpleToolbar(activity, toolbar);
  }

  void setTasks(List<Task> tasks) {
    adapter.swapData(tasks);
  }

  Observable<Void> refreshCalled() {
    return RxSwipeRefreshLayout.refreshes(swipeRefreshLayout);
  }

  Observable<Void> addTaskClicks() {
    return RxView.clicks(addTask);
  }
}