package com.example.it193_group8_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.it193_group8_finalproject.model.User;
import com.example.it193_group8_finalproject.retrofit.RetrofitService;
import com.example.it193_group8_finalproject.retrofit.UserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);

        Button btnLogin=(Button) findViewById(R.id.btnLogin);
        Button btnSignin=(Button) findViewById(R.id.btnSignin);
        EditText usernameInput = findViewById(R.id.inputUsername);
        EditText passwordInput = findViewById(R.id.inputPassword);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor passName = sharedPreferences.edit();


        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String username = String.valueOf(usernameInput.getText());
                String password = String.valueOf(passwordInput.getText());

                User xUser = new User(username, password, "user");

                userApi.getAllUsers().enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.isSuccessful()){

                            List<User> allUsers = response.body();

                            //Error Handling - Username not found not working, but Wrong password is working

                            for(User yUser: allUsers){
                                if(xUser.getUsername().equals(yUser.getUsername())){
                                    if(xUser.getPassword().equals(yUser.getPassword())){

                                        String userAuth = "user";
                                        String adminAuth = "admin";

                                        if(yUser.getRole().equals(userAuth)){
                                            Toast.makeText(getBaseContext(), "User Login Successful.", Toast.LENGTH_SHORT).show();
                                            passName.putString("userName", xUser.getUsername());
                                            passName.commit();
                                            Intent intent = new Intent(getApplicationContext(), userLanding.class);
                                            startActivity(intent);
                                        }

                                        if(yUser.getRole().equals(adminAuth)){
                                            Toast.makeText(getBaseContext(), "Admin Login Successful.", Toast.LENGTH_SHORT).show();
                                            passName.putString("adminName", xUser.getUsername());
                                            passName.commit();
                                            Intent intent = new Intent(getApplicationContext(), adminLanding.class);
                                            startActivity(intent);
                                        }

                                    } else {
                                        Toast.makeText(getBaseContext(), "Wrong Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Failed to Connect", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Network Error", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnSignin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),signin.class);
                startActivity(intent);
            }
        });
    }
}