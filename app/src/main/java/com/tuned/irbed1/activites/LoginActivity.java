package com.tuned.irbed1.activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.tuned.irbed1.R;
import com.tuned.irbed1.model.UserModel;
import com.tuned.irbed1.retrofit.Api;
import com.tuned.irbed1.retrofit.RetroClient;
import com.tuned.irbed1.utl.ConstantValue;
import com.tuned.irbed1.utl.General;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button login;
    Toolbar toolBar;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email= findViewById(R.id.email);
        password= findViewById(R.id.password);
        login= findViewById(R.id.login);
        toolBar = findViewById(R.id.toolBar);
        pd= new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.please_wait));

        General.setActionBarWithoutTitle(this,toolBar,false);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!General.isValidEmail(email.getText().toString())){
                    email.setError(getResources().getString(R.string.in_valid_email));
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())){
                    password.setError(getResources().getString(R.string.required));
                    return;
                }
                login();
            }
        });
    }

    public void  login(){
        pd.show();
        Api service = RetroClient.getApiService();
        RequestBody emailRequestBody = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("text/plain"), password.getText().toString());
        Call<UserModel> resultCall = service.login(
                emailRequestBody,
                passwordRequestBody
        );

        resultCall.enqueue(new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, retrofit2.Response<UserModel> response) {
                pd.dismiss();
                if (response.body().getResult().equals("0")){
                    General.showToast(LoginActivity.this,response.body().getMsg());
                }else{
                    General.addToSharedPreference(LoginActivity.this, ConstantValue.ID,response.body().getId());
                    General.addToSharedPreference(LoginActivity.this, ConstantValue.NAME,response.body().getName());
                    General.addToSharedPreference(LoginActivity.this, ConstantValue.PHONE,response.body().getPhone());
                    General.addToSharedPreference(LoginActivity.this, ConstantValue.EMAIL,response.body().getEmail());
                    General.addToSharedPreference(LoginActivity.this, ConstantValue.PASSWORD,password.getText().toString());
                    General.goToActivity(LoginActivity.this,MainActivity.class);
                    finishAffinity();
                }
            }
            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
}