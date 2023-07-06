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
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class modificarParqueadero extends Fragment {

    private Spinner spnSeccion,spnCupos;
    private Button btnModificar,btnVolver;
    private Spinner spnIDBicicleta;
    private String codigo, idBicicleta;

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
        URL= "http://"+getResources().getString(R.string.IP)+":3000/getStudentBike/";
        View v = inflater.inflate(R.layout.fragment_modificar_parqueadero,container,false);
        spnCupos = v.findViewById(R.id.spnCuposModParq);
        spnSeccion = v.findViewById(R.id.spnSeccionModParq);
        spnIDBicicleta = v.findViewById(R.id.spnIdBici);
        btnModificar = v.findViewById(R.id.btnModificarParqueadero);
        btnVolver = v.findViewById(R.id.btnVolverModificarParqueadero);


        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        idBicicleta = String.valueOf(((Sesion)getActivity().getApplicationContext()).getIdBici());

        CargarBicicletas();

        spnSeccion.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                mostrarCupos(spnSeccion.getSelectedItemPosition()+1);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(spnCupos.getSelectedItem()!=null){
                    int cupo=Integer.parseInt(spnCupos.getSelectedItem().toString());
                    int bici =Integer.parseInt(spnIDBicicleta.getSelectedItem().toString());
                    String palabras = (bici+","+cupo+","+((Sesion)getActivity().getApplicationContext()).getIdCupo()+","+idBicicleta);
                    Call<Number> call1 = iRetrofit.executeUpdateParqueadero(palabras);
                    call1.enqueue(new Callback<Number>() {
                        @Override
                        public void onResponse(Call<Number> call, Response<Number> response) {
                            Snackbar.make(v, "Parqueadero Actualizado", Snackbar.LENGTH_LONG).show();
                            NavHostFragment.findNavController(modificarParqueadero.this).
                                    navigate(R.id.action_modificarParqueadero_to_admParqueaderos);
                        }

                        @Override
                        public void onFailure(Call<Number> call, Throwable t) {
                            Snackbar.make(v, "No se pudo actualizar el parqueadero", Snackbar.LENGTH_LONG).show();
                            NavHostFragment.findNavController(modificarParqueadero.this).
                                    navigate(R.id.action_modificarParqueadero_to_admParqueaderos);
                        }
                    });
                }else{
                    Snackbar.make(v, "No hay cupos disponibles", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(modificarParqueadero.this).
                        navigate(R.id.action_modificarParqueadero_to_admParqueaderos);
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

    public void CargarBicicletas(){
        String URLAUX = "http://"+getResources().getString(R.string.IP)+":3000/getbicicletasSinUso/";
        retrofit = new Retrofit.Builder().baseUrl(URLAUX).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        ArrayList<Bicicleta> listaBicis = new ArrayList<>();
        ArrayList<Number> listaID = new ArrayList<>();
        System.out.println("codigo = " + codigo);
        Call<List<Bicicleta>> call = iRetrofit.executeGetBICISinUso();
        call.enqueue(new Callback<List<Bicicleta>>() {
            @Override
            public void onResponse(Call<List<Bicicleta>> call, Response<List<Bicicleta>> response) {
                if(response.code()==200){
                    for(int i=0; i<response.body().size();i++){
                        listaBicis.add(response.body().get(i));
                    }
                    listaID.add(Integer.parseInt(idBicicleta));
                    for(Bicicleta i: listaBicis){
                        listaID.add(i.getIdBicicleta());
                    }
                    ArrayAdapter<Number> adapter = new ArrayAdapter<Number>(getContext(), androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, listaID);
                    spnIDBicicleta.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Bicicleta>> call, Throwable t) {
                System.out.println("Problema en encontrar las bicicletas");
            }
        });
    }
}