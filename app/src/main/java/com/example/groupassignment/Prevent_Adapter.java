package com.example.groupassignment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Prevent_Adapter extends BaseAdapter {
    Context ctx;
    private final int[] imageid ;
    private final String[] PreventString ;


    public Prevent_Adapter(Context ctx, int[] imageid, String[] preventString) {
        this.ctx = ctx;
        this.imageid = imageid;
        this.PreventString = preventString;
    }

    @Override
    public int getCount() {
        return PreventString.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        @SuppressLint("ViewHolder") View view =  inflater.inflate(R.layout.prevent_item,parent,false);

        ImageView image = view.findViewById(R.id.imgv_about_covid_prevent);
        TextView textView = view.findViewById(R.id.tv_subtitle);


        image.setImageResource(imageid[position]);
        textView.setText(PreventString[position]);
        return  view;



    }

}



