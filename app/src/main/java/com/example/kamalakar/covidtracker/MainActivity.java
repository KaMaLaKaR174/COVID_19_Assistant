package com.example.kamalakar.covidtracker;

import android.content.Intent;
import android.graphics.Color;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    ScrollView scrollView;
    SimpleArcLoader simpleArcLoader;
    PieChart pieChart;
    TextView cases,recovered,active,critical,today_deaths,affected_countries,today_cases,deaths;
    Button track;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        simpleArcLoader.start();
        fetchData();

        track.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AffectedCountries.class);
                startActivity(intent);
            }
        });

    }

    private void fetchData() {

        String url="https://corona.lmao.ninja/v2/all/";
        RequestQueue requestQueue=Volley.newRequestQueue(this);
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
                scrollView.setVisibility(View.VISIBLE);
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
        );
        requestQueue.add(request);
    }
}
