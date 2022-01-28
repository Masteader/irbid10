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
import com.tuned.irbed1.model.UserModel;
import com.tuned.irbed1.retrofit.Api;
import com.tuned.irbed1.retrofit.RetroClient;
import com.tuned.irbed1.utl.ConstantValue;
import com.tuned.irbed1.utl.General;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

public class SignupActivity extends AppCompatActivity {
    EditText name;
    EditText email;
    EditText phone;
    EditText password;
    EditText confirmPassword;
    Button signup;
    Toolbar toolBar;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        pd = new ProgressDialog(this);
        pd.setMessage(getResources().getString(R.string.please_wait));

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        phone=findViewById(R.id.phone);
        password=findViewById(R.id.password);
        confirmPassword=findViewById(R.id.confirm_password);
        signup=findViewById(R.id.signup);
        toolBar = findViewById(R.id.toolBar);
        General.setActionBarWithoutTitle(this,toolBar,false);

        signup.setOnClickListener(new View.OnClickListener() {
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

                if (!password.getText().toString().equals(confirmPassword.getText().toString())){
                    confirmPassword.setError(getResources().getString(R.string.not_match));
                    return;
                }
                signUp();

            }
        });

    }

    public void signUp(){
        pd.show();
        Api service = RetroClient.getApiService();
        RequestBody emailRequestBody = RequestBody.create(MediaType.parse("text/plain"), email.getText().toString());
        RequestBody passwordRequestBody = RequestBody.create(MediaType.parse("text/plain"), password.getText().toString());
        RequestBody phoneRequestBody = RequestBody.create(MediaType.parse("text/plain"), phone.getText().toString());
        RequestBody nameRequestBody = RequestBody.create(MediaType.parse("text/plain"), name.getText().toString());
        Call<SignUpModel> resultCall = service.SignUp(
                nameRequestBody,
                emailRequestBody,
                passwordRequestBody,
                phoneRequestBody
        );

        resultCall.enqueue(new Callback<SignUpModel>() {
            @Override
            public void onResponse(Call<SignUpModel> call, retrofit2.Response<SignUpModel> response) {
                pd.dismiss();
                if (response.body().getResult().equals("1")){
                    General.goToActivity(SignupActivity.this,LoginActivity.class);
                }else {
                    General.showToast(SignupActivity.this,response.body().getMsg());
                }
            }
            @Override
            public void onFailure(Call<SignUpModel> call, Throwable t) {
                pd.dismiss();
            }
        });
    }
}