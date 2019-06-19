package com.example.navigationdrawertaskassignment.Fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.navigationdrawertaskassignment.AddTask;
import com.example.navigationdrawertaskassignment.R;
import com.example.navigationdrawertaskassignment.Tasks.Task;
import com.example.navigationdrawertaskassignment.Tasks.TaskAdapter;
import com.example.navigationdrawertaskassignment.Tasks.TaskViewModel;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TaskFragment extends Fragment {

    List<Task> mTasks;
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private ViewModel mvModel;
    //    private GestureDetector mGestureDetector;
    private ImageButton addTaskButton;
    private GestureDetector mGestureDetector;
    public static final String ADD_TASK = "NewTask";
    public static final int NewTaskCode = 4321;
    public static final String VIEW_TASK = "ViewTask";
    public static final int ViewTaskCode = 3421;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task,container,false);

        mRecyclerView = (RecyclerView) view.findViewById(R.id.taskRecyclerView);
        addTaskButton = view.findViewById(R.id.addTask);
        mTasks = new ArrayList<>();
        mvModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        ((TaskViewModel) mvModel).getTasks().observe(this, tasks -> {
            mTasks = tasks;
            mTaskAdapter = new TaskAdapter(mTasks);
            mRecyclerView.setAdapter(mTaskAdapter);
            updateUI();
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTask.class);
                startActivityForResult(intent, NewTaskCode);
            }
        });
        return view;    }


        public void updateUI() {
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(mTasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NewTaskCode){
            if(resultCode == RESULT_OK){
                Task task = data.getParcelableExtra(TaskFragment.ADD_TASK);
                ((TaskViewModel)mvModel).insert(task);
                updateUI();
            }
        }
    }
}
