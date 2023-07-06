package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class AsignarCupo extends Fragment {

    private EditText edtCodigo;
    private Spinner spnSeccion,spnCupos;
    private Button btnAsignar, btnVolver;

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/createCupo/";
        View v = inflater.inflate(R.layout.asignar_cupo,container,false);
        mostrarCupos(1);
        edtCodigo = v.findViewById(R.id.edtCodigoEnCupos);
        spnSeccion = v.findViewById(R.id.spnSeccionAsignar);
        btnAsignar = v.findViewById(R.id.btnAsignar);
        spnCupos = v.findViewById(R.id.spnCupos);
        btnVolver = v.findViewById(R.id.btnVolverAsignar);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);




        spnSeccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mostrarCupos(spnSeccion.getSelectedItemPosition()+1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAsignar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spnCupos.getSelectedItem()!=null){
                    if(!edtCodigo.getText().toString().isEmpty() ){
                        int seccion=spnSeccion.getSelectedItemPosition()+1;
                        int cupo=Integer.parseInt(spnCupos.getSelectedItem().toString());
                        String codigo = edtCodigo.getText().toString();
                        ((Sesion)getActivity().getApplicationContext()).setCupo(cupo);
                        ((Sesion)getActivity().getApplicationContext()).setBicicleta(seccion);
                        ((Sesion)getActivity().getApplicationContext()).setCodio(codigo);
                        Call<List<Bicicleta>> call = iRetrofit.executeGetBikes(((Sesion)getActivity().getApplicationContext()).getCodio());
                        call.enqueue(new Callback<List<Bicicleta>>() {
                            @Override
                            public void onResponse(Call<List<Bicicleta>> call, Response<List<Bicicleta>> response) {
                                if(response.code()==200 && !response.body().isEmpty()){
                                    Snackbar.make(v, "Eliga la bicicleta que desea registrar", Snackbar.LENGTH_LONG).show();
                                    NavHostFragment.findNavController(AsignarCupo.this).
                                            navigate(R.id.action_asignarCupo_to_interfazBicicleta);
                                }else{
                                    Snackbar.make(v, "Este estudiante no tiene bicicletas", Snackbar.LENGTH_LONG).show();
                                    NavHostFragment.findNavController(AsignarCupo.this).
                                                navigate(R.id.action_asignarCupo_to_interfaz_administrador);
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Bicicleta>> call, Throwable t) {
                                Snackbar.make(v, "Este estudiante no tiene bicicletas", Snackbar.LENGTH_LONG).show();
                                NavHostFragment.findNavController(AsignarCupo.this).
                                        navigate(R.id.action_asignarCupo_to_interfaz_administrador);
                            }
                        });

                    }else{
                        Snackbar.make(v, "Debe rellenar el campo codigo", Snackbar.LENGTH_LONG).show();
                    }
                }else{
                    Snackbar.make(v, "No hay cupos disponibles", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((Sesion)getActivity().getApplicationContext()).getRol_id()==3){
                    NavHostFragment.findNavController(AsignarCupo.this).
                            navigate(R.id.action_asignarCupo_to_admParqueaderos);
                }else{
                    NavHostFragment.findNavController(AsignarCupo.this).
                            navigate(R.id.action_asignarCupo_to_interfaz_administrador);
                }
            }
        });

        return v;
    }

    public void mostrarCupos(int seccion){
        String URLAUX = "http://"+getResources().getString(R.string.IP)+":3000/getCupo/";
        retrofit = new Retrofit.Builder().baseUrl(URLAUX).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        ArrayList<Cupo> listaCupos = new ArrayList<>();
        ArrayList<Number> listaCuposA = new ArrayList<>();
        System.out.println("seccion = " + seccion);
        Call<List<Cupo>> call = iRetrofit.executeGetCuposEnable(String.valueOf(seccion));
        call.enqueue(new Callback<List<Cupo>>() {
            @Override
            public void onResponse(Call<List<Cupo>> call, Response<List<Cupo>> response) {
                if(response.code()==200){
                    for(int i=0; i<response.body().size(); i++){
                        listaCupos.add(response.body().get(i));
                    }
                    for (Cupo i: listaCupos) {
                        listaCuposA.add(i.getIdCupo());
                    }
                    ArrayAdapter<Number> adapter = new ArrayAdapter<Number>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                            listaCuposA);
                    spnCupos.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Cupo>> call, Throwable t) {
                System.out.println("Problema en encontrar los cupos");
            }
        });
    }
}