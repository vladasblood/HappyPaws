package com.example.it193_group8_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class userLanding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_landing);

        Button btn_user_adopt=(Button) findViewById(R.id.btn_user_adoptlist);
        Button btn_user_logout=(Button) findViewById(R.id.btn_user_logout);
        TextView userPrompt = (TextView) findViewById(R.id.out_user_welcome);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String userNameBanner = sharedPreferences.getString("userName", "D");

        userPrompt.setText(userNameBanner);

        btn_user_adopt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), adoptionPage.class);
                startActivity(intent);
            }
        });

        btn_user_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });
    }
}