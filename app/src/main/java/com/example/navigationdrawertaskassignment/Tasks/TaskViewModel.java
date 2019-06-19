package com.example.navigationdrawertaskassignment.Tasks;


import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.navigationdrawertaskassignment.database.TaskRepository;

import java.util.List;

public class TaskViewModel extends AndroidViewModel {

    private TaskRepository mRepository;
    private LiveData<List<Task>> mTasks;

    public TaskViewModel(@NonNull Application application) {
        super(application);
        mRepository = new TaskRepository(application.getApplicationContext());
        mTasks = mRepository.getAllTasks();
    }

    public LiveData<List<Task>> getTasks(){
        return mTasks;
    }

    public void insert(Task task){
        mRepository.insert(task);
    }

    public void update(Task task){
        mRepository.update(task);
    }

    public void delete(Task task){
        mRepository.delete(task);
    }

    public void deleteAll(){
        mRepository.deleteAll(mTasks.getValue());
    }


}
