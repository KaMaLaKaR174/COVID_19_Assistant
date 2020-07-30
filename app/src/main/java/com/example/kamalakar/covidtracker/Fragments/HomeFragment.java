package com.example.kamalakar.covidtracker.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.kamalakar.covidtracker.R;


public class HomeFragment extends Fragment {

    ImageView call,email,maps;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_home, container, false);
        call=view.findViewById(R.id.home_call);
        email=view.findViewById(R.id.home_email);
        maps=view.findViewById(R.id.home_maps);

        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+"8247877319"));
                v.getContext().startActivity(intent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_EMAIL,new String[] {"mucharlakamalakar@gmail.com"});
                intent.setType("message/rfc822");
                v.getContext().startActivity(Intent.createChooser(intent,"Choose An Email Client"));
            }
        });

        maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String add="google.navigation:q=Blood+Bank,+Near+me";



                Uri mapUri=Uri.parse(add);
                Intent intent =new Intent(Intent.ACTION_VIEW,mapUri);
                intent.setPackage("com.google.android.apps.maps");
                v.getContext().startActivity(intent);

            }
        });

        return view;

    }


}
