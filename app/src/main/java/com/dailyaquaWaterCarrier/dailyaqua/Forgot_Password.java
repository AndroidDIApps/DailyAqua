package com.dailyaquaWaterCarrier.dailyaqua;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Forgot_Password extends AppCompatActivity {

    Button btnSubmit;
    EditText editPhoneNo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        btnSubmit=findViewById(R.id.btnSubmit);
        editPhoneNo=findViewById(R.id.editPhoneNo);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {

                String validationMsg=validateFields();
                if (!validationMsg.isEmpty()) return;
                if (!isValidPhone(editPhoneNo.getText().toString().trim()))
                {
                    editPhoneNo.setError( "Enter 10 digit mobile number!" );
                    return;
                }
                if(!AppData.isOnline(Forgot_Password.this))
                {
                    Toast.makeText(Forgot_Password.this,AppData.NOINTERNETMESSAGE,Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
    private boolean isValidPhone(String phone)
    {
        boolean check=false;
        if(!Pattern.matches("[a-zA-Z]+", phone))
        {
            if((phone.length()==10))  check = true;
        }
        return check;
    }
    private String validateFields()
    {
        //editName,editEmail,editpwd,editphone;
        String message="";
        if( editPhoneNo.getText().toString().length() == 0 ){  editPhoneNo.setError( "Phone no cannot be empty !" ); message=AppData.REGISTRATIONEMPTYVALIDATIONMESSAGE;};
        return  message;
    }
}
