package com.example.jangbogo;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class sale_Finish_SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sale_finish_splash);
        //로딩화면 시작.
        Loadingstart();
    }
    private void Loadingstart(){
        Handler handler=new Handler();
        handler.postDelayed(new Runnable(){
            public void run(){
                Intent intent;
                int ontact = ((OrderActivity)OrderActivity.context_order).ontact;
                if(ontact == 1){
                    intent = new Intent(getApplicationContext(), MainActivity.class);
                }
                else{
                    intent = new Intent(getApplicationContext(),card_Ready_SplashActivity.class);
                }
                startActivity(intent);
                finish();
            }
        },2000);
    }
}