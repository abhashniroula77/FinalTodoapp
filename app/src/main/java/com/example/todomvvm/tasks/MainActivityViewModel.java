package com.example.todomvvm.tasks;

import android.app.Application;
import android.view.View;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.todomvvm.database.AppDatabase;
import com.example.todomvvm.database.Repository;
import com.example.todomvvm.database.TaskEntry;
import com.example.todomvvm.database.UserTask;

import java.util.List;

public class MainActivityViewModel extends AndroidViewModel {



    Repository repository;

   private  LiveData<List<TaskEntry>> tasks;
   private LiveData<List<UserTask>> userTasks;



    public  MainActivityViewModel(Application application){
        super(application);
        AppDatabase database = AppDatabase.getInstance(application);
        repository = new Repository(database);
        tasks = repository.getTasks();
        userTasks = repository.getUserTask();
    }

    public LiveData<List<TaskEntry>> getTasks(){
        return tasks;
    }

    public LiveData<List<UserTask> >getUserTask(){return userTasks;}

    public void deleteTask(TaskEntry task){
        repository.deleteTask(task);
    }

    public void deleteAllNotes()
    {
        repository.deleteAllNotes();
    }

}
