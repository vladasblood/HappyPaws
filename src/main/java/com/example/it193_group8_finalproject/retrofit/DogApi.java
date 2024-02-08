package com.example.it193_group8_finalproject.retrofit;

import com.example.it193_group8_finalproject.model.Dog;
import com.example.it193_group8_finalproject.model.User;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
public interface DogApi {
    @GET("/dogs")
    Call<List<Dog>> getAllDogs();

    @POST("/add-dog")
    Call<Dog> addDog(@Body Dog dog);

    @PUT("/update-dog")
    Call<Dog> updateDog(@Body Dog dog);

    @DELETE("/dogs/{id}")
    Call<Dog> deleteDog(@Path("id") int id);
}
