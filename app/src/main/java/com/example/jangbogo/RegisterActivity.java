package com.example.jangbogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private EditText email_Et, password_Et, name_Et, phone_Et, sellnum_Et;
    private TextView submit_Tv;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);


        firebaseAuth = FirebaseAuth.getInstance(); //접근권한
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        email_Et = (EditText) findViewById(R.id.email_Et);
        password_Et = (EditText) findViewById(R.id.password_Et);
        name_Et = (EditText) findViewById(R.id.name_Et);
        phone_Et = (EditText) findViewById(R.id.phone_Et);
        sellnum_Et = (EditText) findViewById(R.id.sellnum_Et);

        submit_Tv = (TextView) findViewById(R.id.submit_Tv);
        submit_Tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = email_Et.getText().toString().trim();
                String password = password_Et.getText().toString().trim();

                if (!email.equals("") && !password.equals("")) {
                    // 이메일과 비밀번호가 공백이 아닌 경우
                    createUser(email, password);
                    // 이메일과 비밀번호가 공백인 경우
                } else if (email.equals("")) {
                    Toast.makeText(RegisterActivity.this, "이메일을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (password.equals("")) {
                    // 이메일과 비밀번호가 공백인 경우
                    Toast.makeText(RegisterActivity.this, "비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(RegisterActivity.this, "입력 오류", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void createUser(String email, String password) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            String uid = user.getUid();
                            String email = user.getEmail();
                            String password = password_Et.getText().toString().trim();
                            String name = name_Et.getText().toString().trim();
                            String phone = phone_Et.getText().toString().trim();
                            String sellNum = sellnum_Et.getText().toString().trim();

                            //해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                            HashMap<Object,String> hashMap = new HashMap<>();

                            hashMap.put("uid",uid);
                            hashMap.put("email", email);
                            hashMap.put("password", password);
                            hashMap.put("name", name);
                            hashMap.put("phone",phone);
                            hashMap.put("sellNum",sellNum);

                            mDatabase.child(uid).setValue(hashMap);

                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();
                            Intent intent = new Intent(RegisterActivity.this, loginActivity.class);
                            startActivity(intent);
                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(RegisterActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}