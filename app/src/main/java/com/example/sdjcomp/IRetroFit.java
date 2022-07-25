package com.example.sdjcomp;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface IRetroFit {
    @POST("/login")
    Call<PreLoginUsuario> executeLogin(@Body HashMap<String,String> map);

    @POST("/registerUser")
    Call<Usuario> executeRegister(@Body Usuario map);

    @GET("/getUser/{correo}")
    Call<Usuario> executeGetUser(@Path("correo") String correo);
}
