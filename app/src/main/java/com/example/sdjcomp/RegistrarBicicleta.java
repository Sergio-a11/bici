package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

public class RegistrarBicicleta extends Fragment {

    TextView txtCedulaPropietario, txtFechaRegistro, txtLugarRegistro, txtMarca, txtNumSerie, txtTipo, txtColor;
    Button btnRegistrar;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_registrar_bicicleta, container, false);

        btnRegistrar = (Button) v.findViewById(R.id.btnRegistrarBicicletaDesdePantallaRegistro);

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtCedulaPropietario = (TextView) v.findViewById(R.id.txtCedulaPropietario);
                txtFechaRegistro = (TextView) v.findViewById(R.id.txtFechaRegistro);
                txtLugarRegistro = (TextView) v.findViewById(R.id.txtLugarRegistro);
                txtMarca = (TextView) v.findViewById(R.id.txtMarca);
                txtNumSerie = (TextView) v.findViewById(R.id.txtNumSerie);
                txtTipo = (TextView) v.findViewById(R.id.txtTipo);
                txtColor = (TextView) v.findViewById(R.id.txtColorCicla);

                /*switch (txtMarca.getText().toString())
                {

                }*/

            }
        });





        return v;
    }
}