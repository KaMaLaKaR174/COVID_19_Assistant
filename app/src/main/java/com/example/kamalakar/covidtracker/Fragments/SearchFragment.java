package com.example.kamalakar.covidtracker.Fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.kamalakar.covidtracker.Activities.DonorSearchActivity;
import com.example.kamalakar.covidtracker.R;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    View view;
    float value[]={12.4f,12.6f,12.3f,12.7f,12.2f,12.8f,12.1f,12.9f};
    String BloodGroupName[]={"A+","A-","B+","B-","O+","O-","AB+","AB-"};
    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view= inflater.inflate(R.layout.fragment_search, container, false);
        setupPieChart();
        return view;

    }

    private void setupPieChart() {
        ArrayList<PieEntry> pieEntryList=new ArrayList<>();
        for(int i=0;i<BloodGroupName.length;i++){
            pieEntryList.add(new PieEntry(value[i],BloodGroupName[i]));
        }

        ArrayList<Integer> colors=new ArrayList<>();
        int myColor1=Color.parseColor("#e53935");
        int myColor2=Color.parseColor("#e57373");


        colors.add(myColor1);
        colors.add(myColor2);
        colors.add(myColor1);
        colors.add(myColor2);
        colors.add(myColor1);
        colors.add(myColor2);
        colors.add(myColor1);
        colors.add(myColor2);

        PieDataSet dataSet=new PieDataSet(pieEntryList,"");
        dataSet.setSliceSpace(2);
        dataSet.setColors(colors);
        dataSet.setValueTextSize(0);

        PieData data=new PieData(dataSet);

        PieChart chart= view.findViewById(R.id.chart);
        chart.setData(data);
        chart.setHoleRadius(45f);
        chart.getDescription().setEnabled(false);
        chart.getLegend().setEnabled(false);
        chart.setCenterText(" Select Blood Group  ");


        chart.setDrawEntryLabels(true);

        chart.animateY(3000);
        chart.invalidate();

        chart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {



                int pos1=e.toString().indexOf("y: ");
                String group=e.toString().substring(pos1+3);

                for(int i=0;i<value.length;i++){
                    if(value[i]== Float.parseFloat(group)){
                        pos1=i;
                        break;
                    }
                }


                String BloodGroup=BloodGroupName[pos1];

                Intent intent = new Intent(getActivity(), DonorSearchActivity.class);
                intent.putExtra("message", BloodGroup);
                startActivity(intent);



            }

            @Override
            public void onNothingSelected() {

            }
        });








    }


}
