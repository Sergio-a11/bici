package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AsignarCupo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AsignarCupo extends Fragment {

    private EditText edtCodigo;
    private Spinner spnSeccion;
    private Button btnAsignar;

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AsignarCupo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AsignarCupo.
     */
    // TODO: Rename and change types and number of parameters
    public static AsignarCupo newInstance(String param1, String param2) {
        AsignarCupo fragment = new AsignarCupo();
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
        URL="http://"+getResources().getString(R.string.IP)+":3000/createCupo/";
        View v = inflater.inflate(R.layout.asignar_cupo,container,false);

        edtCodigo = v.findViewById(R.id.edtCodigoEnCupos);
        spnSeccion = v.findViewById(R.id.spnSeccionAsignar);
        btnAsignar = v.findViewById(R.id.btnAsignarCupo);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        int seccion=1;
        switch (spnSeccion.getSelectedItemPosition()){
            case 1:{
                seccion=1;
                break;
            }
            case 2:{
                seccion=2;
                break;
            }
            case 3:{
                seccion=3;
                break;
            }
        }

        Cupo objC = new Cupo(String.valueOf(seccion),true);

        Call<Cupo> call = iRetrofit.executeCreateCupo(objC);
        call.enqueue(new Callback<Cupo>() {
            @Override
            public void onResponse(Call<Cupo> call, Response<Cupo> response) {
                if(response.code()==200){
                    Toast.makeText(getContext(), "Cupo Creado", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Cupo> call, Throwable t) {

            }
        });

        return v;
    }
}