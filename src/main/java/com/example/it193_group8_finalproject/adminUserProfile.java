package com.example.it193_group8_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.it193_group8_finalproject.model.Dog;
import com.example.it193_group8_finalproject.model.User;
import com.example.it193_group8_finalproject.retrofit.DogApi;
import com.example.it193_group8_finalproject.retrofit.RetrofitService;
import com.example.it193_group8_finalproject.retrofit.UserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adminUserProfile extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_userprofile);

        TextView username = findViewById(R.id.out_admin_username);
        TextView role = findViewById(R.id.out_admin_userrole);
        Button makeAdmin = findViewById(R.id.btn_admin_toadmin);
        Button makeUser = findViewById(R.id.btn_admin_touser);
        Button returnButton = findViewById(R.id.btn_admin_userprofiletouserlist);

        Intent intent = getIntent();
        int selectedId = intent.getIntExtra("selectedUser", 0);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        userApi.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    List<User> allUsers = response.body();

                    for(User user: allUsers){
                        if(selectedId == user.getUser_id()){

                            username.setText(user.getUsername());
                            role.setText(user.getRole());

                        }
                    }

                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        makeAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userApi.getAllUsers().enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.isSuccessful()){
                            int userSelectedPosition = -1;

                            List<User> allUser = response.body();

                            for(int i = 0; i < allUser.size(); i++){
                                User currentUser = allUser.get(i);
                                if(selectedId == currentUser.getUser_id()){
                                    userSelectedPosition = i;
                                    break;
                                }
                            }

                            User xUser = allUser.get(userSelectedPosition);
                            xUser.setRole("admin");
                            userApi.updateUser(xUser).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    Toast.makeText(getBaseContext(), "User to Admin", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), adminUserProfile.class);
                                    intent.putExtra("selectedUser", selectedId);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });
            }
        });

        makeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userApi.getAllUsers().enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.isSuccessful()){
                            int userSelectedPosition = -1;

                            List<User> allUser = response.body();

                            for(int i = 0; i < allUser.size(); i++){
                                User currentUser = allUser.get(i);
                                if(selectedId == currentUser.getUser_id()){
                                    userSelectedPosition = i;
                                    break;
                                }
                            }

                            User xUser = allUser.get(userSelectedPosition);
                            xUser.setRole("user");
                            userApi.updateUser(xUser).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
                                    Toast.makeText(getBaseContext(), "Admin to User", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), adminUserProfile.class);
                                    intent.putExtra("selectedUser", selectedId);
                                    startActivity(intent);
                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {

                                }
                            });
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {

                    }
                });
            }
        });

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminUserList.class);
                startActivity(intent);
            }
        });
    }
}
