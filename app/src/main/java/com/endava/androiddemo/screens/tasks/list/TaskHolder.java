package com.endava.androiddemo.screens.tasks.list;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.endava.androiddemo.R;
import com.endava.androiddemo.database.domain.Task;
import com.endava.androiddemo.utils.StringUtils;
import rx.subjects.PublishSubject;

class TaskHolder extends RecyclerView.ViewHolder {

  @BindView(R.id.taskName)
  TextView taskName;
  @BindView(R.id.taskDescription)
  TextView taskDescription;
  @BindView(R.id.taskAssignee)
  TextView taskAssignee;
  @BindView(R.id.taskDate)
  TextView taskDate;

  private View view;

  TaskHolder(View view, PublishSubject<Integer> clickSubject) {
    super(view);
    this.view = view;
    ButterKnife.bind(this, view);
    view.setOnClickListener(item -> clickSubject.onNext(getAdapterPosition()));
  }

  void bind(Task task) {
    taskName.setText(
      TextUtils.isEmpty(task.getName()) ? view.getContext().getString(R.string.label_missing_name)
        : task.getName());

    taskDescription.setText(TextUtils.isEmpty(task.getDescription()) ? view.getContext()
      .getString(R.string.label_missing_description) : task.getDescription());

    taskAssignee.setText(
      TextUtils.isEmpty(task.getAssignee()) ? view.getContext().getString(R.string.label_missing_assignee)
        : task.getAssignee());

    taskDate.setText(null == task.getDate() ? view.getContext().getString(R.string.label_missing_date)
      : StringUtils.getDisplayDate(task.getDate()));
  }
}