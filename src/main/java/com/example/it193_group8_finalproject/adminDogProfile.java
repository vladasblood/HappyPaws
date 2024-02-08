package com.example.it193_group8_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.it193_group8_finalproject.model.Dog;
import com.example.it193_group8_finalproject.retrofit.DogApi;
import com.example.it193_group8_finalproject.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adminDogProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_dogprofile);

        TextView setDogName = (TextView) findViewById(R.id.txt_admin_dogname);
        TextView setDogSex = (TextView) findViewById(R.id.out_admin_dogsex);
        TextView setDogAge = (TextView) findViewById(R.id.out_admin_dogage);
        ImageView setDogImage = (ImageView) findViewById(R.id.img_admin_dogimg);

        Button editButton = (Button) findViewById(R.id.btn_admin_edit);
        Button returnButton = (Button) findViewById(R.id.btn_admin_dogprofiletodoglist);
        Button deleteButton = (Button) findViewById(R.id.btn_admin_deletedog);

        Intent intent = getIntent();
        int selectedId = intent.getIntExtra("selectedDog", 0);

        RetrofitService retrofitService = new RetrofitService();
        DogApi dogApi = retrofitService.getRetrofit().create(DogApi.class);

        dogApi.getAllDogs().enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                if(response.isSuccessful()){
                    List<Dog> allDogs = response.body();

                    for(Dog dog: allDogs){
                        if(selectedId == dog.getDog_id()){

                            setDogName.setText(dog.getDog_name());
                            setDogSex.setText(dog.getDog_sex());
                            setDogAge.setText(String.valueOf(dog.getDog_age()));
                            Picasso.get().load(dog.getDog_img()).into(setDogImage);
                        }
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Dog not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Dog>> call, Throwable t) {
            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminDogProfileEdit.class);
                intent.putExtra("Id", selectedId);
                startActivity(intent);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dogApi.deleteDog(selectedId).enqueue(new Callback<Dog>() {
                    @Override
                    public void onResponse(Call<Dog> call, Response<Dog> response) {

                    }

                    @Override
                    public void onFailure(Call<Dog> call, Throwable t) {

                    }
                });

                Toast.makeText(getBaseContext(), "Dog Deleted", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), adminDogList.class);
                startActivity(intent);
            }
        });


        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminDogList.class);
                startActivity(intent);
            }
        });


    }
}
