package com.example.it193_group8_finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.it193_group8_finalproject.model.Dog;
import com.example.it193_group8_finalproject.retrofit.DogApi;
import com.example.it193_group8_finalproject.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class adminDogProfileEdit extends AppCompatActivity {

    RadioButton radioButton;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_editdog);

        EditText dog_name_input = (EditText) findViewById(R.id.dogNameInput);
        EditText dog_image_input = (EditText) findViewById(R.id.dogImageInput);
        EditText dog_age_input = (EditText) findViewById(R.id.dogAgeInput);
        EditText dog_href_input = (EditText) findViewById(R.id.dogHrefInput);
        TextView dog_edit_or_add = (TextView) findViewById(R.id.out_admin_addoredit);

        radioGroup = findViewById(R.id.dogSexInput);
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);

        Button btn_admin_editcommit = (Button) findViewById(R.id.btn_admin_editcommit);
        Button btn_admin_dogedittodogprofile = (Button) findViewById(R.id.btn_admin_dogedittodogprofile);

        Intent intent = getIntent();
        int layerId = intent.getIntExtra("Id", 0);

        if(layerId == 0) {

            RetrofitService retrofitService = new RetrofitService();
            DogApi dogApi = retrofitService.getRetrofit().create(DogApi.class);

            dog_edit_or_add.setText("Add");

            btn_admin_editcommit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    String selectedSex = "";

                    if (radioId == R.id.radio_one)
                        selectedSex = "Male";
                    else if (radioId == R.id.radio_two)
                        selectedSex = "Female";

                    Dog dog = new Dog(String.valueOf(dog_name_input.getText()), String.valueOf(dog_image_input.getText()), selectedSex,
                            Integer.parseInt(dog_age_input.getText().toString()), String.valueOf(dog_href_input.getText()));

                    dogApi.addDog(dog).enqueue(new Callback<Dog>() {
                        @Override
                        public void onResponse(Call<Dog> call, Response<Dog> response) {
                            Toast.makeText(getBaseContext(), "Dog Added!", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(Call<Dog> call, Throwable t) {
                            Toast.makeText(getBaseContext(), "Dog Failed!", Toast.LENGTH_SHORT).show();
                        }
                    });

                    Intent intent = new Intent(getApplicationContext(), adminDogList.class);
                    startActivity(intent);
                }
            });

            btn_admin_dogedittodogprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), adminDogList.class);
                    startActivity(intent);
                }
            });
        }
        else {

            dog_edit_or_add.setText("Edit");

            RetrofitService retrofitService = new RetrofitService();
            DogApi dogApi = retrofitService.getRetrofit().create(DogApi.class);

            dogApi.getAllDogs().enqueue(new Callback<List<Dog>>() {
                @Override
                public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                    if(response.isSuccessful()){
                        List<Dog> allDogs = response.body();

                        for(Dog dog: allDogs){
                            if(layerId == dog.getDog_id()){


                                dog_name_input.setText(dog.getDog_name());

                                if(dog.getDog_sex().equals("Female"))
                                    radioGroup.check(R.id.radio_two);
                                else if(dog.getDog_sex().equals("Male"))
                                    radioGroup.check(R.id.radio_one);

                                dog_age_input.setText(String.valueOf(dog.getDog_age()));
                                dog_image_input.setText(dog.getDog_img());
                                dog_href_input.setText(String.valueOf(dog.getDog_href()));
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

            btn_admin_editcommit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    dogApi.getAllDogs().enqueue(new Callback<List<Dog>>() {
                        @Override
                        public void onResponse(Call<List<Dog>> call, Response<List<Dog>> response) {
                            if(response.isSuccessful()){

                                int dogSelectedPosition = -1;

                                List<Dog> allDogs = response.body();

                                for(int i = 0; i < allDogs.size(); i++){
                                    Dog currentDog = allDogs.get(i);
                                    if(layerId == currentDog.getDog_id()){
                                        dogSelectedPosition = i;
                                        break;
                                    }
                                }

                                    Dog xDog = allDogs.get(dogSelectedPosition);
                                    xDog.setDog_name(String.valueOf(dog_name_input.getText()));
                                    xDog.setDog_age(Integer.parseInt(String.valueOf(dog_age_input.getText())));

                                    int radioIdx = radioGroup.getCheckedRadioButtonId();
                                    radioButton = findViewById(radioIdx);

                                    if (radioIdx== R.id.radio_one)
                                        xDog.setDog_sex("Male");
                                    else if (radioIdx == R.id.radio_two)
                                        xDog.setDog_sex("Female");

                                    xDog.setDog_img(String.valueOf(dog_image_input.getText()));
                                    xDog.setDog_href(String.valueOf(dog_href_input.getText()));

                                    dogApi.updateDog(xDog).enqueue(new Callback<Dog>() {
                                        @Override
                                        public void onResponse(Call<Dog> call, Response<Dog> response) {
                                            Toast.makeText(getBaseContext(), "Updated Dog", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(getApplicationContext(), adminDogProfile.class);
                                            intent.putExtra("selectedDog", layerId);
                                            startActivity(intent);
                                        }

                                        @Override
                                        public void onFailure(Call<Dog> call, Throwable t) {
                                            Toast.makeText(getBaseContext(), "Failed to Update Dog", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                            } else {
                                Toast.makeText(getBaseContext(), "Dog not found", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<List<Dog>> call, Throwable t) {
                        }
                    });

                }
            });

            btn_admin_dogedittodogprofile.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(layerId > 0){
                        Intent intent = new Intent(getApplicationContext(), adminDogProfile.class);
                        intent.putExtra("selectedDog", layerId);
                        startActivity(intent);
                    }
                    else{
                        Intent intent = new Intent(getApplicationContext(), adminDogList.class);
                        startActivity(intent);
                    }
                }
            });
        }
    }
}