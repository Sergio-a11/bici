package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InterfazAdministrador extends Fragment {

    private Button btnVerRegistrosCupos;
    private Button btnCerrarSesion;
    private Button btnAsignarCupo;
    private Button btnDeAsignarCupo;

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.interfaz_administrador,container,false);
        URL="http://"+getResources().getString(R.string.IP)+":3000/getUser/";
        btnCerrarSesion = v.findViewById(R.id.btnCerrarSesion);
        btnVerRegistrosCupos = v.findViewById(R.id.btnVerRegistrosCupos);
        btnAsignarCupo = v.findViewById(R.id.btnAsignarCupo);
        btnDeAsignarCupo = v.findViewById(R.id.btnDesAsignarCupo);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        Call<Usuario> call = iRetrofit.executeGetUser(((Sesion)getActivity().getApplicationContext()).getCorreo());
        call.enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                if(response.code()==200){
                    ((Sesion)getActivity().getApplicationContext()).setCodigo(response.body().getCodigo());
                    ((Sesion)getActivity().getApplicationContext()).setNombre(response.body().getNombre());
                    ((Sesion)getActivity().getApplicationContext()).setCorreo(response.body().getCorreo());
                    ((Sesion)getActivity().getApplicationContext()).setClave(response.body().getClave());
                    ((Sesion)getActivity().getApplicationContext()).setPseguridad(response.body().getPseguridad());
                    ((Sesion)getActivity().getApplicationContext()).setRseguridad(response.body().getRseguridad());
                    ((Sesion)getActivity().getApplicationContext()).setRol_id(response.body().getRol_id());

                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnVerRegistrosCupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InterfazAdministrador.this)
                        .navigate(R.id.action_interfaz_administrador_to_consultarCupo);
            }
        });

        btnAsignarCupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InterfazAdministrador.this)
                        .navigate(R.id.action_interfaz_administrador_to_asignarCupo);
            }
        });

        btnDeAsignarCupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InterfazAdministrador.this)
                        .navigate(R.id.action_interfaz_administrador_to_desasignarCupo);
            }
        });

        btnCerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Sesion) getActivity().getApplicationContext()).setCorreo("");
                NavHostFragment.findNavController(InterfazAdministrador.this)
                        .navigate(R.id.action_interfaz_administrador_to_Home);
            }
        });

        return v;
    }
}