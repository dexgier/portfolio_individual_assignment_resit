package com.example.navigationdrawertaskassignment.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.navigationdrawertaskassignment.Tasks.Task;

import java.util.List;

@Dao
public interface TaskDAO {

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);

    @Delete
    void delete(List<Task> tasks);

    @Update
    void update(Task task);

    @Query("SELECT * from taskTable")
    LiveData<List<Task>> getAllTasks();
}
