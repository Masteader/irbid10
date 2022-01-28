package com.tuned.irbed1.activites;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.tuned.irbed1.R;
import com.tuned.irbed1.utl.ConstantValue;
import com.tuned.irbed1.utl.General;

public class SplashActivity extends AppCompatActivity {
    TextView appName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);




        appName = findViewById(R.id.appName);

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.blink);
        appName.startAnimation(animation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (General.getPreferenceValue(SplashActivity.this, ConstantValue.ID,"").equals("")){
                    General.goToActivity(SplashActivity.this,SelectActivity.class);
                }else {
                    General.goToActivity(SplashActivity.this,MainActivity.class);
                }
                finish();

            }
        },3000);



    }
}