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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class modificarRoles extends Fragment {

    private TextView txtPrueba,edtID;
    private EditText edtNombre;
    private String result;
    private int idmarca;
    private Button btnModificar,btnVolver;

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
        URL = "http://" + getResources().getString(R.string.IP) + ":3000/getMarca/";
        View v = inflater.inflate(R.layout.fragment_modificar_roles,container,false);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        edtID = v.findViewById(R.id.textView26);
        edtNombre = v.findViewById(R.id.edtRolModificar);
        btnModificar = v.findViewById(R.id.btnModificarRol);
        btnVolver = v.findViewById(R.id.btnVolvermodRoles);

        edtID.setText("Modificando Rol ID = "+String.valueOf(((Sesion)getActivity().getApplicationContext()).getIdRol()));
        idmarca=((Sesion)getActivity().getApplicationContext()).getIdRol();
        Call<Rol> call = iRetrofit.executeGetRol(((Sesion)getActivity().getApplicationContext()).getIdRol());
        call.enqueue(new Callback<Rol>() {
            @Override
            public void onResponse(Call<Rol> call, Response<Rol> response) {
                edtNombre.setText(response.body().getRol());
            }

            @Override
            public void onFailure(Call<Rol> call, Throwable t) {
                Toast.makeText(getContext(), "Problema inesperado para encontrar el rol", Toast.LENGTH_LONG).show();
            }
        });
        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Rol objR = new Rol(idmarca,
                        edtNombre.getText().toString());
                System.out.println("objeto =" + edtNombre.getText().toString());
                Call<Number> call1 = iRetrofit.executeUpdateRol(objR);
                call1.enqueue(new Callback<Number>() {
                    @Override
                    public void onResponse(Call<Number> call, Response<Number> response) {
                        Toast.makeText(getContext(), "Rol Actualizado", Toast.LENGTH_LONG).show();
                        NavHostFragment.findNavController(modificarRoles.this).
                              navigate(R.id.action_modificarRoles_to_roles);
                    }

                    @Override
                    public void onFailure(Call<Number> call, Throwable t) {

                    }
                });
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(modificarRoles.this).
                        navigate(R.id.action_modificarRoles_to_roles);
            }
        });
        return v;


    }
}