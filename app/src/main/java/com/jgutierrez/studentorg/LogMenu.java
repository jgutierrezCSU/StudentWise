package com.jgutierrez.studentorg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Stack;

public class LogMenu extends AppCompatActivity {

// add code here for LogMenu

    Stack<String> logs;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_menu);

        logs = new Stack<>();
        listView = (ListView) findViewById(R.id.logList);
        final ArrayAdapter<String> logAdapter = new ArrayAdapter<String>(LogMenu.this,android.R.layout.simple_list_item_1,logs);
        listView.setAdapter(logAdapter);



    }
}
