package com.dailyaquaWaterCarrier.dailyaqua;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.regex.Pattern;


public class OTP_Registration extends Activity {

    EditText edtOTP;
    Button btnVerify;
    TextView txtvwResend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp__registration);
        edtOTP=findViewById(R.id.editOTP);
        btnVerify=findViewById(R.id.btnVerify);
        txtvwResend=findViewById(R.id.editOTP);

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String validationMsg=validateFields();
                if (!validationMsg.isEmpty()) return;
                if (!isValidOTP(edtOTP.getText().toString().trim()))
                {
                    edtOTP.setError( "Enter 4 digit mobile number!" );
                    return;
                }
                if(!AppData.isOnline(OTP_Registration.this))
                {
                    Toast.makeText(OTP_Registration.this,AppData.NOINTERNETMESSAGE,Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
        txtvwResend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

            }
        });
    }
    private String validateFields()
    {
        //editName,editEmail,editpwd,editphone;
        String message="";
        if( edtOTP.getText().toString().length() == 0 ){  edtOTP.setError( "OTP cannot be empty !" ); message=AppData.REGISTRATIONEMPTYVALIDATIONMESSAGE;};
        return  message;
    }
    private boolean isValidOTP(String phone)
    {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        {
            if((phone.length()==4))  check = true;
        }
        return check;
    }

}


