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

public class admUsuarios extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="";
    private TableLayout tablaUsuarios;
    private Button btnCrear,btnVolver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL="http://"+getResources().getString(R.string.IP)+":3000/getUsers/";
        View v = inflater.inflate(R.layout.fragment_adm_usuarios,container,false);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaUsuarios = v.findViewById(R.id.tablaUsuarios);
        btnVolver = v.findViewById(R.id.btnVolverUsuarioAdmin);
        btnCrear = v.findViewById(R.id.btnCrearUsuarioAdmin);
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
                    Button btnModificar = new Button(getActivity());
                    Button btnEliminar = new Button(getActivity());
                    textCodigo.setText(response.body().get(i).getCodigo());
                    textNombre.setText(response.body().get(i).getNombre());
                    textCorreo.setText(response.body().get(i).getCorreo());
                    textClave.setText(response.body().get(i).getClave());
                    textPseguridad.setText(String.valueOf(response.body().get(i).getPseguridad()));
                    textRseguridad.setText(response.body().get(i).getRseguridad());
                    textRol.setText(String.valueOf(response.body().get(i).getRol_id()));
                    btnModificar.setText("Modificar");
                    btnModificar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((Sesion)getActivity().getApplicationContext()).setCodigo(textCodigo.getText().toString());
                            ((Sesion)getActivity().getApplicationContext()).setNombre(textNombre.getText().toString());
                            ((Sesion)getActivity().getApplicationContext()).setCorreo(textCorreo.getText().toString());
                            ((Sesion)getActivity().getApplicationContext()).setClave(textClave.getText().toString());
                            ((Sesion)getActivity().getApplicationContext()).setPseguridad(textPseguridad.getText().charAt(0));
                            ((Sesion)getActivity().getApplicationContext()).setRseguridad(textRseguridad.getText().toString());
                            NavHostFragment.findNavController(admUsuarios.this).navigate(R.id.action_admUsuarios_to_fragment_modificar_usuario);
                        }
                    });
                    btnEliminar.setText("Eliminar");
                    btnEliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Call call1 = iRetrofit.executeDeleteUser(textCodigo.getText().toString());
                            call1.enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    Snackbar.make(v, "Usuario Borrado", Snackbar.LENGTH_LONG).show();
                                    NavHostFragment.findNavController(admUsuarios.this).
                                            navigate(R.id.action_admUsuarios_to_admin2);
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    Snackbar.make(v, "No se pudo borrar el usuario", Snackbar.LENGTH_LONG).show();
                                }
                            });

                        }
                    });
                    fila.addView(textCodigo);
                    fila.addView(textNombre);
                    fila.addView(textCorreo);
                    fila.addView(textClave);
                    fila.addView(textPseguridad);
                    fila.addView(textRseguridad);
                    fila.addView(textRol);
                    fila.addView(btnModificar);
                    fila.addView(btnEliminar);
                    tablaUsuarios.addView(fila);
                }
            }

            @Override
            public void onFailure(Call<List<Usuario>> call, Throwable t) {
                Snackbar.make(v, "No se pudieron encontrar los usuarios", Snackbar.LENGTH_LONG).show();
            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admUsuarios.this).navigate(R.id.action_admUsuarios_to_fragment_registro2);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admUsuarios.this).navigate(R.id.action_admUsuarios_to_admin2);
            }
        });

        return v;
    }
}