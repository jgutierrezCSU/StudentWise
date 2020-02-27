package com.jgutierrez.studentorg;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.google.gson.reflect.TypeToken;
import com.jgutierrez.studentorg.model.CourseScheduleItemModel;
import com.jgutierrez.studentorg.model.SchedulesModel;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

public class SelectScheduleActivity extends BaseActivity {
    String ACTIVITY_TITLE = "Select Schedule";
    String scheduleString;
    SchedulesModel scheduleData;
    boolean[][] scheduleStatus;
    Map<Integer, CourseScheduleItemModel> scheduleDataMap;
    Map<Integer, TableRow> scheduleRowMap;
    TableLayout selectedTable;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_schedule);
        scheduleString = getIntent().getStringExtra("scheduleData");
        scheduleData = gson.fromJson(scheduleString, SchedulesModel.class);
        setToolbar(ACTIVITY_TITLE);
        scheduleStatus = new boolean[13][8];
        scheduleDataMap = new TreeMap<>();
        scheduleRowMap = new TreeMap<>();
        selectedTable = findViewById(R.id.selected_schedule_table);
        Button saveButton = findViewById(R.id.button_save_selected_schedule);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveResult();
            }
        });
        setTable();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    void setTable() {
        TableLayout tableLayout = findViewById(R.id.table_schedule);
        for (int i =1; i<=11;i++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setWeightSum(6);
            TextView textview = new TextView(this);
            textview.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
            textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
            textview.setBackground(getResources().getDrawable(R.drawable.border_right_primary));
            textview.setText(String.format("%02d.00", i+6));
            textview.setHeight(60);
            textview.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
            tableRow.addView(textview);

            for(int j=1;j<=5;j++) {
                final TextView textViewColumn = new TextView(this);
                final int row = i;
                final int col = j;
                textViewColumn.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
                textViewColumn.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
                textViewColumn.setText(String.format(" "));
                textViewColumn.setHeight(80);
                textViewColumn.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);

//                textViewColumn.setBackgroundColor(getResources().getColor(R.color.materialcolorpicker__lightgrey));
                if(scheduleData.getData()[row-1][col-1] == null) {
                    textViewColumn.setBackground(getResources().getDrawable(R.drawable.border_full_light_grey_available));
                    textViewColumn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            toggleCell(row, col, textViewColumn);
                        }
                    });
                } else {
                    textViewColumn.setBackground(getResources().getDrawable(R.drawable.border_full_light_grey_unavailable));
                }
                tableRow.addView(textViewColumn);
            }
            tableLayout.addView(tableRow);
        }
    }
    /*
        Toggle Cell as selected or not selected
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void toggleCell(int row, int column, TextView view) {
        if(!scheduleStatus[row][column]) {
            changeCellStatus(row,column,true );
            changeCellBackground(view, getResources().getDrawable(R.drawable.border_full_light_grey_selected));
            addList(row, column);
        } else  {
            changeCellStatus(row, column, false);
            changeCellBackground(view,getResources().getDrawable(R.drawable.border_full_light_grey_available));
            removeList(row,column);
        }
    }

    private int getKey(int row, int column) {
        return (column*100)+row;
    }

    private void removeList(int row, int column) {
        int key = getKey(row,column);
        scheduleRowMap.remove(key);
        scheduleDataMap.remove(key);
        flushTableLayout(selectedTable);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void addList(int row, int column) {
        CourseScheduleItemModel courseScheduleItem = new CourseScheduleItemModel();
        courseScheduleItem.setTime(String.format("%d%d",column,row));
//        Log.d("SAMS_ACT",key+"");
//        Log.d("SAMS_LOG",gson.toJson(scheduleDataMap));
        int key = getKey(row,column);
        scheduleDataMap.put(key, courseScheduleItem);
        TableRow tableRow = createScheduleRow(row, column,courseScheduleItem);
        scheduleRowMap.put(key,tableRow);
        flushTableLayout(selectedTable);

    }

    private void flushTableLayout(TableLayout selectedTable) {
        selectedTable.removeAllViews();
        for(TableRow item : scheduleRowMap.values()) {
            selectedTable.addView(item);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private TableRow createScheduleRow(int row, int column, final CourseScheduleItemModel courseScheduleItem) {
        TableRow tableRow = new TableRow(this);
        tableRow.setWeightSum(10);
        TextView textview = new TextView(this);
        textview.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 4f));
        textview.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textview.setText(String.format("%s, %02d.00-%02d.00", getDayName(column),row+6,row+7));
        textview.setHeight(60);
        textview.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tableRow.addView(textview);

        TextView textView2 = new TextView(this);
        textView2.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 1f));
        textView2.setText(":");
        textView2.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        textView2.setHeight(60);
        textView2.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        tableRow.addView(textView2);

        final EditText editText = new EditText(this);
        editText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.MATCH_PARENT, 5f));
        editText.setHint("Room Name");
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                courseScheduleItem.setClassRoom(editText.getText().toString());
            }
        });
        tableRow.addView(editText);

        return tableRow;

    }

    private String getDayName(int column) {
        String[] days = {
                "Mon","Tue","Wed","Thu","Fri"
        };
        return days[column-1];
    }

    private void saveResult() {
        if(checkAllFilled()) {
            Intent intent = new Intent();
            Collection<CourseScheduleItemModel> scheduleCollection = scheduleDataMap.values();
            Type itemType = new TypeToken<Collection<CourseScheduleItemModel>>(){}.getType();
            String intentString = gson.toJson(scheduleCollection,itemType);
            intent.putExtra("scheduleList", intentString);
            setResult(RESULT_OK, intent);
            finish();
        }

    }

    private boolean checkAllFilled() {
        boolean f = true;
        for(Map.Entry<Integer, CourseScheduleItemModel> item : scheduleDataMap.entrySet()) {
            if(item.getValue().getClassRoom() == null) {
                ((EditText) scheduleRowMap.get(item.getKey()).getChildAt(2)).setError("No Value");
                f = false;
            }
        }
        return f;
    }

    private void changeCellBackground(TextView view, Drawable background) {
        view.setBackground(background);
    }

    private void changeCellStatus(int row, int column, Boolean status) {
        scheduleStatus[row][column] = status;
    }




}
