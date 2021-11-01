package com.example.jangbogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView home_Iv, board_Iv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        home_Iv = (ImageView) findViewById(R.id.home_Iv);
        board_Iv = (ImageView) findViewById(R.id.board_Iv);
        home_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //홈버튼 기능
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        board_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //게시판 버튼 기능
                Intent intent = new Intent(getApplicationContext(),BoardActivity.class);
                startActivity(intent);
            }
        });
    }
}