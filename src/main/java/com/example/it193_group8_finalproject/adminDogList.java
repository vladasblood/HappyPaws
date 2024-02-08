package com.example.it193_group8_finalproject;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.it193_group8_finalproject.model.Dog;
import com.example.it193_group8_finalproject.retrofit.DogApi;
import com.example.it193_group8_finalproject.retrofit.RetrofitService;
import com.example.it193_group8_finalproject.dogListAdapter;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adminDogList extends AppCompatActivity {

    ListView dogListLV;
    List<Dog> allDogsList = new ArrayList<>();

    dogListAdapter dogAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_doglist);

        Button btn_admin_adddog = findViewById(R.id.btn_admin_adddog);
        Button btn_admin_doglisttolanding = findViewById(R.id.btn_admin_doglisttolanding);

        RetrofitService retrofitService = new RetrofitService();
        DogApi dogApi = retrofitService.getRetrofit().create(DogApi.class);
        dogListLV = findViewById(R.id.lv_admin_doglist);

        dogApi.getAllDogs().enqueue(new Callback<List<Dog>>() {
            @Override
            public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                if (response.isSuccessful()) {
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
                    Toast.makeText(adminDogList.this, "Failed to fetch dogs!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Dog>> call, Throwable t) {
                Toast.makeText(getBaseContext(), "Empty Dog List", Toast.LENGTH_SHORT).show();
            }
        });

        btn_admin_adddog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminDogProfileEdit.class);
                intent.putExtra("Id", 0);
                startActivity(intent);
            }
        });

        btn_admin_doglisttolanding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), adminLanding.class);
                startActivity(intent);
            }
        });
    }

    private void updateAdapter(List<Dog> dogs) {
        if (!dogs.isEmpty()) {
            dogAdapter = new dogListAdapter(adminDogList.this, dogs);
        } else {
            dogAdapter = new dogListAdapter(adminDogList.this, allDogsList);
        }
        dogListLV.setAdapter(dogAdapter);

        dogListLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int selectedDogId = dogs.get(position).getDog_id();

                Intent intent = new Intent(getApplicationContext(), adminDogProfile.class);
                intent.putExtra("selectedDog", selectedDogId);
                startActivity(intent);

                Toast.makeText(adminDogList.this, "You clicked on " + dogs.get(position).getDog_id(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isPageExisting(String dogHref) {
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
