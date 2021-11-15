package com.example.jangbogo;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView home_Iv, board_Iv;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<list> arrayList;
    private ArrayList<list> resultList;
    private FirebaseDatabase database;
    private Button mSearchBtn;
    private EditText mSearchField;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");
    private DatabaseReference resultRef = FirebaseDatabase.getInstance().getReference("Users/Board");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        recyclerView = findViewById(R.id.recyclerView); //아이디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //list 객체를 담을 어레이 리스트

        mSearchField = (EditText) findViewById(R.id.edit_search);
        mSearchBtn = (Button) findViewById(R.id.search_btn);

        mSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String searchText = mSearchField.getText().toString().trim();
                search(searchText);
            }
        });



        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            int i;
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //파이어베이스 데이터베이스의 데이터를 받아오는곳
                arrayList.clear(); //초기화
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    list List = snapshot1.child("Board").getValue(list.class); //만들어뒀던 list 객체에 데이터 담기
                    if(List != null){
                        arrayList.add(List); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                        i = 0;
                    }
                }
                adapter.notifyItemInserted(i); // 리스트 저장 및 새로고침
                i++;
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

    //검색기능(순차 탐색)
    private void search(String searchText) {
        Toast.makeText(getApplicationContext(), "search", Toast.LENGTH_SHORT).show();
        resultList = new ArrayList<>();
        if(searchText.length() == 0){
            resultList.addAll(arrayList);
        }
        else{
            for(list List: arrayList){
                if(List.getStore_name().contains(searchText)){
                    resultList.add(List);
                }
            }
        }

        CustomAdapter resultAdapter = new CustomAdapter(resultList, this);
        recyclerView.removeAllViewsInLayout();
        recyclerView.setAdapter(resultAdapter);

    }
}