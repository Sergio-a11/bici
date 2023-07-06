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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ReporteBicicletas extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaBicicletas;

    public ReporteBicicletas() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getControlBicicletas/";
        View v = inflater.inflate(R.layout.fragment_reporte_bicicletas,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaBicicletas = v.findViewById(R.id.tblCB);
        Call<List<ControlBicicletas>> call = iRetrofit.executeGetControlBicicletas();
        call.enqueue(new Callback<List<ControlBicicletas>>() {
            @Override
            public void onResponse(Call<List<ControlBicicletas>> call, Response<List<ControlBicicletas>> response) {
                for(int i=0; i<response.body().size(); i++)
                {
                    System.out.println("hey");
                    TableRow fila = new TableRow(getActivity());
                    TextView txtID = new TextView(getActivity());
                    txtID.setGravity(Gravity.CENTER);
                    TextView txtIDbici = new TextView(getActivity());
                    txtIDbici.setGravity(Gravity.CENTER);
                    TextView txtCedula = new TextView(getActivity());
                    txtCedula.setGravity(Gravity.LEFT);
                    TextView txtFecha = new TextView(getActivity());
                    txtFecha.setGravity(Gravity.LEFT);
                    TextView txtLugar = new TextView(getActivity());
                    txtLugar.setGravity(Gravity.LEFT);
                    TextView txtIDmarca = new TextView(getActivity());
                    txtIDmarca.setGravity(Gravity.CENTER);
                    TextView txtNumSerie = new TextView(getActivity());
                    txtNumSerie.setGravity(Gravity.CENTER);
                    TextView txtIDtipo = new TextView(getActivity());
                    txtIDtipo.setGravity(Gravity.CENTER);
                    TextView txtColor = new TextView(getActivity());
                    txtColor.setGravity(Gravity.CENTER);
                    TextView txtCodigo = new TextView(getActivity());
                    txtCodigo.setGravity(Gravity.LEFT);
                    TextView txtCreated = new TextView(getActivity());
                    txtCreated.setGravity(Gravity.LEFT);
                    txtCreated.setPadding(10,0,10,0);
                    TextView txtModified = new TextView(getActivity());
                    txtModified.setGravity(Gravity.LEFT);
                    txtModified.setPadding(10,0,10,0);
                    TextView txtEstatus = new TextView(getActivity());
                    txtEstatus.setGravity(Gravity.CENTER);

                    txtID.setText(String.valueOf(response.body().get(i).getId()));
                    txtIDbici.setText(String.valueOf(response.body().get(i).getIdBicicleta()));
                    txtIDmarca.setText(String.valueOf(response.body().get(i).getMarca_id()));
                    txtIDtipo.setText(String.valueOf(response.body().get(i).getTipo_id()));
                    txtCedula.setText(response.body().get(i).getCedulaPropietario());
                    txtFecha.setText(response.body().get(i).getFechaRegistro());
                    txtLugar.setText(response.body().get(i).getLugarRegistro());
                    txtNumSerie.setText(response.body().get(i).getNumSerie());
                    txtColor.setText(response.body().get(i).getColor());
                    txtCodigo.setText(response.body().get(i).getEstudiante_id());
                    txtEstatus.setText(response.body().get(i).getStatus());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat output = new SimpleDateFormat("EEEE, yyyy/MM/dd - HH:mm:ss");

                    try {
                        Date d = null;
                        d = sdf.parse(response.body().get(i).getCreated_date());
                        txtCreated.setText(output.format(d));
                        d = sdf.parse(response.body().get(i).getLast_modified_date());
                        txtModified.setText(output.format(d));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        txtCreated.setText(response.body().get(i).getCreated_date());
                        txtModified.setText(response.body().get(i).getLast_modified_date());
                    }

                    fila.addView(txtID);
                    fila.addView(txtIDbici);
                    fila.addView(txtCedula);
                    fila.addView(txtFecha);
                    fila.addView(txtLugar);
                    fila.addView(txtIDmarca);
                    fila.addView(txtNumSerie);
                    fila.addView(txtIDtipo);
                    fila.addView(txtColor);
                    fila.addView(txtCodigo);
                    fila.addView(txtCreated);
                    fila.addView(txtModified);
                    fila.addView(txtEstatus);
                    tablaBicicletas.addView(fila);
                }
            }

            @Override
            public void onFailure(Call<List<ControlBicicletas>> call, Throwable t) {
                System.out.println("hola");
                System.out.println(t.getMessage());
            }
        });
        return v;
    }
}