package com.example.retrofitparttwo.network;

import com.example.retrofitparttwo.models.Post;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {

    @GET("posts")       //Here, "posts" is the End point
    Call<List<Post>> getAllPost();      // hare Post will be the model data class

}
