package com.example.sdjcomp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
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

public class ReporteUsuarios extends Fragment {
    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaUsuarios;

        public ReporteUsuarios() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getControlUsuarios/";
        View v = inflater.inflate(R.layout.fragment_reporte_usuarios,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaUsuarios = v.findViewById(R.id.tblCU);
        Call<List<ControlUsuarios>> call = iRetrofit.executeGetControlUsuarios();
        call.enqueue(new Callback<List<ControlUsuarios>>() {
            @Override
            public void onResponse(Call<List<ControlUsuarios>> call, Response<List<ControlUsuarios>> response) {
                for(int i=0; i<response.body().size(); i++)
                {
                    System.out.println("hey");
                    TableRow fila = new TableRow(getActivity());
                    TextView txtID = new TextView(getActivity());
                    txtID.setTextColor(Color.rgb(255,255,255));
                    txtID.setGravity(Gravity.CENTER);
                    TextView txtCodigo = new TextView(getActivity());
                    TextView txtNombre = new TextView(getActivity());
                    TextView txtCorreo = new TextView(getActivity());
                    TextView txtClave = new TextView(getActivity());
                    TextView txtPseguridad = new TextView(getActivity());
                    TextView txtRseguridad = new TextView(getActivity());
                    TextView txtIDrol = new TextView(getActivity());
                    TextView txtCreated = new TextView(getActivity());
                    TextView txtModified = new TextView(getActivity());
                    TextView txtEstatus = new TextView(getActivity());

                    txtID.setText(String.valueOf(response.body().get(i).getId()));
                    txtIDrol.setText(String.valueOf(response.body().get(i).getRol_id()));
                    txtPseguridad.setText(String.valueOf(response.body().get(i).getPseguridad()));
                    txtCodigo.setText(response.body().get(i).getCodigo());
                    txtNombre.setText(response.body().get(i).getNombre());
                    txtCorreo.setText(response.body().get(i).getCorreo());
                    txtClave.setText(response.body().get(i).getClave());
                    txtRseguridad.setText(response.body().get(i).getRseguridad());
                    txtEstatus.setText(response.body().get(i).getStatus());
                    txtCreated.setText(response.body().get(i).getCreated_date());
                    txtModified.setText(response.body().get(i).getLast_modified_date());

                    fila.addView(txtID);
                    fila.addView(txtCodigo);
                    fila.addView(txtNombre);
                    fila.addView(txtCorreo);
                    fila.addView(txtClave);
                    fila.addView(txtPseguridad);
                    fila.addView(txtRseguridad);
                    fila.addView(txtIDrol);
                    fila.addView(txtCreated);
                    fila.addView(txtModified);
                    fila.addView(txtEstatus);
                    tablaUsuarios.addView(fila);
                }
            }

            @Override
            public void onFailure(Call<List<ControlUsuarios>> call, Throwable t) {
                System.out.println("hola");
                System.out.println(t.getMessage());
            }
        });
        return v;
    }
}