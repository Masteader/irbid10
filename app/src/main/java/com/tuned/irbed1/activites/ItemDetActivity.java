package com.tuned.irbed1.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tuned.irbed1.R;
import com.tuned.irbed1.adapters.ImageRecycleAdapter;
import com.tuned.irbed1.adapters.ItemsRecycleAdapter;
import com.tuned.irbed1.model.ImageResData;
import com.tuned.irbed1.model.ItemsResData;
import com.tuned.irbed1.model.ResultModel;
import com.tuned.irbed1.retrofit.Api;
import com.tuned.irbed1.retrofit.RetroClient;
import com.tuned.irbed1.utl.ConstantValue;
import com.tuned.irbed1.utl.General;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ItemDetActivity extends AppCompatActivity {
    TextView name;
    TextView price;
    TextView currency;
    TextView des;
    Button addToCart;
    String id;
    RecyclerView recImage;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_det);

        name=findViewById(R.id.name);
        price=findViewById(R.id.price);
        currency=findViewById(R.id.currency);
        des=findViewById(R.id.des);
        addToCart=findViewById(R.id.add_to_cart);
        recImage=findViewById(R.id.rec_image);
        recImage.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.please_wait));
        pd.setCancelable(false);


        name.setText(getIntent().getExtras().getString("name"));
        price.setText(getIntent().getExtras().getString("price"));
        currency.setText(getIntent().getExtras().getString("currency"));
        des.setText(getIntent().getExtras().getString("des"));
        id = getIntent().getExtras().getString("id");

        getData();


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (General.getPreferenceValue(ItemDetActivity.this, ConstantValue.ID, "").equals("")) {
                    General.goToActivity(ItemDetActivity.this, LoginActivity.class);
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(ItemDetActivity.this);
                    alert.setIcon(R.drawable.ic_baseline_shopping_cart_24);
                    alert.setTitle(getResources().getString(R.string.amazing_choice));
                    alert.setMessage(getResources().getString(R.string.what_do_you_want_to_do));
                    alert.setPositiveButton(getResources().getString(R.string.view_cart), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AddToCart(1);
                        }
                    });

                    alert.setNegativeButton(getResources().getString(R.string.continue_shopping), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AddToCart(2);
                        }
                    });
                    alert.show();
                }
            }
        });

    }



    public void getData(){
        Api service = RetroClient.getApiService();
        RequestBody Id_itemsRequestBody = RequestBody.create(MediaType.parse("text/plain"), id);

        Call<ImageResData> resultCall = service.getItemImages(Id_itemsRequestBody);
        resultCall.enqueue(new Callback<ImageResData>() {
            @Override
            public void onResponse(Call<ImageResData> call, retrofit2.Response<ImageResData> response) {
                ImageRecycleAdapter recycleAdapter =new ImageRecycleAdapter(ItemDetActivity.this,response.body().getImages());
                recImage.setAdapter(recycleAdapter);
            }
            @Override
            public void onFailure(Call<ImageResData> call, Throwable t) {

            }
        });
    }


    public void AddToCart(int clickType){
        pd.show();
        Api service = RetroClient.getApiService();
        RequestBody Id_itemsRequestBody = RequestBody.create(MediaType.parse("text/plain"), id);
        RequestBody Id_usersRequestBody = RequestBody.create(MediaType.parse("text/plain"), General.getPreferenceValue(this, ConstantValue.ID,""));

        Call<ResultModel> resultCall = service.AddToCart(Id_itemsRequestBody,Id_usersRequestBody);
        resultCall.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, retrofit2.Response<ResultModel> response) {
                pd.dismiss();
                if (clickType == 1){
                    General.goToActivity(ItemDetActivity.this,CartActivity.class);
                }else {
                    finish();
                }
            }
            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

}