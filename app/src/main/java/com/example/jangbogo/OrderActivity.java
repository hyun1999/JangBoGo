package com.example.jangbogo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class OrderActivity extends AppCompatActivity {
    ImageView home_Order_Iv;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private TextView Order_name_tv,store_tv_item,store_tv_sale,store_item_view,sale_price_tv,ship_untact_tv,ship_ontact_tv,pack_untact_tv,pack_ontact_tv;
    private EditText sale_price_et;
    private EditText o_name;
    private EditText o_phone;
    private EditText o_address;
    private EditText o_item;
    private EditText o_price;
    private list item1;
    public static Context context_order;
    public int ontact = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        firebaseAuth = FirebaseAuth.getInstance(); //접근권한
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        Order_name_tv = (TextView) findViewById(R.id.Order_name_tv);
        store_tv_item = (TextView) findViewById(R.id.store_tv_item);
        store_tv_sale = (TextView) findViewById(R.id.store_tv_sale);
        store_item_view = (TextView) findViewById(R.id.store_item_view);
        sale_price_tv = (TextView) findViewById(R.id.sale_price_tv);
        sale_price_et = (EditText) findViewById(R.id.sale_price_et);

        o_name = (EditText) findViewById(R.id.o_name);
        o_phone = (EditText) findViewById(R.id.o_phone);
        o_address = (EditText) findViewById(R.id.o_address);
        o_item = (EditText) findViewById(R.id.o_item);
        o_price = (EditText) findViewById(R.id.o_price);

        ship_untact_tv = (TextView) findViewById(R.id.ship_untact_tv);
        ship_ontact_tv = (TextView) findViewById(R.id.ship_ontact_tv);
        pack_untact_tv = (TextView) findViewById(R.id.pack_untact_tv);
        pack_ontact_tv = (TextView) findViewById(R.id.pack_ontact_tv);

        context_order = this;

        getItemDetail();
        setItem();

        Intent intent = getIntent();
        String store_name = intent.getStringExtra("store_name");
        String store_item = intent.getStringExtra("store_item");
        String store_sale = intent.getStringExtra("store_sale");
        String store_sell = intent.getStringExtra("store_sell");
        String sellerUid = intent.getStringExtra("uid");


        Order_name_tv.setText(store_name);
        store_item_view.setText(store_item);
        home_Order_Iv = (ImageView) findViewById(R.id.home_Order_Iv);

        if(store_sale.equals("흥정 불가능")){
            sale_price_tv.setVisibility(View.GONE);
        }
        if(store_sale.equals("흥정 불가능")){
            sale_price_et.setVisibility(View.GONE);
        }

        FirebaseUser user = firebaseAuth.getCurrentUser();
        String uid = user.getUid();
        getNameAndPhone(uid);


        //배달 비대면결제 클릭시
        ship_untact_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plus_sell(sellerUid);

                String name = o_name.getText().toString().trim();
                String phone = o_phone.getText().toString().trim();
                String address = o_address.getText().toString().trim();
                String item = o_item.getText().toString().trim();
                String price = o_price.getText().toString().trim();
                if (!name.equals("") && !address.equals("") && !phone.equals("")
                        && !address.equals("") && !item.equals("") && !price.equals("")) {
                    // 모든 사항이 공백이 아닐경우
                    if(store_sale.equals("흥정 불가능")){
                        Intent intent = new Intent(getApplicationContext(), card_Ready_SplashActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), sale_Ready_SplashActivity.class);
                        startActivity(intent);
                    }
                } else if (name.equals("")) {
                    Toast.makeText(OrderActivity.this, "이름을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (phone.equals("")) {
                    Toast.makeText(OrderActivity.this, "전화번호를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (address.equals("")) {
                    Toast.makeText(OrderActivity.this, "주소를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (item.equals("")) {
                    Toast.makeText(OrderActivity.this, "구매하실 상품을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (price.equals("")) {
                    Toast.makeText(OrderActivity.this, "가격을 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OrderActivity.this, "입력오류가 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        //배달 대면결제 클릭시
        ship_ontact_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = o_name.getText().toString().trim();
                String phone = o_phone.getText().toString().trim();
                String address = o_address.getText().toString().trim();
                String item = o_item.getText().toString().trim();
                String price = o_price.getText().toString().trim();
                if (!name.equals("") && !address.equals("") && !phone.equals("")
                        && !address.equals("") && !item.equals("") && !price.equals("")) {
                    // 모든 사항이 공백이 아닐경우
                    if(store_sale.equals("흥정 불가능")){
                        Intent intent = new Intent(getApplicationContext(), direct_SplashActivity.class);
                        startActivity(intent);
                    }else{
                        ontact = 1;
                        Intent intent = new Intent(getApplicationContext(), sale_Ready_SplashActivity.class);
                        startActivity(intent);
                    }
                } else if (name.equals("")) {
                    Toast.makeText(OrderActivity.this, "이름을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (phone.equals("")) {
                    Toast.makeText(OrderActivity.this, "전화번호를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (address.equals("")) {
                    Toast.makeText(OrderActivity.this, "주소를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (item.equals("")) {
                    Toast.makeText(OrderActivity.this, "구매하실 상품을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (price.equals("")) {
                    Toast.makeText(OrderActivity.this, "가격을 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OrderActivity.this, "입력오류가 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        //포장 비대면결제 클릭시
        pack_untact_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = o_name.getText().toString().trim();
                String phone = o_phone.getText().toString().trim();
                String address = o_address.getText().toString().trim();
                String item = o_item.getText().toString().trim();
                String price = o_price.getText().toString().trim();
                if (!name.equals("") && !address.equals("") && !phone.equals("")
                        && !address.equals("") && !item.equals("") && !price.equals("")) {
                    // 모든 사항이 공백이 아닐경우
                    if(store_sale.equals("흥정 불가능")){
                        Intent intent = new Intent(getApplicationContext(), card_Ready_SplashActivity.class);
                        startActivity(intent);
                    }else{
                        Intent intent = new Intent(getApplicationContext(), sale_Ready_SplashActivity.class);
                        startActivity(intent);
                    }
                } else if (name.equals("")) {
                    Toast.makeText(OrderActivity.this, "이름을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (phone.equals("")) {
                    Toast.makeText(OrderActivity.this, "전화번호를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (address.equals("")) {
                    Toast.makeText(OrderActivity.this, "주소를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (item.equals("")) {
                    Toast.makeText(OrderActivity.this, "구매하실 상품을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (price.equals("")) {
                    Toast.makeText(OrderActivity.this, "가격을 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OrderActivity.this, "입력오류가 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        //포장 대면결제 클릭시
        pack_ontact_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = o_name.getText().toString().trim();
                String phone = o_phone.getText().toString().trim();
                String address = o_address.getText().toString().trim();
                String item = o_item.getText().toString().trim();
                String price = o_price.getText().toString().trim();
                if (!name.equals("") && !address.equals("") && !phone.equals("")
                        && !address.equals("") && !item.equals("") && !price.equals("")) {
                    // 모든 사항이 공백이 아닐경우
                    if(store_sale.equals("흥정 불가능")){
                        Intent intent = new Intent(getApplicationContext(), direct_SplashActivity.class);
                        startActivity(intent);
                    }else{
                        ontact = 1;
                        Intent intent = new Intent(getApplicationContext(), sale_Ready_SplashActivity.class);
                        startActivity(intent);//작동x
                    }
                } else if (name.equals("")) {
                    Toast.makeText(OrderActivity.this, "이름을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (phone.equals("")) {
                    Toast.makeText(OrderActivity.this, "전화번호를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (address.equals("")) {
                    Toast.makeText(OrderActivity.this, "주소를 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (item.equals("")) {
                    Toast.makeText(OrderActivity.this, "구매하실 상품을 입력하세요.", Toast.LENGTH_LONG).show();
                } else if (price.equals("")) {
                    Toast.makeText(OrderActivity.this, "가격을 입력하세요.", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(OrderActivity.this, "입력오류가 있습니다.", Toast.LENGTH_LONG).show();
                }
            }
        });
        home_Order_Iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //홈버튼 기능
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
    public void getItemDetail() {
        Intent intent = getIntent();
        if (intent != null) {
            Bundle bld = intent.getExtras();

            Object obj = bld.get("item1");
            if (obj != null && obj instanceof list) {
                this.item1 = (list) obj;
            }
        }
    }

    public void setItem() {
        if (this.item1 != null) {
            String name = this.item1.getStore_name();
            String item = this.item1.getStore_item();
            String sale = this.item1.getStore_sale();
            if (name != null) {
                Order_name_tv.setText(name);
            }
            if (item != null) {
                store_tv_item.setText(item);
            }
            if (sale != null) {
                store_tv_sale.setText(sale);
            }
        }
    }

    public void getNameAndPhone(String uid){
        mDatabase.child(uid).child("name").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting name", task.getException());
                }
                else {
                    o_name.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });

        mDatabase.child(uid).child("phone").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting phone", task.getException());
                }
                else {
                    o_phone.setText(String.valueOf(task.getResult().getValue()));
                }
            }
        });
    }
    // 판매수 증가
    public void plus_sell(String uid){
        mDatabase.child(uid).child("Board").child("store_sell").get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting store_sell", task.getException());
                }
                else {
                    int sellpoint = Integer.parseInt(String.valueOf(task.getResult().getValue()));
                    sellpoint = sellpoint + 1;
                    mDatabase.child(uid).child("Board/store_sell").setValue(String.valueOf(sellpoint));
                }
            }
        });
    }
}