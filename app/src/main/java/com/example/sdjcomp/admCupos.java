package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class admCupos extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaCupos;
    private EditText edtCupoAdmin;
    private TextView txtPruebamMarca;
    private Button btnVer, btnDesasignar,btnCrear, btnVolver;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public admCupos() {
        // Required empty public constructor
    }

    public static admCupos newInstance(String param1, String param2) {
        admCupos fragment = new admCupos();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getAll/cupos/";
        View v = inflater.inflate(R.layout.fragment_adm_cupos,container,false);

        //btnVer = v.findViewById(R.id.btnAdmCupoUso);
        //btnDesasignar = v.findViewById(R.id.btnAdmDesasignarCupo);
        btnCrear = v.findViewById(R.id.btnadmCrearCupo);
        btnVolver = v.findViewById(R.id.btnVolverAdmCupos);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaCupos = v.findViewById(R.id.tablaCupos);
        Call<List<Cupo>> call = iRetrofit.executeGetCupos();
        call.enqueue(new Callback<List<Cupo>>() {
            @Override
            public void onResponse(Call<List<Cupo>> call, Response<List<Cupo>> response) {
                for(int i=0; i<response.body().size(); i++){
                    TableRow fila = new TableRow(getActivity());
                    TextView textId = new TextView(getActivity());
                    TextView textSeccion = new TextView(getActivity());
                    TextView textEstado = new TextView(getActivity());
                    Button btnModificar = new Button(getActivity());
                    Button btnEliminar = new Button(getActivity());
                    textId.setText(String.valueOf(response.body().get(i).getIdCupo()));
                    textSeccion.setText(response.body().get(i).getSeccion());
                    textEstado.setText(String.valueOf(response.body().get(i).getEstado()));
                    btnModificar.setText("Modificar");

                    btnModificar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            System.out.println("id cupo = " + Integer.parseInt(textId.getText().toString()));
                            ((Sesion)getActivity().getApplicationContext()).setIdCupo(Integer.parseInt(textId.getText().toString()));
                            NavHostFragment.findNavController(admCupos.this).
                                    navigate(R.id.action_admCupos_to_modificarCupos);
                        }
                    });
                    btnEliminar.setText("Eliminar");
                    btnEliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Call call1 = iRetrofit.executeDeleteCupos(Integer.parseInt(textId.getText().toString()));
                            call1.enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    Toast.makeText(getContext(), "Cupo Borrado", Toast.LENGTH_LONG).show();
                                    NavHostFragment.findNavController(admCupos.this).
                                            navigate(R.id.action_admCupos_to_admin);
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    Toast.makeText(getContext(), "No se pudo borrar la marca", Toast.LENGTH_LONG).show();
                                }
                            });

                        }
                    });
                    textId.setGravity(Gravity.CENTER);
                    textSeccion.setGravity(Gravity.CENTER);
                    textEstado.setGravity(Gravity.CENTER);
                    fila.addView(textId);
                    fila.addView(textSeccion);
                    fila.addView(textEstado);
                    fila.addView(btnModificar);
                    fila.addView(btnEliminar);
                    tablaCupos.addView(fila);
                }

            }

            @Override
            public void onFailure(Call<List<Cupo>> call, Throwable t) {
                System.out.println("fail");
            }
        });
        /*btnVer = v.findViewById(R.id.btnAdmCupoUso);
        btnDesasignar = v.findViewById(R.id.btnAdmDesasignarCupo);
        btnAsignar = v.findViewById(R.id.btnadmAsignarCupo);
        btnVolver = v.findViewById(R.id.btnVolverAdmCupos);*/
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admCupos.this).
                        navigate(R.id.action_admCupos_to_crearCupos);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admCupos.this).
                        navigate(R.id.action_admCupos_to_admin);
            }
        });
        return v;
    }
}