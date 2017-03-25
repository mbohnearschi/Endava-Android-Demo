package com.endava.androiddemo.screens.tasks.core;

import com.endava.androiddemo.R;
import com.endava.androiddemo.database.domain.Task;
import com.endava.androiddemo.utils.rx.RxSchedulers;
import com.endava.androiddemo.utils.rx.TestRxSchedulers;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rx.Observable;
import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CompositeSubscription.class)
public class TasksPresenterTest {

  private TasksView view;
  private TasksModel model;
  private CompositeSubscription subscriptions;
  private RxSchedulers rxSchedulers;
  private TasksPresenter presenter;

  @Before
  public void setUp() throws Exception {
    view = mock(TasksView.class);
    model = mock(TasksModel.class);
    subscriptions = mock(CompositeSubscription.class);
    rxSchedulers = new TestRxSchedulers();
    presenter = new TasksPresenter(view, model, subscriptions, rxSchedulers);
  }

  @Test
  public void onCreate() throws Exception {
    String toolbarTasks = "Tasks";
    doNothing().when(view).setupView(toolbarTasks);
    when(model.getString(R.string.toolbar_tasks)).thenReturn(toolbarTasks);
    when(view.addTaskClicks()).thenReturn(Observable.never());
    when(model.getTasks()).thenReturn(Observable.never());
    when(view.refreshCalled()).thenReturn(Observable.never());

    presenter.onCreate();

    verify(view).setupView(toolbarTasks);
    verify(model).getString(R.string.toolbar_tasks);
    verify(view).addTaskClicks();
    verify(model).getTasks();
    verify(view).refreshCalled();
  }

  @Test
  public void onCreateAddTaskClicked() throws Exception {
    String toolbarTasks = "Tasks";
    doNothing().when(view).setupView(toolbarTasks);
    doNothing().when(model).showAddScreen();
    when(model.getString(R.string.toolbar_tasks)).thenReturn(toolbarTasks);
    when(view.addTaskClicks()).thenReturn(Observable.just(null));
    when(model.getTasks()).thenReturn(Observable.never());
    when(view.refreshCalled()).thenReturn(Observable.never());

    presenter.onCreate();

    verify(view).setupView(toolbarTasks);
    verify(model).getString(R.string.toolbar_tasks);
    verify(view).addTaskClicks();
    verify(model).getTasks();
    verify(view).refreshCalled();
    verify(model).showAddScreen();
  }

  @Test
  public void onCreateLoadTasks() throws Exception {
    List<Task> mockTasks = new ArrayList<>();
    String toolbarTasks = "Tasks";
    doNothing().when(view).setupView(toolbarTasks);
    doNothing().when(view).setTasks(mockTasks);
    when(model.getString(R.string.toolbar_tasks)).thenReturn(toolbarTasks);
    when(view.addTaskClicks()).thenReturn(Observable.never());
    when(model.getTasks()).thenReturn(Observable.just(mockTasks));
    when(view.refreshCalled()).thenReturn(Observable.never());

    presenter.onCreate();

    verify(view).setupView(toolbarTasks);
    verify(model).getString(R.string.toolbar_tasks);
    verify(view).addTaskClicks();
    verify(model).getTasks();
    verify(view).refreshCalled();
    verify(view).setTasks(mockTasks);
  }

  @Test
  public void onResume() throws Exception {
    List<Task> mockTasks = new ArrayList<>();
    doNothing().when(subscriptions).add(any(Subscription.class));
    doNothing().when(view).setLoading(any(Boolean.class));
    doNothing().when(model).saveTasks(mockTasks);
    doNothing().when(view).setTasks(mockTasks);
    when(model.networkAvailable()).thenReturn(Observable.just(true));
    when(model.loadNetworkTasks()).thenReturn(Observable.just(mockTasks));

    presenter.onResume();

    verify(subscriptions).add(any(Subscription.class));
    verify(model).networkAvailable();
    verify(view).setLoading(true);
    verify(view).setLoading(false);
    verify(model).saveTasks(mockTasks);
    verify(view).setTasks(mockTasks);
  }

  @Test
  public void onResumeOffline() throws Exception {
    doNothing().when(subscriptions).add(any(Subscription.class));
    doNothing().when(view).setLoading(false);
    when(model.networkAvailable()).thenReturn(Observable.just(false));

    presenter.onResume();

    verify(subscriptions).add(any(Subscription.class));
    verify(model).networkAvailable();
    verify(view).setLoading(false);
  }

  @Test
  public void onDestroy() throws Exception {
    doNothing().when(subscriptions).clear();

    presenter.onDestroy();

    verify(subscriptions).clear();
  }
}