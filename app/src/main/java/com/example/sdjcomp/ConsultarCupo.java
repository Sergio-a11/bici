package com.example.sdjcomp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ConsultarCupo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ConsultarCupo extends Fragment {

    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="http://192.168.1.14:3000/getOne/";

    private ImageButton btnConsultarEstudiante;
    private EditText edtConsultarEstudiante;

    AlertDialog.Builder fin;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ConsultarCupo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ConsultarCupo.
     */
    // TODO: Rename and change types and number of parameters
    public static ConsultarCupo newInstance(String param1, String param2) {
        ConsultarCupo fragment = new ConsultarCupo();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.consultar_cupo,container,false);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        btnConsultarEstudiante = v.findViewById(R.id.btnConsultarCodigo);
        edtConsultarEstudiante = v.findViewById(R.id.edtConsultarCodigo);
        fin = new AlertDialog.Builder(getActivity());


        btnConsultarEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Call<Usuario> call = iRetrofit.executeGetUserByCode(edtConsultarEstudiante.getText().toString()+",codigo,usuarios");
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.code()==200){
                            System.out.println("Usuario Encontrado");

                            fin.setMessage(response.body().getCodigo()+"\n"+response.body().getCorreo()
                                    +"\n"+response.body().getNombre()).setCancelable(false).setNegativeButton("Volver al menu principal", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    NavHostFragment.findNavController(ConsultarCupo.this).navigate(R.id.action_consultarCupo_self);
                                }
                            });
                            AlertDialog alerta = fin.create();
                            alerta.setTitle("Informacion Estudiante");
                            alerta.show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {
                        Toast.makeText(getContext(), "Usuario No Encontrado", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        return v;
    }
}