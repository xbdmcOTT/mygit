package com.example.logintest;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button reg;
    Button cha;
    TextView user;
    EditText username;
    TextView textView;
    EditText password;
    Button Login;
    Button pre;
    MyDBHelper myDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        myDBHelper = new MyDBHelper(this, "UserData", null, 1);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, register.class);
                startActivity(intent);
            }
        });//注册按钮响应

        cha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, change.class);
                startActivity(intent);
            }
        });//修改密码按钮响应

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Username = username.getText().toString().trim();
                String Password = password.getText().toString().trim();
                if (Username.isEmpty() || Password.isEmpty()) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("mistake").setMessage("账号或密码不能为空")
                            .setPositiveButton("确定", null).show();
                } else {
                    LoginApp(Username, Password);

                }

            }
        });//登录按钮响应


        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
                alertDialog.setMessage("确定要退出程序吗");
                alertDialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // android.os.Process.killProcess(android.os.Process.myPid());
                        System.exit(0);
                    }
                });
                alertDialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alertDialog1 = alertDialog.create();
                alertDialog1.show();

            }

        });
    }
    // 退出响应

    private void init() {
        cha = (Button) findViewById(R.id.Cha);
        reg = (Button) findViewById(R.id.register);
        user = (TextView) findViewById(R.id.user);
        username = (EditText) findViewById(R.id.username);
        textView = (TextView) findViewById(R.id.oldpwd);
        password = (EditText) findViewById(R.id.oldpassword);
        Login = (Button) findViewById(R.id.login);
        pre = (Button) findViewById(R.id.out);
    }//初始化



    public  void  LoginApp(String Name, String Password) {
        SQLiteDatabase db = myDBHelper.getWritableDatabase();
        String Qurey = "select * from Users where name=? ";
        Cursor cursor = db.rawQuery(Qurey, new String[]{Name});
            if (cursor.moveToFirst()) {
                String passwordString = cursor.getString(cursor.getColumnIndex("password"));

                if (Password.equals(passwordString)) {
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("正确").setMessage("登陆成功")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent intent = new Intent();
                                    Bundle bundle = new Bundle();
                                    String nameString = username.getText().toString();
                                    bundle.putString("name", nameString);
                                    intent.putExtras(bundle);
                                    intent.setClass(MainActivity.this, Choose.class);
                                    startActivity(intent);
                                    username.setText("");
                                    password.setText("");
                                }
                            }).show();
                } else {
                    Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                }

            }
            else {
                Toast.makeText(this, "用户名错误", Toast.LENGTH_SHORT).show();
            }

            cursor.close();
        }


}//登录检测



