package com.tuned.irbed1.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.tuned.irbed1.R;
import com.tuned.irbed1.model.ImageModel;
import com.tuned.irbed1.model.NotificationModel;

import java.util.ArrayList;


public class NotificationRecycleAdapter extends    RecyclerView.Adapter<NotificationRecycleAdapter.viewItem> {

    Context c;
    ArrayList<NotificationModel> items;
    public NotificationRecycleAdapter(Context c, ArrayList<NotificationModel> item) {
        items=item;
        this.c=c;
    }
    class  viewItem extends RecyclerView.ViewHolder {
        ImageView image;
        TextView title;
        TextView msg;
        public viewItem(View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.image);
            title= itemView.findViewById(R.id.title);
            msg= itemView.findViewById(R.id.msg);
        }
    }


    @Override
    public viewItem onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_rec_notification, parent, false);
        return new viewItem(itemView);
    }

    @Override
    public void onBindViewHolder( viewItem holder, final int position) {
        Glide.with(c).load(items.get(position).getImage()).into(holder.image);
        holder.title.setText(items.get(holder.getAdapterPosition()).getTitle());
        holder.msg.setText(items.get(holder.getAdapterPosition()).getMsg());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }




}
