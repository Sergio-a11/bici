package com.example.sdjcomp;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link admUsuarios#newInstance} factory method to
 * create an instance of this fragment.
 */
public class admUsuarios extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaUsuarios;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admUsuarios() {
        // Required empty public constructor
    }

    public static admUsuarios newInstance(String param1, String param2) {
        admUsuarios fragment = new admUsuarios();
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
        URL="http://"+getResources().getString(R.string.IP)+":3000/getUsers/";
        View v = inflater.inflate(R.layout.fragment_adm_usuarios,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaUsuarios = v.findViewById(R.id.tablaUsuarios);
        Call<List<Usuario>> call = iRetrofit.executeGetUsers();
        call.enqueue(new Callback<List<Usuario>>() {
            @Override
            public void onResponse(Call<List<Usuario>> call, Response<List<Usuario>> response) {
                for(int i=0; i<response.body().size(); i++){
                    TableRow fila = new TableRow(getActivity());
                    TextView textCodigo = new TextView(getActivity());
                    TextView textNombre = new TextView(getActivity());
                    TextView textCorreo = new TextView(getActivity());
                    TextView textClave = new TextView(getActivity());
                    TextView textPseguridad = new TextView(getActivity());
                    TextView textRseguridad = new TextView(getActivity());
                    TextView textRol = new TextView(getActivity());

                    textCodigo.setText(response.body().get(i).getCodigo());
                    textNombre.setText(response.body().get(i).getNombre());
                    textCorreo.setText(response.body().get(i).getCorreo());
                    textClave.setText(response.body().get(i).getClave());
                    textPseguridad.setText(String.valueOf(response.body().get(i).getPseguridad()));
                    textRseguridad.setText(response.body().get(i).getRseguridad());
                    textRol.setText(String.valueOf(response.body().get(i).getRol_id()));
                    fila.addView(textCodigo);
                    fila.addView(textNombre);
                    fila.addView(textCorreo);
                    fila.addView(textClave);
                    fila.addView(textPseguridad);
                    fila.addView(textRseguridad);
                    fila.addView(textRol);
                    tablaUsuarios.addView(fila);
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {

            }
        });
        return v;
    }
}