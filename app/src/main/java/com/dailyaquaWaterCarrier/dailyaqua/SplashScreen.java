package com.dailyaquaWaterCarrier.dailyaqua;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 3000;
    private SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        mSharedPreferences = getSharedPreferences(AppData.SHAREDPREF, Context.MODE_PRIVATE);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(!AppData.isOnline(SplashScreen.this))
                {
                    Toast.makeText(SplashScreen.this,AppData.NOINTERNETMESSAGE,Toast.LENGTH_LONG).show();
                    finish();
                }
                redirectToActivity();
//                Intent i = new Intent(SplashScreen.this, Activity_Home.class);
//                startActivity(i);
//                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void redirectToActivity()
    {
        if (mSharedPreferences.contains(AppData.SHAREDPREFUSERSTATUS)) {
            String sharedPrefVal = (mSharedPreferences.getString(AppData.SHAREDPREFUSERSTATUS, "0"));
            if (sharedPrefVal.equals("0")) {
                Intent i = new Intent(SplashScreen.this, Registration.class);
                startActivity(i);
                finish();
            }
            else if(sharedPrefVal.equals("1"))
            {
                Intent i = new Intent(SplashScreen.this, OTP_Registration.class);
                startActivity(i);
                finish();
            }
            else if(sharedPrefVal.equals("2"))
            {
                Intent i = new Intent(SplashScreen.this, Login.class);
                startActivity(i);
                finish();
            }
            else if(sharedPrefVal.equals("3"))
            {
                //Redirect to Home screen
               // Intent i = new Intent(SplashScreen.this, Registration.class);
                //startActivity(i);
                //finish();
            }
        }
        else {
            Intent i = new Intent(SplashScreen.this, Activity_Home.class);
            startActivity(i);
            finish();
        }
    }
}