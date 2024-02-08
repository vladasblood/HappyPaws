package com.example.it193_group8_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.it193_group8_finalproject.model.User;
import com.example.it193_group8_finalproject.retrofit.RetrofitService;
import com.example.it193_group8_finalproject.retrofit.UserApi;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class signin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin_page);

        Button btnCreateAccount=(Button) findViewById(R.id.btn_createaccount);
        Button btnReturnLogin=(Button) findViewById(R.id.btn_returnlogin);
        EditText usernameInput = findViewById(R.id.in_createusername);
        EditText passwordInput = findViewById(R.id.in_createpassword);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        btnCreateAccount.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){

                String username = String.valueOf(usernameInput.getText());
                String password = String.valueOf(passwordInput.getText());

                userApi.getAllUsers().enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {

                        if(response.isSuccessful()){
                            List<User> allUsers = response.body();
                            boolean usernameExists = false;

                            for(User xUser: allUsers) {
                                if (username.equals(xUser.getUsername())) {
                                    usernameExists = true;
                                    Toast.makeText(getBaseContext(), "Username Already Existed!", Toast.LENGTH_SHORT).show();
                                    break;
                                }
                            }

                            if(!usernameExists){

                                User user = new User(username, password, "user");

                                userApi.addUser(user).enqueue(new Callback<User>() {
                                    @Override
                                    public void onResponse(Call<User> call, Response<User> response) {
                                        Toast.makeText(getBaseContext(), "Welcome!", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(getApplicationContext(), login.class);
                                        startActivity(intent);
                                    }
                                    @Override
                                    public void onFailure(Call<User> call, Throwable t) {
                                        Toast.makeText(getBaseContext(), "Failed!", Toast.LENGTH_SHORT).show();
                                        Logger.getLogger(signin.class.getName()).log(Level.SEVERE, "Error");
                                    }
                                });

                                }
                            }
                        }
                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(getBaseContext(), "Failed User Signup", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnReturnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),login.class);
                startActivity(intent);
            }
        });
    }


}