package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

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

                            savedInstanceState.putString("idMarca",String.valueOf(1));
                            //NavHostFragment.findNavController(admMarcas.this).
                             //       navigate(R.id.action_admMarcas_to_admin);
                        }
                    });
                    btnEliminar.setText("Eliminar");
                    btnEliminar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            NavHostFragment.findNavController(admMarcas.this).
                                    navigate(R.id.action_admMarcas_to_admin);
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
        return v;
    }
}