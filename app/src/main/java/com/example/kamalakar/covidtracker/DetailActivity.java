package com.example.kamalakar.covidtracker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private int position;
    TextView cases,active,deaths,recovered,critical,todayCases,todayDeaths,country;
    ImageView flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent=getIntent();
        position=intent.getIntExtra("position",0);

        getSupportActionBar().setTitle("Details");
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        cases=findViewById(R.id.cases_detail);
        deaths=findViewById(R.id.deaths_detail);
        active=findViewById(R.id.active_detail);
        critical=findViewById(R.id.critical_detail);
        recovered=findViewById(R.id.recovered_detail);
        todayCases=findViewById(R.id.today_cases_detail);
        todayDeaths=findViewById(R.id.today_deaths_detail);
        country=findViewById(R.id.country_detail);
        flag=findViewById(R.id.flag_detail);

        cases.setText(AffectedCountries.countryList.get(position).getCases());
        active.setText(AffectedCountries.countryList.get(position).getActive());
        recovered.setText(AffectedCountries.countryList.get(position).getRecovered());
        deaths.setText(AffectedCountries.countryList.get(position).getDeaths());
        critical.setText(AffectedCountries.countryList.get(position).getCritical());
        todayCases.setText(AffectedCountries.countryList.get(position).getTodayCases());
        todayDeaths.setText(AffectedCountries.countryList.get(position).getTodayDeaths());
        country.setText(AffectedCountries.countryList.get(position).getCountryName());

        Picasso.with(this).load(AffectedCountries.countryList.get(position).getFlag()).fit().into(flag);




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

