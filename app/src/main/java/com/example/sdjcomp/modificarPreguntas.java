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

public class modificarPreguntas extends Fragment {

    private TextView edtID;
    private EditText edtPregunta;
    private String result;
    private int idmarca;
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
        URL = "http://" + getResources().getString(R.string.IP) + ":3000/getAll/preguntas/";
        View v = inflater.inflate(R.layout.fragment_modificar_preguntas,container,false);
        edtID = v.findViewById(R.id.txtModificarPregunta);
        edtPregunta = v.findViewById(R.id.edtModificarPregunta);
        btnModificar = v.findViewById(R.id.btnModrPregunta);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        edtID.setText("Modificando Marca ID = "+String.valueOf(((Sesion)getActivity().getApplicationContext()).getIdPreguntas()));
        idmarca=((Sesion)getActivity().getApplicationContext()).getIdPreguntas();
        Call<Pregunta> call = iRetrofit.executeGetPregunta(((Sesion)getActivity().getApplicationContext()).getIdPreguntas());
        call.enqueue(new Callback<Pregunta>() {
            @Override
            public void onResponse(Call<Pregunta> call, Response<Pregunta> response) {
                edtPregunta.setText(response.body().getPregunta());
            }

            @Override
            public void onFailure(Call<Pregunta> call, Throwable t) {
                Toast.makeText(getContext(), "Problema inesperado para encontrar la pregunta", Toast.LENGTH_LONG).show();
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Pregunta objP = new Pregunta(String.valueOf(idmarca),
                        edtPregunta.getText().toString());
                System.out.println("objeto " + edtPregunta.getText().toString());
                Call<Number> call1 = iRetrofit.executeUpdatePregunta(objP);
                System.out.println("objP = " + objP.getPregunta() + " " + objP.getCodigo());
                call1.enqueue(new Callback<Number>() {
                    @Override
                    public void onResponse(Call<Number> call, Response<Number> response) {
                        Toast.makeText(getContext(), "Pregunta Actualizada", Toast.LENGTH_LONG).show();
                        NavHostFragment.findNavController(modificarPreguntas.this).
                                navigate(R.id.action_modificarPreguntas_to_admPreguntas);
                    }

                    @Override
                    public void onFailure(Call<Number> call, Throwable t) {
                        Toast.makeText(getContext(), "No se pudo actualizar la pregunta", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return v;
    }
}