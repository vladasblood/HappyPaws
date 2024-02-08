package com.example.it193_group8_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.it193_group8_finalproject.model.Dog;
import com.example.it193_group8_finalproject.model.User;
import com.example.it193_group8_finalproject.retrofit.RetrofitService;
import com.example.it193_group8_finalproject.retrofit.UserApi;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adminUserList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userlist_page);

        Button btn_user_doglisttolanding = (Button) findViewById(R.id.btn_user_doglisttolanding);


        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        ListView userLV;
        List<User> allUserList;
        userLV = findViewById(R.id.lv_admin_userlist);

        userApi.getAllUsers().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                if(response.isSuccessful()){
                    List<User> allUserList = response.body();
                    ArrayAdapter<User> userLVAdapter = new ArrayAdapter<>(adminUserList.this, android.R.layout.simple_list_item_1, allUserList);
                    userLV.setAdapter(userLVAdapter);

                    userLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            int selectedUserId = allUserList.get(position).getUser_id();

                            Intent intent = new Intent(getApplicationContext(), adminUserProfile.class);
                            intent.putExtra("selectedUser",selectedUserId);
                            startActivity(intent);

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {

            }
        });

        btn_user_doglisttolanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminLanding.class);
                startActivity(intent);
            }
        });


    }
}