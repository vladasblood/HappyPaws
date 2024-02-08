package com.example.it193_group8_finalproject.retrofit;

import com.example.it193_group8_finalproject.model.User;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserApi {
    @GET("/users")
    Call<List<User>> getAllUsers();

    @POST("/add-user")
    Call<User> addUser(@Body User user);

    @PUT("/update-user")
    Call<User> updateUser(@Body User user);

    @DELETE("/users/{id}")
    Call<User> deleteUser(@Path("id") int id);
}
