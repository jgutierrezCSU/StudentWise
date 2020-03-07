package com.jgutierrez.studentorg.controllers;

import android.content.Intent;
import android.os.Build;
import android.util.ArrayMap;

import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.jgutierrez.studentorg.firebaseService.FirebaseDatabaseService;
import com.jgutierrez.studentorg.fragments.ScheduleFragment;
import com.jgutierrez.studentorg.model.CourseModel;
import com.jgutierrez.studentorg.model.SchedulesItemModel;
import com.jgutierrez.studentorg.model.SchedulesModel;
import com.jgutierrez.studentorg.services.DataSyncService;


import java.util.Map;
import java.util.TreeMap;



public class ScheduleFragmentController {
    Map<String, CourseModel> coursesData;
    DatabaseReference coursesDataRef;
    ScheduleFragment fragment;
    DatabaseReference scheduleDataRef;
    SchedulesModel schedulesData;

    Gson gson;
    public ScheduleFragmentController(ScheduleFragment fragment) {
        this.fragment = fragment;
        coursesDataRef = FirebaseDatabaseService.getInstance().child("courses");
        scheduleDataRef = FirebaseDatabaseService.getInstance().child("schedules");
        gson = new Gson();

        updateData();
        updateScheduleData();
    }

    public void updateData() {
        coursesDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                coursesData = new TreeMap<>();
                if(dataSnapshot.getValue() != null) {
                   for(DataSnapshot item : dataSnapshot.getChildren()) {
                       coursesData.put(item.getKey(),item.getValue(CourseModel.class));
                   }
                   fragment.updateLayout(coursesData);
                    Intent intent = new Intent(fragment.getContext(), DataSyncService.class);
                    fragment.getActivity().startService(intent);

                }

            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onCancelled(DatabaseError databaseError) {
                coursesData = new ArrayMap<>();
                fragment.updateLayout(coursesData);

            }
        });
    }

    public void addData(CourseModel model) {
        coursesDataRef.push().setValue(model);
        updateData();

    }

    public void flushData() {
        coursesData = new TreeMap<>();
        fragment.updateLayout(coursesData);
        Intent intent = new Intent(fragment.getContext(), DataSyncService.class);
        fragment.getActivity().startService(intent);
        schedulesData = new SchedulesModel();
        fragment.updateScheduleTable(schedulesData);

    }

    public void updateScheduleData() {
        scheduleDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                schedulesData = new SchedulesModel();
                for(DataSnapshot i : dataSnapshot.getChildren()) {
                    for(DataSnapshot j : i.getChildren()) {
                        schedulesData.getData()[Integer.parseInt(i.getKey())][Integer.parseInt(j.getKey())] = j.getValue(SchedulesItemModel.class);
                    }

                }
                fragment.updateScheduleTable(schedulesData);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    public void deleteCourseData(String key) {
        coursesDataRef.child(key).removeValue();
        updateData();
        flushData();
    }


}
