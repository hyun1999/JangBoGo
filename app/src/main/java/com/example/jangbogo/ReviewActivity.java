package com.example.jangbogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ReviewActivity extends AppCompatActivity {

    private EditText titleEdit;
    private RatingBar reviewRating;
    private EditText reviewEdit;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private TextView cancel_tv, ok_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        firebaseAuth = FirebaseAuth.getInstance(); //접근권한
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        ok_tv = (TextView) findViewById(R.id.ok_tv);
        cancel_tv = (TextView) findViewById(R.id.cancel_tv);
        titleEdit = (EditText) findViewById(R.id.titleEdit);
//        reviewRating = (RatingBar) findViewById(R.id.reviewRating);
        reviewEdit = (EditText) findViewById(R.id.reviewEdit);

        cancel_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //홈버튼 기능
                Intent intent = new Intent(getApplicationContext(), StoreActivity.class);
                startActivity(intent);
            }
        });

        ok_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String re_title = titleEdit.getText().toString().trim();
                String re_review = reviewEdit.getText().toString().trim();

                if (!re_title.equals("") && !re_review.equals("")) {
//                    // 모든 사항이 공백이 아닐경우
                    createPost(re_title, re_review);
                } else if (re_title.equals("")) {
                    // 제목이 공백인 경우
                    Toast.makeText(ReviewActivity.this, "제목을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (re_review.equals("")) {
                    // 후기가 공백인 경우
                    Toast.makeText(ReviewActivity.this, "후기를 입력하세요.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createPost(String re_title, String re_review) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();
        DatabaseReference mData = FirebaseDatabase.getInstance().getReference("Users");
        String sellNum = String.valueOf(mData.child(uid).child("sellNum").getDatabase());

        mData.child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mData.child(uid).child("sellNum").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                } else {
                    //sellNum[0] = String.valueOf(task.getResult().getValue());

                    //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                    HashMap<Object, String> hashMap = new HashMap<>();

                    hashMap.put("title", re_title);
                    hashMap.put("review ", re_review);

                    //
                    mDatabase.child(uid).setValue(hashMap);
                    //

                    Toast.makeText(ReviewActivity.this, "글 작성 성공", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ReviewActivity.this, MainActivity.class);
                    startActivity(intent);

                }
            }


            });
        }
    }