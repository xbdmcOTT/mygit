package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

public class Choose extends AppCompatActivity {

    TextView textView;
    Button mus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        Bundle bundle=new Bundle();
        bundle=getIntent().getExtras();
        String name =bundle.getString("name");
        textView.setText(name+",登陆成功");

        mus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Choose.this, MusicPlayer.class);
                startActivity(intent);
            }
        });//播放音乐按钮响应
    }

    private void init() {
        textView=(TextView)findViewById(R.id.textView5);
        mus = (Button) findViewById(R.id.button3);
    }//初始化
}


