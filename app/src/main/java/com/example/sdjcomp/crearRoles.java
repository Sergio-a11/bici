package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class crearRoles extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private EditText edtIdRol, edtRol;
    private Button btnCrear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getAll/roles/";
        View v = inflater.inflate(R.layout.fragment_crear_roles,container,false);
        btnCrear = v.findViewById(R.id.btnCrearRol);
        edtRol = v.findViewById(R.id.edtCrearRol);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rol objr = new Rol(edtRol.getText().toString());
                Call<Number> call = iRetrofit.executeRegisterRol(objr);
                call.enqueue(new Callback<Number>() {
                    @Override
                    public void onResponse(Call<Number> call, Response<Number> response) {
                        if(response.code()==200){
                            if(Integer.parseInt(String.valueOf(response.body()))==1){
                                Toast.makeText(getContext(), "Rol Registrado Con Exito", Toast.LENGTH_LONG).show();

                                NavHostFragment.findNavController(crearRoles.this).navigate(R.id.action_crearRoles_to_roles);
                            }
                        }else if(response.code()==412){
                            Toast.makeText(getContext(), "Rol ya existe", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Number> call, Throwable t) {

                    }
                });
            }
        });
        return v;
    }
}