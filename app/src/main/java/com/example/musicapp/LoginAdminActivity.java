package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginAdminActivity extends AppCompatActivity {

    private EditText eName;
    private EditText ePass;
    private Button eLogin;

    private String UserName = "Admin";
    private String PassWord = "123456";
    boolean co = false;
    private int counter = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);
        eName = findViewById(R.id.login_name);
        ePass =findViewById(R.id.login_password);
        eLogin = findViewById(R.id.btn_login);
        eLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String inputName= eName.getText().toString();
                String inputPass = ePass.getText().toString();
                if(inputName.isEmpty()||inputPass.isEmpty())
                {
                    Toast.makeText(LoginAdminActivity.this, "Please enter all ", Toast.LENGTH_SHORT).show();
                }
                else {
                    co =validate(inputName,inputPass);
                    if (!co)
                    {

                        counter--;
                        Toast.makeText(LoginAdminActivity.this, "Incorrect", Toast.LENGTH_SHORT).show();
                        if(counter==0)
                        {
                            eLogin.setEnabled(false);
                        }
                    }
                    else {

                        Toast.makeText(LoginAdminActivity.this, "Login successful   ", Toast.LENGTH_SHORT).show();

                        Intent i = new Intent(LoginAdminActivity.this,LoginActivity.class);
                        startActivity(i);
                    }
                }
            }
        });
    }
    private boolean validate  (String name, String pass)
    {
        if(name.equals(UserName)&&pass.equals(PassWord))
        {
            return true;
        }

        return false;
    }
}