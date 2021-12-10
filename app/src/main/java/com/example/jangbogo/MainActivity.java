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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.ktx.Firebase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ImageView home_Iv, board_Iv;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<list> arrayList;
    private ArrayList<list> resultList;
    private ArrayList<String> uidList;
    private FirebaseDatabase database;
    private Button mSearchBtn;
    private EditText mSearchField;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        recyclerView = findViewById(R.id.recyclerView); //아이디 연결
        recyclerView.setHasFixedSize(true); //리사이클러뷰 기존성능 강화
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        arrayList = new ArrayList<>(); //list 객체를 담을 어레이 리스트
        uidList = new ArrayList<>(); //list 객체를 담을 어레이 리스트

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
                uidList.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    list List = snapshot1.child("Board").getValue(list.class); //만들어뒀던 list 객체에 데이터 담기
                    String uid = snapshot1.child("uid").getValue().toString();
                    if (List != null) {
                        arrayList.add(List); //담은 데이터들을 배열리스트에 넣고 리사이클러뷰로 보낼준비
                        uidList.add(uid);
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

        adapter = new CustomAdapter(arrayList, uidList, this);
        recyclerView.setAdapter(adapter);


        //홈버튼 게시판버튼
        home_Iv = (ImageView) findViewById(R.id.home_Iv);
        board_Iv = (ImageView) findViewById(R.id.board_Iv);
        home_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //홈버튼 기능
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
        board_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uid = FirebaseAuth.getInstance().getUid();
                databaseReference.child(uid).child("sellNum").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        String sellNum = String.valueOf(task.getResult().getValue());
                        if(sellNum.equals("null")){
                            Toast.makeText(MainActivity.this, "판매자만 글쓰기가 가능합니다.", Toast.LENGTH_SHORT).show();
                        }else{
                            Intent intent = new Intent(getApplicationContext(), BoardActivity.class);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }

    //검색기능(순차 탐색)
    private void search(String searchText) {
        resultList = new ArrayList<>();
        // 문자 입력이 없을때는 모든 데이터를 보여준다
        if (searchText.length() == 0) {
            resultList.addAll(arrayList);
            // 문자 입력을 할때
        } else {
            for (list List : arrayList) {
                if (List.getStore_name().contains(searchText)) {
                    resultList.add(List);
                }
            }
        }

        CustomAdapter resultAdapter = new CustomAdapter(resultList, uidList, this);
        recyclerView.removeAllViewsInLayout();
        recyclerView.setAdapter(resultAdapter);
    }

}