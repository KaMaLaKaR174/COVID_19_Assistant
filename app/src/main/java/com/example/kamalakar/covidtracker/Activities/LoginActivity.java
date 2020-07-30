package com.example.kamalakar.covidtracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamalakar.covidtracker.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText email,password;
    Button login_btn;
    TextView signup;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Covid Assistant");
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.login_email);
        password=findViewById(R.id.login_password);
        login_btn=findViewById(R.id.login_btn);
        signup=findViewById(R.id.textView);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Signing in....");
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                //Toast.makeText(LoginActivity.this, "sign up page", Toast.LENGTH_SHORT).show();
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser();
        if(currentUser!=null){
            startActivity(new Intent(LoginActivity.this,NavigationDrawerActivity.class));
            finish();
        }



    }

    private void signIn() {
        String login_email=email.getText().toString().trim();
        String login_password=password.getText().toString().trim();
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(login_email,login_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this,NavigationDrawerActivity.class));
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
