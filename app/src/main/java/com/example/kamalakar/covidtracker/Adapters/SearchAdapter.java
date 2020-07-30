package com.example.kamalakar.covidtracker.Adapters;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kamalakar.covidtracker.R;

import java.util.ArrayList;

import androidx.recyclerview.widget.RecyclerView;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SerachViewHolder>{

    Context context;

    ArrayList<String> nameList;
    ArrayList<String> emailList;
    ArrayList<String> phoneList;


    class SerachViewHolder extends RecyclerView.ViewHolder{

        private TextView listName,listEmail,listPhone;
        ImageView phone,email;





        public SerachViewHolder(View itemView) {
            super(itemView);

            listName=(TextView) itemView.findViewById(R.id.listname);
            listEmail=(TextView) itemView.findViewById(R.id.listemail);
            listPhone=(TextView) itemView.findViewById(R.id.listphone);
            phone=itemView.findViewById(R.id.donorPhone);
            email=itemView.findViewById(R.id.donorEmail);

            phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Intent intent =new Intent(Intent.ACTION_DIAL);
                    intent.setData(Uri.parse("tel:"+listPhone.getText().toString()));
                    v.getContext().startActivity(intent);


                }
            });
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    Intent intent =new Intent(Intent.ACTION_SEND);
                    intent.putExtra(Intent.EXTRA_EMAIL,new String[] {listEmail.getText().toString().trim()});
                    intent.setType("message/rfc822");
                    v.getContext().startActivity(Intent.createChooser(intent,"Choose An Email Client"));
                }
            });







        }
    }
    public SearchAdapter()
    {

    }

    public SearchAdapter(Context context, ArrayList<String> nameList, ArrayList<String> emailList, ArrayList<String> phoneList) {
        this.context = context;
        this.nameList = nameList;
        this.emailList = emailList;
        this.phoneList = phoneList;



    }

    @Override
    public SearchAdapter.SerachViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.search_list,parent,false);

        return  new SearchAdapter.SerachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SerachViewHolder holder, int position) {

        holder.listName.setText(nameList.get(position));
        holder.listEmail.setText(emailList.get(position));
        holder.listPhone.setText(phoneList.get(position));


    }


    @Override
    public int getItemCount() {
        return nameList.size();
    }
}
