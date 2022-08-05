package com.android.miniaccount.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.miniaccount.MainActivity;
import com.android.miniaccount.R;

public class LoginActivity extends AppCompatActivity {

    Button btnLogin;
    EditText editTextName;
    EditText editTextPassword;
    TextView textViewRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogin = (Button) this.findViewById(R.id.buttonLogin);
        editTextName = (EditText)this.findViewById(R.id.editTextName);
        editTextPassword = (EditText)this.findViewById(R.id.editTextPassword);
        textViewRegister = (TextView)findViewById(R.id.textViewRegister);

        SharedPreferences sp = this.getSharedPreferences("MiniAccount", Context.MODE_PRIVATE);

        String name = sp.getString("name",null);
        editTextName.setText(name);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Login();
            }
        });
        textViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
    }

    private void Login(){
        String name = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        SharedPreferences sp = this.getSharedPreferences("MiniAccount", Context.MODE_PRIVATE);

        if(name.equals(sp.getString("name_"+name,null)) &&
                password.equals(sp.getString("password_"+name,null))){
            finish();
            Intent intent = new Intent();
            intent.setClass(this, MainActivity.class);
            startActivity(intent);
        }else if(!name.equals(sp.getString("name_"+name,null))){
            Toast.makeText(this, "用户名不存在,请注册!", Toast.LENGTH_LONG).show();
        }else if(!password.equals(sp.getString("password_"+name,null))){
            String prompt = sp.getString("prompt_"+name,null);
            Toast.makeText(this, "密码错误! 提示词为:"+prompt, Toast.LENGTH_LONG).show();
        }
    }
    private void Register(){
        Intent intent = new Intent();
        intent.setClass(this, RegisterActivity.class);
        startActivity(intent);
    }


}