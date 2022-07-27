package com.example.sdjcomp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InterfazBicicleta extends Fragment {

    private TextView text;
    private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="http://192.168.20.25:3000/getBikes/";
    private Button btnVer;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_interfaz_bicicleta, container, false);

        btnVer = (Button) v.findViewById(R.id.btnCargar);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        btnVer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                text = (TextView) v.findViewById(R.id.txtBici);
                System.out.println("Aver");

                List<Bicicleta> list = new ArrayList<>();
                System.out.println("((Sesion)getActivity().getApplicationContext()).getCodigo() = " + ((Sesion)getActivity().getApplicationContext()).getCodigo());
                Call<List<Bicicleta>> call = iRetrofit.executeGetBikes(((Sesion)getActivity().getApplicationContext()).getCodigo());
                System.out.println("ex "+call.isExecuted());
                call.enqueue(new Callback<List<Bicicleta>>() {
                    @Override
                    public void onResponse(Call<List<Bicicleta>> call, Response<List<Bicicleta>> response) {
                        if(response.code()==200)
                        {
                            System.out.println(response.body().size());
                            for(int i=0; i<response.body().size();i++)
                            {
                                list.add(response.body().get(i));
                            }
                            for (Bicicleta b: list) {
                                System.out.println("b.getCedulaPropietario() = " + b.getCedulaPropietario());
                                text.setText(b.getEstudiante_id());
                                System.out.println("nel");
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Bicicleta>> call, Throwable t) {
                        Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                        System.out.println("Fail");
                    }
                });


            }
        });



        return v;
    }
}