package com.endava.androiddemo.database.domain;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class Task {

  @Id(autoincrement = true)
  private Long id;

  private String name;
  private String description;
  private String assignee;
  private Long date;

  @Generated(hash = 1939417873)
  public Task(Long id, String name, String description, String assignee,
          Long date) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.assignee = assignee;
      this.date = date;
  }

  @Generated(hash = 733837707)
  public Task() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getAssignee() {
    return assignee;
  }

  public void setAssignee(String assignee) {
    this.assignee = assignee;
  }

  public Long getDate() {
    return date;
  }

  public void setDate(Long date) {
    this.date = date;
  }
}
