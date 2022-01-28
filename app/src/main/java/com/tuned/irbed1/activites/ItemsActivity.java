package com.tuned.irbed1.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tuned.irbed1.R;
import com.tuned.irbed1.adapters.ItemsRecycleAdapter;
import com.tuned.irbed1.model.ItemsResData;
import com.tuned.irbed1.retrofit.Api;
import com.tuned.irbed1.retrofit.RetroClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ItemsActivity extends AppCompatActivity {
    RecyclerView rec;
    String Id_categories;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items);
        rec = findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        Id_categories = getIntent().getExtras().getString("Id_categories");


        getData();
    }

    public void getData(){
        Api service = RetroClient.getApiService();
        RequestBody Id_categoriesRequestBody = RequestBody.create(MediaType.parse("text/plain"), Id_categories);

        Call<ItemsResData> resultCall = service.GetItems(Id_categoriesRequestBody);
        resultCall.enqueue(new Callback<ItemsResData>() {
            @Override
            public void onResponse(Call<ItemsResData> call, retrofit2.Response<ItemsResData> response) {
                ItemsRecycleAdapter itemsRecycleAdapter =new ItemsRecycleAdapter(ItemsActivity.this,response.body().getItems());
                rec.setAdapter(itemsRecycleAdapter);

            }
            @Override
            public void onFailure(Call<ItemsResData> call, Throwable t) {

            }
        });
    }

}