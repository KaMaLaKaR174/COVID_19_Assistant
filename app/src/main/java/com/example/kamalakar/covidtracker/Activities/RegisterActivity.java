package com.example.kamalakar.covidtracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kamalakar.covidtracker.R;
import com.example.kamalakar.covidtracker.Models.UserProfile;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    EditText userName,email,password,phone,age,address;
    Spinner bloodGroup,gender;
    Button register;
    CheckBox donor;
    FirebaseAuth firebaseAuth;

    String reg_bloodGroup="null",reg_gender="null",isDonor="false";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName=findViewById(R.id.reg_user_name);
        email=findViewById(R.id.reg_email);
        password=findViewById(R.id.reg_password);
        phone=findViewById(R.id.reg_phone);
        age=findViewById(R.id.reg_age);
        address=findViewById(R.id.reg_add);

        bloodGroup=findViewById(R.id.reg_bloodgroup);
        gender=findViewById(R.id.reg_gender);
        donor=findViewById(R.id.donorbutton);
        register=findViewById(R.id.reg_button);

        firebaseAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Signing up....");

        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(donor.isChecked()){
                    isDonor="true";
                }
            }
        });
        //ArrayAdapter<CharSequence> adapter
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(RegisterActivity.this,R.array.bloodgroups,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bloodGroup.setAdapter(adapter);

        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reg_bloodGroup=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(RegisterActivity.this,R.array.gender,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        gender.setAdapter(adapter1);

        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reg_gender=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });


    }

    private void signIn() {
        String reg_email=email.getText().toString().trim();
        String reg_password=password.getText().toString().trim();

        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        firebaseAuth.createUserWithEmailAndPassword(reg_email,reg_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    storeUserData();
                    Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    progressDialog.dismiss();
                    finish();

                }
                else{
                    Toast.makeText(RegisterActivity.this, "Registration Failed"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

    private void storeUserData() {
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference reference=firebaseDatabase.getReference(firebaseAuth.getCurrentUser().getUid());
        UserProfile userProfile=new UserProfile(email.getText().toString().trim(),
                userName.getText().toString().trim(),
                phone.getText().toString().trim(),
                reg_bloodGroup,reg_gender,
                age.getText().toString(),
                address.getText().toString(),
                isDonor
                );
        reference.setValue(userProfile);




    }
}
