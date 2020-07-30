package com.example.kamalakar.covidtracker.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.kamalakar.covidtracker.Adapters.InventoryAdapter;
import com.example.kamalakar.covidtracker.Models.Inventory;
import com.example.kamalakar.covidtracker.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class StockFragment extends Fragment {
    ListView listView;
    List<Inventory> inventoryList;
    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    DatabaseReference reference;
    public StockFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_stock, container, false);
        inventoryList=new ArrayList<>();
        listView= view.findViewById(R.id.listview);
        firebaseDatabase=FirebaseDatabase.getInstance();
        firebaseAuth=FirebaseAuth.getInstance();
        reference=firebaseDatabase.getReference();

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int a1=0,a2=0,b1=0,b2=0,o1=0,o2=0,ab1=0,ab2=0;
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    String donor=snapshot.child("checkDonor").getValue(String.class);
                    String bg=snapshot.child("registerBloodGroup").getValue(String.class);
                    if(donor.equals("true")){
                        if(bg.equals("A+")){
                            a1=a1+1;
                        }
                        else if(bg.equals("A-")){
                            a2=a2+1;
                        }
                        else if(bg.equals("B+")){
                            b1=b1+1;
                        }
                        else if(bg.equals("B-")){
                            b2=b2+1;
                        }
                        else if(bg.equals("O+")){
                            o1=o1+1;
                        }
                        else if(bg.equals("O-")){
                            o2=o2+1;
                        }
                        else if(bg.equals("AB-")){
                            ab1=ab1+1;
                        }
                        else if(bg.equals("AB-")){
                            ab2=ab2+1;
                        }
                    }
                }
                inventoryList.add(new Inventory("A+",String.valueOf(a1)));
                inventoryList.add(new Inventory("A-",String.valueOf(a2)));
                inventoryList.add(new Inventory("B+",String.valueOf(b1)));
                inventoryList.add(new Inventory("B-",String.valueOf(b2)));
                inventoryList.add(new Inventory("O+",String.valueOf(o1)));
                inventoryList.add(new Inventory("O-",String.valueOf(o2)));
                inventoryList.add(new Inventory("AB+",String.valueOf(ab1)));
                inventoryList.add(new Inventory("AB-",String.valueOf(ab2)));

                InventoryAdapter adapter=new InventoryAdapter(getContext(),inventoryList);
                listView.setAdapter(adapter);




            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return view;


    }


}
