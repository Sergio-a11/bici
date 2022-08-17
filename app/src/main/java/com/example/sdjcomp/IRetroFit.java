package com.example.sdjcomp;

import java.util.HashMap;
import java.util.List;

import kotlin.io.path.ExperimentalPathApi;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface IRetroFit {

// Usuarios

    @POST("/login")
    Call<PreLoginUsuario> executeLogin(@Body HashMap<String,String> map);

    @POST("/registrarUsuario")
    Call<Number> executeRegister(@Body Usuario map);

    @GET("/getUser/{correo}")
    Call<Usuario> executeGetUser(@Path("correo") String correo);

    @PUT("/updateUser")
    Call<Usuario> executeUpdateUser(@Body Usuario codigo);

    @POST("/updatePassword")
    Call<Number> executeUpdatePassword(@Body Usuario codigo);

    @DELETE("/deleteUser/{codigo}")
    Call<Number> executeDeleteUser(@Path("codigo") String codigo);
    
    @PATCH("/updateOne/{palabras}")
    Call<Number> executeUpdateOneUser(@Path("palabras") String palabras);

    //BICICLETAS
    @GET("/getBikes/{codigo}")
    Call<List<Bicicleta>> executeGetBikes(@Path("codigo") String codigo);

    @GET("/getBike/{codigo}/{id}")
    Call<Bicicleta> executeGetBike(@Path("codigo") String codigo, @Path("id") int id);

    @GET("/getOne/{palabras}")
    Call<Usuario> executeGetUserByCode(@Path("palabras") String palabras);

    @POST("/registerParqueadero/{parqueadero}")
    Call<Parqueadero> executeCreateParqueadero(@Path("parqueadero") String parqueadero);

    @GET("/getAll/{tabla}")
    Call<List<Pregunta>> executeGetAll(@Path("tabla") String tabla);

    //Cupo
    @GET("/getCupo/{seccion}")
    Call<List<Cupo>> executeGetCuposEnable(@Path("seccion") String seccion);

    @DELETE("/deleteCupos/{id}")
    Call<Number> executeDeleteCupos(@Path("id") Number id);

    @GET("/getSlots/")
    Call<List<Cupo>> executeGetCupos();
    //Parqueadero
    @GET("/getParqueadero/{seccion}")
    Call<List<Bicicleta>> executeGetParqueaderos(@Path("seccion") String seccion);

    @GET("/getBikeForDesasignar/{numSerie}")
    Call<BicicletaParaBorrar> executeGetBikeForDelete(@Path("numSerie") String numSerie);
    @DELETE("/deleteParqueadero/{idBicicleta}")
    Call<Number> executeDeleteParqueadero(@Path("idBicicleta") Number idBicicleta);
    @GET("/getMarcas")
    Call<List<Marca>> executeGetMarca();

    @GET("/getParqueaderos")
    Call<List<Parqueadero>> executeGetadmParqueaderos();

    @GET("/getTipos")
    Call<List<Tipo>> executeGetTipos();

    @GET("/getRoles")
    Call<List<Rol>> executeGetRoles();

    @GET("/getadmBicicletas")
    Call<List<Bicicleta>> executeGetadmbicicletas();

    @GET("/getUsers")
    Call<List<Usuario>> executeGetUsers();

    @POST("/registerBike")
    Call<Number> executeRegisterBike(@Body BicicletaRegistrar bici);

    @PUT("/updateBicicleta")
    Call<Number> executeUpdateBicicleta(@Body BicicletaRegistrar bici);

    @DELETE("/deleteBicicleta/{idBicicleta}")
    Call<Number> executeDeleteBicicleta(@Path("idBicicleta") int idBicicleta);

    //reportes
    @GET("/getControlParqueaderos")
    Call<List<ControlParqueaderos>> executeGetControlParquederos();

    @GET("/getMarca/{id}")
    Call<Marca> executeGetMarcas(@Path("id") Number id);

    @PUT("/updateMarca")
    Call<Number> executeUpdateMarca(@Body Marca marca);

    @DELETE("/deleteMarca/{id}")
    Call<Number> executeDeleteMarca(@Path("id") Number id);

    @POST("/createMarca")
    Call<Number> executeRegisterMarca(@Body Marca marca);

    @PUT("/updateParqueadero/{palabras}")
    Call<Number> executeUpdateParqueadero(@Path("palabras") String palabras);

    @GET("/getbicicletasSinUso")
    Call<List<Bicicleta>> executeGetBICISinUso();
    @PUT("/updateCupo")
    Call<Number> executeUpdateCupo(@Body Cupo cupo);

    @GET("/getCupoa/{id}")
    Call<Cupo> executeGetCupoa(@Path("id") Number id);

    @DELETE("/deletePregunta/{id}")
    Call<Number> executeDeletePregunta(@Path("id") Number id);

    @POST("/createCupo")
    Call<Number> executeRegisterCupo(@Body Cupo map);

    @POST("/createPregunta")
    Call<Number> executeRegisterPregunta(@Body Pregunta map);

    @POST("/createRol")
    Call<Number> executeRegisterRol(@Body Rol map);

    @DELETE("/deleteRol/{id}")
    Call<Number> executeDeleteRol(@Path("id") Number id);

    @GET("/getRol/{id}")
    Call<Rol> executeGetRol(@Path("id") Number id);

    @PUT("/updateRol")
    Call<Number> executeUpdateRol(@Body Rol rol);

    @GET("/getPregunta/{id}")
    Call<Pregunta> executeGetPregunta(@Path("id") Number id);

    @PUT("/updatePregunta")
    Call<Number> executeUpdatePregunta(@Body Pregunta pregunta);

    @POST("/crearTipo/{tipo}")
    Call<Number> executeCrearTipo(@Body Tipo tipo);

    @PUT("/updateTipo")
    Call<Number> executeUpdateTipo(@Body Tipo tipo);

    @DELETE("/deleteTipo/{id}")
    Call<Number> executeDeleteTipo(@Path("id") String id);
}
