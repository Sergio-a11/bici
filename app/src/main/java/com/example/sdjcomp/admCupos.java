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
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admCupos() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment admCupos.
     */
    // TODO: Rename and change types and number of parameters
    public static admCupos newInstance(String param1, String param2) {
        admCupos fragment = new admCupos();
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
                    textId.setBackgroundResource(R.drawable.columna_1);
                    textId.setText(String.valueOf(response.body().get(i).getIdCupo()));
                    textId.setTypeface(null, Typeface.BOLD);
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
                System.out.println("fail");
            }
        });
        return v;
    }
}