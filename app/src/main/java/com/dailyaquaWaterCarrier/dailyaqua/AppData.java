package com.dailyaquaWaterCarrier.dailyaqua;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class AppData {
    public static  final String URLPath="http://apanaquiz.com/api/";
    public static  final String LoginAPI="http://35.169.229.140:8006/WebService/UserServices.asmx/";
    public static  final String RegistrationAPI="saveregister";

    public static  final String SHAREDPREF="DAquaPref";
    public static  final String SHAREDPREFUSERID="UserId";
    public static  final String SHAREDPREFLOGGEDIN="LoggedIn";
    public static  final String SHAREDPREFREGISTERED="Registered";
    public static  final String SHAREDPREFOTPPENDING="OTPPending";
    public static  final String SHAREDPREFUSERSTATUS="0";

    //SHAREDPREFUSERSTATUS==> 0=registerScreen,1=otpPendingScreen,2=loginScreen,3=homeScreen

    //Messages
    public static  final String ASYNCFAILEDMESSAGE="Some network error occurred";
    public static  final String ASYNCEXCEPTIONMESSAGE="Some error occurred";
    public static  final String SHAREDPREFMESSAGE="Unable to fetch data";
    public static  final String GENERICMESSAGE="Some issues occurred";
    public static  final String INVALIDREPORTLINK="Invalid report to load";
    public static  final String NOINTERNETMESSAGE="No internet connection";
    public static  final String INVALIDCREDENTIALS="Invalid username/password";
    public static  final String LOGINFORMVALIDATIONMESSAGE="Please enter UserName and Password";
    public static  final String REGISTRATIONVALIDATIONMESSAGE="All fields are required";
    public static  final String REGISTRATIONEMPTYVALIDATIONMESSAGE="Empty validation failed";



    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        } else {
            return false;
        }
    }

}