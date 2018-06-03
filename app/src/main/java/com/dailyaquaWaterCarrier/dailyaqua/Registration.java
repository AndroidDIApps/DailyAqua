package com.dailyaquaWaterCarrier.dailyaqua;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Registration extends Activity {

    TextView txtTrmsCndtn,txtvwLogin;
    Button btnRegister;
    EditText editName,editEmail,editpwd,editphone;
    private SharedPreferences mSharedPreferences;
    String response = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mSharedPreferences = getSharedPreferences(AppData.SHAREDPREF, Context.MODE_PRIVATE);

        txtTrmsCndtn=findViewById(R.id.txtvwTermsCndtn);
        txtvwLogin=findViewById(R.id.txtvwLogin);
        btnRegister=findViewById(R.id.btnRegister);
        editName=findViewById(R.id.editName);
        editEmail=findViewById(R.id.editEmail);
        editpwd=findViewById(R.id.editpwd);
        editphone=findViewById(R.id.editphone);

        String text = "<font color='black'>By continuing I agree to </font><font color='red'>Terms and conditions</font>";
        txtTrmsCndtn.setText(Html.fromHtml(text), TextView.BufferType.SPANNABLE);
        txtTrmsCndtn.setPaintFlags(txtTrmsCndtn.getPaintFlags() |   Paint.UNDERLINE_TEXT_FLAG);
        // add button listener
        txtTrmsCndtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                // custom dialog
                final Dialog dialog = new Dialog(Registration.this, android.R.style.DeviceDefault_Light_ButtonBar_AlertDialog);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.terms_condition);
                dialog.setTitle("Title...");
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
        txtvwLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent i = new Intent(Registration.this, Login.class);
                startActivity(i);
              //  finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String validationMsg=validateFields();
                if (!validationMsg.isEmpty()) return;
                if(!emailValidator(editEmail.getText().toString()))
                {
                    editEmail.setError( "Invalid Email!" );
                    return;
                }
                if (!isPwdValid(editpwd.getText().toString().trim()))
                {
                    editpwd.setError( "Password must be of minimum 6 characters!" );
                    return;
                }
                if (!isValidPhone(editphone.getText().toString().trim()))
                {
                    editphone.setError( "Enter 10 digit mobile number!" );
                    return;
                }
                if(!AppData.isOnline(Registration.this))
                {
                    Toast.makeText(Registration.this,AppData.NOINTERNETMESSAGE,Toast.LENGTH_LONG).show();
                    return;
                }
                registerAuthenticated(editName.getText().toString().trim(),editpwd.getText().toString(),
                                        editphone.getText().toString().trim(),editEmail.getText().toString().trim());

                mSharedPreferences=  Registration.this.getSharedPreferences(AppData.SHAREDPREF, 0); //
                SharedPreferences.Editor editor = mSharedPreferences.edit();
                editor.putString(AppData.SHAREDPREFUSERSTATUS,"1");
                // editor.putString("ClientId","1");
                editor.commit();
                Intent i = new Intent(Registration.this, OTP_Registration.class);
                startActivity(i);
                finish();
            }
        });
    }

    private String validateFields()
    {
        //editName,editEmail,editpwd,editphone;
        String message="";
        if( editName.getText().toString().length() == 0 ){  editName.setError( "Name is required !" ); message=AppData.REGISTRATIONEMPTYVALIDATIONMESSAGE;};
        if( editEmail.getText().toString().length() == 0 ) { editEmail.setError( "Email is required !" );message=AppData.REGISTRATIONEMPTYVALIDATIONMESSAGE;};
        if( editpwd.getText().toString().length() == 0 )  {editpwd.setError( "Password is required !" );message=AppData.REGISTRATIONEMPTYVALIDATIONMESSAGE;};
        if( editphone.getText().toString().length() == 0 ) { editphone.setError( "Phone is required !" );message=AppData.REGISTRATIONEMPTYVALIDATIONMESSAGE;};
        return  message;
    }
    private boolean emailValidator(String email)
    {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
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
    private boolean isPwdValid(String pwd)
    {
        boolean check=false;
        if((pwd.length()>=6))  check = true;
        return check;

    }

    private void registerAuthenticated(String name,String phone,String pwd,String email)
    {
        validateUserTask task = new validateUserTask();
        task.execute(new String[]{name,phone,pwd,email});
    }

    private class validateUserTask extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub
            String res = null;
            try {
                ArrayList<NameValuePair> postParameters = new ArrayList<NameValuePair>();
                postParameters.add(new BasicNameValuePair("Name", params[0] ));
                postParameters.add(new BasicNameValuePair("Number", params[1] ));
                postParameters.add(new BasicNameValuePair("Password", params[2] ));
                postParameters.add(new BasicNameValuePair("Email", params[3] ));
                response = CustomHttpClient.executeHttpPost( AppData.URLPath+AppData.RegistrationAPI, postParameters);
                res=response.toString();
                res= res.replaceAll("\\s+","");
            }
            catch (Exception e) {}
            return res;
        }//close doInBackground
        @Override
        protected void onPreExecute() {
            progressDialog = ProgressDialog.show(Registration.this,"","Please wait...");
        }

        @Override
        protected void onPostExecute(String result) {
            if (result!=null && result!=""){
                try
                {
                    JSONObject mainObject = new JSONObject(result);
                    String  userId = mainObject.getString("UserId");
                    if(userId=="null" || userId ==null)
                    {
                        Toast.makeText(Registration.this,AppData.INVALIDCREDENTIALS,Toast.LENGTH_LONG).show();
                    }
                    else
                    {
//                        pref=  Login.this.getSharedPreferences(AppData.SHAREDPREF, 0); //
//                        SharedPreferences.Editor editor = pref.edit();
//                        editor.putString(AppData.SHAREDPREFCLIENTID,String.valueOf(mainObject.getInt("ClientId")));
//                        editor.commit();
//                        Intent i = new Intent(Registration.this, Registration.class);
//                        startActivity(i);
//                        finish();
                    }
                }
                catch (Exception ex)
                {
                    Toast.makeText(Registration.this,AppData.ASYNCEXCEPTIONMESSAGE,Toast.LENGTH_LONG).show();
                }
            }
            else
            {
                Toast.makeText(Registration.this,AppData.ASYNCFAILEDMESSAGE,Toast.LENGTH_LONG).show();
            }
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }//close onPostExecute
    }// close validateUserTask


}
