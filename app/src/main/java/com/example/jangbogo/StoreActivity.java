package com.example.jangbogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class StoreActivity extends AppCompatActivity {

    private TextView store_tv_name, store_tv_intro, store_tv_item;
    private FirebaseDatabase database;
    private list item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        store_tv_name = (TextView) findViewById(R.id.store_tv_name);
        store_tv_intro = (TextView) findViewById(R.id.store_tv_intro);
        store_tv_item = (TextView) findViewById(R.id.store_tv_item);

        database = FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동
        DatabaseReference databaseReference = database.getReference("Users");

        getItemDetail();
        setItem();
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
            if(name != null){
                store_tv_name.setText(name);
            }
            if(intro != null){
                store_tv_intro.setText(intro);
            }
            if(item != null){
                store_tv_item.setText(item);
            }
        }

    }
}