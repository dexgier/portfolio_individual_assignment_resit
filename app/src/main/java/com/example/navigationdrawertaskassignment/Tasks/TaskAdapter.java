package com.example.navigationdrawertaskassignment.Tasks;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navigationdrawertaskassignment.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder>{
    private List<Task> mTasks;

    public TaskAdapter(List<Task> mTasks){
        this.mTasks = mTasks;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.grid_cell,viewGroup,false);
        return new TaskViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder taskViewHolder, int i) {
        Task task = mTasks.get(i);
        taskViewHolder.title.setText(task.getTitle());
        taskViewHolder.description.setText(task.getDescription());
        taskViewHolder.points.setText(task.getPoints());
        taskViewHolder.responsible.setText(task.getResponsible());
    }

    @Override
    public int getItemCount() {
        return mTasks.size();
    }

    public void updateTasksList(List<Task> newTasksList) {
        mTasks = newTasksList;
        if(newTasksList != null) {
            this.notifyDataSetChanged();
        }
    }


    public class TaskViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        public TextView description;
        public TextView points;
        public TextView responsible;

        public TaskViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskText);
            description = itemView.findViewById(R.id.descriptionText);
            points = itemView.findViewById(R.id.pointsText);
            responsible = itemView.findViewById(R.id.responsibleText);
        }
    }
}
