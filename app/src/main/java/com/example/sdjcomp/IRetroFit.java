package com.example.sdjcomp;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface IRetroFit {
    @POST("/login")
    Call<PreLoginUsuario> executeLogin(@Body HashMap<String,String> map);
}
