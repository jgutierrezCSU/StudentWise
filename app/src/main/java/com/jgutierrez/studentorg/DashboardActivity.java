package com.jgutierrez.studentorg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jgutierrez.studentorg.fragments.ScheduleFragment;


public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    static String  mUsername = "ANONYMOUS";
    public static String userGroup = "ANONYMOUS";
    private FirebaseAuth mAuth;
    // for cards
    private CardView cardSched, cardGroupC, cardEvents, cardWeightCal ,cardAppoint,cardNotes;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        mAuth = FirebaseAuth.getInstance();

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

        // for Cards
        cardSched= findViewById(R.id.schedulercard);
        cardGroupC=findViewById(R.id.groupchatcard);
        cardEvents=findViewById(R.id.eventscard);
        cardWeightCal=findViewById(R.id.weightcalculatercard);
        cardAppoint =findViewById(R.id.appointmentcard);
        cardNotes=findViewById(R.id.notescard);
        //for cards
        cardSched.setOnClickListener(this);
        cardGroupC.setOnClickListener(this);
        cardEvents.setOnClickListener(this);
        cardWeightCal.setOnClickListener(this);
        cardAppoint.setOnClickListener(this);
        cardNotes.setOnClickListener(this);

    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
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



    // for selecting cards
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.schedulercard:
                startActivity(new Intent(this, ScheduleMainActivity.class));
                break;
            case R.id.groupchatcard:
                startActivity(new Intent(this, ScheduleFragment.class)); //TODO send to second Main Activities or not call it like this
                break;
            case R.id.eventscard:
                startActivity(new Intent(this, ScheduleFragment.class));
                break;
            case R.id.weightcalculatercard:
                startActivity(new Intent(this, ScheduleFragment.class));
                break;
            case R.id.appointmentcard:
                startActivity(new Intent(this, ScheduleFragment.class));
                break;
            case R.id.notescard:
                startActivity(new Intent(this, ScheduleFragment.class));
                break;
        }
    }




}
