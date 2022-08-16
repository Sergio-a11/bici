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
import android.widget.ImageButton;
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

public class ModificarUsuario extends Fragment {

    private HomeBinding binding;
    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";

    private EditText edtNombre;
    private EditText edtClave;
    private EditText edtRespuesta;
    private Spinner spnPreguntas;
    private Button btnModificar, btnVolver;
    private ImageButton btnModificarPregunta,btnModificarNombre,btnModificarClave,btnModificarRespuesta;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/updateUser/";
        View v = inflater.inflate(R.layout.fragment_modificar_usuario,container,false);

        edtNombre = v.findViewById(R.id.edtNombreM);
        edtClave = v.findViewById(R.id.edtClaveM);
        edtRespuesta = v.findViewById(R.id.edtRespuestaM);
        spnPreguntas = v.findViewById(R.id.spnPreguntasM);
        btnModificar = v.findViewById(R.id.btnModificarUsuario);
        btnModificarNombre = v.findViewById(R.id.btnModificarNombre);
        btnModificarClave = v.findViewById(R.id.btnModificarClave);
        btnModificarRespuesta = v.findViewById(R.id.btnModificarRespuesta);
        btnModificarPregunta = v.findViewById(R.id.btnModificarPregunta);
        btnVolver = v.findViewById(R.id.btnVolverModUsuario);

        edtNombre.setText(((Sesion)getActivity().getApplicationContext()).getNombre());
        edtClave.setText(((Sesion)getActivity().getApplicationContext()).getClave());
        edtRespuesta.setText(((Sesion)getActivity().getApplicationContext()).getRseguridad());

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

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtNombre.getText().toString().isEmpty()&&
                    !edtClave.getText().toString().isEmpty()&&
                    !edtRespuesta.getText().toString().isEmpty())
                {
                    Usuario objU = new Usuario(((Sesion)getActivity().getApplicationContext()).getCodigo(),edtNombre.getText().toString(),
                            ((Sesion)getActivity().getApplicationContext()).getCorreo(),edtClave.getText().toString(),
                            spnPreguntas.getSelectedItemPosition()+1,edtRespuesta.getText().toString(),2);

                    Call<Usuario> call = iRetrofit.executeUpdateUser(objU);
                    call.enqueue(new Callback<Usuario>() {
                        @Override
                        public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                            if(response.code()==200){
                                Snackbar.make(v, "Usuario Modificado", Snackbar.LENGTH_LONG).show();
                                NavHostFragment.findNavController(ModificarUsuario.this).navigate(R.id.action_fragment_modificar_usuario_to_InterfazEstudiante);
                            }
                        }

                        @Override
                        public void onFailure(Call<Usuario> call, Throwable t) {
                            Snackbar.make(v, "No redirigio", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Snackbar.make(v, "Todos los campos deben estar llenos", Snackbar.LENGTH_LONG).show();
                }

            }
        });


        btnModificarNombre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtNombre.getText().toString().isEmpty()){
                    CambiarAtributo(2);
                }else{
                    Snackbar.make(v, "El campo Nombre debe estar lleno", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnModificarClave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtNombre.getText().toString().isEmpty()){
                    CambiarAtributo(3);
                }else{
                    Snackbar.make(v, "El campo Clave debe estar lleno", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnModificarRespuesta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtNombre.getText().toString().isEmpty()){
                    CambiarAtributo(4);
                }else{
                    Snackbar.make(v, "El campo Respuesta debe estar lleno", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnModificarPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CambiarAtributo(1);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(ModificarUsuario.this).navigate(R.id.action_fragment_modificar_usuario_to_InterfazEstudiante);
            }
        });

        return v;
    }

    public void CambiarAtributo(int i){
        String URLaux = "http://"+getResources().getString(R.string.IP)+":3000/updateOne/";
        retrofit = new Retrofit.Builder().baseUrl(URLaux).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        String valor,campo;
        String palabras="";
        int valorp=0;

        switch (i){
            case 1:{
                valorp = spnPreguntas.getSelectedItemPosition()+1;
                campo = "Pseguridad";
                 palabras= valorp+","+campo+","+((Sesion)getActivity().getApplicationContext()).getCodigo()+",usuarios";
                break;
            }
            case 2:{
                valor = edtNombre.getText().toString();
                campo = "nombre";
                 palabras= valor+","+campo+","+((Sesion)getActivity().getApplicationContext()).getCodigo()+",usuarios";
                break;
            }
            case 3:{
                valor = edtClave.getText().toString();
                campo = "clave";
                 palabras= valor+","+campo+","+((Sesion)getActivity().getApplicationContext()).getCodigo()+",usuarios";
                break;
            }
            case 4:{
                valor = edtRespuesta.getText().toString();
                campo = "Rseguridad";
                 palabras= valor+","+campo+","+((Sesion)getActivity().getApplicationContext()).getCodigo()+",usuarios";
                break;
            }
            default:{
                valor=null;
                campo=null;
                break;
            }
        }

        Call<Number> call = iRetrofit.executeUpdateOneUser(palabras);
        call.enqueue(new Callback<Number>() {
            @Override
            public void onResponse(Call<Number> call, Response<Number> response) {
                if(response.code()==200){
                    if(Integer.parseInt(String.valueOf(response.body()))==1){
                        Toast.makeText(getContext(), campo+" Modificado", Toast.LENGTH_LONG).show();
                        NavHostFragment.findNavController(ModificarUsuario.this).navigate(R.id.action_fragment_modificar_usuario_to_InterfazEstudiante);
                    }
                }
            }
            @Override
            public void onFailure(Call<Number> call, Throwable t) {
                Toast.makeText(getContext(), "Campo no modificado", Toast.LENGTH_LONG).show();
            }
        });
    }

}