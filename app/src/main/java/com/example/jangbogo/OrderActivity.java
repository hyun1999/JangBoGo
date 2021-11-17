package com.example.jangbogo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class OrderActivity extends AppCompatActivity {
    ImageView home_Order_Iv;
    private TextView Order_name_tv,store_tv_item,store_tv_sale,store_item_view,sale_price_tv;
    private EditText sale_price_et;
    private list item1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.order);

        Order_name_tv = (TextView) findViewById(R.id.Order_name_tv);
        store_tv_item = (TextView) findViewById(R.id.store_tv_item);
        store_tv_sale = (TextView) findViewById(R.id.store_tv_sale);
        store_item_view = (TextView) findViewById(R.id.store_item_view);
        sale_price_tv = (TextView) findViewById(R.id.sale_price_tv);
        sale_price_et = (EditText) findViewById(R.id.sale_price_et);

        getItemDetail();
        setItem();


        Intent intent = getIntent();
        String store_name = intent.getStringExtra("store_name");
        String store_item = intent.getStringExtra("store_item");
        String store_sale = intent.getStringExtra("store_sale");

        Order_name_tv.setText(store_name);
        store_item_view.setText(store_item);
        home_Order_Iv = (ImageView) findViewById(R.id.home_Order_Iv);

        if(store_sale.equals("흥정 불가능")){
            sale_price_tv.setVisibility(View.GONE);
        }
        if(store_sale.equals("흥정 불가능")){
            sale_price_et.setVisibility(View.GONE);
        }


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
}