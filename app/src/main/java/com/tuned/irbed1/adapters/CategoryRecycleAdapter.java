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
import com.tuned.irbed1.activites.ItemsActivity;
import com.tuned.irbed1.model.CategoryModel;

import java.util.ArrayList;


public class CategoryRecycleAdapter extends RecyclerView.Adapter<CategoryRecycleAdapter.viewItem>
{

    Context c;
    ArrayList<CategoryModel> items;

    public CategoryRecycleAdapter(Context c, ArrayList<CategoryModel> item)
    {
        items = item;
        this.c = c;
    }

    class viewItem extends RecyclerView.ViewHolder
    {
        ImageView image;
        TextView name;
        View view;

        public viewItem(View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            view = itemView.findViewById(R.id.view);

        }
    }


    @Override
    public viewItem onCreateViewHolder(final ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_category, parent, false);
        return new viewItem(itemView);
    }

    @Override
    public void onBindViewHolder(viewItem holder, final int position)
    {
        holder.name.setText(items.get(position).getName());
        Glide.with(c).load(items.get(position).getImage()).into(holder.image);
        holder.view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(c, ItemsActivity.class);
                intent.putExtra("Id_categories", items.get(holder.getAdapterPosition()).getId());
                c.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return items.size();
    }


}
