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


public class modificarMarca extends Fragment {

    private TextView txtPrueba;
    private EditText edtID,edtNombre;
    private String result;

    private Button btnVolver,btnModificar;

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
        View v = inflater.inflate(R.layout.modificar_marca,container,false);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        edtID = v.findViewById(R.id.edtIDModificarMarca);
        edtNombre = v.findViewById(R.id.edtNombreModificarMarca);
        btnVolver = v.findViewById(R.id.btnVolverModificarMarca);
        btnModificar = v.findViewById(R.id.btnModificarMarca);

        txtPrueba = v.findViewById(R.id.txtPruebaMarca);

        txtPrueba.setText(String.valueOf(((Sesion)getActivity().getApplicationContext()).getIdMarca()));

        Call<Marca> call = iRetrofit.executeGetMarcas(((Sesion)getActivity().getApplicationContext()).getIdMarca());
        call.enqueue(new Callback<Marca>() {
            @Override
            public void onResponse(Call<Marca> call, Response<Marca> response) {
                edtID.setText(String.valueOf(response.body().getId()));
                edtNombre.setText(response.body().getMarca());
            }

            @Override
            public void onFailure(Call<Marca> call, Throwable t) {
                Toast.makeText(getContext(), "Problema inesperado para encontrar la marca", Toast.LENGTH_LONG).show();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Marca objM = new Marca(Integer.parseInt(edtID.getText().toString()),edtNombre.getText().toString());
                Call<Number> call1 = iRetrofit.executeUpdateMarca(objM);
                call1.enqueue(new Callback<Number>() {
                    @Override
                    public void onResponse(Call<Number> call, Response<Number> response) {
                        Toast.makeText(getContext(), "Marca Actualizada", Toast.LENGTH_LONG).show();
                        NavHostFragment.findNavController(modificarMarca.this).
                                navigate(R.id.action_modificarMarca_to_admMarcas);
                    }

                    @Override
                    public void onFailure(Call<Number> call, Throwable t) {
                        Toast.makeText(getContext(), "No se pudo actualizar la marca", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(modificarMarca.this).
                        navigate(R.id.action_modificarMarca_to_admMarcas);
            }
        });

        return v;
    }
}