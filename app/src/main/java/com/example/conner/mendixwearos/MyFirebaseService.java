package com.example.conner.mendixwearos;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

public class MyFirebaseService extends FirebaseInstanceIdService {
    private static final String TAG = "MyFirebaseService";
    public MyFirebaseService() {

    }

    @Override
    public void onTokenRefresh(){
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, refreshedToken);
        sendRegistrationToServer(refreshedToken);
        FirebaseMessaging.getInstance().subscribeToTopic("mxtest");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseMessaging.getInstance().subscribeToTopic("mxtest");
        Log.d(TAG, "subscribed");
    }

    private void sendRegistrationToServer(String token){
        // TODO
        // [] Post to mendix app to identify myself
    }

}
