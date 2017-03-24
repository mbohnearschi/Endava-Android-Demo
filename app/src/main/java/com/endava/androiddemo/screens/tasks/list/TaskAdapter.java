package com.endava.androiddemo.screens.tasks.list;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import com.endava.androiddemo.R;
import com.endava.androiddemo.database.domain.Task;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.subjects.PublishSubject;

public class TaskAdapter extends RecyclerView.Adapter<TaskHolder> {

  private final PublishSubject<Integer> itemClicks = PublishSubject.create();

  private List<Task> tasks = new ArrayList<>();

  public void swapData(List<Task> tasks) {
    this.tasks.clear();
    this.tasks.addAll(tasks);
    notifyDataSetChanged();
  }

  public Observable<Integer> observeClicks() {
    return itemClicks;
  }

  public Task getTask(int position) {
    return tasks.get(position);
  }

  @Override
  public TaskHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    return new TaskHolder(inflater.inflate(R.layout.item_task, parent, false), itemClicks);
  }

  @Override
  public void onBindViewHolder(TaskHolder holder, int position) {
    holder.bind(tasks.get(position));
  }

  @Override
  public int getItemCount() {
    return tasks.size();
  }
}