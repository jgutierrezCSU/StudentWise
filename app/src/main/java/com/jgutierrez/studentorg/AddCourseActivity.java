package com.jgutierrez.studentorg;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.jgutierrez.studentorg.model.SchedulesModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class AddCourseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_schedule);
        //setRetainInstance(true);
    }
/*
    SchedulesModel schedulesData;
    CourseModel[] coursesArray;
    LayoutInflater inflater;
    LinearLayout coursesListView;
    TableLayout tableLayout;
    Gson gson = new Gson();
    int REQUEST_CODE = 1;
   // ScheduleFragmentController controller;
    private static String LOG_TAG = "SCHED_FRAGMENT";
   // public static ScheduleFragment _instance;

   public ScheduleFragment() {
        controller = new ScheduleFragmentController(this);
        schedulesData = new SchedulesModel();
    }

    public static ScheduleFragment getInstance() {
        if (_instance == null) {
            _instance = new ScheduleFragment();
        }
        return _instance;

    }


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.activity_show_schedule, container, false);
        schedulesData = new SchedulesModel();
        controller.updateScheduleData();
        Intent intent = new Intent(getContext(), DataSyncService.class);
        getActivity().startService(intent);
        controller.updateData();
        FloatingActionButton scheduleFAB = rootView.findViewById(R.id.schedule_insert_fab);
        scheduleFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddCourseActivity.class);
                intent.putExtra("scheduleData", gson.toJson(schedulesData));
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
        coursesListView = rootView.findViewById(R.id.courses_list);
        tableLayout = rootView.findViewById(R.id.table_schedule);
        return rootView;
    }

    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                String intentString = data.getStringExtra("newCourse");
                Log.d(LOG_TAG, intentString);
                CourseModel model = gson.fromJson(intentString, CourseModel.class);
                controller.addData(model);
            }
        }
    }

    private void setTable() {

        Context context = getContext();
        for (int i = 0; i < 11; i++) {
            if (tableLayout.getChildAt(i + 1) != null)
                tableLayout.removeViewAt(i + 1);
            TableRow tableRow = new TableRow(context);
            tableRow.setWeightSum(6);
            TextView textview = new TextView(context);
            textview.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
            textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textview.setBackground(getResources().getDrawable(R.drawable.border_right_primary));
            textview.setText(String.format("%02d.00", i + 7));
            textview.setHeight(60);
            textview.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            tableRow.addView(textview);

            for (int j = 0; j < 5; j++) {
                TextView textViewColumn = new TextView(context);
                textViewColumn.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
                textViewColumn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                if (schedulesData.getData()[i][j] != null) {
                    textViewColumn.setBackgroundColor(Color.parseColor(schedulesData.getData()[i][j].getColorHex()));
                } else {
                }

//                textViewColumn.setText(String.format("%d%d", j,i+1));
                textViewColumn.setHeight(80);
                textViewColumn.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
                tableRow.addView(textViewColumn);
                textview.setPadding(1, 1, 1, 1);
            }
            tableLayout.addView(tableRow, i + 1);
        }

    }


    public void updateLayout(final Map<String, CourseModel> coursesArray) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                adaptLinearLayout(coursesListView, coursesArray);
            }
        });

    }

    private void adaptLinearLayout(LinearLayout layout, Map<String, CourseModel> coursesArray) {
        layout.removeAllViews();
        Log.d("NEWADAPTOR", gson.toJson(coursesArray));
        int height = 0;
        for (Map.Entry<String, CourseModel> item : coursesArray.entrySet()) {
            View inflated = inflateLayout(item.getKey(), item.getValue(), layout);
            layout.addView(inflated);
            height += inflated.getMeasuredHeight();
        }
        layout.getLayoutParams().height = height;
    }


    private View inflateLayout(final String key, CourseModel model, ViewGroup parent) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View inflated = inflater.inflate(R.layout.component_courses_list_view, parent, false);
        inflated.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                controller.deleteCourseData(key);
                return true;
            }

        });
        TextView courseName = inflated.findViewById(R.id.course_name);
        courseName.setText(String.format("%s %s", model.getCourseId(), model.getCourseName()));
        TextView lecturerName = inflated.findViewById(R.id.lecturer_name);
        lecturerName.setText(String.format("%s", model.getLecturerName()));
        TreeMap<String, List<String>> roomMap = new TreeMap<>();
        for (CourseScheduleItemModel item : model.getSchedules()) {
            if (roomMap.get(item.getClassRoom()) == null) {
                List<String> temp = new ArrayList<>();
                temp.add(item.getTime());
                roomMap.put(item.getClassRoom(), temp);
            } else {
                roomMap.get(item.getClassRoom()).add(item.getTime());
            }
        }
        LinearLayout roomContainer = inflated.findViewById(R.id.room_container);
        for (Map.Entry<String, List<String>> entry : roomMap.entrySet()) {
            boolean first = true;
            String room = "";
            for (String item : entry.getValue()) {
                if (first) {
                    room = room.concat(item);
                    first = false;
                } else {
                    room = room.concat(",");
                    room = room.concat(item);
                }

            }
            TextView textView = new TextView(getContext());
            textView.setText(String.format("%s:%s", room, entry.getKey()));
            roomContainer.addView(textView);

        }
        ImageButton mailIntent = inflated.findViewById(R.id.mail_button);
        if (model.getLecturerEmail() != null && !model.getLecturerEmail().equals("")) {
            final String lecturerMail = model.getLecturerEmail();
            mailIntent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_EMAIL, lecturerMail);
                    intent.setData(Uri.parse(String.format("mailto:%s", lecturerMail)));
                    getContext().startActivity(Intent.createChooser(intent, "Send Email"));
                }
            });
        } else {
            mailIntent.setVisibility(View.GONE);
        }

        View colorFlag = inflated.findViewById(R.id.color_flag);
        colorFlag.setBackgroundColor(Color.parseColor(model.getColorHex()));


        return inflated;
    }

    public void updateScheduleTable(SchedulesModel schedulesModel) {
        this.schedulesData = schedulesModel;
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                setTable();
            }
        });
    }
*/
}