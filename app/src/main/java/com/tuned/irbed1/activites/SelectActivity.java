package com.tuned.irbed1.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tuned.irbed1.R;
import com.tuned.irbed1.utl.ConstantValue;
import com.tuned.irbed1.utl.General;

public class SelectActivity extends AppCompatActivity {

    Button login;
    Button signup;
    Button guest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        login = findViewById(R.id.login);
        signup = findViewById(R.id.signup);
        guest = findViewById(R.id.guest);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                General.goToActivity(SelectActivity.this,LoginActivity.class);
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                General.goToActivity(SelectActivity.this,SignupActivity.class);
            }
        });


        guest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                General.addToSharedPreference(SelectActivity.this, ConstantValue.ID,"");
                General.goToActivity(SelectActivity.this,MainActivity.class);
                finish();
            }
        });

    }
}