package com.jgutierrez.studentorg;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.jgutierrez.studentorg.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView mTvEmail, mTvLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mAuth = FirebaseAuth.getInstance();


        mTvLogout = findViewById(R.id.logout);
        mTvEmail = findViewById(R.id.username);


        FirebaseUser currentUser = mAuth.getCurrentUser();
      /*  if (currentUser == null) {
//No one signed in
            startActivity(new Intent(this, LoginActivity.class));
            this.finish();
        }else{
//User logged in
            mTvEmail.setText(currentUser.getEmail());
        }



        mTvLogout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                MainActivity.this.finish();

            }
        });
*/

    }


}