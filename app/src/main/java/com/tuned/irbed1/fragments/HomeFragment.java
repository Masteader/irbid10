package com.tuned.irbed1.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuned.irbed1.R;
import com.tuned.irbed1.adapters.CategoryRecycleAdapter;
import com.tuned.irbed1.model.CategoriesResData;
import com.tuned.irbed1.retrofit.Api;
import com.tuned.irbed1.retrofit.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;


public class HomeFragment extends Fragment
{
    RecyclerView rec;

    public HomeFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        rec = view.findViewById(R.id.rec);
        rec.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        getData();
        return view;
    }

    public void getData()
    {
        Api service = RetroClient.getApiService();
        Call<CategoriesResData> resultCall = service.GetCategries();
        resultCall.enqueue(new Callback<CategoriesResData>()
        {
            @Override
            public void onResponse(Call<CategoriesResData> call, retrofit2.Response<CategoriesResData> response)
            {
                CategoryRecycleAdapter categoryRecycleAdapter = new CategoryRecycleAdapter(getActivity(), response.body().getCategories());
                rec.setAdapter(categoryRecycleAdapter);

            }

            @Override
            public void onFailure(Call<CategoriesResData> call, Throwable t)
            {

            }
        });
    }
}