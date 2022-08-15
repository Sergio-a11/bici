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

public class registrarMarca extends Fragment {

    private EditText edtNombre;
    private String result;

    private Button btnVolver,btnCrear;

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL = "http://" + getResources().getString(R.string.IP) + ":3000/createMarca/";
        View v = inflater.inflate(R.layout.fragment_registrar_marca,container,false);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        edtNombre = v.findViewById(R.id.edtNomCrearMarca);
        btnVolver = v.findViewById(R.id.btnVolverCrearMarca);
        btnCrear = v.findViewById(R.id.btnCrearMarcaAdm);

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Marca objM = new Marca(edtNombre.getText().toString());
                System.out.println("objM = " + objM);
                Call<Number> call = iRetrofit.executeRegisterMarca(objM);
                call.enqueue(new Callback<Number>() {
                    @Override
                    public void onResponse(Call<Number> call, Response<Number> response) {
                        NavHostFragment.findNavController(registrarMarca.this).
                                navigate(R.id.action_registrarMarca_to_admMarcas);
                    }

                    @Override
                    public void onFailure(Call<Number> call, Throwable t) {
                        Toast.makeText(getContext(), "No se pudo crear la marca", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });



        return v;
    }
}