package com.android.miniaccount.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.miniaccount.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextPasswordMessage;
    private Button btRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName = (EditText)findViewById(R.id.editTextRegisterName);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword = (EditText)findViewById(R.id.editTextConfirmPassword);
        editTextPasswordMessage = (EditText)findViewById(R.id.editTextPasswordMessage);
        btRegister = (Button)this.findViewById(R.id.buttonRegister);

        editTextName.setText("lys");
        editTextPassword.setText("123456");
        editTextConfirmPassword.setText("123456");
        editTextPasswordMessage.setText("123456");

        editTextName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Judge();
            }
        });

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void Judge(){
        if(editTextName.getText().toString().trim().length() > 6){
            Toast.makeText(this, "用户名过长!", Toast.LENGTH_LONG).show();
        }
    }

    private void register() {
        String name = editTextName.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirm = editTextConfirmPassword.getText().toString().trim();
        String prompt = editTextPasswordMessage.getText().toString().trim();

        if(name.length() < 1){
            Toast.makeText(this,"昵称不能为空",Toast.LENGTH_LONG).show();
            return;
        }
        if(!password.equals(confirm)){
            Toast.makeText(this,"两次输入的密码不同",Toast.LENGTH_LONG).show();
            return;
        }

        SharedPreferences sp = this.getSharedPreferences("MiniAccount", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putString("name",name);
        editor.putString("password",password);
        editor.putString("prompt",prompt);
        editor.putString("name_"+name,name);
        editor.putString("password_"+name,password);
        editor.putString("prompt_"+name,prompt);
        editor.apply();

        finish();
        Intent intent = new Intent(this,LoginActivity.class);
        startActivity(intent);
    }
}