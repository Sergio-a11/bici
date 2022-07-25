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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InterfazEstudiante extends Fragment {

    private InterfazEstudianteBinding binding;

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="http://192.168.1.14:3000/getUser/";

    private TextView txtTitulo;
    private Button btnActualizarUsuario;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        View v = inflater.inflate(R.layout.interfaz_estudiante, container, false);


        txtTitulo = v.findViewById(R.id.lblTitulo);
        btnActualizarUsuario = v.findViewById(R.id.btnModificarUsuario);

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
                    txtTitulo.setText("Bienvenido "+response.body().getNombre());
                }

            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

        btnActualizarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InterfazEstudiante.this)
                        .navigate(R.id.action_InterfazEstudiante_to_fragment_modificar_usuario);
            }
        });


        binding = InterfazEstudianteBinding.inflate(inflater, container, false);
        return v;

    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(InterfazEstudiante.this)
                        .navigate(R.id.action_SecondFragment_to_FirstFragment);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

}