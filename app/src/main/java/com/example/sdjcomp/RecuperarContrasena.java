package com.example.sdjcomp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecuperarContrasena extends Fragment {
    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private EditText txtPregunta;
    private EditText txtPassword;
    private Button btnModificar;
    private Spinner spnPreguntas;
    private String URL="";
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/updateUser/";
        View v = inflater.inflate(R.layout.fragment_modificar_usuario,container,false);
        txtPregunta = v.findViewById(R.id.txtPregunta);
        btnModificar = v.findViewById(R.id.btnRecuperarClave);
        spnPreguntas = v.findViewById(R.id.spnPreguntas);
        txtPassword = v.findViewById(R.id.txtPassword);
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
                        listaPreguntasNom.add(i.getPregunta());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                            listaPreguntasNom);
                    spnPreguntas.setAdapter(adapter);
                }
            }



            @Override
            public void onFailure(Call<List<Pregunta>> call, Throwable t) {
                System.out.println("Preguntas No Encontradas");
            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int preguntaF = spnPreguntas.getSelectedItemPosition()+1;
                Usuario objU = new Usuario(txtPassword.getText().toString(),preguntaF,
                        txtPregunta.getText().toString());
                HashMap<String,Usuario> map = new HashMap<>();
                Call<Number> call = iRetrofit.executeRegister(objU);
                call.enqueue(new Callback<Number>() {
                    @Override
                    public void onResponse(Call<Number> call, Response<Number> response) {
                        if(response.code()==200){
                            if(Integer.parseInt(String.valueOf(response.body()))==1){
                                Toast.makeText(getContext(), "Contraseña modificada", Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                    @Override
                    public void onFailure(Call<Number> call, Throwable t) {
                        System.out.println("t = " + t);
                        Toast.makeText(getContext(), "Contraseña no modificada", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return v;
    }
}