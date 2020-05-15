package com.example.kamalakar.covidtracker;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

public class MyAdapter extends ArrayAdapter<CountryModel> {

    private Context context;
    private List<CountryModel> countryList;
    private List<CountryModel> countryListFiltered;
    public MyAdapter( Context context, List<CountryModel> countryList) {
        super(context, R.layout.list_adapter,countryList);
        this.context=context;
        this.countryList=countryList;
        this.countryListFiltered=countryList;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.list_adapter,null,true);
        TextView countryName=view.findViewById(R.id.countryName);
        ImageView flag=view.findViewById(R.id.flagImage);
        countryName.setText(countryListFiltered.get(position).getCountryName());
        Picasso.with(context).load(countryListFiltered.get(position).getFlag()).fit().into(flag);

        return view;
    }

    @Override
    public int getCount() {
        return countryListFiltered.size();
    }


    @Override
    public CountryModel getItem(int position) {
        return countryListFiltered.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public Filter getFilter() {
        Filter filter=new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults filterResults=new FilterResults();
                if(constraint==null||constraint.length()==0){
                    filterResults.count=countryList.size();
                    filterResults.values=countryList;
                }
                else{
                    List<CountryModel> result=new ArrayList<>();
                    String search=constraint.toString().toLowerCase();
                    for(CountryModel model: countryList){
                        if(model.getCountryName().toLowerCase().indexOf(search)==0){
                            result.add(model);
                        }
                    }
                    filterResults.count=result.size();
                    filterResults.values=result;
                }
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                countryListFiltered= (List<CountryModel>) results.values;
                AffectedCountries.countryList= (List<CountryModel>) results.values;
                notifyDataSetChanged();

            }
        };
        return filter;
    }
}
