package com.example.alex.sharespace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class MenuActivity extends AppCompatActivity implements View.OnClickListener{

    private Button shareSpace, rentSpace, profileSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        shareSpace = (Button) findViewById(R.id.shareSpace);
        rentSpace = (Button) findViewById(R.id.rentSpace);
        profileSettings = (Button) findViewById(R.id.profileSettings);

        shareSpace.setOnClickListener(this);
        rentSpace.setOnClickListener(this);
        profileSettings.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == rentSpace){

        }
        if (v == shareSpace){
            startActivity(new Intent(this,UploadActivity.class));
        }
        if (v == profileSettings){

            startActivity(new Intent(this,ProfileActivity.class));
        }
    }
}
