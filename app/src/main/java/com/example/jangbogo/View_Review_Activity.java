package com.example.jangbogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class View_Review_Activity extends AppCompatActivity {
    private TextView store_name;
    String store_uid, review_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review);

        store_name = (TextView) findViewById(R.id.review_name);

        Intent intent = getIntent();
        store_uid = intent.getStringExtra("store_uid");
        review_name = intent.getStringExtra("store_name");
        store_name.setText(review_name + "의 리뷰");
    }
}