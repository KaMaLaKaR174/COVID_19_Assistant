package com.example.kamalakar.covidtracker.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;

import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.kamalakar.covidtracker.Models.AffectedCountries;
import com.example.kamalakar.covidtracker.R;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

public class GlobalStatsActivity extends AppCompatActivity {

    ScrollView scrollView;
    SimpleArcLoader simpleArcLoader;
    PieChart pieChart;
    SwipeRefreshLayout swipeRefreshLayout;
    TextView cases,recovered,active,critical,today_deaths,affected_countries,today_cases,deaths;
    Button track;
    TextToSpeech textToSpeech;
    SharedPreferences sharedPreferences;
    boolean toSpeak=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_stats);
        getSupportActionBar().setTitle("Covid Assistant");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        scrollView=findViewById(R.id.scroll_view);
        simpleArcLoader=findViewById(R.id.loader);
        pieChart=findViewById(R.id.piechart);
        cases=findViewById(R.id.cases);
        recovered=findViewById(R.id.recovered);
        active=findViewById(R.id.active);
        critical=findViewById(R.id.critical);
        deaths=findViewById(R.id.deaths);
        today_deaths=findViewById(R.id.today_deaths);
        affected_countries=findViewById(R.id.affected_countries);
        today_cases=findViewById(R.id.today_cases);
        track=findViewById(R.id.btn_track);
        swipeRefreshLayout=findViewById(R.id.swipe_layout);
        sharedPreferences= PreferenceManager.getDefaultSharedPreferences(this);
        toSpeak=sharedPreferences.getBoolean(NavigationDrawerActivity.KEY_SWITCH,true);

        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status==TextToSpeech.SUCCESS){
                    textToSpeech.setLanguage(Locale.ENGLISH);
                }
            }
        });
        simpleArcLoader.start();
        fetchData();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);

                        pieChart.clearChart();
                        textToSpeech.speak(" ",TextToSpeech.QUEUE_FLUSH,null);
                        fetchData();
                        scrollView.setVisibility(View.VISIBLE);
                        scrollView.scrollTo(0,0);
                    }
                },2000);
                simpleArcLoader.setVisibility(View.VISIBLE);
                simpleArcLoader.start();
                scrollView.setVisibility(View.GONE);


            }
        });
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_bright),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light)
        );

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GlobalStatsActivity.this,AffectedCountries.class);
                startActivity(intent);
            }
        });



    }

    private void fetchData() {

        String url="https://corona.lmao.ninja/v2/all/";
        RequestQueue requestQueue=Volley.newRequestQueue(GlobalStatsActivity.this);
        StringRequest request=new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject data=new JSONObject(response.toString());
                            cases.setText(data.getString("cases"));
                            today_cases.setText(data.getString("todayCases"));
                            today_deaths.setText(data.getString("todayDeaths"));
                            recovered.setText(data.getString("recovered"));
                            critical.setText(data.getString("critical"));
                            active.setText(data.getString("active"));
                            deaths.setText(data.getString("deaths"));
                            affected_countries.setText(data.getString("affectedCountries"));

                            pieChart.addPieSlice(new PieModel("Cases",Integer.parseInt(cases.getText().toString()),Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("Recovered",Integer.parseInt(recovered.getText().toString()),Color.parseColor("#66BB6A")));
                            pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(active.getText().toString()),Color.parseColor("#29B6F6")));
                            pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(deaths.getText().toString()),Color.parseColor("#EF5350")));

                            pieChart.startAnimation();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            simpleArcLoader.stop();
                            simpleArcLoader.setVisibility(View.GONE);
                            scrollView.setVisibility(View.VISIBLE);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleArcLoader.stop();
                simpleArcLoader.setVisibility(View.GONE);

                AlertDialog.Builder builder=new AlertDialog.Builder(GlobalStatsActivity.this);
                builder.setTitle("API Error");
                builder.setMessage("Could not load data");
                builder.setPositiveButton("Okay!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog=builder.create();
                alertDialog.show();

                scrollView.setVisibility(View.VISIBLE);

                //Toast.makeText(GlobalStatsActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
        );
        requestQueue.add(request);
        if(toSpeak)
        readData();

    }

    private void readData() {
        textToSpeech.speak("Here are the Global stats for you",TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak("Cases",TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak(cases.getText().toString(),TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak("Recovered",TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak(recovered.getText().toString(),TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak("Critical",TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak(critical.getText().toString(),TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak("Active",TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak(active.getText().toString(),TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak("Deaths",TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak(deaths.getText().toString(),TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak("Today Cases",TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak(today_cases.getText().toString(),TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak("Today Deaths",TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak(today_deaths.getText().toString(),TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak("Affected Countries",TextToSpeech.QUEUE_ADD,null);
        textToSpeech.speak(affected_countries.getText().toString(),TextToSpeech.QUEUE_ADD,null);
    }

    @Override
    protected void onPause() {
        super.onPause();
        textToSpeech.speak(" ",TextToSpeech.QUEUE_FLUSH,null);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(toSpeak)
        readData();
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
