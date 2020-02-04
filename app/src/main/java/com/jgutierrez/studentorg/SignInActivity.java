package com.jgutierrez.studentorg;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignInActivity extends AppCompatActivity {

    private TextView mBtnSignIn;
    private EditText mEtEmail, mEtPassword;
    private RelativeLayout root, mRlSignUp, mRlFadingLayout;
    private ProgressBar mProgressBar;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mBtnSignIn = findViewById(R.id.login);
        mEtPassword = findViewById(R.id.password);
        mEtEmail = findViewById(R.id.username);
       // root = findViewById(R.id.rlSigInRoot);
        mRlSignUp = findViewById(R.id.orSignUp);
       // mRlFadingLayout = findViewById(R.id.rlFadingLayout);
       // mProgressBar = findViewById(R.id.progressBar);

        mRlSignUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this, SignUpActivity.class));
                finish();
            }
        });

        mBtnSignIn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = mEtEmail.getText().toString();
                String password = mEtPassword.getText().toString();

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {

                                mProgressBar.setVisibility(View.INVISIBLE);
                                mRlFadingLayout.setVisibility(View.INVISIBLE);

                                if (task.isSuccessful()) {
                                    startActivity(new Intent(SignInActivity.this, MainActivity.class));
                                    finish();
                                } else {
                                    Snackbar
                                            .make(root, task.getException().getLocalizedMessage(), Snackbar.LENGTH_LONG)
                                            .show();
                                }
                            }
                        });

            }
        });

    }
}