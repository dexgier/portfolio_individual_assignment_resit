package com.example.navigationdrawertaskassignment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.navigationdrawertaskassignment.Fragment.TaskFragment;
import com.example.navigationdrawertaskassignment.Tasks.Task;

public class AddTask extends AppCompatActivity {

    private TextView mTitle;
    private TextView mDescription;
    private TextView mPoints;
    private TextView mResponsible;
    private ImageButton mAddTaskButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        mTitle = findViewById(R.id.titleEditText);
        mDescription = findViewById(R.id.descriptionEditText);
        mPoints = findViewById(R.id.pointsEditText);
        mResponsible = findViewById(R.id.responsibleEditText);
        mAddTaskButton = findViewById(R.id.addTask);

        mAddTaskButton.setOnClickListener(v -> {
            String titleText = mTitle.getText().toString();
            String descriptionText = mDescription.getText().toString();
            String pointsText = mPoints.getText().toString();
            String responsibleText = mResponsible.getText().toString();

            if(!TextUtils.isEmpty(titleText) && !TextUtils.isEmpty(descriptionText) && !TextUtils.isEmpty(pointsText) && !TextUtils.isEmpty(responsibleText)){
                Task task = new Task(titleText,descriptionText, pointsText, responsibleText);
                Intent intent = new Intent();
                intent.putExtra(TaskFragment.ADD_TASK,task);
                setResult(Activity.RESULT_OK,intent);
                finish();
            }else{
                Snackbar.make(v,"fill in all the fields",Snackbar.LENGTH_SHORT);

            }
        });


    }
}
