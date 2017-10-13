package com.fengpy.myapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @ description:
 * @ time: 2017/10/10.
 * @ author: peiyun.feng
 * @ email: fengpy@aliyun.com
 */

public class LoginActivity extends AppCompatActivity {
    private EditText userET,pwdET;
    private Button loginBtn;
    private SharedPreferences sharedPreferences;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    public void init() {
        userET = (EditText) findViewById(R.id.et_username);
        pwdET = (EditText) findViewById(R.id.et_password);
        loginBtn = (Button) findViewById(R.id.login);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!userET.getText().toString().equals("") && !pwdET.getText().toString().equals("")) {
                    sharedPreferences.edit().putString("username", userET.getText().toString()).commit();
                    sharedPreferences.edit().putString("password", pwdET.getText().toString()).commit();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "用户名或密码为空！", Toast.LENGTH_SHORT).show();
                }

            }
        });

        sharedPreferences = getSharedPreferences("zhuce", 0);

        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        if(!username.equals("")) {
            userET.setText(username);
        }

        if(!password.equals("")) {
            pwdET.setText(password);
        }

        if(!username.equals("") && !password.equals("")) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }

}
