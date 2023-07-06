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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegistrarBicicleta extends Fragment {

    private TextView txtCedulaPropietario, txtFechaRegistro, txtLugarRegistro, txtMarca, txtNumSerie, txtTipo, txtColor;
    private EditText edtEstudiante;
    private Button btnRegistrar, btnVolver;
    private Spinner spnMarcas, spnTipos;
    private String URL="";
    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private LinearLayout ly;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getMarcas/";
        View v = inflater.inflate(R.layout.fragment_registrar_bicicleta, container, false);


        ly = v.findViewById(R.id.HlayoutBicicleta);
        if(((Sesion)getActivity().getApplicationContext()).getRol_id()==3){
            ly.setVisibility(View.VISIBLE);
        }else{
            ly.setVisibility(View.GONE);
        }
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        spnMarcas = (Spinner) v.findViewById(R.id.spnMarca);
        spnTipos = (Spinner) v.findViewById(R.id.spnTipo);

        btnVolver = v.findViewById(R.id.btnVolverRegBici);

        ArrayList<Marca> lstMarcas = new ArrayList<>();
        ArrayList<String> auxLstMarcas = new ArrayList<>();

        Call<List<Marca>> call = iRetrofit.executeGetMarca();
        call.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                if(response.code()==200)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        lstMarcas.add(response.body().get(i));
                    }
                    for(Marca m: lstMarcas)
                    {
                        auxLstMarcas.add(m.getMarca());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, auxLstMarcas);
                    spnMarcas.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {
                Snackbar.make(v, "Marcas No Encontradas", Snackbar.LENGTH_LONG).show();
            }
        });

        //rellenar spnIdBici marcas

        ArrayList<Tipo> lstTipos = new ArrayList<>();
        ArrayList<String> auxLstTipos = new ArrayList<>();

        Call<List<Tipo>> call2 = iRetrofit.executeGetTipos();
        call2.enqueue(new Callback<List<Tipo>>() {
            @Override
            public void onResponse(Call<List<Tipo>> call, Response<List<Tipo>> response) {
                if(response.code()==200)
                {
                    for(int i=0; i<response.body().size(); i++)
                    {
                        lstTipos.add(response.body().get(i));
                    }
                    for(Tipo m: lstTipos)
                    {
                        auxLstTipos.add(m.getTipo());
                    }
                    ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), androidx.constraintlayout.widget.R.layout.support_simple_spinner_dropdown_item, auxLstTipos);
                    spnTipos.setAdapter(adapter2);
                }
            }

            @Override
            public void onFailure(Call<List<Tipo>> call, Throwable t) {
                Snackbar.make(v, "Tipos No Encontradas", Snackbar.LENGTH_LONG).show();
            }
        });

        btnRegistrar = (Button) v.findViewById(R.id.btnRegistrarBicicletaDesdePantallaRegistro);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtCedulaPropietario = (TextView) v.findViewById(R.id.txtCedulaPropietario);
                txtFechaRegistro = (TextView) v.findViewById(R.id.txtFechaRegistro);
                txtLugarRegistro = (TextView) v.findViewById(R.id.txtLugarRegistro);
                txtNumSerie = (TextView) v.findViewById(R.id.txtNumSerie);
                txtColor = (TextView) v.findViewById(R.id.txtColorCicla);
                edtEstudiante = v.findViewById(R.id.edtEstudianteCrearBicicleta);
                int marca = spnMarcas.getSelectedItemPosition()+1;
                int tipo = spnTipos.getSelectedItemPosition()+1;

                if(!txtCedulaPropietario.getText().toString().isEmpty()
                        && !txtFechaRegistro.getText().toString().isEmpty()
                        && !txtLugarRegistro.getText().toString().isEmpty()
                        && !txtNumSerie.getText().toString().isEmpty()
                        && !txtColor.getText().toString().isEmpty())
                {
                    BicicletaRegistrar objB = new BicicletaRegistrar();
                    objB.setCedulaPropietario(txtCedulaPropietario.getText().toString());
                    objB.setFechaRegistro(txtFechaRegistro.getText().toString());
                    objB.setLugarRegistro(txtLugarRegistro.getText().toString());
                    objB.setMarca(marca);
                    objB.setNumSerie(txtNumSerie.getText().toString());
                    objB.setTipo(tipo);
                    objB.setColor(txtColor.getText().toString());
                    if(((Sesion)getActivity().getApplicationContext()).getRol_id()==3){
                        objB.setEstudiante_id(edtEstudiante.getText().toString());
                    }else{
                        objB.setEstudiante_id(((Sesion)getActivity().getApplicationContext()).getCodigo());
                    }


                    Call<Number> call = iRetrofit.executeRegisterBike(objB);
                    call.enqueue(new Callback<Number>() {
                        @Override
                        public void onResponse(Call<Number> call, Response<Number> response) {
                            if(Integer.parseInt(String.valueOf(response.body()))==1){
                                Snackbar.make(v, "Bicicleta Registrado Con Exito", Snackbar.LENGTH_LONG).show();
                                if(((Sesion)getActivity().getApplicationContext()).getRol_id()==3){
                                    NavHostFragment.findNavController(RegistrarBicicleta.this).navigate(R.id.action_registrarBicicleta_to_admBicicletas);
                                }else{
                                    NavHostFragment.findNavController(RegistrarBicicleta.this).navigate(R.id.action_registrarBicicleta_to_interfazBicicleta2);
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<Number> call, Throwable t) {
                            Snackbar.make(v, "Bicicleta NO Registrado", Snackbar.LENGTH_LONG).show();
                            if(((Sesion)getActivity().getApplicationContext()).getRol_id()==3){
                                NavHostFragment.findNavController(RegistrarBicicleta.this).navigate(R.id.action_registrarBicicleta_to_admBicicletas);
                            }else{
                                NavHostFragment.findNavController(RegistrarBicicleta.this).navigate(R.id.action_registrarBicicleta_to_interfazBicicleta2);
                            }
                        }
                    });
                }
                else
                {
                    Snackbar.make(v, "Debe completar todos los campos", Snackbar.LENGTH_LONG).show();
                }

            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(((Sesion)getActivity().getApplicationContext()).getRol_id()==3){
                    NavHostFragment.findNavController(RegistrarBicicleta.this).navigate(R.id.action_registrarBicicleta_to_admBicicletas);
                }else{
                    NavHostFragment.findNavController(RegistrarBicicleta.this).navigate(R.id.action_registrarBicicleta_to_InterfazEstudiante);
                }
            }
        });

        return v;
    }
}