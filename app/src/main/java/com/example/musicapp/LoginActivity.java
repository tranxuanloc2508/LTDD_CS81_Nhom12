package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    EditText edt_Phone,edt_Pass;
    Button btn_Login;
    String Phone,Password,userpassword;

    ProgressDialog LoadingBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        LoadingBar = new ProgressDialog(this);
        edt_Phone = (EditText)findViewById(R.id.login_phone);
        edt_Pass = (EditText)findViewById(R.id.login_password);
        btn_Login = (Button)findViewById(R.id.btn_login);

        LoadingBar.setTitle("Login Account");
        LoadingBar.setMessage("Pleasr wait");
        LoadingBar.setCanceledOnTouchOutside(false);

        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Phone = edt_Phone.getText().toString();
                Password = edt_Pass.getText().toString();
                LoginAccount(Phone,Password);
            }
        });
    }

    private void LoginAccount(String phone, String password) {
        //kiem tra editext co dung hay ko
        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please enter your phone number", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password))
        {
            Toast.makeText(this, "Please enter your password", Toast.LENGTH_SHORT).show();
        }
        else {

            LoadingBar.show();

            final DatabaseReference mRef;
            mRef = FirebaseDatabase.getInstance().getReference();
            mRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    //kiem tra edittext trong khong, sau do kiem tra sdt co ton tai hay ko
                    //neu ton tai thi truy xuat toi password trong csdl
                    if (snapshot.child("Users").child(Phone).exists())
                    {
                        //neu password co trong database
                        userpassword =snapshot.child("Users").child(Phone).child("password").getValue().toString();
                        //kiem tra
                        if(Password.equals(userpassword))
                        {
                            //di toi musiclibrary activity//de tao moi activity
                            LoadingBar.dismiss();
                            Intent i = new Intent(LoginActivity.this,MusicLibActivity.class);
                            startActivity(i);

                            // nếu người dùng tồn tại thì sẽ chuuyeen tới libmusic
                        }

                        else {
                            // nếu users tồn tại nhưng mà password sai
                            LoadingBar.dismiss();
                            Toast.makeText(LoginActivity.this, "please enter valid password", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        //users ko tồn tại
                        LoadingBar.dismiss();
                        Toast.makeText(LoginActivity.this, "User with this number dose not exits", Toast.LENGTH_SHORT).show();
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }

}