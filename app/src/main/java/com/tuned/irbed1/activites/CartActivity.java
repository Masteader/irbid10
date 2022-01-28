package com.tuned.irbed1.activites;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.tuned.irbed1.R;
import com.tuned.irbed1.adapters.CartRecycleAdapter;
import com.tuned.irbed1.adapters.ItemsRecycleAdapter;
import com.tuned.irbed1.model.CartModel;
import com.tuned.irbed1.model.CartResData;
import com.tuned.irbed1.model.ItemsResData;
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

public class CartActivity extends AppCompatActivity {
    RecyclerView rec;
    Button done;
    int totalPrice;
    LocationManager locationManager;
    int MINIMUM_TIME_BETWEEN_UPDATES = 1000;
    int MINIMUM_DISTANCE_CHANGE_FOR_UPDATES = 1;
    String Longitude = "";
    String Latitude="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }



        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)
        {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if (!locationManager.isProviderEnabled( LocationManager.GPS_PROVIDER))
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage("GPS Not Enabled,Do you want to enable it?");
            dialog.setTitle("GPS Example");
            dialog.setIcon(R.mipmap.ic_launcher);
            dialog.setCancelable(false);
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    // TODO Auto-generated method stub
                    Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(i, 77);
                    //get gps
                }
            });

            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    finish();

                }
            });


            dialog.show();

        }


        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                MINIMUM_TIME_BETWEEN_UPDATES,
                MINIMUM_DISTANCE_CHANGE_FOR_UPDATES,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {

                        Latitude = String.valueOf(location.getLatitude());
                        Longitude = String.valueOf(location.getLongitude());
                        Toast.makeText(getApplicationContext(), Latitude + ","+Longitude, Toast.LENGTH_LONG).show();

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {
                        Toast.makeText(getApplicationContext(), "Status changed - Hi", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onProviderEnabled(String provider) {
                        Toast.makeText(getApplicationContext(), "Enabled - Hi", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                        Toast.makeText(getApplicationContext(), "Disabled - Hi", Toast.LENGTH_SHORT).show();
                    }
                }
        );



        rec=findViewById(R.id.rec);
        done= findViewById(R.id.done);

        rec.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddOrder();
            }
        });
        getData();

    }

    public void AddOrder(){
        Api service = RetroClient.getApiService();
        RequestBody Id_users = RequestBody.create(MediaType.parse("text/plain"), General.getPreferenceValue(this, ConstantValue.ID,""));
        RequestBody totalPriceRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(totalPrice));
        RequestBody latitudeRequestBody = RequestBody.create(MediaType.parse("text/plain"), Latitude);
        RequestBody longitudeRequestBody = RequestBody.create(MediaType.parse("text/plain"), Longitude);

        Call<ResultModel> resultCall = service.AddOrder(
                Id_users,
                totalPriceRequestBody,
                latitudeRequestBody,
                longitudeRequestBody);
        resultCall.enqueue(new Callback<ResultModel>() {
            @Override
            public void onResponse(Call<ResultModel> call, retrofit2.Response<ResultModel> response) {
                General.showToast(CartActivity.this,response.body().getMsg());
                finishAffinity();
                General.goToActivity(CartActivity.this,SplashActivity.class);
            }
            @Override
            public void onFailure(Call<ResultModel> call, Throwable t) {

            }
        });
    }


    public void getData(){
        Api service = RetroClient.getApiService();
        RequestBody Id_usersRequestBody = RequestBody.create(MediaType.parse("text/plain"), General.getPreferenceValue(this, ConstantValue.ID,""));

        Call<CartResData> resultCall = service.getCart(Id_usersRequestBody);
        resultCall.enqueue(new Callback<CartResData>() {
            @Override
            public void onResponse(Call<CartResData> call, retrofit2.Response<CartResData> response) {
                CartRecycleAdapter cartRecycleAdapter =new CartRecycleAdapter(CartActivity.this,response.body().getCart());
                rec.setAdapter(cartRecycleAdapter);
                ArrayList<CartModel> cartModelArrayList = response.body().getCart();
                for ( int i =0;i<cartModelArrayList.size();i++){
                    totalPrice = totalPrice+(cartModelArrayList.get(i).getPrice()*cartModelArrayList.get(i).getCount());
                }

            }
            @Override
            public void onFailure(Call<CartResData> call, Throwable t) {

            }
        });
    }
}