package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLOutput;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class modificarCupos extends Fragment {

    private EditText edtSeccion,edtEstado;
    private TextView edtID;
    private Button btnVolver,btnModificar;
    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL = "";
    private int idcupo;
    public modificarCupos() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL = "http://" + getResources().getString(R.string.IP) + ":3000/getSlots/";
        View v = inflater.inflate(R.layout.fragment_modificar_cupos,container,false);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        edtID = v.findViewById(R.id.txtModificandoCupo);
        edtSeccion = v.findViewById(R.id.edtSeccion);
        edtEstado = v.findViewById(R.id.edtEstado);
        btnModificar = v.findViewById(R.id.btnModCupo);
        btnVolver = v.findViewById(R.id.btnVolverModificarMarca);

        Call<Cupo> call = iRetrofit.executeGetCupoa(((Sesion)getActivity().getApplicationContext()).getIdCupo());
        call.enqueue(new Callback<Cupo>() {
            @Override
            public void onResponse(Call<Cupo> call, Response<Cupo> response) {
                System.out.println("id cupo = " + String.valueOf(response.body().getIdCupo()));
                System.out.println("estado = " + response.body().getEstado());
                System.out.println("seccion = " + String.valueOf(response.body().getSeccion()));
                idcupo = response.body().getIdCupo();
                edtID.setText("Modificando el cupo ID : "+String.valueOf(response.body().getIdCupo()));
                edtEstado.setText(String.valueOf(response.body().getEstado()));
                edtSeccion.setText(String.valueOf(response.body().getSeccion()));
            }

            @Override
            public void onFailure(Call<Cupo> call, Throwable t) {
                Toast.makeText(getContext(), "Problema inesperado para encontrar el cupo", Toast.LENGTH_LONG).show();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cupo objC = new Cupo(idcupo,
                        edtSeccion.getText().toString(),
                        Integer.parseInt(edtEstado.getText().toString())
                );
                Call<Number> call1 = iRetrofit.executeUpdateCupo(objC);
                call1.enqueue(new Callback<Number>() {
                    @Override
                    public void onResponse(Call<Number> call, Response<Number> response) {
                        Toast.makeText(getContext(), "Cupo Actualizado", Toast.LENGTH_LONG).show();
                        NavHostFragment.findNavController(modificarCupos.this).
                                navigate(R.id.action_modificarCupos_to_admCupos);
                    }

                    @Override
                    public void onFailure(Call<Number> call, Throwable t) {
                        Toast.makeText(getContext(), "No se pudo actualizar el cupo", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(modificarCupos.this).
                        navigate(R.id.action_modificarCupos_to_admCupos);
            }
        });
        return v;
    }
}