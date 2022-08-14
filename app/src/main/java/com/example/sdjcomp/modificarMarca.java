package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class modificarMarca extends Fragment {

    private TextView txtPrueba;

    public modificarMarca() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        txtPrueba.setText(savedInstanceState.getString("idMarca"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.modificar_marca,container,false);

        txtPrueba = v.findViewById(R.id.txtPruebaMarca);

        return v;
    }
}