package com.example.sdjcomp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.sdjcomp.databinding.InterfazEstudianteBinding;
import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InterfazEstudiante extends Fragment {

    private InterfazEstudianteBinding binding;

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";

    private TextView txtTitulo;
    private Button btnActualizarUsuario, btnVerRegistros, btnRegistrarBicicleta, btnVolver;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getUser/";
        View v = inflater.inflate(R.layout.interfaz_estudiante, container, false);


        txtTitulo = v.findViewById(R.id.lblTitulo);
        btnActualizarUsuario = v.findViewById(R.id.btnModificarUsuario);
        btnRegistrarBicicleta = v.findViewById(R.id.btnRegistrarBicicleta);
        btnVolver = v.findViewById(R.id.btnVolver);


        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        //ver registros

        btnVerRegistros = (Button) v.findViewById(R.id.btnVerRegistros);
        btnVerRegistros.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InterfazEstudiante.this)
                        .navigate(R.id.action_InterfazEstudiante_to_interfazBicicleta);
            }
        });

        btnRegistrarBicicleta = (Button) v.findViewById(R.id.btnRegistrarBicicleta);
        btnRegistrarBicicleta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InterfazEstudiante.this)
                        .navigate(R.id.action_InterfazEstudiante_to_registrarBicicleta);
            }
        });



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
                    txtTitulo.setText("Bienvenido "+response.body().getNombre());
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Snackbar.make(v, "La Bicicleta no se pudo asignar", Snackbar.LENGTH_LONG).show();
            }
        });

        btnActualizarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InterfazEstudiante.this)
                        .navigate(R.id.action_InterfazEstudiante_to_fragment_modificar_usuario);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((Sesion) getActivity().getApplicationContext()).setCorreo("");
                ((Sesion) getActivity().getApplicationContext()).setClave("");
                ((Sesion) getActivity().getApplicationContext()).setValidado(false);

                NavHostFragment.findNavController(InterfazEstudiante.this)
                        .navigate(R.id.action_InterfazEstudiante_to_Home);
            }
        });


        binding = InterfazEstudianteBinding.inflate(inflater, container, false);
        return v;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void eliminar()
    {
        URL = "http://"+getResources().getString(R.string.IP)+":3000/deleteUser/";
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
    }

}