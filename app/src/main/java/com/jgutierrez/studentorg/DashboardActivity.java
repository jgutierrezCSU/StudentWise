package com.jgutierrez.studentorg;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class DashboardActivity extends AppCompatActivity {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    static String  mUsername = "ANONYMOUS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


    // Initialize Firebase Auth
        // for checking if cur user is logged in
    mFirebaseAuth = FirebaseAuth.getInstance();
    mFirebaseUser = mFirebaseAuth.getCurrentUser();
        if (mFirebaseUser == null) {
        // Not signed in, launch the Sign In activity
        startActivity(new Intent(this, MainActivity.class));
        finish();
        return;
    } else {
        mUsername = mFirebaseUser.getDisplayName();

     }
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
                //sign cur user out
                mFirebaseAuth.signOut();
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
