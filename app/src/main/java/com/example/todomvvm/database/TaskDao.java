package com.example.todomvvm.database;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("select * from task where userTaskId = :userTaskId order by priority")
    LiveData<List<TaskEntry>> loadAllTasks(int userTaskId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTask(TaskEntry task);

    @Update
    void update(TaskEntry task);

    @Delete
    void deleteTask(TaskEntry task);

    @Query("DELETE FROM task")
    void deleteAllNotes();




    @Query("Select * from task where id =:taskId")
    LiveData<TaskEntry> loadTAskById(int taskId);




}
