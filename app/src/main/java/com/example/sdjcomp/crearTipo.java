package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class crearTipo extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private EditText edtTipo;
    private Button btnCrear,btnVolver;

    public crearTipo() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/crearTipo/";
        View v = inflater.inflate(R.layout.fragment_crear_tipo,container,false);
        edtTipo = v.findViewById(R.id.edtCrearTipo);
        btnCrear = v.findViewById(R.id.btnCrearTipoA);
        btnVolver = v.findViewById(R.id.btnVolverCrearTipo);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        Tipo objT = new Tipo();

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                objT.setTipo(edtTipo.getText().toString());
                Call<Number> call = iRetrofit.executeCrearTipo(objT);
                call.enqueue(new Callback<Number>() {
                    @Override
                    public void onResponse(Call<Number> call, Response<Number> response) {
                        Snackbar.make(v, "Tipo Creado", Snackbar.LENGTH_LONG).show();
                        NavHostFragment.findNavController(crearTipo.this)
                                .navigate(R.id.action_crearTipo_to_admTipos);
                    }

                    @Override
                    public void onFailure(Call<Number> call, Throwable t) {
                        Snackbar.make(v, "No se pudo crear el tipo", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(crearTipo.this)
                        .navigate(R.id.action_crearTipo_to_admTipos);
            }
        });



        return v;
    }
}