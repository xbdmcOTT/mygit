package com.example.logintest;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class change extends AppCompatActivity {
    TextView user;
    EditText name;
    TextView pwd;
    EditText password;
    TextView newpwd;
    EditText newpassword;
    Button Change;
    Button previous;
    MyDBHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change);
        init();
        dbHelper=new MyDBHelper(this,"UserData",null,1);
        Change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = (name.getText()).toString().trim();
                String Password = (password.getText()).toString().trim();
                String NPassword = (newpassword.getText()).toString().trim();
                if (username.isEmpty() || Password.isEmpty() || NPassword.isEmpty())
                    Toast.makeText(change.this, "输入信息不全", Toast.LENGTH_SHORT).show();
                else if (CheckIsAlreadyInDatabase(username))
                {
                        Toast.makeText(change.this, "用户不存在", Toast.LENGTH_SHORT).show();
                    }
                else if (Password.equals(NPassword))
                {
                    Toast.makeText(change.this, "新密码不能和旧密码相同", Toast.LENGTH_SHORT).show();
                }else  if(Change(username, NPassword))
                {
                    Toast.makeText(change.this, "修改成功", Toast.LENGTH_SHORT).show();
                    name.setText("");
                    password.setText("");
                    newpassword.setText("");
                }

                }


        });//修改密码信息检测

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });//返回登录页面

    }

    private  void init(){
        user=(TextView)findViewById(R.id.user);
        name=(EditText) findViewById(R.id.username);
        pwd=(TextView)findViewById(R.id.oldpwd);
        password=(EditText) findViewById(R.id.oldpassword);
        newpwd=(TextView)findViewById(R.id.newpwd);
        newpassword =(EditText) findViewById(R.id.newpassword);
        Change=(Button) findViewById(R.id.Change);
        previous=(Button) findViewById(R.id.button);
    }//初始化
    public boolean CheckIsAlreadyInDatabase(String value ){
        SQLiteDatabase db =dbHelper.getWritableDatabase();
        String Query ="select * from Users where name=?";
        Cursor cursor= db.rawQuery(Query,new String[]{value});
        if(cursor.moveToFirst()){
            cursor.close();
            return false;
        }
        cursor.close();
        return true;

    }//判断用户是否存在
    public boolean Change(String name ,String password ){
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values .put("name",name);
        values.put("password",password);
        db.insert("Users",null,values);
        db.close();
        return true;
    }//插入新密码信息
}
