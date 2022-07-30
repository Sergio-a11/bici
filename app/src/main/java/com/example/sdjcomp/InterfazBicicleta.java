package com.example.sdjcomp;

import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InterfazBicicleta extends Fragment {

        private Retrofit retrofit;
    private IRetroFit iRetrofit;
    private String URL="http://192.168.20.25:3000/getBikes/";
    private RecyclerView recyclerViewCiclas;
    private RecyclerViewAdapter adapterCiclas;
    AlertDialog.Builder alertBici;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_interfaz_bicicleta, container, false);

        //btnVer = (Button) v.findViewById(R.id.btnCargar);
        retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create()).build();
        iRetrofit = retrofit.create(IRetroFit.class);

        alertBici = new AlertDialog.Builder(getActivity());
        //cardView = (CardView) v.findViewById(R.id.cardView);

        List<Bicicleta> lstBicicletas = new ArrayList<>();
        System.out.println("((Sesion)getActivity().getApplicationContext()).getCodigo() = " + ((Sesion)getActivity().getApplicationContext()).getCodigo());
        Call<List<Bicicleta>> call = iRetrofit.executeGetBikes(((Sesion)getActivity().getApplicationContext()).getCodigo());
        call.enqueue(new Callback<List<Bicicleta>>() {
            @Override
            public void onResponse(Call<List<Bicicleta>> call, Response<List<Bicicleta>> response) {
                if(response.code()==200)
                {
                    System.out.println(response.body().size());
                    for(int i=0; i<response.body().size();i++)
                    {
                        lstBicicletas.add(response.body().get(i));
                    }
                    recyclerViewCiclas=(RecyclerView)v.findViewById(R.id.recBicicleta);
                    recyclerViewCiclas.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapterCiclas=new RecyclerViewAdapter(lstBicicletas);

                    adapterCiclas.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            System.out.println("Averrer");
                            System.out.println(lstBicicletas.get(recyclerViewCiclas.getChildAdapterPosition(view)).getMarca());
                            alertBici.setMessage("Marca: "+lstBicicletas.get(recyclerViewCiclas.getChildAdapterPosition(view)).getMarca()+
                            "\nTipo: "+lstBicicletas.get(recyclerViewCiclas.getChildAdapterPosition(view)).getTipo()+
                            "\nId: "+String.valueOf(lstBicicletas.get(recyclerViewCiclas.getChildAdapterPosition(view)).getIdBicicleta())+
                            "\nColor: "+lstBicicletas.get(recyclerViewCiclas.getChildAdapterPosition(view)).getColor())
                                    .setCancelable(true).setTitle("Tu Bicicleta")
                                    .create().show();
                        }
                    });
                    recyclerViewCiclas.setAdapter(adapterCiclas);
                }
            }

            @Override
            public void onFailure(Call<List<Bicicleta>> call, Throwable t) {
                Toast.makeText(getContext(), t.getMessage(), Toast.LENGTH_LONG).show();
                System.out.println("Fail");
            }
        });

        return v;
    }
}