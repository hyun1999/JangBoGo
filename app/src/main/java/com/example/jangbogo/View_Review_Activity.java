package com.example.jangbogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class View_Review_Activity extends AppCompatActivity {

    private TextView store_name;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    ArrayList<review> reviewList;
    String store_uid, review_name;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_review);

        store_name = (TextView) findViewById(R.id.review_name);

        Intent intent = getIntent();
        store_uid = intent.getStringExtra("store_uid");
        review_name = intent.getStringExtra("store_name");
        store_name.setText(review_name + "의 리뷰");

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_review);
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        reviewList = new ArrayList<>();

        databaseReference.child(store_uid).child("Board").child("Review").addListenerForSingleValueEvent(new ValueEventListener() {
            int i;
            int count = 0;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는곳
                reviewList.clear(); //초기화

                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    review r = snapshot1.getValue(review.class); //만들어뒀던 list 객체에 데이터 담기
                    if (r != null) {
                        reviewList.add(r); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                        count = count + 1;
                    }
                    else{
                        Toast.makeText(getApplicationContext(), String.valueOf(count), Toast.LENGTH_SHORT).show();
                    }
                }
                adapter.notifyItemInserted(i); // 리스트 저장 및 새로고침
                i++;

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        adapter = new ReviewAdapter(reviewList, this);
        recyclerView.setAdapter(adapter);

    }
}