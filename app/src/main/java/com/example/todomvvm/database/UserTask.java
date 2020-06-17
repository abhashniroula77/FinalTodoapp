package com.example.todomvvm.database;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Relation;

import java.util.List;

@Entity
public class UserTask {

    @Embedded public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "userTaskId",
            entity = TaskEntry.class



    )
    public List<TaskEntry> taskEntry;

    public UserTask(User user, List<TaskEntry> taskEntry) {
        this.user = user;
        this.taskEntry = taskEntry;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<TaskEntry>  getTaskEntry() {
        return taskEntry;
    }

    public void setTaskEntry(List<TaskEntry> taskEntry) {
        this.taskEntry = taskEntry;
    }
}
