package com.jgutierrez.studentorg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class DashboardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
    }






    // For Tool/Action Bar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    // For Tool/Action Bar Menu
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sign_out_menu:
                startActivity(new Intent(this, MainActivity.class));
                finish(); // log out
                return true;
            case R.id.log_menu:
                startActivity(new Intent(DashboardActivity.this, LogMenu.class));
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
