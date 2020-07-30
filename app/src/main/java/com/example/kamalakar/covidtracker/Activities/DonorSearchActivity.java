package com.example.kamalakar.covidtracker.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kamalakar.covidtracker.R;
import com.example.kamalakar.covidtracker.Adapters.SearchAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DonorSearchActivity extends AppCompatActivity {

    TextView donorBloodGroup;
    RecyclerView recyclerView;
    FirebaseDatabase database;
    FirebaseAuth firebaseAuth;
    DatabaseReference myref;
    FirebaseUser firebaseUser;


    ArrayList<String> nameList;
    ArrayList<String> emailList;
    ArrayList<String> phoneList;
    SearchAdapter searchAdapter;
    String bg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_search);

        getSupportActionBar().setTitle("Covid Assistant");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        donorBloodGroup=findViewById(R.id.donorbloodtext);
        Bundle bundle=getIntent().getExtras();
        bg=bundle.getString("message");
        donorBloodGroup.setText(bg);

        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this,LinearLayoutManager.VERTICAL));



        firebaseAuth=FirebaseAuth.getInstance();
        database=FirebaseDatabase.getInstance();
        myref=database.getReference();
        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();

        nameList=new ArrayList<>();
        emailList=new ArrayList<>();
        phoneList=new ArrayList<>();

        myref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot snapshot:dataSnapshot.getChildren())
                {

                    String uid=snapshot.getKey();
                    if(uid.equals("stock")||uid.equals("Request")||uid.equals(firebaseAuth.getCurrentUser().getUid()))
                        continue;
                    String donorName=snapshot.child("registerName").getValue(String.class);
                    String donorEmail=snapshot.child("registerEmail").getValue(String.class);
                    String donorPhone=snapshot.child("registerPhone").getValue(String.class);
                    String donorCheck=snapshot.child("checkDonor").getValue(String.class);
                    String donorBloodGroup=snapshot.child("registerBloodGroup").getValue(String.class);

                    if(donorBloodGroup.equals(bg)&&donorCheck.equals("true"))
                    {
                        nameList.add(donorName);
                        phoneList.add(donorPhone);
                        emailList.add(donorEmail);

                    }



                }

                searchAdapter=new SearchAdapter(DonorSearchActivity.this,nameList,emailList,phoneList);
                if(searchAdapter.getItemCount()==0){
                    Toast.makeText(DonorSearchActivity.this,"No Donors Found",Toast.LENGTH_SHORT).show();
                }
                else {
                    recyclerView.setAdapter(searchAdapter);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
