package com.jgutierrez.studentorg;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.gson.Gson;
import com.jgutierrez.studentorg.firebaseService.FirebaseDatabaseService;

public abstract class BaseActivity extends AppCompatActivity {
    String personName;
    private String LOG_TAG = "BaseActivity";
    SharedPreferences pref;
    Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pref = getSharedPreferences("deafult", Context.MODE_PRIVATE);
    }

    @Override
    public void onStart(){
        super.onStart();
        checkAuthenticated();
        FirebaseDatabaseService.getInstance().child("fcm_token").setValue(FirebaseInstanceId.getInstance().getToken());
    }

    public void checkAuthenticated() {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        if(user == null) {
            Intent intent = new Intent(this,LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();
        } else {
            // Get the name of the person and store in shared preferences
            personName = user.getDisplayName();
            Log.d("BaseActivity", personName);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("personName", personName);
            editor.apply();
        }
    }
    public void signOut(View view) {
        FirebaseAuth.getInstance().signOut();
        checkAuthenticated();
    }

    public void closeActivity(){
        finish();
    }

    public void setToolbar(String title) {

        ImageButton backButton = findViewById(R.id.toolbar_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeActivity();
            }
        });
        TextView titleView = findViewById(R.id.title_view);
        titleView.setText(title);
        try {
            personName = pref.getString("personName", null);
            Log.d(LOG_TAG, String.format("Person's name: %s", personName));
            String initial = Character.toString(personName.charAt(0));
            Log.d(LOG_TAG, String.format("Initial: %s", initial));
            TextView nameInitial = findViewById(R.id.name_initial);
            nameInitial.setText(initial);
        } catch (NullPointerException e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Person's Name not found");
        }
    }

}
