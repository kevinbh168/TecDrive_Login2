package com.brena.tecdrive_login.Interfaces;

import com.brena.tecdrive_login.Models.Posts;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPostsApi {

    @GET("publication/")
    Call<List<Posts>> getPosts();

}
