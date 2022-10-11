package com.example.coffeeapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import com.example.coffeeapp.R;

public class ConnectActivity extends AppCompatActivity {
    ImageButton imgRegister, imgLogin, imgFace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect);
        //findviewid
        imgFace = findViewById(R.id.btnface);
        imgLogin = findViewById(R.id.btnlogin);
        imgRegister = findViewById(R.id.btnregister);

        imgFace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), FacebookActivity.class);
                startActivity(intent);
            }
        });
        imgLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), LoginActivity.class);
                startActivity(intent);
            }
        });
        imgRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}