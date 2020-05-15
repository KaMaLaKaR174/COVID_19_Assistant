package com.example.kamalakar.covidtracker;

import android.content.Context;
import android.content.Intent;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.leo.simplearcloader.SimpleArcLoader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.ReferenceQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;

public class AffectedCountries extends AppCompatActivity {
    EditText editText;
    SimpleArcLoader loader;
    ListView listView;
    MyAdapter myAdapter;


    public static List<CountryModel> countryList;
    CountryModel countryModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_affected_countries);

        editText=findViewById(R.id.edit_text);
        loader=findViewById(R.id.loader1);
        listView=findViewById(R.id.listView);

        Objects.requireNonNull(getSupportActionBar()).setTitle("Affected Countries");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowCustomEnabled(true);

        countryList=new ArrayList<>();
        loader.start();
        fetchData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(),DetailActivity.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                myAdapter.getFilter().filter(s);
                myAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home){
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void fetchData() {
        String url="https://corona.lmao.ninja/v2/countries/";
        RequestQueue queue=Volley.newRequestQueue(AffectedCountries.this);
        StringRequest request=new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray=new JSONArray(response);
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject data=jsonArray.getJSONObject(i);
                        String country=data.getString("country");
                        String cases=data.getString("cases");
                        String deaths=data.getString("deaths");
                        String recovered=data.getString("recovered");
                        String critical=data.getString("critical");
                        String active=data.getString("active");
                        String todayCases=data.getString("todayCases");
                        String todayDeaths=data.getString("todayDeaths");

                        JSONObject object=data.getJSONObject("countryInfo");
                        String flag=object.getString("flag");

                        countryModel=new CountryModel(flag,cases,todayCases,deaths,todayDeaths,active,recovered,critical,country);
                        countryList.add(countryModel);

                    }
                    myAdapter=new MyAdapter(AffectedCountries.this,countryList);
                    listView.setAdapter(myAdapter);
                    loader.stop();
                    loader.setVisibility(View.GONE);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AffectedCountries.this, e.toString(), Toast.LENGTH_SHORT).show();
                    loader.stop();
                    loader.setVisibility(View.GONE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.stop();
                loader.setVisibility(View.GONE);
                Toast.makeText(AffectedCountries.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        );
        queue.add(request);
    }
}
