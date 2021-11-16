package com.example.jangbogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {
    ImageView home_Order_Iv;
    private TextView Order_name_tv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);


        Order_name_tv = (TextView) findViewById(R.id.Order_name_tv);

        Intent intent = getIntent();
        String stringData = intent.getStringExtra("store_name");
        Order_name_tv.setText(stringData);
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