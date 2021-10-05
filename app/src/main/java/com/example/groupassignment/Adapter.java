package com.example.groupassignment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.BreakIterator;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private int[] numberimages;
    LayoutInflater inflater;


    public Adapter(Context ctx, int[] numberimages) {
        this.numberimages = numberimages;
        this.inflater = LayoutInflater.from(ctx);
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.row_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.gridIcon.setImageResource(numberimages[position]);
    }

    @Override
    public int getItemCount() {

        return numberimages.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView gridIcon;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            gridIcon = itemView.findViewById(R.id.imgv_about_covid_symptoms);

        }
    }
}