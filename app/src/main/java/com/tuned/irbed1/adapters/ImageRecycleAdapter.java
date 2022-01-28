package com.tuned.irbed1.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuned.irbed1.R;
import com.tuned.irbed1.activites.ItemsActivity;
import com.tuned.irbed1.model.ImageModel;

import java.util.ArrayList;


public class ImageRecycleAdapter extends    RecyclerView.Adapter<ImageRecycleAdapter.viewItem> {

    Context c;
    ArrayList<ImageModel> items;
    public ImageRecycleAdapter(Context c, ArrayList<ImageModel> item) {
        items=item;
        this.c=c;
    }
    class  viewItem extends RecyclerView.ViewHolder {
        ImageView image;
        public viewItem(View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.image);
        }
    }


    @Override
    public viewItem onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rec_image, parent, false);
        return new viewItem(itemView);
    }

    @Override
    public void onBindViewHolder( viewItem holder, final int position) {
        Glide.with(c).load(items.get(position).getImage()).into(holder.image);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }




}
