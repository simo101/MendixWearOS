package com.example.conner.mendixwearos;

import android.content.Intent;
import android.os.Bundle;
import android.support.wearable.activity.WearableActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends WearableActivity {

    private EditText etUrl;
    private EditText etUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        etUrl = findViewById(R.id.editText);
        etUserName = findViewById(R.id.editText2);

        etUrl.setText(((MyApplication) this.getApplication()).getBaseUrl());
        etUserName.setText(((MyApplication) this.getApplication()).getUserName());

        // Enables Always-on
        setAmbientEnabled();
    }

    public void onClickBack(View v){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public void onClickSave(View v){
        ((MyApplication) this.getApplication()).setBaseUrl(etUrl.getText().toString());
        ((MyApplication) this.getApplication()).setUserName(etUserName.getText().toString());
        this.onClickBack(v);
    }
}
