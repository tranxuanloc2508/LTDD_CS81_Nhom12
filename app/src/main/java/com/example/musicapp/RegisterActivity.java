package com.example.musicapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    EditText edt_phone,edt_name,edt_password;
    String Phone,Password,Name;
    Button btn_register;
    ProgressDialog loadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        edt_name = (EditText)findViewById(R.id.register_name);
        edt_phone=(EditText)findViewById(R.id.register_phone);
        edt_password= (EditText)findViewById(R.id.register_password);

        btn_register = (Button)findViewById(R.id.btn_register);

        loadingDialog = new ProgressDialog(this);

        loadingDialog.setTitle("Creating Acount...");
        loadingDialog.setMessage("Please wait...");
        loadingDialog.setCanceledOnTouchOutside(false);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Name =edt_name.getText().toString();
                Phone = edt_phone.getText().toString();
                Password= edt_password.getText().toString();


                CreateNewAcount(Name,Phone,Password);
            }
        });
    }

    private void CreateNewAcount(String name, String phone, String password) {
        //check

        if(TextUtils.isEmpty(phone)){
            Toast.makeText(this, "Please enter phone number....", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(this, "Please enter password....", Toast.LENGTH_SHORT).show();
        }
        else if(TextUtils.isEmpty(name)){
            Toast.makeText(this, "Please enter name....", Toast.LENGTH_SHORT).show();
        }
        else {

            // Tao account moi
            loadingDialog.show();
            final DatabaseReference mref;
            mref = FirebaseDatabase.getInstance().getReference();
            mref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!(snapshot.child("User").child(phone).exists())){
                        //neu user ko out se tao account moi vao database

                        HashMap<String,Object> userdata = new HashMap<>();
                        userdata.put("name",name);
                        userdata.put("phone",phone);
                        userdata.put("password",password);


                        mref.child("Users").child(phone).updateChildren(userdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    loadingDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Register success", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    loadingDialog.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Please Try again..", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                    else
                    {
                        //user out
                        loadingDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, "User with this number already exits", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}