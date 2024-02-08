package com.example.it193_group8_finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.it193_group8_finalproject.model.Dog;
import com.example.it193_group8_finalproject.retrofit.DogApi;
import com.example.it193_group8_finalproject.retrofit.RetrofitService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adoptionPage extends AppCompatActivity {

    ListView dogListLV;
    List<Dog> allDogsList = new ArrayList<>();

    dogListAdapter dogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_dogadoptionlist);

        Button btn_user_returnLanding=(Button) findViewById(R.id.btn_user_doglisttolanding);


        RetrofitService retrofitService = new RetrofitService();
        DogApi dogApi = retrofitService.getRetrofit().create(DogApi.class);
        dogListLV = findViewById(R.id.lv_user_adoptionlist);

        dogApi.getAllDogs().enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                if(response.isSuccessful()){
                    List<Dog> allDogs = response.body();

                    new AsyncTask<List<Dog>, Void, List<Dog>>() {
                        @Override
                        protected List<Dog> doInBackground(List<Dog>... params) {
                            List<Dog> existingDogs = new ArrayList<>();
                            for (Dog dog : params[0]) {
                                if (isPageExisting(dog.getDog_href())) {
                                    existingDogs.add(dog);
                                } else {
                                    dogApi.deleteDog(dog.getDog_id()).enqueue(new Callback<Dog>() {
                                        @Override
                                        public void onResponse(Call<Dog> call, Response<Dog> response) {}
                                        @Override
                                        public void onFailure(Call<Dog> call, Throwable t) {}
                                    });
                                }
                            }
                            return existingDogs;
                        }

                        @Override
                        protected void onPostExecute(List<Dog> existingDogs) {
                            super.onPostExecute(existingDogs);
                            updateAdapter(existingDogs);
                        }
                    }.execute(allDogs);


                } else {
                    Toast.makeText(adoptionPage.this, "Failed to fetch dogs!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Dog>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Empty Dog List", Toast.LENGTH_SHORT).show();
            }
        });

        btn_user_returnLanding.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(), userLanding.class);
                startActivity(intent);
            }
        });
    }

    private void updateAdapter(List<Dog> dogs) {
        if (!dogs.isEmpty()) {
            dogAdapter = new dogListAdapter(adoptionPage.this, dogs);
        } else {
            dogAdapter = new dogListAdapter(adoptionPage.this, allDogsList);
        }
        dogListLV.setAdapter(dogAdapter);

        dogListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedDogId = dogs.get(position).getDog_id();

                Intent intent = new Intent(getApplicationContext(), userDogProfile.class);
                intent.putExtra("chosenDog", selectedDogId);
                startActivity(intent);
            }
        });
    }

    private boolean isPageExisting(String dogHref){
        try {
            URL url = new URL(dogHref);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            int responseCode = connection.getResponseCode();

            return (responseCode >= 200 && responseCode < 300);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
}