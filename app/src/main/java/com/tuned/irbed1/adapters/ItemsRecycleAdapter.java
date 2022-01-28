package com.tuned.irbed1.adapters;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuned.irbed1.R;
import com.tuned.irbed1.activites.ItemDetActivity;
import com.tuned.irbed1.model.ItemModel;

import java.util.ArrayList;


public class ItemsRecycleAdapter extends    RecyclerView.Adapter<ItemsRecycleAdapter.viewItem> {

    Context c;
    ArrayList<ItemModel> items;
    public ItemsRecycleAdapter(Context c, ArrayList<ItemModel> item) {
        items=item;
        this.c=c;
    }
    class  viewItem extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        TextView price;
        TextView currency;
        View view;
        public viewItem(View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.image);
            name= itemView.findViewById(R.id.name);
            price= itemView.findViewById(R.id.price);
            currency= itemView.findViewById(R.id.currency);
            view= itemView.findViewById(R.id.view);

        }
    }


    @Override
    public viewItem onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rec_items, parent, false);
        return new viewItem(itemView);
    }

    @Override
    public void onBindViewHolder( viewItem holder,  int position) {
        holder.name.setText(items.get(holder.getAdapterPosition()).getName());
        holder.price.setText(items.get(holder.getAdapterPosition()).getPrice());
        holder.currency.setText(items.get(holder.getAdapterPosition()).getCurrency());
        Glide.with(c).load(items.get(holder.getAdapterPosition()).getImage()).into(holder.image);
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(c, ItemDetActivity.class);
                intent.putExtra("id",items.get(holder.getAdapterPosition()).getId());
                intent.putExtra("image",items.get(holder.getAdapterPosition()).getImage());
                intent.putExtra("name",items.get(holder.getAdapterPosition()).getName());
                intent.putExtra("price",items.get(holder.getAdapterPosition()).getPrice());
                intent.putExtra("currency",items.get(holder.getAdapterPosition()).getCurrency());
                intent.putExtra("des",items.get(holder.getAdapterPosition()).getDescription());
                c.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }




}
