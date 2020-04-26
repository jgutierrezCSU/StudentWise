package com.jgutierrez.studentorg;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

import androidx.annotation.ColorInt;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.jgutierrez.studentorg.model.CourseModel;
import com.jgutierrez.studentorg.model.CourseScheduleItemModel;
import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;


import java.util.Arrays;

public class AddCourseActivity extends BaseActivity {
    String ACTIVITY_TITLE ="Add New Schedule";
    String NO_VALUE_ERROR = "No Value";
    ColorPicker cp;
    int selectedColor;
    String scheduleString;
    int REQUEST_CODE = 11;

    CourseModel model;
    ImageView checkedIcon;
    ImageView cancelIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        model = new CourseModel();
        setContentView(R.layout.activity_add_course);
        setToolbar(ACTIVITY_TITLE);
        scheduleString = getIntent().getStringExtra("scheduleData");
        ImageButton colorFlagButton = findViewById(R.id.button_select_color);
        colorFlagButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showColorPicker();
            }
        });
        cp = new ColorPicker(AddCourseActivity.this, 0, 0, 0);
        checkedIcon = findViewById(R.id.check_icon);
        cancelIcon = findViewById(R.id.cancel_icon);
        ImageButton scheduleButton = findViewById(R.id.button_select_schedule);
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSelectScheduleActivity();
            }
        });

        Button buttonAddCourse = findViewById(R.id.button_add_course);
        buttonAddCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveCourse();
            }
        });


    }



    private void showSelectScheduleActivity() {
        Intent intent = new Intent(this, SelectScheduleActivity.class);
        intent.putExtra("scheduleData", scheduleString);
        startActivityForResult(intent, REQUEST_CODE);
    }

    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                // A contact was picked.  Here we will just display it
                // to the user.
                String intentString = data.getStringExtra("scheduleList");
                CourseScheduleItemModel[] scheduleItemModel = gson.fromJson(intentString, CourseScheduleItemModel[].class);
                if(scheduleItemModel.length == 0) {
                    cancelIcon.setVisibility(View.VISIBLE);
                    checkedIcon.setVisibility(View.GONE);
                } else {
                    model.setSchedules(Arrays.asList(scheduleItemModel));
                    cancelIcon.setVisibility(View.GONE);
                    checkedIcon.setVisibility(View.VISIBLE);
                }

            }
        }
    }
    private void showColorPicker() {
        cp.show();
        cp.setCallback(new ColorPickerCallback() {
            @Override
            public void onColorChosen(@ColorInt int color) {
                selectedColor = color;
                cp.dismiss();
                ConstraintLayout colorSample = findViewById(R.id.color_sample);
                colorSample.setBackgroundColor(color);
                model.setColorHex(String.format("#%06X", (0xFFFFFF & color)));
            }
        });
    }

    boolean checkCourseId() {
        EditText courseId = findViewById(R.id.course_id);
        String data1=courseId.getText().toString();
        Log.d("xxxID",data1);
        if(courseId.getText() != null && !courseId.getText().toString().equals("")) {
            model.setCourseId(courseId.getText().toString());
            return true;
        } else {
            courseId.setError(NO_VALUE_ERROR);
            return false;
        }
    }

    boolean checkCourseName() {
        EditText courseName = findViewById(R.id.course_name);
        String data2=courseName.getText().toString();
        Log.d("xxxCouName",data2);
        if(courseName.getText() != null && !courseName.getText().toString().equals("")) {
            model.setCourseName(courseName.getText().toString());
            return true;
        } else {
            courseName.setError(NO_VALUE_ERROR);
            return false;
        }
    }

    boolean checkLecturerName() {
        EditText lecturerName = findViewById(R.id.lecturer_name);
        String data3=lecturerName.getText().toString();
        Log.d("xxxLecName",data3);
        if(lecturerName.getText() != null && !lecturerName.getText().toString().equals("")) {
            model.setLecturerName(lecturerName.getText().toString());
            return true;
        } else {
            lecturerName.setError(NO_VALUE_ERROR);
            return false;
        }
    }

    boolean checkLecturerEmail() {
        EditText lecturerEmail = findViewById(R.id.lecturer_email);
        if(lecturerEmail.getText() != null) {
            model.setLecturerEmail(lecturerEmail.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    boolean checkLecturerPhone() {
        EditText lecturerPhone = findViewById(R.id.lecturer_phone);
        String data4=lecturerPhone.getText().toString();
        Log.d("xxxPhone",data4);
        if(lecturerPhone.getText() != null) {
            model.setLecturerPhone(lecturerPhone.getText().toString());
            return true;
        } else {
            return false;
        }
    }

    boolean checkCourseSchedule() {
        if(model.getSchedules() != null && model.getSchedules().size() != 0) {
            cancelIcon.setVisibility(View.GONE);
            checkedIcon.setVisibility(View.VISIBLE);
            return true;
        } else {
            cancelIcon.setVisibility(View.VISIBLE);
            checkedIcon.setVisibility(View.GONE);
            return false;

        }
    }

    boolean checkCourseColorFlag() {
        if(model.getColorHex() != null && !model.getColorHex().equals("")) {
            return true;
        } else {
            return false;

        }
    }

    boolean checkComplete() {
        boolean isComplete = true;
        checkLecturerEmail();
        checkLecturerPhone();
        isComplete = isComplete && checkCourseId();
        isComplete = isComplete && checkCourseName();
        isComplete = isComplete && checkCourseColorFlag();
        isComplete = isComplete && checkCourseSchedule();
        isComplete = isComplete && checkLecturerName();
        if (isComplete == true){
            Log.d("xxx","Good");
        }

        return isComplete;
    }
    void saveCourse() {
        if(checkComplete()) {
            Intent intent = new Intent();
            intent.putExtra("newCourse", gson.toJson(model));

            Log.d("xxxModel", String.valueOf(model));
            setResult(RESULT_OK, intent);
            finish();
        }
    }
}
