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
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class admPreguntas extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaPreguntas;
    private Button btnVolver,btnCrear;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getAll/preguntas/";
        View v = inflater.inflate(R.layout.fragment_adm_preguntas,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaPreguntas = v.findViewById(R.id.tablaPreguntas);
        btnVolver = v.findViewById(R.id.btnPreguToAdmin);
        btnCrear = v.findViewById(R.id.btnCrearPregunta);
        Call<List<Pregunta>> call = iRetrofit.executeGetAll("preguntas");

        call.enqueue(new Callback<List<Pregunta>>() {
            @Override
            public void onResponse(Call<List<Pregunta>> call, Response<List<Pregunta>> response) {
                for(int i=0; i<response.body().size(); i++){
                    TableRow fila = new TableRow(getActivity());
                    TextView textId = new TextView(getActivity());
                    TextView textPregunta = new TextView(getActivity());
                    Button btnModificar = new Button(getActivity());
                    Button btnEliminar = new Button(getActivity());
                    textId.setText(String.valueOf(response.body().get(i).getCodigo()));
                    textPregunta.setText(response.body().get(i).getPregunta());
                    btnModificar.setText("Modificar");
                    btnModificar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println("id pregunta = " + Integer.parseInt(textId.getText().toString()));
                            ((Sesion)getActivity().getApplicationContext()).setIdPreguntas(Integer.parseInt(textId.getText().toString()));
                            NavHostFragment.findNavController(admPreguntas.this).
                                    navigate(R.id.action_admPreguntas_to_modificarPreguntas);
                        }
                    });
                    btnEliminar.setText("Eliminar");
                    btnEliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Call call1 = iRetrofit.executeDeletePregunta(Integer.parseInt(textId.getText().toString()));
                            call1.enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    Toast.makeText(getContext(), "Pregunta Borrada", Toast.LENGTH_LONG).show();
                                    NavHostFragment.findNavController(admPreguntas.this).
                                            navigate(R.id.action_admPreguntas_to_admin);
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    Toast.makeText(getContext(), "No se pudo borrar la pregunta", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    });
                    textId.setGravity(Gravity.CENTER);
                    textPregunta.setGravity(Gravity.CENTER);
                    fila.addView(textId);
                    fila.addView(textPregunta);
                    fila.addView(btnModificar);
                    fila.addView(btnEliminar);
                    tablaPreguntas.addView(fila);
                }
            }

            @Override
            public void onFailure(Call<List<Pregunta>> call, Throwable t) {
                Snackbar.make(v, "No se pudieron encontrar las preguntas", Snackbar.LENGTH_LONG).show();
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admPreguntas.this).
                        navigate(R.id.action_admPreguntas_to_crearPregunta);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admPreguntas.this).
                        navigate(R.id.action_admPreguntas_to_admin);
            }
        });

        return v;
    }
}