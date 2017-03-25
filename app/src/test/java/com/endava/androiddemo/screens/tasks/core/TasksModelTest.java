package com.endava.androiddemo.screens.tasks.core;

import com.endava.androiddemo.R;
import com.endava.androiddemo.api.TaskApi;
import com.endava.androiddemo.database.domain.Task;
import com.endava.androiddemo.database.repository.TaskRepository;
import com.endava.androiddemo.screens.add.AddActivity;
import com.endava.androiddemo.screens.tasks.TasksActivity;
import com.endava.androiddemo.utils.NetworkUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import rx.Observable;
import rx.observers.TestSubscriber;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ TasksActivity.class, NetworkUtils.class, AddActivity.class })
public class TasksModelTest {

  private TasksActivity activity;
  private TaskApi taskApi;
  private TaskRepository taskRepository;
  private TasksModel model;

  @Before
  public void setUp() throws Exception {
    activity = mock(TasksActivity.class);
    taskApi = mock(TaskApi.class);
    taskRepository = mock(TaskRepository.class);
    model = new TasksModel(activity, taskApi, taskRepository);
  }

  @Test
  public void getString() throws Exception {
    int testResourceId = R.string.app_name;
    String mockString = "Test";
    when(activity.getString(testResourceId)).thenReturn(mockString);

    String string = model.getString(testResourceId);

    verify(activity).getString(testResourceId);
    assertEquals(mockString, string);
  }

  @Test
  public void getTasks() throws Exception {
    List<Task> mockTasks = new ArrayList<>();
    when(taskRepository.getAll()).thenReturn(mockTasks);

    TestSubscriber<List<Task>> testSubscriber = new TestSubscriber<>();
    model.getTasks().subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    testSubscriber.assertReceivedOnNext(Arrays.asList(mockTasks));
  }

  @Test
  public void loadNetworkTasks() throws Exception {
    List<Task> mockTasks = new ArrayList<>();
    when(taskApi.getTasks()).thenReturn(Observable.just(mockTasks));

    TestSubscriber<List<Task>> testSubscriber = new TestSubscriber<>();
    model.loadNetworkTasks().subscribe(testSubscriber);

    verify(taskApi).getTasks();
    testSubscriber.assertNoErrors();
    testSubscriber.assertReceivedOnNext(Arrays.asList(mockTasks));
  }

  @Test
  public void networkAvailable() throws Exception {
    mockStatic(NetworkUtils.class);
    when(NetworkUtils.networkAvailable(activity)).thenReturn(Observable.just(true));

    TestSubscriber<Boolean> testSubscriber = new TestSubscriber<>();
    model.networkAvailable().subscribe(testSubscriber);

    testSubscriber.assertNoErrors();
    testSubscriber.assertReceivedOnNext(Arrays.asList(true));
  }

  @Test
  public void showAddScreen() throws Exception {
    mockStatic(AddActivity.class);

    model.showAddScreen();

    verifyStatic();
  }

  @Test
  public void saveTasks() throws Exception {
    List<Task> mockTasks = new ArrayList<>();
    doNothing().when(taskRepository).deleteAll();
    doNothing().when(taskRepository).saveAll(mockTasks);

    model.saveTasks(mockTasks);

    verify(taskRepository).deleteAll();
    verify(taskRepository).saveAll(mockTasks);
  }
}