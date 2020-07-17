package com.abhishek.architecturepattern_livedata_pagging_di;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.abhishek.architecturepattern_livedata_pagging_di.ui.PagingDemoAct;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
    }

    private void init() {
        Thread timer= new Thread()
        {
            public void run()
            {
                try
                {
                    //Display for 3 seconds
                    sleep(3000);
                }
                catch (InterruptedException e)
                {
                    // TODO: handle exception
                    e.printStackTrace();
                }
                finally
                {
                    //Goes to Activity  PagingDemoAct.java(STARTINGPOINT)
                    Intent intent =new Intent(SplashScreenActivity.this, PagingDemoAct.class);
                    finishAffinity();
                    startActivity(intent);
                }
            }
        };
        timer.start();
    }

}