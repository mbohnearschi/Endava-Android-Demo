package com.endava.androiddemo.database.repository;

import com.endava.androiddemo.database.dao.DaoSession;
import com.endava.androiddemo.database.dao.TaskDao;
import com.endava.androiddemo.database.domain.Task;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TaskRepositoryTest {

  private DaoSession daoSession;
  private TaskDao dao;
  private TaskRepository repository;

  @Before
  public void setUp() throws Exception {
    daoSession = mock(DaoSession.class);
    dao = mock(TaskDao.class);
    repository = new TaskRepository(daoSession);

    when(daoSession.getTaskDao()).thenReturn(dao);
  }

  @Test
  public void saveAll() throws Exception {
    List<Task> mockTasks = new ArrayList<>();
    doNothing().when(dao).saveInTx(mockTasks);

    repository.saveAll(mockTasks);

    verify(dao).saveInTx(mockTasks);
  }

  @Test
  public void deleteAll() throws Exception {
    doNothing().when(dao).deleteAll();

    repository.deleteAll();

    verify(dao).deleteAll();
  }

  @Test
  public void getAll() throws Exception {
    List<Task> mockTasks = new ArrayList<>();
    when(dao.loadAll()).thenReturn(mockTasks);

    List<Task> tasks = repository.getAll();

    verify(dao).loadAll();
    assertEquals(mockTasks, tasks);
  }
}