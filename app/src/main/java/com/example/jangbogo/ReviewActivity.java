package com.example.jangbogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
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
import com.google.firestore.v1.StructuredQuery;

import java.util.HashMap;

public class ReviewActivity extends AppCompatActivity {

    private EditText titleEdit;
    private RatingBar reviewRating;
    private EditText reviewEdit;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private TextView ok_tv;
    String store_uid, review_count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        firebaseAuth = FirebaseAuth.getInstance(); //접근권한
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        ok_tv = (TextView) findViewById(R.id.ok_tv);
        titleEdit = (EditText) findViewById(R.id.titleEdit);
        reviewRating = (RatingBar) findViewById(R.id.reviewRating);
        reviewEdit = (EditText) findViewById(R.id.reviewEdit);


        Intent intent = getIntent();
        store_uid = intent.getStringExtra("store_uid");
        review_count = intent.getStringExtra("review_count");


        ok_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String re_title = titleEdit.getText().toString().trim();
                String re_review = reviewEdit.getText().toString().trim();
                int re_rating = (int) reviewRating.getRating();

                if (!re_title.equals("") && !re_review.equals("")) {
                    // 모든 사항이 공백이 아닐경우
                    createReview(re_title, re_review, String.valueOf(re_rating));
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

    private void createReview(String re_title, String re_review, String re_rating) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();

        HashMap<Object, String> hashMap = new HashMap<>();
        hashMap.put("user_uid", uid);
        hashMap.put("re_title", re_title);
        hashMap.put("re_review", re_review);
        hashMap.put("re_rating", re_rating);



        mDatabase.child(store_uid).child("Board").child("review_count").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting store_sell", task.getException());
                } else {
                    int review_count = Integer.parseInt(String.valueOf(task.getResult().getValue()));
                    mDatabase.child(store_uid).child("Board").child("Review").child(String.valueOf(review_count)).setValue(hashMap);
                    mDatabase.child(store_uid).child("Board/review_count").setValue(String.valueOf(review_count+1));
                }
            }
        });

        Toast.makeText(ReviewActivity.this, "review 작성 성공", Toast.LENGTH_SHORT).show();
//        Intent intent = new Intent(ReviewActivity.this, StoreActivity.class);
//        startActivity(intent);
        finish();

    }
}