package com.tuned.irbed1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuned.irbed1.R;
import com.tuned.irbed1.adapters.CartRecycleAdapter;
import com.tuned.irbed1.model.CartResData;
import com.tuned.irbed1.retrofit.Api;
import com.tuned.irbed1.retrofit.RetroClient;
import com.tuned.irbed1.utl.ConstantValue;
import com.tuned.irbed1.utl.General;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;


public class CartFragment extends Fragment
{

    RecyclerView rec;
    Button done;

    public CartFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        rec = view.findViewById(R.id.rec);
        done = view.findViewById(R.id.done);


        rec.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

            }
        });
        getData();
        return view;
    }

    public void getData()
    {
        Api service = RetroClient.getApiService();
        RequestBody Id_usersRequestBody = RequestBody.create(MediaType.parse("text/plain"), General.getPreferenceValue(getActivity(), ConstantValue.ID, ""));

        Call<CartResData> resultCall = service.getCart(Id_usersRequestBody);
        resultCall.enqueue(new Callback<CartResData>()
        {
            @Override
            public void onResponse(Call<CartResData> call, retrofit2.Response<CartResData> response)
            {
                CartRecycleAdapter cartRecycleAdapter = new CartRecycleAdapter(getActivity(), response.body().getCart());
                rec.setAdapter(cartRecycleAdapter);

            }

            @Override
            public void onFailure(Call<CartResData> call, Throwable t)
            {

            }
        });
    }

}