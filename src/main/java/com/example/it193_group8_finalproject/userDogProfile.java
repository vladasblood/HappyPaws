package com.example.it193_group8_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.it193_group8_finalproject.model.Dog;
import com.example.it193_group8_finalproject.retrofit.DogApi;
import com.example.it193_group8_finalproject.retrofit.RetrofitService;

import com.squareup.picasso.Picasso;


import java.net.URI;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class userDogProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dogprofile);

        TextView setDogName = (TextView) findViewById(R.id.txt_user_dogname);
        TextView setDogSex = (TextView) findViewById(R.id.out_user_dogsex);
        TextView setDogAge = (TextView) findViewById(R.id.out_user_dogage);
        ImageView setDogImage = (ImageView) findViewById(R.id.img_user_dogimg);

        Button returnButton = (Button) findViewById(R.id.btn_user_returntolist);
        Button adoptButton = (Button) findViewById(R.id.btn_user_adoptdog);

        Intent intent = getIntent();
        int selectedId = intent.getIntExtra("chosenDog", 0);

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

        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adoptionPage.class);
                startActivity(intent);
            }
        });

        adoptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dogApi.getAllDogs().enqueue(new Callback<List<Dog>>() {
                    @Override
                    public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                        if(response.isSuccessful()){
                            List<Dog> allDogs = response.body();

                            for(Dog dog: allDogs){
                                if(selectedId == dog.getDog_id()){

                                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(dog.getDog_href())));
                                }
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<List<Dog>> call, Throwable t) {

                    }
                });


            }
        });



    }
}