package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Gravity;
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

public class admTipos extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaTipos;
    private Button btnCrear,btnVolver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getTipos/";
        View v = inflater.inflate(R.layout.fragment_adm_tipos,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaTipos = v.findViewById(R.id.tablaTipos);
        btnCrear = v.findViewById(R.id.btnCrearTipo);
        btnVolver = v.findViewById(R.id.btnVolverTipoAdmin);
        Call<List<Tipo>> call = iRetrofit.executeGetTipos();
        call.enqueue(new Callback<List<Tipo>>() {
            @Override
            public void onResponse(Call<List<Tipo>> call, Response<List<Tipo>> response) {
                for(int i=0; i<response.body().size(); i++){
                    TableRow fila = new TableRow(getActivity());
                    TextView textId = new TextView(getActivity());
                    TextView textTipo = new TextView(getActivity());
                    Button btnModificar = new Button(getActivity());
                    Button btnEliminar = new Button(getActivity());
                    textId.setText(String.valueOf(response.body().get(i).getId()));
                    textTipo.setText(response.body().get(i).getTipo());
                    textId.setGravity(Gravity.CENTER);
                    textTipo.setGravity(Gravity.CENTER);
                    btnModificar.setText("Modificar");
                    btnModificar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((Sesion)getActivity().getApplicationContext()).setIdTipo(Integer.parseInt(textId.getText().toString()));
                            NavHostFragment.findNavController(admTipos.this)
                                    .navigate(R.id.action_admTipos_to_modificarTipo);
                        }
                    });
                    btnEliminar.setText("Eliminar");
                    btnEliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Call<Number> call1 = iRetrofit.executeDeleteTipo(textId.getText().toString());
                            call1.enqueue(new Callback<Number>() {
                                @Override
                                public void onResponse(Call<Number> call, Response<Number> response) {
                                    Snackbar.make(v, "Tipo Borrado", Snackbar.LENGTH_LONG).show();
                                    NavHostFragment.findNavController(admTipos.this)
                                            .navigate(R.id.action_admTipos_to_admin);
                                }

                                @Override
                                public void onFailure(Call<Number> call, Throwable t) {
                                    Snackbar.make(v, "No se pudo borrar el tipo", Snackbar.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                    fila.addView(textId);
                    fila.addView(textTipo);
                    fila.addView(btnModificar);
                    fila.addView(btnEliminar);
                    tablaTipos.addView(fila);
                }
            }

            @Override
            public void onFailure(Call<List<Tipo>> call, Throwable t) {
                Snackbar.make(v, "No se pudieron encontrar los tipos", Snackbar.LENGTH_LONG).show();
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admTipos.this)
                        .navigate(R.id.action_admTipos_to_crearTipo);
            }
        });
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admTipos.this)
                        .navigate(R.id.action_admTipos_to_admin);
            }
        });
        return v;
    }
}