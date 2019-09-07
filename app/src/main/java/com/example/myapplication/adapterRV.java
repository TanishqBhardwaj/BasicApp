package com.example.myapplication;

import android.media.Image;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public  class adapterRV extends RecyclerView.Adapter<adapterRV.viewHolder> {

    private String[] data;
    public adapterRV(String[] data){
        this.data = data;
    }


    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_items, parent, false);
        return new viewHolder((view));
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder holder, int position) {
        String title = data[position];
        holder.text.setText(title);
    }

    @Override
    public int getItemCount() {
        return data.length;
    }

    public class viewHolder extends RecyclerView.ViewHolder{
        ImageView iconimg;
        TextView text;

        public viewHolder(@NonNull View itemView) {
            super(itemView);
            iconimg = itemView.findViewById(R.id.iconImg);
            text = itemView.findViewById(R.id.text);


        }
    }


}
