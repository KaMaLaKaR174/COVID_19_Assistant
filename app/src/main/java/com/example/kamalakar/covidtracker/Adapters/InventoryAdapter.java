package com.example.kamalakar.covidtracker.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kamalakar.covidtracker.Models.Inventory;
import com.example.kamalakar.covidtracker.R;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class InventoryAdapter extends ArrayAdapter<Inventory> {

    List<Inventory> inventoryList;


    public InventoryAdapter(@NonNull Context context, @NonNull List<Inventory> list) {
        super(context, R.layout.inventory_adapter, list);
        this.inventoryList=list;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.inventory_adapter,parent,false);
        TextView first=view.findViewById(R.id.first_text);
        TextView second=view.findViewById(R.id.second_text);
        first.setText(inventoryList.get(position).bloodGroup);
        second.setText(inventoryList.get(position).donor);

        return view;

    }
}
