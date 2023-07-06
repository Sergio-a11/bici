package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DesasignarCupo extends Fragment {

    private EditText edtNumSerie,edtNombre,edtTipo, edtColor;
    private Button btnDesaginar,btnVolver;
    private ImageButton btnNumSerie;

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
         int idBicicleta;
        URL="http://"+getResources().getString(R.string.IP)+":3000/getBikeForDesasignar/";
        View v = inflater.inflate(R.layout.desasignar_cupo,container,false);

        edtNumSerie = v.findViewById(R.id.edtNumSerieDAC);
        edtNombre = v.findViewById(R.id.edtNombreDAC);
        edtColor = v.findViewById(R.id.edtColorDAC);
        edtTipo = v.findViewById(R.id.edtTipoDAC);
        btnNumSerie = v.findViewById(R.id.btnNumSerieDAC);
        btnVolver = v.findViewById(R.id.btnVolverDAC);
        btnDesaginar = v.findViewById(R.id.btnDesasignarCupo);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);


        btnNumSerie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtNumSerie.getText().toString().isEmpty()){
                    Call<BicicletaParaBorrar> call = iRetrofit.executeGetBikeForDelete(edtNumSerie.getText().toString());
                    System.out.println("edtNumSerie = " + edtNumSerie.getText().toString());
                    call.enqueue(new Callback<BicicletaParaBorrar>() {
                        @Override
                        public void onResponse(Call<BicicletaParaBorrar> call, Response<BicicletaParaBorrar> response) {
                            ((Sesion)getActivity().getApplicationContext()).setIdBici(response.body().getIdBicicleta());
                            System.out.println("response.body().getIdBicicleta() = " + response.body().getIdBicicleta());
                            edtNombre.setText(response.body().getNombre());
                            edtColor.setText(response.body().getColor());
                            edtTipo.setText(response.body().getTipo());
                        }

                        @Override
                        public void onFailure(Call<BicicletaParaBorrar> call, Throwable t) {
                            Snackbar.make(v, "Bicicleta No encontrada", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Snackbar.make(v, "Debe digitar el Numero de Serie de la Bicicleta", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnDesaginar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((Sesion)getActivity().getApplicationContext()).getIdBici()!=0 && !edtNumSerie.getText().toString().isEmpty()){
                    String URL = "http://"+getResources().getString(R.string.IP)+":3000/deleteParqueadero/";
                    Call<Number> call = iRetrofit.executeDeleteParqueadero(((Sesion)getActivity().getApplicationContext()).getIdBici());
                    System.out.println("((Sesion)getActivity().getApplicationContext()).getIdBici() = " + ((Sesion)getActivity().getApplicationContext()).getIdBici());
                    call.enqueue(new Callback<Number>() {
                        @Override
                        public void onResponse(Call<Number> call, Response<Number> response) {
                            Snackbar.make(v, "Cupo de Parqueadero Eliminado", Snackbar.LENGTH_LONG).show();
                            NavHostFragment.findNavController(DesasignarCupo.this)
                                    .navigate(R.id.action_desasignarCupo_to_interfaz_administrador);
                        }

                        @Override
                        public void onFailure(Call<Number> call, Throwable t) {
                            Snackbar.make(v, "Cupo de Parqueadero No Eliminado", Snackbar.LENGTH_LONG).show();
                        }
                    });
                }else{
                    Snackbar.make(v, "Debe buscar la bicicleta primero", Snackbar.LENGTH_LONG).show();
                }
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(DesasignarCupo.this)
                        .navigate(R.id.action_desasignarCupo_to_interfaz_administrador);
            }
        });

        return v;
    }
}