package com.example.sdjcomp;

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

public class admTipos extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaTipos;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getTipos/";
        View v = inflater.inflate(R.layout.fragment_adm_tipos,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaTipos = v.findViewById(R.id.tablaTipos);
        Call<List<Tipo>> call = iRetrofit.executeGetTipos();
        call.enqueue(new Callback<List<Tipo>>() {
            @Override
            public void onResponse(Call<List<Tipo>> call, Response<List<Tipo>> response) {
                for(int i=0; i<response.body().size(); i++){
                    TableRow fila = new TableRow(getActivity());
                    TextView textId = new TextView(getActivity());
                    TextView textTipo = new TextView(getActivity());
                    textId.setText(String.valueOf(response.body().get(i).getId()));
                    textTipo.setText(response.body().get(i).getTipo());
                    fila.addView(textId);
                    fila.addView(textTipo);
                    tablaTipos.addView(fila);
                }
            }

            @Override
            public void onFailure(Call<List<Tipo>> call, Throwable t) {
                Snackbar.make(v, "No se pudieron encontrar los tipos", Snackbar.LENGTH_LONG).show();
            }
        });
        return v;
    }
}