package com.tuned.irbed1.activites;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.tuned.irbed1.R;
import com.tuned.irbed1.adapters.NotificationRecycleAdapter;
import com.tuned.irbed1.model.NotificationResModel;
import com.tuned.irbed1.retrofit.Api;
import com.tuned.irbed1.retrofit.RetroClient;

import retrofit2.Call;
import retrofit2.Callback;

public class NotificationActivity extends AppCompatActivity
{
    RecyclerView rec;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        rec = findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        getData();
    }


    public void getData()
    {
        Api service = RetroClient.getApiService();

        Call<NotificationResModel> resultCall = service.getNotification();
        resultCall.enqueue(new Callback<NotificationResModel>()
        {
            @Override
            public void onResponse(Call<NotificationResModel> call, retrofit2.Response<NotificationResModel> response)
            {
                NotificationRecycleAdapter itemsRecycleAdapter = new NotificationRecycleAdapter(NotificationActivity.this, response.body().getNotification());
                rec.setAdapter(itemsRecycleAdapter);

            }

            @Override
            public void onFailure(Call<NotificationResModel> call, Throwable t)
            {

            }
        });
    }

}