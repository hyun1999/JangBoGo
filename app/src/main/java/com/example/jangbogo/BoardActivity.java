package com.example.jangbogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
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

public class BoardActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private EditText store_name_Et;
    private EditText store_address_Et;
    private EditText store_phone_Et;
    private EditText store_intro_Et;
    private EditText store_item_Et;
    private EditText store_time_Et;
    private CheckBox store_sale_Cb;
    private TextView submit_Tv;
    private DatabaseReference mDatabase;
    ImageView home_Iv;

    String resultText = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.board);

        firebaseAuth = FirebaseAuth.getInstance(); //접근권한
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        store_name_Et = (EditText) findViewById(R.id.store_name_Et);
        store_address_Et = (EditText) findViewById(R.id.store_address_Et);
        store_phone_Et = (EditText) findViewById(R.id.store_phone_Et);
        store_intro_Et = (EditText) findViewById(R.id.store_intro_Et);
        store_item_Et = (EditText) findViewById(R.id.store_item_Et);
        store_time_Et = (EditText) findViewById(R.id.store_time_Et);
        store_sale_Cb = (CheckBox) findViewById(R.id.store_sale_Cb);

        CheckBox checkBox = (CheckBox) findViewById(R.id.store_sale_Cb) ;
        checkBox.setOnClickListener(new CheckBox.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (((CheckBox)v).isChecked() == true) {
                    resultText = "true";
                } else {
                    resultText = "false";
                }
            }
        }) ;

        home_Iv = (ImageView) findViewById(R.id.home_Iv);
        home_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //홈버튼 기능
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });


        submit_Tv = (TextView) findViewById(R.id.submit_Tv);
        submit_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String store_name = store_name_Et.getText().toString().trim();
                String store_address = store_address_Et.getText().toString().trim();
                String store_phone = store_phone_Et.getText().toString().trim();
                String store_intro = store_intro_Et.getText().toString().trim();
                String store_item = store_item_Et.getText().toString().trim();
                String store_time = store_time_Et.getText().toString().trim();
                String store_sell = "0";
                String store_sale = resultText;
                if (!store_name.equals("") && !store_address.equals("") && !store_phone.equals("")
                        && !store_intro.equals("") && !store_item.equals("") && !store_time.equals("")) {
                    // 모든 사항이 공백이 아닐경우
                    createPost(store_name, store_address, store_phone, store_intro, store_item, store_time, store_sale, store_sell);
                } else if (store_name.equals("")) {
                    // 가게 이름이 공백인 경우
                    Toast.makeText(BoardActivity.this, "가게이름을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (store_address.equals("")) {
                    // 가게 주소가 공백인 경우
                    Toast.makeText(BoardActivity.this, "가게 주소를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (store_phone.equals("")) {
                    // 전화번호가 공백인 경우
                    Toast.makeText(BoardActivity.this, "대표 전화번호를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (store_intro.equals("")) {
                    // 가게 소개가 공백인 경우
                    Toast.makeText(BoardActivity.this, "가게 소개를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (store_item.equals("")) {
                    // 판매 상품이 공백인 경우
                    Toast.makeText(BoardActivity.this, "판매상품을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (store_time.equals("")) {
                    // 영업 시간이 공백인 경우
                    Toast.makeText(BoardActivity.this, "영업 시간을 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    // 예외 발생
                    Toast.makeText(BoardActivity.this, "입력오류가 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createPost(String store_name, String store_address, String store_phone, String store_intro, String store_item, String store_time, String store_sale, String store_sell) {
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

                    if (sellNum != null && user != null) {

                        //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                        HashMap<Object, String> hashMap = new HashMap<>();

                        hashMap.put("store_name", store_name);
                        hashMap.put("store_address", store_address);
                        hashMap.put("store_phone", store_phone);
                        hashMap.put("store_intro", store_intro);
                        hashMap.put("store_item", store_item);
                        hashMap.put("store_time", store_time);
                        hashMap.put("store_sell", store_sell);
                        hashMap.put("review_count", "0");
                        hashMap.put("review_sum", "0");
                        if(store_sale =="true"){
                            hashMap.put("store_sale", "흥정 가능");
                        } else{
                            hashMap.put("store_sale", "흥정 불가능");
                        }


                        //
                        mDatabase.child(uid).child("Board").setValue(hashMap);
                        //

                        Toast.makeText(BoardActivity.this, "글 작성 성공", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(BoardActivity.this, MainActivity.class);
                        startActivity(intent);

                    } else {
                        Toast.makeText(BoardActivity.this, "sellnum 오류 발생", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}