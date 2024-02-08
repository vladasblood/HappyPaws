package com.example.it193_group8_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.it193_group8_finalproject.model.User;
import com.example.it193_group8_finalproject.retrofit.RetrofitService;
import com.example.it193_group8_finalproject.retrofit.UserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adminLanding extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_landing);

        Button btn_admin_adoptlist=(Button) findViewById(R.id.btn_admin_adoptlist);
        Button btn_admin_userlist=(Button) findViewById(R.id.btn_admin_userlist);
        Button btn_admin_logout=(Button) findViewById(R.id.btn_admin_logout);

        TextView tvAdmin = (TextView) findViewById(R.id.out_admin_welcome);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String adminNameBanner = sharedPreferences.getString("adminName", "D");

        tvAdmin.setText(adminNameBanner);

        btn_admin_adoptlist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), adminDogList.class);
                startActivity(intent);
            }
        });

        btn_admin_userlist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), adminUserList.class);
                startActivity(intent);
            }
        });

        btn_admin_logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });
    }
}