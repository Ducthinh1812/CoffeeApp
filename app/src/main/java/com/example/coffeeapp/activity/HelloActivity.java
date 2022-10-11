package com.example.coffeeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.R;

public class HelloActivity extends AppCompatActivity {
    LinearLayout linearLayout;
    ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);
        linearLayout = findViewById(R.id.manhLoading1);
        img = findViewById(R.id.imageView2);
        img.setX(2000);
        img.animate().translationXBy(-2000).setDuration(3000);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                linearLayout.setVisibility(View.GONE);
                Intent intent = new Intent(HelloActivity.this, ConnectActivity.class);
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}