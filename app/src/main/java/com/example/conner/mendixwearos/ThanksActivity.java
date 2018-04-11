package com.example.conner.mendixwearos;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.TextView;

public class ThanksActivity extends WearableActivity {

//    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        // Enables Always-on
        setAmbientEnabled();
    }

    public void onClickAnotherButton(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
