package com.example.kamalakar.covidtracker.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.kamalakar.covidtracker.R;
import com.example.kamalakar.covidtracker.Models.UserProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    TextView userName,phoneNumber,address,bloodGroup,age,gender,email;
    FirebaseAuth firebaseAuth;
    FirebaseUser currentUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        userName=findViewById(R.id.profilename);
        phoneNumber=findViewById(R.id.profilephone);
        address=findViewById(R.id.profileaddress);
        bloodGroup=findViewById(R.id.profilebloodgroup);
        age=findViewById(R.id.profileage);
        gender=findViewById(R.id.profilegender);
        email=findViewById(R.id.profileemail);

        firebaseAuth=FirebaseAuth.getInstance();
        currentUser=firebaseAuth.getCurrentUser();
        DatabaseReference reference=FirebaseDatabase.getInstance().getReference(currentUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserProfile userProfile=dataSnapshot.getValue(UserProfile.class);

                userName.setText(userProfile.getRegisterName());
                phoneNumber.setText(userProfile.getRegisterPhone());
                address.setText(userProfile.getRegisterAdd1());
                bloodGroup.setText(userProfile.getRegisterBloodGroup());
                age.setText(userProfile.getRegisterAge());
                email.setText(userProfile.getRegisterEmail());
                gender.setText(userProfile.getRegisterGender());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
