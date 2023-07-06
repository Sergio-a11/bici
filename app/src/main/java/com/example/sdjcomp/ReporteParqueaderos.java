package com.example.sdjcomp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ReporteParqueaderos extends Fragment {


    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaParqueaderos;

    public ReporteParqueaderos() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getControlParqueaderos/";
        View v = inflater.inflate(R.layout.fragment_reporte_parqueaderos,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaParqueaderos = v.findViewById(R.id.tblCP);
        Call<List<ControlParqueaderos>> call = iRetrofit.executeGetControlParquederos();
        call.enqueue(new Callback<List<ControlParqueaderos>>() {
            @Override
            public void onResponse(Call<List<ControlParqueaderos>> call, Response<List<ControlParqueaderos>> response) {
                for(int i=0; i<response.body().size(); i++)
                {
                    TableRow fila = new TableRow(getActivity());
                    TextView txtID = new TextView(getActivity());
                    txtID.setGravity(Gravity.CENTER);
                    TextView txtIDcupo = new TextView(getActivity());
                    txtIDcupo.setGravity(Gravity.CENTER);
                    TextView txtSeccion = new TextView(getActivity());
                    txtSeccion.setGravity(Gravity.CENTER);
                    TextView txtIDparq = new TextView(getActivity());
                    txtIDparq.setGravity(Gravity.CENTER);
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
                    TextView txtNombre = new TextView(getActivity());
                    txtNombre.setGravity(Gravity.LEFT);
                    TextView txtCorreo = new TextView(getActivity());
                    txtCorreo.setGravity(Gravity.LEFT);
                    TextView txtArrive = new TextView(getActivity());
                    txtArrive.setGravity(Gravity.LEFT);
                    txtArrive.setPadding(10,0,10,0);
                    TextView txtDeparture = new TextView(getActivity());
                    txtDeparture.setGravity(Gravity.LEFT);
                    txtDeparture.setPadding(10,0,10,0);
                    TextView txtEstatus = new TextView(getActivity());
                    txtEstatus.setGravity(Gravity.CENTER);

                    txtID.setText(String.valueOf(response.body().get(i).getId()));
                    txtIDcupo.setText(String.valueOf(response.body().get(i).getIdCupo()));
                    txtIDparq.setText(String.valueOf(response.body().get(i).getIdParqueadero()));
                    txtIDbici.setText(String.valueOf(response.body().get(i).getBicicleta_idBicicleta()));
                    txtIDmarca.setText(String.valueOf(response.body().get(i).getMarca_id()));
                    txtIDtipo.setText(String.valueOf(response.body().get(i).getTipo_id()));
                    txtSeccion.setText(response.body().get(i).getSeccion());
                    txtCedula.setText(response.body().get(i).getCedulaPropietario());
                    txtFecha.setText(response.body().get(i).getFechaRegistro());
                    txtLugar.setText(response.body().get(i).getLugarRegistro());
                    txtNumSerie.setText(response.body().get(i).getNumSerie());
                    txtColor.setText(response.body().get(i).getColor());
                    txtCodigo.setText(response.body().get(i).getEstudiante_id());
                    txtNombre.setText(response.body().get(i).getNombre());
                    txtCorreo.setText(response.body().get(i).getCorreo());
                    txtEstatus.setText(response.body().get(i).getStatus());

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    SimpleDateFormat output = new SimpleDateFormat("EEEE, yyyy/MM/dd - HH:mm:ss");

                    try {
                        Date d = null;
                        d = sdf.parse(response.body().get(i).getArrived_time());
                        txtArrive.setText(output.format(d));
                        d = sdf.parse(response.body().get(i).getDeparture_time());
                        txtDeparture.setText(output.format(d));
                    } catch (ParseException e) {
                        e.printStackTrace();
                        txtArrive.setText(response.body().get(i).getArrived_time());
                        txtDeparture.setText(response.body().get(i).getDeparture_time());
                    }

                    fila.addView(txtID);
                    fila.addView(txtIDcupo);
                    fila.addView(txtSeccion);
                    fila.addView(txtIDparq);
                    fila.addView(txtIDbici);
                    fila.addView(txtCedula);
                    fila.addView(txtFecha);
                    fila.addView(txtLugar);
                    fila.addView(txtIDmarca);
                    fila.addView(txtNumSerie);
                    fila.addView(txtIDtipo);
                    fila.addView(txtColor);
                    fila.addView(txtCodigo);
                    fila.addView(txtNombre);
                    fila.addView(txtCorreo);
                    fila.addView(txtArrive);
                    fila.addView(txtDeparture);
                    fila.addView(txtEstatus);
                    tablaParqueaderos.addView(fila);
                }
            }

            @Override
            public void onFailure(Call<List<ControlParqueaderos>> call, Throwable t) {
                Snackbar.make(v, t.getMessage(), Snackbar.LENGTH_LONG).show();
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}