package com.example.navigationdrawertaskassignment.Fragment;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.navigationdrawertaskassignment.AddTask;
import com.example.navigationdrawertaskassignment.MainActivity;
import com.example.navigationdrawertaskassignment.R;
import com.example.navigationdrawertaskassignment.Tasks.Task;
import com.example.navigationdrawertaskassignment.Tasks.TaskAdapter;
import com.example.navigationdrawertaskassignment.Tasks.TaskViewModel;
import com.example.navigationdrawertaskassignment.database.TaskRepository;

import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class TaskFragment extends Fragment implements RecyclerView.OnItemTouchListener {

    List<Task> mTasks;
    private RecyclerView mRecyclerView;
    private TaskAdapter mTaskAdapter;
    private ViewModel mvModel;
    private TaskRepository taskRepo;
    private HomeFragment homeFrag;

    //private GestureDetector mGestureDetector;
    private ImageButton addTaskButton;
    private ImageButton deleteListButton;
    private GestureDetector mGestureDetector;

    public static final String ADD_TASK = "NewTask";
    public static final int NEW_TASK_CODE = 4321;
    public static final String VIEW_TASK = "ViewTask";
    public static final int VIEW_TASK_CODE = 3421;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task,container,false);

        mRecyclerView = view.findViewById(R.id.taskRecyclerView);
        addTaskButton = view.findViewById(R.id.addTask);
        deleteListButton = view.findViewById(R.id.deleteList);

        mTasks = new ArrayList<>();
        mvModel = ViewModelProviders.of(this).get(TaskViewModel.class);
        ((TaskViewModel) mvModel).getTasks().observe(this, tasks -> {
            mTasks = tasks;
            mTaskAdapter = new TaskAdapter(mTasks);
            mRecyclerView.setAdapter(mTaskAdapter);
            updateUI();
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity().getApplicationContext(), DividerItemDecoration.VERTICAL));
        mRecyclerView.setLayoutManager(layoutManager);

        mGestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        touchHelperHandler();
        mRecyclerView.addOnItemTouchListener(this);

        addTaskButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), AddTask.class);
                startActivityForResult(intent, NEW_TASK_CODE);
            }
        });

        deleteListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAll(mTasks);
            }
        });

        return view;
    }


        public void updateUI() {
        if (mTaskAdapter == null) {
            mTaskAdapter = new TaskAdapter(mTasks);
            mRecyclerView.setAdapter(mTaskAdapter);
        } else {
            mTaskAdapter.updateTasksList(mTasks);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == NEW_TASK_CODE){
            if(resultCode == RESULT_OK){
                Task task = data.getParcelableExtra(TaskFragment.ADD_TASK);
                ((TaskViewModel)mvModel).insert(task);
            }
        }else if(requestCode == VIEW_TASK_CODE){
            if(resultCode == RESULT_OK) {
                Task updateTask = data.getParcelableExtra(TaskFragment.VIEW_TASK);
                ((TaskViewModel)mvModel).update(updateTask);
            }
        }
    }

    private void deleteAll(List<Task> tasksList) {
        if(tasksList.size() > 0) {
            ((TaskViewModel)mvModel).deleteAll();
            Snackbar undoAll = Snackbar.make(mRecyclerView, "All the entries have been deleted from the list!", Snackbar.LENGTH_LONG);
            undoAll.show();
        } else {
            Snackbar noItem = Snackbar.make(mRecyclerView, "There are no current entries in the tasklist!", Snackbar.LENGTH_LONG);
            noItem.show();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
        View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
        if (child != null){
            int adapterPosition = recyclerView.getChildAdapterPosition(child);
            if (mGestureDetector.onTouchEvent(motionEvent)){
                Intent intent = new Intent(getActivity(), AddTask.class);
                intent.putExtra(VIEW_TASK, mTasks.get(adapterPosition));
                startActivityForResult(intent, VIEW_TASK_CODE);
            }
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }

    private void touchHelperHandler() {
        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                int position = (viewHolder.getAdapterPosition());
                final Task STORE_GAME = mTasks.get(position);
                ((TaskViewModel)mvModel).delete(mTasks.get(position));
                mTasks.remove(position);
                mTaskAdapter.notifyItemRemoved(position);

                String points = STORE_GAME.getPoints();
                int addPoints =  Integer.parseInt(points);
                HomeFragment.addPoints(addPoints);


                Snackbar undoSnackBar = Snackbar.make(viewHolder.itemView, "Completed task: " + STORE_GAME.getTitle()+ ", " + STORE_GAME.getPoints() + " points have been added!", Snackbar.LENGTH_LONG);
                undoSnackBar.setAction("Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((TaskViewModel)mvModel).insert(STORE_GAME);
                    }
                });
                undoSnackBar.show();
            }
        });
        touchHelper.attachToRecyclerView(mRecyclerView);
    }
}
