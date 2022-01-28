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
import com.tuned.irbed1.activites.CartActivity;
import com.tuned.irbed1.activites.ItemDetActivity;
import com.tuned.irbed1.activites.ItemsActivity;
import com.tuned.irbed1.model.CartModel;
import com.tuned.irbed1.model.CategoryModel;
import com.tuned.irbed1.model.ResultModel;
import com.tuned.irbed1.retrofit.Api;
import com.tuned.irbed1.retrofit.RetroClient;
import com.tuned.irbed1.utl.ConstantValue;
import com.tuned.irbed1.utl.General;

import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class CartRecycleAdapter extends    RecyclerView.Adapter<CartRecycleAdapter.viewItem> {

    Context c;
    ArrayList<CartModel> items;
    public CartRecycleAdapter(Context c, ArrayList<CartModel> item) {
        items=item;
        this.c=c;
    }
    class  viewItem extends RecyclerView.ViewHolder {
        ImageView image;
        ImageView delete;
        TextView name;
        TextView price;
        TextView pluse;
        TextView count;
        TextView minse;

        public viewItem(View itemView) {
            super(itemView);
            image= itemView.findViewById(R.id.image);
            name= itemView.findViewById(R.id.name);
            price= itemView.findViewById(R.id.price);
            delete= itemView.findViewById(R.id.delete);
            delete= itemView.findViewById(R.id.delete);
            pluse= itemView.findViewById(R.id.pluse);
            count= itemView.findViewById(R.id.count);
            minse = itemView.findViewById(R.id.minse);

        }
    }


    @Override
    public viewItem onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_cart, parent, false);
        return new viewItem(itemView);
    }

    @Override
    public void onBindViewHolder( viewItem holder, final int position) {

       holder.name.setText(items.get(holder.getAdapterPosition()).getName());
       holder.price.setText(String.valueOf(items.get(holder.getAdapterPosition()).getPrice()));
       holder.count.setText(String.valueOf(items.get(holder.getAdapterPosition()).getCount()));
       holder.pluse.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int count = items.get(holder.getAdapterPosition()).getCount();
               if (count<99){
                   count++;
                   items.get(holder.getAdapterPosition()).setCount(count);
                   holder.count.setText(String.valueOf(items.get(holder.getAdapterPosition()).getCount()));

                   updateCart(items.get(holder.getAdapterPosition()).getId(), String.valueOf(items.get(holder.getAdapterPosition()).getCount()));
               }
           }
       });


        holder.minse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = items.get(holder.getAdapterPosition()).getCount();
                if (count>1){
                    count--;
                    items.get(holder.getAdapterPosition()).setCount(count);
                    holder.count.setText(String.valueOf(items.get(holder.getAdapterPosition()).getCount()));
                    updateCart(items.get(holder.getAdapterPosition()).getId(), String.valueOf(items.get(holder.getAdapterPosition()).getCount()));
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(items.get(holder.getAdapterPosition()).getId());
                items.remove(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public void  updateCart(String Id,String count){

        Api service = RetroClient.getApiService();
        RequestBody Id_RequestBody = RequestBody.create(MediaType.parse("text/plain"), Id);
        RequestBody countRequestBody = RequestBody.create(MediaType.parse("text/plain"), count);

        Call<ResultModel> resultCall = service.UpdateCart(
                Id_RequestBody,
                countRequestBody
        );
        resultCall.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, retrofit2.Response<ResultModel> response) {

            }
            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
            }
        });




    }

    public  void  delete(String Id){
            Api service = RetroClient.getApiService();
            RequestBody Id_RequestBody = RequestBody.create(MediaType.parse("text/plain"), Id);
            Call<ResultModel> resultCall = service.deleteCart(
                    Id_RequestBody
            );
            resultCall.enqueue(new Callback<ResultModel>() {
                @Override
                public void onResponse(Call<ResultModel> call, retrofit2.Response<ResultModel> response) {

                }
                @Override
                public void onFailure(Call<ResultModel> call, Throwable t) {
                }
            });

        }

}
