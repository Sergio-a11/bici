package com.example.sdjcomp;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
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

public class ReporteParametrizados extends Fragment {

    Spinner spnReportes;
    Button btnFiltrar;
    LinearLayout lCodigo, lNumSerie, lCiudad;
    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaReporte;
    TextView txtID, txtidCupoR, txtSeccionR, txtIdParqueaderoR, txtIdBiciletaR, txtCedulaR, txtFechaRegistroR,txtLugarRegistroR,txtIdMarcaR,txtNumSerieR,txtIdTipoR,txtColorR,txtCodigoR,txtNombreR,txtCorreoR,txtArriveTimeR,txtDepartureTimeR,txtEstadoR;

    public ReporteParametrizados() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reporte_parametrizados,container,false);
        spnReportes = v.findViewById(R.id.spnReportes);
        btnFiltrar = v.findViewById(R.id.btnFiltrarR);
        tablaReporte = v.findViewById(R.id.tblReportes);

        //TextView columns
        //txtID = v.findViewById(R.id.txtidRepo);
        txtidCupoR = v.findViewById(R.id.txtidCupoR);
        txtSeccionR = v.findViewById(R.id.txtSeccionR);
        txtIdParqueaderoR = v.findViewById(R.id.txtIdParqueaderoR);
        txtIdBiciletaR = v.findViewById(R.id.txtIdBiciletaR);
        txtCedulaR = v.findViewById(R.id.txtCedulaR);
        txtFechaRegistroR = v.findViewById(R.id.txtFechaRegistroR);
        txtLugarRegistroR = v.findViewById(R.id.txtLugarRegistroR);
        txtIdMarcaR = v.findViewById(R.id.txtIdMarcaR);
        txtNumSerieR = v.findViewById(R.id.txtNumSerieR);
        txtIdTipoR = v.findViewById(R.id.txtIdTipoR);
        txtColorR = v.findViewById(R.id.txtColorR);
        txtCodigoR = v.findViewById(R.id.txtCodigoR);
        txtNombreR = v.findViewById(R.id.txtNombreR);
        txtCorreoR = v.findViewById(R.id.txtCorreoR);
        txtArriveTimeR = v.findViewById(R.id.txtArriveTimeR);
        txtDepartureTimeR = v.findViewById(R.id.txtDepartureTimeR);
        txtEstadoR = v.findViewById(R.id.txtEstadoR);


        //layouts
        lCodigo = v.findViewById(R.id.layoutCodigo);
        lNumSerie = v.findViewById(R.id.layoutNumSerie);
        lCiudad = v.findViewById(R.id.layoutCiudad);

        //desactivar
        //txtCodigo.setEnabled(false);

        spnReportes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                limpiar(i);
                switch (i) {
                    case 0:
                    {
                        //Activacion de layouts
                        lCodigo.setVisibility(View.VISIBLE);
                        lNumSerie.setVisibility(View.VISIBLE);
                        lCiudad.setVisibility(View.GONE);
                        filtrar(i, v);
                        break;
                    }
                    case 1:
                    {
                        //Activacion de layouts
                        lCodigo.setVisibility(View.GONE);
                        lNumSerie.setVisibility(View.GONE);
                        lCiudad.setVisibility(View.VISIBLE);
                        filtrar(i, v);
                        break;
                    }
                    default:
                    {
                        lCodigo.setVisibility(View.VISIBLE);
                        lNumSerie.setVisibility(View.VISIBLE);
                        lCiudad.setVisibility(View.VISIBLE);
                        //edit tex

                        txtIdBiciletaR.setVisibility(View.VISIBLE);
                        txtFechaRegistroR.setVisibility(View.VISIBLE);
                        txtIdMarcaR.setVisibility(View.VISIBLE);
                        txtIdTipoR.setVisibility(View.VISIBLE);
                        txtCorreoR.setVisibility(View.VISIBLE);
                    }

                }
                Snackbar.make(v, "Rellene los campos", Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        return v;
    }

    void filtrar(int position, View v)
    {
        System.out.println("hola");
        //Edit Text
        EditText txtCodigo = v.findViewById(R.id.txtCodigoRepo);
        EditText txtNumSerie = v.findViewById(R.id.txtNumSerieRepo);
        EditText txtCiudad = v.findViewById(R.id.txtCiudadRepo);

        btnFiltrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpiar(position);
                switch (position)
                {
                    //Entradas y salidas por bici o estudiante
                    case 0:
                    {
                        String codigo = "";
                        String numSerie = "";
                        if(txtCodigo.getText().toString().isEmpty() && txtNumSerie.getText().toString().isEmpty()) {
                            Snackbar.make(v, "Rellene algun filtro", Snackbar.LENGTH_LONG).show();
                            codigo = "%";
                            numSerie = "%";
                        }else
                        {
                            if(!txtCodigo.getText().toString().isEmpty() && !txtNumSerie.getText().toString().isEmpty())
                            {
                                numSerie = txtNumSerie.getText().toString();
                                codigo = txtCodigo.getText().toString();
                            }else
                            {
                                if(txtCodigo.getText().toString().isEmpty())
                                {
                                    codigo = "%";
                                    numSerie = txtNumSerie.getText().toString();
                                }
                                if(txtNumSerie.getText().toString().isEmpty()) {
                                    numSerie = "%";
                                    codigo = txtCodigo.getText().toString();
                                }
                            }
                        }

                            URL = "http://" + getResources().getString(R.string.IP) + ":3000/getReporteEntradas/";
                            retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
                            iRetrofit = retrofit.create(IRetroFit.class);
                            System.out.println("a"+codigo);
                            System.out.println("b"+numSerie);
                            Call<List<ControlParqueaderos>> call = iRetrofit.executeGetReporteEntradas(codigo, numSerie);
                            call.enqueue(new Callback<List<ControlParqueaderos>>() {
                                @Override
                                public void onResponse(Call<List<ControlParqueaderos>> call, Response<List<ControlParqueaderos>> response) {
                                    txtIdBiciletaR.setVisibility(View.GONE);
                                    txtFechaRegistroR.setVisibility(View.GONE);
                                    txtIdMarcaR.setVisibility(View.GONE);
                                    txtIdTipoR.setVisibility(View.GONE);
                                    txtCorreoR.setVisibility(View.GONE);
                                    //txtID.setVisibility(View.GONE);

                                    for (int i = 0; i < response.body().size(); i++) {
                                        System.out.println("hey");
                                        TableRow fila = new TableRow(getActivity());
                                        TextView txtIDcupo = new TextView(getActivity());
                                        txtIDcupo.setTextColor(Color.rgb(255, 255, 255));
                                        txtIDcupo.setGravity(Gravity.CENTER);
                                        TextView txtSeccion = new TextView(getActivity());
                                        TextView txtIDparq = new TextView(getActivity());
                                        TextView txtCedula = new TextView(getActivity());
                                        TextView txtLugar = new TextView(getActivity());
                                        TextView txtNumSerie = new TextView(getActivity());
                                        TextView txtColor = new TextView(getActivity());
                                        TextView txtCodigo = new TextView(getActivity());
                                        TextView txtNombre = new TextView(getActivity());
                                        TextView txtArrive = new TextView(getActivity());
                                        TextView txtDeparture = new TextView(getActivity());
                                        TextView txtEstatus = new TextView(getActivity());


                                        txtIDcupo.setText(String.valueOf(response.body().get(i).getIdCupo()));
                                        txtIDparq.setText(String.valueOf(response.body().get(i).getIdParqueadero()));
                                        txtSeccion.setText(response.body().get(i).getSeccion());
                                        txtCedula.setText(response.body().get(i).getCedulaPropietario());
                                        txtLugar.setText(response.body().get(i).getLugarRegistro());
                                        txtNumSerie.setText(response.body().get(i).getNumSerie());
                                        txtColor.setText(response.body().get(i).getColor());
                                        txtCodigo.setText(response.body().get(i).getEstudiante_id());
                                        txtNombre.setText(response.body().get(i).getNombre());
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

                                        fila.addView(txtIDcupo);
                                        fila.addView(txtSeccion);
                                        fila.addView(txtIDparq);
                                        fila.addView(txtCedula);
                                        fila.addView(txtLugar);
                                        fila.addView(txtNumSerie);
                                        fila.addView(txtColor);
                                        fila.addView(txtCodigo);
                                        fila.addView(txtNombre);
                                        fila.addView(txtArrive);
                                        fila.addView(txtDeparture);
                                        fila.addView(txtEstatus);
                                        tablaReporte.addView(fila);
                                    }
                                }

                                @Override
                                public void onFailure(Call<List<ControlParqueaderos>> call, Throwable t) {
                                    System.out.println("hola");
                                    System.out.println(t.getMessage());
                                }
                            });

                        break;
                    }
                    //bicicletas por ciudad
                    case 1:
                    {
                        String ciudad = "";
                        if(txtCiudad.getText().toString().isEmpty())
                        {
                            Snackbar.make(v, "Rellene el filtro", Snackbar.LENGTH_LONG).show();
                            ciudad = "%";
                        }else
                        {
                            ciudad = txtCiudad.getText().toString();
                        }


                        URL = "http://" + getResources().getString(R.string.IP) + ":3000/getReporteBiciCiudad/";
                        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
                        iRetrofit = retrofit.create(IRetroFit.class);
                        Call<List<ControlBicicletas>> call = iRetrofit.executeGetReporteCiudad(ciudad);
                        call.enqueue(new Callback<List<ControlBicicletas>>() {
                            @Override
                            public void onResponse(Call<List<ControlBicicletas>> call, Response<List<ControlBicicletas>> response) {

                                txtCorreoR.setVisibility(View.GONE);
                                txtNombreR.setVisibility(View.GONE);
                                txtidCupoR.setVisibility(View.GONE);
                                txtSeccionR.setVisibility(View.GONE);
                                txtIdParqueaderoR.setVisibility(View.GONE);


                                for (int i = 0; i < response.body().size(); i++) {
                                    System.out.println("hey");
                                    TableRow fila = new TableRow(getActivity());
                                    //TextView txtid = new TextView(getActivity());
                                    TextView txtidBici = new TextView(getActivity());
                                    TextView txtidMarca = new TextView(getActivity());
                                    TextView txtidTipo = new TextView(getActivity());
                                    TextView txtCedula = new TextView(getActivity());
                                    TextView txtFecha = new TextView(getActivity());
                                    TextView txtLugar = new TextView(getActivity());
                                    txtLugar.setTextColor(Color.rgb(255, 255, 255));
                                    txtLugar.setGravity(Gravity.CENTER);
                                    TextView txtNumSerie = new TextView(getActivity());
                                    TextView txtColor = new TextView(getActivity());
                                    TextView txtCodigo = new TextView(getActivity());
                                    TextView txtCreated = new TextView(getActivity());
                                    TextView txtUpdate = new TextView(getActivity());
                                    TextView txtEstatus = new TextView(getActivity());


                                    //txtid.setText(String.valueOf(response.body().get(i).getId()));
                                    txtidBici.setText(String.valueOf(response.body().get(i).getIdBicicleta()));
                                    txtidMarca.setText(String.valueOf(response.body().get(i).getMarca_id()));
                                    txtidTipo.setText(String.valueOf(response.body().get(i).getTipo_id()));
                                    txtFecha.setText(response.body().get(i).getFechaRegistro());
                                    txtCedula.setText(response.body().get(i).getCedulaPropietario());
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
                                        txtUpdate.setText(output.format(d));
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                        txtCreated.setText(response.body().get(i).getCreated_date());
                                        txtUpdate.setText(response.body().get(i).getLast_modified_date());
                                    }

                                    //fila.addView(txtid);
                                    fila.addView(txtidBici);
                                    fila.addView(txtCedula);
                                    fila.addView(txtFecha);
                                    fila.addView(txtLugar);
                                    fila.addView(txtidMarca);
                                    fila.addView(txtNumSerie);
                                    fila.addView(txtidTipo);
                                    fila.addView(txtColor);
                                    fila.addView(txtCodigo);
                                    fila.addView(txtCreated);
                                    fila.addView(txtUpdate);
                                    fila.addView(txtEstatus);
                                    tablaReporte.addView(fila);
                                }
                            }

                            @Override
                            public void onFailure(Call<List<ControlBicicletas>> call, Throwable t) {
                                System.out.println("hola");
                                System.out.println(t.getMessage());
                            }
                        });

                        break;
                    }
                }

            }
        });
    }

    void limpiar(int position)
    {

        tablaReporte.removeAllViews();
        TableRow fila = new TableRow(getActivity());
        //((ViewGroup)txtID.getParent()).removeView(txtID);
        ((ViewGroup)txtidCupoR.getParent()).removeView(txtidCupoR);
        ((ViewGroup)txtSeccionR.getParent()).removeView(txtSeccionR);
        ((ViewGroup)txtCedulaR.getParent()).removeView(txtCedulaR);
        ((ViewGroup)txtIdParqueaderoR.getParent()).removeView(txtIdParqueaderoR);
        ((ViewGroup)txtIdBiciletaR.getParent()).removeView(txtIdBiciletaR);
        ((ViewGroup)txtFechaRegistroR.getParent()).removeView(txtFechaRegistroR);
        ((ViewGroup)txtLugarRegistroR.getParent()).removeView(txtLugarRegistroR);
        ((ViewGroup)txtIdMarcaR.getParent()).removeView(txtIdMarcaR);
        ((ViewGroup)txtNumSerieR.getParent()).removeView(txtNumSerieR);
        ((ViewGroup)txtIdTipoR.getParent()).removeView(txtIdTipoR);
        ((ViewGroup)txtColorR.getParent()).removeView(txtColorR);
        ((ViewGroup)txtCodigoR.getParent()).removeView(txtCodigoR);
        ((ViewGroup)txtNombreR.getParent()).removeView(txtNombreR);
        ((ViewGroup)txtCorreoR.getParent()).removeView(txtCorreoR);
        ((ViewGroup)txtArriveTimeR.getParent()).removeView(txtArriveTimeR);
        ((ViewGroup)txtDepartureTimeR.getParent()).removeView(txtDepartureTimeR);
        ((ViewGroup)txtEstadoR.getParent()).removeView(txtEstadoR);

        switch (position)
        {
            case 0:
            {
                fila.addView(txtidCupoR);
                fila.addView(txtSeccionR);
                fila.addView(txtIdParqueaderoR);
                fila.addView(txtCedulaR);
                fila.addView(txtLugarRegistroR);
                fila.addView(txtNumSerieR);
                fila.addView(txtColorR);
                fila.addView(txtCodigoR);
                fila.addView(txtNombreR);
                fila.addView(txtArriveTimeR);
                fila.addView(txtDepartureTimeR);
                fila.addView(txtEstadoR);
                fila.addView(txtFechaRegistroR);
                fila.addView(txtIdBiciletaR);
                fila.addView(txtIdMarcaR);
                fila.addView(txtIdTipoR);
                fila.addView(txtCorreoR);
                break;
            }
            case 1:
            {
                //fila.addView(txtID);
                fila.addView(txtIdBiciletaR);
                fila.addView(txtCedulaR);
                fila.addView(txtFechaRegistroR);
                fila.addView(txtLugarRegistroR);
                fila.addView(txtIdMarcaR);
                fila.addView(txtNumSerieR);
                fila.addView(txtIdTipoR);
                fila.addView(txtColorR);
                fila.addView(txtCodigoR);
                fila.addView(txtArriveTimeR);
                fila.addView(txtDepartureTimeR);
                fila.addView(txtEstadoR);
                fila.addView(txtNombreR);
                fila.addView(txtidCupoR);
                fila.addView(txtSeccionR);
                fila.addView(txtIdParqueaderoR);
                fila.addView(txtCorreoR);
                break;
            }
            default:
            {
                fila.addView(txtidCupoR);
                fila.addView(txtSeccionR);
                fila.addView(txtIdParqueaderoR);
                fila.addView(txtCedulaR);
                fila.addView(txtLugarRegistroR);
                fila.addView(txtNumSerieR);
                fila.addView(txtColorR);
                fila.addView(txtCodigoR);
                fila.addView(txtNombreR);
                fila.addView(txtArriveTimeR);
                fila.addView(txtDepartureTimeR);
                fila.addView(txtEstadoR);
                fila.addView(txtFechaRegistroR);
                fila.addView(txtIdBiciletaR);
                fila.addView(txtIdMarcaR);
                fila.addView(txtIdTipoR);
                fila.addView(txtCorreoR);
            }
        }


        tablaReporte.addView(fila);

    }

}