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

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Registro#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Registro extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";

    private EditText edtCodigo;
    private EditText edtNombre;
    private EditText edtCorreo;
    private EditText edtClave;
    private EditText edtRespuesta;
    private Spinner spnPreguntas;
    private Button btnRegistrar, btnVolver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/registrarUsuario/";
        View v = inflater.inflate(R.layout.fragment_registro, container, false);
        edtCodigo = (EditText) v.findViewById(R.id.edtCodigo);
        edtNombre = (EditText) v.findViewById(R.id.edtNombre);
        edtCorreo = (EditText) v.findViewById(R.id.edtCorreo);
        edtClave = (EditText) v.findViewById(R.id.edtClave);
        edtRespuesta = (EditText) v.findViewById(R.id.edtRespuesta);
        btnRegistrar = (Button) v.findViewById(R.id.btnRegistrarUsuario);
        spnPreguntas = (Spinner) v.findViewById(R.id.spnPreguntas);
        btnVolver = (Button) v.findViewById(R.id.btnVolverRegistro);

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
                Snackbar.make(v, "Preguntas No Encontradas", Snackbar.LENGTH_LONG).show();
            }
        });



        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtCodigo.getText().toString().isEmpty() &&
                   !edtNombre.getText().toString().isEmpty() &&
                   !edtCorreo.getText().toString().isEmpty() &&
                   !edtClave.getText().toString().isEmpty() &&
                   !edtRespuesta.getText().toString().isEmpty())
                {
                    int preguntaF = spnPreguntas.getSelectedItemPosition()+1;

                    Usuario objU = new Usuario(edtCodigo.getText().toString(),edtNombre.getText().toString(),
                            edtCorreo.getText().toString(),edtClave.getText().toString(),
                            preguntaF,edtRespuesta.getText().toString(),2);

                    HashMap<String,Usuario> map = new HashMap<>();

                    map.put("usuario",objU);
                    System.out.println("objU PS = " + objU.getPseguridad());
                    System.out.println("objU RS = " + objU.getRseguridad());
                    System.out.println("objU rol = " + objU.getRol_id());

                    Call<Number> call = iRetrofit.executeRegister(objU);
                    call.enqueue(new Callback<Number>() {
                        @Override
                        public void onResponse(Call<Number> call, Response<Number> response) {
                            if(response.code()==200){
                                if(Integer.parseInt(String.valueOf(response.body()))==1){
                                    Snackbar.make(v, "Usuario Registrado Con Exito", Snackbar.LENGTH_LONG).show();
                                    NavHostFragment.findNavController(Registro.this).navigate(R.id.action_fragment_registro_to_Home);
                                }
                            }else if(response.code()==412){
                                Snackbar.make(v, "Este usuario ya existe", Snackbar.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Number> call, Throwable t) {
                            Snackbar.make(v, "Usuario No Registrado", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Snackbar.make(v, "Debe Rellenar Todos los campos", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Registro.this).navigate(R.id.action_fragment_registro_to_Home);
            }
        });

        return v;
    }
}