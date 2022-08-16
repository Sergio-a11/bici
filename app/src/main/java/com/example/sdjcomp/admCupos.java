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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class admCupos extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaCupos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getAll/cupos/";
        View v = inflater.inflate(R.layout.fragment_adm_cupos,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaCupos = v.findViewById(R.id.tablaCupos);
        Call<List<Cupo>> call = iRetrofit.executeGetCupos();
        call.enqueue(new Callback<List<Cupo>>() {
            @Override
            public void onResponse(Call<List<Cupo>> call, Response<List<Cupo>> response) {
                for(int i=0; i<response.body().size(); i++){
                    TableRow fila = new TableRow(getActivity());
                    TextView textId = new TextView(getActivity());
                    TextView textSeccion = new TextView(getActivity());
                    TextView textEstado = new TextView(getActivity());
                    textId.setText(String.valueOf(response.body().get(i).getIdCupo()));
                    textSeccion.setText(response.body().get(i).getSeccion());
                    textEstado.setText(String.valueOf(response.body().get(i).getEstado()));
                    fila.addView(textId);
                    fila.addView(textSeccion);
                    fila.addView(textEstado);
                    tablaCupos.addView(fila);
                }

            }

            @Override
            public void onFailure(Call<List<Cupo>> call, Throwable t) {
                Snackbar.make(v, "No se pudieron encontrar los cupos", Snackbar.LENGTH_LONG).show();
            }
        });
        return v;
    }
}