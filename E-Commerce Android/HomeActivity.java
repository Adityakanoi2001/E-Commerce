package com.example.quinbayproject.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.quinbayproject.R;

public class HomeActivity extends AppCompatActivity {

    private TextView tvUserLogin;
    private TextView tvMerchantLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        tvUserLogin = findViewById(R.id.tv_login);
        tvMerchantLogin = findViewById(R.id.tv_merchant);

        tvUserLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this,UserLoginActivity.class);
                startActivity(intent);

            }
        });

        tvMerchantLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, MerchantLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}