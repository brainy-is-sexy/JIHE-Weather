package com.android.activity;


import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.android.R;

public class LoadActivity extends AppCompatActivity {
    private final int time = 3000;
    private boolean lag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //延时3秒后，将进入登录页面
                if(lag){
                    finish();
                    Intent intent = new Intent(LoadActivity.this , LoginActivity.class);
                    startActivity(intent);
                }
            }
        } , time);

        Button button = (Button) findViewById(R.id.button);
          //给按钮添加监听事件，当点击时，直接进入登录页面
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Intent intent = new Intent(LoadActivity.this , LoginActivity.class);
                startActivity(intent);
                lag = false;
            }
        });
    }

}
