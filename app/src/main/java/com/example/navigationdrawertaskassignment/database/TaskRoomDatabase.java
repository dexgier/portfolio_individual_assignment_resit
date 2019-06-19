package com.example.navigationdrawertaskassignment.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.navigationdrawertaskassignment.Tasks.Task;

@Database(entities = {Task.class}, version = 1, exportSchema = false)
public abstract class TaskRoomDatabase extends RoomDatabase {

    private final static String NAME_DATBASE = "taskDatabase";
    public abstract TaskDAO dao();
    private static volatile TaskRoomDatabase INSTANCE;

    public static TaskRoomDatabase getDatabase(final Context context){
        if(INSTANCE == null){
            synchronized (TaskRoomDatabase.class){
                if(INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TaskRoomDatabase.class, NAME_DATBASE)
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}