package com.example.navigationdrawertaskassignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.navigationdrawertaskassignment.Fragment.HomeFragment;
import com.example.navigationdrawertaskassignment.Fragment.TaskFragment;
import com.example.navigationdrawertaskassignment.Tasks.Task;

public class AddTask extends AppCompatActivity {

    //class variables
    private TextView mTitle;
    private TextView mDescription;
    private Spinner mPoints;
    private Spinner mResponsible;
    private Task task;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //init stuff
        initButton();
        mTitle = findViewById(R.id.titleEditText);
        mDescription = findViewById(R.id.descriptionEditText);
        mPoints = findViewById(R.id.pointsEditText);
        mResponsible = findViewById(R.id.responsibleEditText);

        //Check if task exists and then set the right values to the fields
        task = getIntent().getParcelableExtra(TaskFragment.VIEW_TASK);
        if (task != null) {
            mTitle.setText(task.getTitle());
            mDescription.setText(task.getDescription());
            mPoints.setSelection(((ArrayAdapter) mPoints.getAdapter()).getPosition(task.getPoints()));
            mResponsible.setSelection(((ArrayAdapter) mResponsible.getAdapter()).getPosition(task.getResponsible()));
            this.setTitle(R.string.title_activity_add_task_update);
        } else {
            this.setTitle(R.string.title_activity_add_task_new);
        }
    }

    //initialize buttons and take care of making a new intent or editing a existing intent.
    private void initButton() {
        ImageButton mAddTaskButton =findViewById(R.id.addTask);
        mAddTaskButton.setOnClickListener(v -> {
            String titleText = mTitle.getText().toString();
            String descriptionText = mDescription.getText().toString();
            String pointsText = mPoints.getSelectedItem().toString();
            String responsibleText = mResponsible.getSelectedItem().toString();

            if (task != null) {
                if (!TextUtils.isEmpty(titleText) && !TextUtils.isEmpty(descriptionText) && !TextUtils.isEmpty(pointsText) && !TextUtils.isEmpty(responsibleText)) {
                    task.setTitle(titleText);
                    task.setDescription(descriptionText);
                    task.setPoints(pointsText);
                    task.setResponsible(responsibleText);

                    Intent resultIntent = new Intent();
                    resultIntent.putExtra(TaskFragment.VIEW_TASK, task);
                    Log.d("Task: ", task.getTitle() + " " + task.getDescription());
                    setResult(Activity.RESULT_OK, resultIntent);
                    finish();
                } else {
                    Toast.makeText(this, "Please fill in all the fields",Toast.LENGTH_SHORT).show();
                }
            } else {
                if(!TextUtils.isEmpty(titleText) && !TextUtils.isEmpty(descriptionText) && !TextUtils.isEmpty(pointsText) && !TextUtils.isEmpty(responsibleText)){
                    Task task = new Task(titleText,descriptionText, pointsText, responsibleText);
                    Intent intent = new Intent();
                    intent.putExtra(TaskFragment.ADD_TASK,task);
                    setResult(Activity.RESULT_OK,intent);
                    finish();
                }else
                    Toast.makeText(this, "Please fill in all the fields",Toast.LENGTH_SHORT).show();
            }
        });


    }
}
