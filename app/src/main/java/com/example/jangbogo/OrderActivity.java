package com.example.jangbogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class OrderActivity extends AppCompatActivity {
    ImageView home_Order_Iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        home_Order_Iv = (ImageView) findViewById(R.id.home_Order_Iv);
        home_Order_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //홈버튼 기능
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}