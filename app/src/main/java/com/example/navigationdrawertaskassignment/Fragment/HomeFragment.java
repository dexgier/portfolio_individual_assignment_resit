package com.example.navigationdrawertaskassignment.Fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.navigationdrawertaskassignment.R;

public class HomeFragment extends Fragment {

    private TextView pointsTextView;
    public static int totalPoints = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);

        pointsTextView = view.findViewById(R.id.pointsEdit);
        pointsTextView.setText(String.valueOf(totalPoints));
        return view;
    }


    public static void addPoints(int points) {
        totalPoints += points;
    }
}
