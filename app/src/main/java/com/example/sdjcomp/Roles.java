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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Roles#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Roles extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaRoles;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Roles() {
        // Required empty public constructor
    }

    public static Roles newInstance(String param1, String param2) {
        Roles fragment = new Roles();
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
        View v = inflater.inflate(R.layout.fragment_roles,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        tablaRoles = v.findViewById(R.id.tablaRoles);
        Call<List<Rol>> call = iRetrofit.executeGetRoles();

        call.enqueue(new Callback<List<Rol>>() {
            @Override
            public void onResponse(Call<List<Rol>> call, Response<List<Rol>> response) {
                for(int i=0; i<response.body().size(); i++){
                    TableRow fila = new TableRow(getActivity());
                    TextView textRol = new TextView(getActivity());
                    textRol.setBackgroundResource(R.drawable.columna_1);
                    textRol.setText(response.body().get(i).getRol());
                    textRol.setTypeface(null, Typeface.BOLD);
                    TextView textCodigo = new TextView(getActivity());
                    textCodigo.setText(String.valueOf(response.body().get(i).getCodigo()));
                    fila.addView(textCodigo);
                    fila.addView(textRol);
                    tablaRoles.addView(fila);
                }

            }

            @Override
            public void onFailure(Call<List<Rol>> call, Throwable t) {
                System.out.println("fail");
            }
        });
        return v;
    }
}