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
import com.tuned.irbed1.model.SignUpModel;
import com.tuned.irbed1.retrofit.Api;
import com.tuned.irbed1.retrofit.RetroClient;
import com.tuned.irbed1.utl.ConstantValue;
import com.tuned.irbed1.utl.General;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class ProfileActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText phone;
    EditText password;
    Button update;
    Toolbar toolBar;
    ProgressDialog pd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.please_wait));

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        update=findViewById(R.id.update);
        toolBar = findViewById(R.id.toolBar);

        name.setText(General.getPreferenceValue(this,ConstantValue.NAME,""));
        email.setText(General.getPreferenceValue(this,ConstantValue.EMAIL,""));
        phone.setText(General.getPreferenceValue(this,ConstantValue.PHONE,""));
        password.setText(General.getPreferenceValue(this,ConstantValue.PASSWORD,""));

        General.setActionBarWithoutTitle(this,toolBar,false);

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (TextUtils.isEmpty(name.getText().toString())){
                    name.setError(getResources().getString(R.string.required));
                    return;
                }

                if (!General.isValidEmail(email.getText().toString())){
                    email.setError(getResources().getString(R.string.in_valid_email));
                    return;
                }

                if (TextUtils.isEmpty(phone.getText().toString())){
                    phone.setError(getResources().getString(R.string.required));
                    return;
                }
                if (TextUtils.isEmpty(password.getText().toString())){
                    password.setError(getResources().getString(R.string.required));
                    return;
                }


                updateProfile();

            }
        });
    }


    public void updateProfile(){
        pd.show();
        Api service = RetroClient.getApiService();
        RequestBody emailRequestBody = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("text/plain"), password.getText().toString());
        RequestBody phoneRequestBody = RequestBody.create(MediaType.parse("text/plain"), phone.getText().toString());
        RequestBody nameRequestBody = RequestBody.create(MediaType.parse("text/plain"), name.getText().toString());
        RequestBody Id_usersRequestBody = RequestBody.create(MediaType.parse("text/plain"), General.getPreferenceValue(ProfileActivity.this, ConstantValue.ID,""));

        Call<SignUpModel> resultCall = service.updateProfile(
                nameRequestBody,
                emailRequestBody,
                passwordRequestBody,
                phoneRequestBody,
                Id_usersRequestBody
        );

        resultCall.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, retrofit2.Response<SignUpModel> response) {
                pd.dismiss();
                General.showToast(ProfileActivity.this,response.body().getMsg());
            }
            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                pd.dismiss();
            }
        });
    }

}