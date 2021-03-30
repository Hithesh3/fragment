package com.example.fragmentlist;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ListApi {
    @GET("posts")
    Call<List<ListModel>> getList();
}
