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
                    System.out.println("hey");
                    TableRow fila = new TableRow(getActivity());
                    TextView txtID = new TextView(getActivity());
                    txtID.setTextColor(Color.rgb(255,255,255));
                    txtID.setGravity(Gravity.CENTER);
                    TextView txtIDcupo = new TextView(getActivity());
                    TextView txtSeccion = new TextView(getActivity());
                    TextView txtIDparq = new TextView(getActivity());
                    TextView txtIDbici = new TextView(getActivity());
                    TextView txtCedula = new TextView(getActivity());
                    TextView txtFecha = new TextView(getActivity());
                    TextView txtLugar = new TextView(getActivity());
                    TextView txtIDmarca = new TextView(getActivity());
                    TextView txtNumSerie = new TextView(getActivity());
                    TextView txtIDtipo = new TextView(getActivity());
                    TextView txtColor = new TextView(getActivity());
                    TextView txtCodigo = new TextView(getActivity());
                    TextView txtNombre = new TextView(getActivity());
                    TextView txtCorreo = new TextView(getActivity());
                    TextView txtArrive = new TextView(getActivity());
                    TextView txtDeparture = new TextView(getActivity());
                    TextView txtEstatus = new TextView(getActivity());

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
                    txtArrive.setText(response.body().get(i).getArrived_time());
                    txtDeparture.setText(response.body().get(i).getDeparture_time());

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
                System.out.println("hola");
                System.out.println(t.getMessage());
            }
        });
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}