package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.sdjcomp.databinding.HomeBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarUsuario#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarUsuario extends Fragment {

    private HomeBinding binding;
    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="http://192.168.20.25:3000/updateUser/";

    private EditText edtNombre;
    private EditText edtCorreo;
    private EditText edtClave;
    private EditText edtRespuesta;
    private Spinner spnPreguntas;
    private Button btnModificar;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ModificarUsuario() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarUsuario.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarUsuario newInstance(String param1, String param2) {
        ModificarUsuario fragment = new ModificarUsuario();
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
        View v = inflater.inflate(R.layout.fragment_modificar_usuario,container,false);

        edtCorreo = v.findViewById(R.id.edtCorreoM);
        edtNombre = v.findViewById(R.id.edtNombreM);
        edtClave = v.findViewById(R.id.edtClaveM);
        edtRespuesta = v.findViewById(R.id.edtRespuestaM);
        spnPreguntas = v.findViewById(R.id.spnPreguntasM);
        btnModificar = v.findViewById(R.id.btnModificarUsuario);

        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        btnModificar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Usuario objU = new Usuario(((Sesion)getActivity().getApplicationContext()).getCodigo(),edtNombre.getText().toString(),
                        edtCorreo.getText().toString(),edtClave.getText().toString(),
                        1,edtRespuesta.getText().toString(),2);

                Call<Usuario> call = iRetrofit.executeUpdateUser(objU);
                call.enqueue(new Callback<Usuario>() {
                    @Override
                    public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                        if(response.code()==200){
                            NavHostFragment.findNavController(ModificarUsuario.this).navigate(R.id.action_fragment_modificar_usuario_to_InterfazEstudiante);
                        }
                    }

                    @Override
                    public void onFailure(Call<Usuario> call, Throwable t) {

                    }
                });
            }
        });

        return v;
    }
}