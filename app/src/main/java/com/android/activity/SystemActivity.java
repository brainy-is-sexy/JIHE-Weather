package com.android.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.MainActivity;
import com.android.R;
import com.android.database.DBManager;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
public class SystemActivity extends AppCompatActivity implements View.OnClickListener{
    TextView cacheTv,datv;
    ImageView backIv;
    private SharedPreferences pref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system);
        cacheTv = findViewById(R.id.more_tv_cache);
        backIv = findViewById(R.id.more_iv_back);
        datv=findViewById(R.id.more_tv_dashang);
        datv.setOnClickListener(this);
        cacheTv.setOnClickListener(this);
        backIv.setOnClickListener(this);
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.more_iv_back:
                finish();
                break;
            case R.id.more_tv_cache:
                clearCache();
                break;
            case R.id.bt_tuichu:
                Intent intent2 = new Intent(this, LoginActivity.class);
                startActivity(intent2);
                break;
            case R.id.more_tv_dashang:
                Dialog dialog = new Dialog(SystemActivity.this);
                dialog.setContentView(R.layout.qr_code);
                dialog.show();
                break;
        }
    }

    private void clearCache() {
        /* 清除缓存的函数*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示信息").setMessage("确定要删除所有缓存么？");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                DBManager.deleteAllInfo();
                Toast.makeText(SystemActivity.this,"已清除全部缓存！",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SystemActivity.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }).setNegativeButton("取消",null);
        builder.create().show();
    }

}
