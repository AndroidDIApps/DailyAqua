package com.dailyaquaWaterCarrier.dailyaqua;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Pattern;

public class Login extends AppCompatActivity {

    TextView txtvwFrgtPwd;
    Button btnLogin;
    EditText editUsrName,editpwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        txtvwFrgtPwd = findViewById(R.id.txtvwFrgtPwd);
        btnLogin = findViewById(R.id.btnLogin);
        editUsrName = findViewById(R.id.editUsrName);
        editpwd = findViewById(R.id.editpwd);
        txtvwFrgtPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Login.this, Forgot_Password.class);
                startActivity(i);
                //finish();
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String validationMsg=validateFields();
                if (!validationMsg.isEmpty()) return;
                if (!isValidPhone(editUsrName.getText().toString().trim()))
                {
                    editUsrName.setError( "Enter 10 digit mobile number!" );
                    return;
                }
                if(!AppData.isOnline(Login.this))
                {
                    Toast.makeText(Login.this,AppData.NOINTERNETMESSAGE,Toast.LENGTH_LONG).show();
                    return;
                }
            }
        });
    }
    private String validateFields()
    {
        //editName,editEmail,editpwd,editphone;
        String message="";
        if( editUsrName.getText().toString().length() == 0 ){  editUsrName.setError( "Phone no cannot be empty !" ); message=AppData.REGISTRATIONEMPTYVALIDATIONMESSAGE;};
        if( editpwd.getText().toString().length() == 0 ) { editpwd.setError( "Password no cannot be empty !" );message=AppData.REGISTRATIONEMPTYVALIDATIONMESSAGE;};
        return  message;
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
}
