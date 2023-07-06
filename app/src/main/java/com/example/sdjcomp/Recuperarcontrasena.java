package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sdjcomp.databinding.HomeBinding;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Recuperarcontrasena extends Fragment {

    private HomeBinding binding;
    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private EditText txtCodigo;
    private EditText txtRespuesta;
    private EditText txtContraseña;
    private Spinner spnPregunta;
    private Button btnCambiarpw, btnVolver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/updateUser/";
        View v = inflater.inflate(R.layout.fragment_recuperarcontrasena,container,false);

        txtCodigo = v.findViewById(R.id.txtCodigoR);
        txtRespuesta = v.findViewById(R.id.txtRespuestaR);
        txtContraseña = v.findViewById(R.id.txtContrasenaR);
        btnCambiarpw = v.findViewById(R.id.btnCambiarPw);
        spnPregunta = v.findViewById(R.id.spnPreguntaR);
        btnVolver = v.findViewById(R.id.btnVolverRecuperar);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        ArrayList<Pregunta> listaPreguntas = new ArrayList<>();
        ArrayList<String> listaPreguntasNom = new ArrayList<>();

        Call<List<Pregunta>> call = iRetrofit.executeGetAll("preguntas");
        call.enqueue(new Callback<List<Pregunta>>() {
            @Override
            public void onResponse(Call<List<Pregunta>> call, Response<List<Pregunta>> response) {
                if(response.code()==200){
                    for(int i=0; i<response.body().size(); i++){
                        listaPreguntas.add(response.body().get(i));
                    }
                    for (Pregunta i: listaPreguntas) {
                        System.out.println(i.getPregunta());
                        listaPreguntasNom.add(i.getPregunta());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                            listaPreguntasNom);
                    spnPregunta.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Pregunta>> call, Throwable t) {
                Snackbar.make(v, "Preguntas No Encontradas", Snackbar.LENGTH_LONG).show();
            }
        });

        btnCambiarpw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario objU = new Usuario(txtCodigo.getText().toString(),
                        txtContraseña.getText().toString(), spnPregunta.getSelectedItemPosition()+1,
                        txtRespuesta.getText().toString());

                Call<Number> call = iRetrofit.executeUpdatePassword(objU);
                call.enqueue(new Callback<Number>() {
                    @Override
                    public void onResponse(Call<Number> call, Response<Number> response) {
                        if(response.code()==200){
                            if(Integer.parseInt(String.valueOf(response.body()))==1){
                                Snackbar.make(v, "Contraseña Modificada", Snackbar.LENGTH_LONG).show();
                                NavHostFragment.findNavController(Recuperarcontrasena.this).navigate(R.id.action_recuperarcontrasena_to_Home);
                            }
                        }
                        if(response.code()==412){
                            Snackbar.make(v, "Respuesta incorrecta", Snackbar.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Number> call, Throwable t) {
                        Snackbar.make(v, "Error", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Recuperarcontrasena.this).navigate(R.id.action_recuperarcontrasena_to_Home);
            }
        });

        return v;
    }
}