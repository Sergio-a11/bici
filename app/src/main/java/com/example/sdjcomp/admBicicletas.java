package com.example.sdjcomp;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class admBicicletas extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaBicicletas1;
    private TableLayout tablaBicicletas2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getAll/bicicletas/";
        View v = inflater.inflate(R.layout.fragment_adm_bicicletas,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        tablaBicicletas1 = v.findViewById(R.id.tablaBicicletas);
        Call<List<Bicicleta>> call = iRetrofit.executeGetadmbicicletas();

        call.enqueue(new Callback<List<Bicicleta>>() {
            @Override
            public void onResponse(Call<List<Bicicleta>> call, Response<List<Bicicleta>> response) {
                for(int i=0; i<response.body().size(); i++){
                    TableRow fila = new TableRow(getActivity());
                    TextView textIdbici = new TextView(getActivity());
                    TextView textCCpropietario = new TextView(getActivity());
                    TextView textFecharegistro = new TextView(getActivity());
                    TextView textLugarRegistro = new TextView(getActivity());
                    TextView textMarcaID = new TextView(getActivity());
                    TextView textNSerie = new TextView(getActivity());
                    TextView textTipoID = new TextView(getActivity());
                    TextView textColor = new TextView(getActivity());
                    TextView textEstudianteID = new TextView(getActivity());
                    textIdbici.setText(String.valueOf(response.body().get(i).getIdBicicleta()));
                    textCCpropietario.setText(response.body().get(i).getCedulaPropietario());
                    textFecharegistro.setText(response.body().get(i).getFechaRegistro());
                    textLugarRegistro.setText(response.body().get(i).getLugarRegistro());
                    textMarcaID.setText(String.valueOf(response.body().get(i).getMarca()));
                    textNSerie.setText(response.body().get(i).getNumSerie());
                    textTipoID.setText(String.valueOf(response.body().get(i).getTipo()));
                    textColor.setText(response.body().get(i).getColor());
                    textEstudianteID.setText(response.body().get(i).getEstudiante_id());
                    fila.addView(textIdbici);
                    fila.addView(textCCpropietario);
                    fila.addView(textFecharegistro);
                    fila.addView(textLugarRegistro);
                    fila.addView(textMarcaID);
                    fila.addView(textNSerie);
                    fila.addView(textTipoID);
                    fila.addView(textColor);
                    fila.addView(textEstudianteID);
                    tablaBicicletas1.addView(fila);
                }

            }

            @Override
            public void onFailure(Call<List<Bicicleta>> call, Throwable t) {
                Snackbar.make(v, "No se pudieron encontrar las bicicletas", Snackbar.LENGTH_LONG).show();
            }
        });
        return v;
    }
}