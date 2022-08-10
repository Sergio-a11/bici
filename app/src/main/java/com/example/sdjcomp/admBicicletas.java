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

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admBicicletas() {
        // Required empty public constructor
    }

    public static admBicicletas newInstance(String param1, String param2) {
        admBicicletas fragment = new admBicicletas();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getAll/bicicletas/";
        View v = inflater.inflate(R.layout.fragment_adm_bicicletas,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        tablaBicicletas1 = v.findViewById(R.id.tablaBicicletas);
        tablaBicicletas2 = v.findViewById(R.id.tablaBicicletas2);
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
                    textIdbici.setBackgroundResource(R.drawable.columna_bicis);
                    textIdbici.setTypeface(null, Typeface.BOLD);
                    textCCpropietario.setBackgroundResource(R.drawable.columna_bicis);
                    textCCpropietario.setTypeface(null, Typeface.BOLD);
                    textFecharegistro.setBackgroundResource(R.drawable.columna_bicis);
                    textFecharegistro.setTypeface(null, Typeface.BOLD);
                    textLugarRegistro.setBackgroundResource(R.drawable.columna_bicis);
                    textLugarRegistro.setTypeface(null, Typeface.BOLD);
                    textMarcaID.setBackgroundResource(R.drawable.columna_bicis);
                    textMarcaID.setTypeface(null, Typeface.BOLD);
                    textIdbici.setText(String.valueOf(response.body().get(i).getCedulaPropietario()));

                    textCCpropietario.setText(response.body().get(i).getCedulaPropietario());
                    textFecharegistro.setText(response.body().get(i).getFechaRegistro());
                    textLugarRegistro.setText(response.body().get(i).getLugarRegistro());
                    textMarcaID.setText(String.valueOf(response.body().get(i).getMarca()));
                    fila.addView(textIdbici);
                    fila.addView(textCCpropietario);
                    fila.addView(textFecharegistro);
                    fila.addView(textMarcaID);
                    tablaBicicletas1.addView(fila);
                    //tabla 2

                    TableRow fila2 = new TableRow(getActivity());
                    TextView textIdbici2 = new TextView(getActivity());
                    TextView textNSerie = new TextView(getActivity());
                    TextView textTipoID = new TextView(getActivity());
                    TextView textColor = new TextView(getActivity());
                    TextView textEstudianteID = new TextView(getActivity());
                    textIdbici2.setBackgroundResource(R.drawable.columna_1);
                    textIdbici2.setText(String.valueOf(response.body().get(i).getIdBicicleta()));
                    textIdbici2.setTypeface(null, Typeface.BOLD);
                    textNSerie.setText(response.body().get(i).getNumSerie());
                    textTipoID.setText(String.valueOf(response.body().get(i).getTipo()));
                    textColor.setText(response.body().get(i).getColor());
                    textEstudianteID.setText(response.body().get(i).getEstudiante_id());
                    fila2.addView(textIdbici2);
                    fila2.addView(textNSerie);
                    fila2.addView(textTipoID);
                    fila2.addView(textColor);
                    fila2.addView(textEstudianteID);
                    tablaBicicletas2.addView(fila2);
                }

            }

            @Override
            public void onFailure(Call<List<Bicicleta>> call, Throwable t) {
                System.out.println("fail");
            }
        });
        return v;
    }
}