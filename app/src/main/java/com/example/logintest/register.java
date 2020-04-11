package com.example.logintest;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class register extends AppCompatActivity {
    TextView user;
    EditText name;
    TextView pwd;
    EditText password;
    TextView textview;
    EditText newpassword;
    Button Register ;
    Button previous;
    MyDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        dbHelper=new MyDBHelper(this,"UserData",null,1);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = (name.getText()).toString().trim();
                String Password = (password.getText()).toString().trim();
                String NPassword = (newpassword.getText()).toString().trim();
                if (username.isEmpty() || Password.isEmpty() || NPassword.isEmpty())
                    Toast.makeText(register.this, "输入信息不全", Toast.LENGTH_SHORT).show();
               else if (Password.equals(NPassword)) {
                    if (CheckIsAlreadyInDatabase(username)) {
                        Toast.makeText(register.this, "用户已经存在", Toast.LENGTH_SHORT).show();
                    } else {
                                if (Register(username, Password)) {
                                    Toast.makeText(register.this, "注册成功", Toast.LENGTH_SHORT).show();
                                    name.setText("");
                                    password.setText("");
                                    newpassword.setText("");
                                }
                            }
                        } else {
                            Toast.makeText(register.this, "两次密码输入不一致", Toast.LENGTH_SHORT).show();

                        }


                    }



        });//注册信息检测

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });//返回登录页面
    }

    private  void init(){
        user=(TextView)findViewById(R.id.textView);
        name=(EditText) findViewById(R.id.editText);
        pwd=(TextView)findViewById(R.id.newpwd);
        password=(EditText) findViewById(R.id.editText2);
        textview=(TextView)findViewById(R.id.textView4);
       newpassword =(EditText) findViewById(R.id.editText4);
        Register=(Button) findViewById(R.id.Reg);
        previous=(Button) findViewById(R.id.button);
    }//初始化
    public boolean CheckIsAlreadyInDatabase(String value ){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String Query ="select * from Users where name=?";
        Cursor cursor= db.rawQuery(Query,new String[]{value});
        if(cursor.moveToFirst()){
            cursor.close();;
            return true;
        }
        cursor.close();
        return false ;

    }//判断用户是否存在
    public boolean Register(String name ,String password ){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values .put("name",name);
        values.put("password",password);
        db.insert("Users",null,values);
        db.close();
        return true;
    }//插入注册信息
}
