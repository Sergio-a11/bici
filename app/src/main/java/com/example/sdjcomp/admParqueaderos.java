package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class admParqueaderos extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaParqueaderos;
    private Button btnCrear,btnVolver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getAll/parqueaderos/";
        View v = inflater.inflate(R.layout.fragment_adm_parqueaderos,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaParqueaderos = v.findViewById(R.id.tablaParqueaderos);
        btnCrear = v.findViewById(R.id.btnCrearParqueaderoAdmin);
        btnVolver = v.findViewById(R.id.btnVolverParqueaderoAdmin);
        Call<List<Parqueadero>> call = iRetrofit.executeGetadmParqueaderos();
        call.enqueue(new Callback<List<Parqueadero>>() {
            @Override
            public void onResponse(Call<List<Parqueadero>> call, Response<List<Parqueadero>> response) {
                for(int i=0; i<response.body().size(); i++){
                    TableRow fila = new TableRow(getActivity());
                    TextView textId = new TextView(getActivity());
                    TextView textIdBici = new TextView(getActivity());
                    TextView textIdCupo = new TextView(getActivity());
                    Button btnModificar = new Button(getActivity());
                    Button btnEliminar = new Button(getActivity());
                    textId.setText(String.valueOf(response.body().get(i).getIdParqueadero()));
                    textIdBici.setText(String.valueOf(response.body().get(i).getBicicleta_idBicicleta()));
                    textIdCupo.setText(String.valueOf(response.body().get(i).getCupo_idCupo()));
                    btnModificar.setText("Modificar");
                    btnModificar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((Sesion)getActivity().getApplicationContext()).setIdBici(Integer.parseInt(textIdBici.getText().toString()));
                            ((Sesion)getActivity().getApplicationContext()).setIdCupo(Integer.parseInt(textIdCupo.getText().toString()));
                            ((Sesion)getActivity().getApplicationContext()).setModificando(true);
                            NavHostFragment.findNavController(admParqueaderos.this).
                                    navigate(R.id.action_admParqueaderos_to_modificarParqueadero);
                        }
                    });
                    btnEliminar.setText("Eliminar");
                    btnEliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Call<Number> call1 = iRetrofit.executeDeleteParqueadero(Integer.parseInt(textIdBici.getText().toString()));
                            call1.enqueue(new Callback<Number>() {
                                @Override
                                public void onResponse(Call<Number> call, Response<Number> response) {
                                    Snackbar.make(v, "Parqueadero Borrado", Snackbar.LENGTH_LONG).show();
                                    NavHostFragment.findNavController(admParqueaderos.this).
                                            navigate(R.id.action_admParqueaderos_to_admin);
                                }

                                @Override
                                public void onFailure(Call<Number> call, Throwable t) {
                                    Snackbar.make(v, "No se pudo borrar el parqueadero", Snackbar.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                    fila.addView(textId);
                    fila.addView(textIdBici);
                    fila.addView(textIdCupo);
                    fila.addView(btnModificar);
                    fila.addView(btnEliminar);
                    tablaParqueaderos.addView(fila);
                }

            }

            @Override
            public void onFailure(Call<List<Parqueadero>> call, Throwable t) {
                Snackbar.make(v, "No se pudieron encontrar los parqueaderos", Snackbar.LENGTH_LONG).show();
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admParqueaderos.this).
                        navigate(R.id.action_admParqueaderos_to_asignarCupo);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admParqueaderos.this).
                        navigate(R.id.action_admParqueaderos_to_admin);
            }
        });

        return v;
    }
}