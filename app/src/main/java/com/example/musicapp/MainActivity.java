package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    LinearLayout L1,L2;
    TextView tv_tag;

    Animation DowntoTop,Fade;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();

    }

    private void AnhXa() {
        L1= findViewById(R.id.l1);
        L2= findViewById(R.id.l2);
        tv_tag = findViewById(R.id.tv_tag);

        DowntoTop = AnimationUtils.loadAnimation(this,R.anim.downtotop);
        Fade = AnimationUtils.loadAnimation(this,R.anim.fade);

        L2.setAnimation(DowntoTop);
        tv_tag.setAnimation(Fade);

        final Intent i = new Intent(MainActivity.this,HomeActivity.class);

        Thread thread = new Thread(){

            public void run(){
                try {
                    sleep(5000);//mo app trong 5s

                }catch (InterruptedException e)
                {
                    e.printStackTrace();
                }finally {
                    {
                        startActivity(i);
                        finish();
                    }
                }
            }

        };thread.start();

    }
}