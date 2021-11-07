package com.example.jangbogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ImageView home_Iv, board_Iv;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<list> arrayList;
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        recyclerView = findViewById(R.id.recyclerView); //아이디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //list 객체를 담을 어레이 리스트

        database = FirebaseDatabase.getInstance();//파이어베이스 데이터베이스 연동



        //DatabaseReference databaseReference = database.getReference().child("Board"); // 데이터베이스 연결
        DatabaseReference databaseReference = database.getReference("Users");

        //보드를 추가할 때마다 리셋됨 세연 ㅅㄱ
        //int temp = 0;
        //String str = String.valueOf(temp);
        //databaseReference.child(str);
        //temp++;
        //

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            int i;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는곳
                arrayList.clear(); //초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    //String str1 = snapshot1.getValue().toString();
                    //System.out.println(str1);
                    list List = snapshot1.child("Board").getValue(list.class); //만들어뒀던 list 객체에 데이터 담기
                    if(List != null){
                        arrayList.add(List); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                        i = 0;
                    }
                }
                adapter.notifyItemInserted(i); // 리스트 저장 및 새로고침
                i++;
                //databaseReference.child("Board").setValue(null);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter = new CustomAdapter(arrayList, this);
        recyclerView.setAdapter(adapter);



        //홈버튼 게시판버튼
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