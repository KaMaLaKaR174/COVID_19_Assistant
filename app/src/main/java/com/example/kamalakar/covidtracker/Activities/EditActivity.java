package com.example.kamalakar.covidtracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kamalakar.covidtracker.Models.UserProfile;
import com.example.kamalakar.covidtracker.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditActivity extends AppCompatActivity {

    EditText name,age,add,phone;
    CheckBox isDonor;
    Spinner bloodGroup,gender;
    Button update;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    String edit_bloodGroup,edit_gender,email,donor="false";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setTitle("  ");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        name=findViewById(R.id.edit_name);
        age=findViewById(R.id.edit_age);
        add=findViewById(R.id.edit_add);
        phone=findViewById(R.id.edit_phone);
        update=findViewById(R.id.update);
        bloodGroup=findViewById(R.id.edit_bloodgroup);
        gender=findViewById(R.id.edit_gender);
        isDonor=findViewById(R.id.edit_checkdonor);
        firebaseAuth=FirebaseAuth.getInstance();
        firebaseDatabase=FirebaseDatabase.getInstance();
        reference=firebaseDatabase.getReference(firebaseAuth.getCurrentUser().getUid());

        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(EditActivity.this,R.array.bloodgroups,android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        bloodGroup.setAdapter(adapter);

        bloodGroup.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edit_bloodGroup=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapter1=ArrayAdapter.createFromResource(EditActivity.this,R.array.gender,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gender.setAdapter(adapter1);
        gender.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                edit_gender=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);
                name.setText(userProfile.getRegisterName());
                age.setText(userProfile.getRegisterAge());
                add.setText(userProfile.getRegisterAdd1());
                phone.setText(userProfile.getRegisterPhone());
                email=userProfile.getRegisterEmail();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        isDonor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isDonor.isChecked()){
                    donor="true";
                }
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edit_bloodGroup.equals("Blood Group")) {
                    Toast.makeText(EditActivity.this, "Select Correct Blood Group", Toast.LENGTH_SHORT).show();

                } else if (edit_gender.equals("Gender")) {
                    Toast.makeText(EditActivity.this, "Select Gender", Toast.LENGTH_SHORT).show();
                } else {
                    if(validate()){
                        UserProfile userProfile=new UserProfile(email,name.getText().toString(),
                                phone.getText().toString(),edit_bloodGroup,edit_gender,age.getText().toString(),
                                add.getText().toString(),donor
                                );
                        reference.setValue(userProfile);
                        Toast.makeText(EditActivity.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        finish();


                    }


                }
            }
        });


    }

    private boolean validate() {
        if(name.getText().toString().trim().isEmpty()){
            name.setError("Please Enter your name");
            return false;
        }
        else if(phone.getText().toString().trim().isEmpty()||phone.getText().toString().trim().length()!=10){
            phone.setError("Please enter valid phone number");
            return false;
        }
        else if(add.getText().toString().trim().isEmpty()){
            add.setError("Please enter address");
            return false;
        }
        else if(age.getText().toString().trim().isEmpty()){
            age.setError("Please enter your age");
            return false;
        }
        return true;

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
