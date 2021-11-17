package com.example.jangbogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {
    ImageView home_intro_Iv;

    private TextView store_tv_name, store_tv_intro, store_tv_item, store_tv_address, store_tv_phone, store_tv_time, store_tv_sale, Order_tv;
    private FirebaseDatabase database;
    private list item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        store_tv_name = (TextView) findViewById(R.id.store_tv_name);
        store_tv_intro = (TextView) findViewById(R.id.store_tv_intro);
        store_tv_item = (TextView) findViewById(R.id.store_tv_item);
        store_tv_address = (TextView) findViewById(R.id.store_tv_address);
        store_tv_phone = (TextView) findViewById(R.id.store_tv_phone);
        store_tv_time = (TextView) findViewById(R.id.store_tv_time);
        store_tv_sale = (TextView) findViewById(R.id.store_tv_sale);
        Order_tv = (TextView) findViewById(R.id.Order_tv);

        database = FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동
        DatabaseReference databaseReference = database.getReference("Users");

        getItemDetail();
        setItem();

        //order버튼
        Order_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrderActivity.class);
                intent.putExtra("store_name", item.getStore_name());
                startActivity(intent);
            }
        });

        home_intro_Iv = (ImageView) findViewById(R.id.home_intro_Iv);
        home_intro_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //홈버튼 기능
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getItemDetail(){
        Intent intent = getIntent();
        if(intent != null){
            Bundle bld = intent.getExtras();

            Object obj = bld.get("item");
            if(obj != null && obj instanceof list){
                this.item = (list) obj;
            }
        }
    }

    public void setItem(){
        if(this.item != null){
            String name = this.item.getStore_name();
            String intro = this.item.getStore_intro();
            String item = this.item.getStore_item();
            String address = this.item.getStore_address();
            String phone = this.item.getStore_phone();
            String time = this.item.getStore_time();
            String sale = this.item.getStore_sale();
            if(name != null){
                store_tv_name.setText(name);
            }
            if(intro != null){
                store_tv_intro.setText(intro);
            }
            if(item != null){
                store_tv_item.setText(item);
            }
            if(address != null){
                store_tv_address.setText(address);
            }
            if(phone != null){
                store_tv_phone.setText(phone);
            }
            if(time != null){
                store_tv_time.setText(time);
            }
            if(sale != null){
                store_tv_sale.setText("* 이가게는 "+sale+"한 가게입니다.");
            }
        }
    }



}