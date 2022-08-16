package com.example.sdjcomp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentResultListener;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class admMarcas extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL = "";
    private TableLayout tablaMarcas;
    private EditText edtCupoAdmin;
    private TextView txtPruebamMarca;
    private Button btnCrear,btnVolver;

    public admMarcas() {
        // Required empty public constructor
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        URL = "http://" + getResources().getString(R.string.IP) + ":3000/getMarcas/";
        View v = inflater.inflate(R.layout.fragment_adm_marcas, container, false);

        btnCrear = v.findViewById(R.id.btnCrearMarca);
        btnVolver = v.findViewById(R.id.btnVolverAdmMarca);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);
        tablaMarcas = v.findViewById(R.id.tablaMarcas);
        edtCupoAdmin = v.findViewById(R.id.edtCupoAdmin);
        Call<List<Marca>> call = iRetrofit.executeGetMarca();

        call.enqueue(new Callback<List<Marca>>() {
            @Override
            public void onResponse(Call<List<Marca>> call, Response<List<Marca>> response) {
                for (int i = 0; i < response.body().size(); i++) {
                    int j = response.body().get(i).getId();
                    TableRow fila = new TableRow(getActivity());
                    TextView textId = new TextView(getActivity());
                    TextView textMarca = new TextView(getActivity());
                    Button btnModificar = new Button(getActivity());
                    Button btnEliminar = new Button(getActivity());
                    textId.setText(String.valueOf(response.body().get(i).getId()));
                    textMarca.setText(response.body().get(i).getMarca());
                    btnModificar.setText("Modificar");
                    btnModificar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            edtCupoAdmin.setText(textMarca.getText().toString());

                            ((Sesion)getActivity().getApplicationContext()).setIdMarca(Integer.parseInt(textId.getText().toString()));
                            NavHostFragment.findNavController(admMarcas.this).
                                    navigate(R.id.action_admMarcas_to_modificarMarca);
                        }
                    });
                    btnEliminar.setText("Eliminar");
                    btnEliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Call call1 = iRetrofit.executeDeleteMarca(Integer.parseInt(textId.getText().toString()));
                            call1.enqueue(new Callback() {
                                @Override
                                public void onResponse(Call call, Response response) {
                                    Snackbar.make(v, "Marca Borrada", Snackbar.LENGTH_LONG).show();
                                    NavHostFragment.findNavController(admMarcas.this).
                                            navigate(R.id.action_admMarcas_to_admin);
                                }

                                @Override
                                public void onFailure(Call call, Throwable t) {
                                    Snackbar.make(v, "No se pudo borrar la marca", Snackbar.LENGTH_LONG).show();
                                }
                            });
                        }
                    });
                    textId.setGravity(Gravity.CENTER);
                    textMarca.setGravity(Gravity.CENTER);
                    fila.addView(textId);
                    fila.addView(textMarca);
                    fila.addView(btnModificar);
                    fila.addView(btnEliminar);
                    tablaMarcas.addView(fila);
                }
            }

            @Override
            public void onFailure(Call<List<Marca>> call, Throwable t) {

            }
        });

        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admMarcas.this).
                        navigate(R.id.action_admMarcas_to_registrarMarca);
            }
        });

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(admMarcas.this).
                        navigate(R.id.action_admMarcas_to_admin);
            }
        });

        return v;
    }
}