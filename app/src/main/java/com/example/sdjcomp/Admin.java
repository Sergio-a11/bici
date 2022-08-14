package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Admin extends Fragment {

    private Button btnRoles;
    private Button btnBicicletas;
    private Button btnCupos;
    private Button btnParqueaderos;
    private Button btnMarcas;
    private Button btnPreguntas;
    private Button btnTipos;
    private Button btnUsuarios;
    private Button btnReportes;
    private String URL="";


    public Admin() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //URL="http://"+getResources().getString(R.string.IP)+":3000/updateUser/";
        View v = inflater.inflate(R.layout.fragment_admin,container,false);
        btnRoles = v.findViewById(R.id.btnRoles);
        btnBicicletas = v.findViewById(R.id.btnBicicletas);
        btnCupos = v.findViewById(R.id.btnCupos);
        btnParqueaderos = v.findViewById(R.id.btnParqueaderos);
        btnMarcas = v.findViewById(R.id.btnMarcas);
        btnPreguntas = v.findViewById(R.id.btnPreguntas);
        btnTipos = v.findViewById(R.id.btnTipos);
        btnUsuarios = v.findViewById(R.id.btnUsuarios);
        btnReportes = v.findViewById(R.id.btnReportes);

        btnParqueaderos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Admin.this).navigate(R.id.action_admin_to_admParqueaderos);
            }
        });
        btnMarcas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Admin.this).navigate(R.id.action_admin_to_admMarcas);
            }
        });
        btnPreguntas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Admin.this).navigate(R.id.action_admin_to_admPreguntas);
            }
        });
        btnTipos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Admin.this).navigate(R.id.action_admin_to_admTipos);
            }
        });
        btnUsuarios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Admin.this).navigate(R.id.action_admin_to_admUsuarios);
            }
        });
        btnBicicletas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Admin.this).navigate(R.id.action_admin_to_admBicicletas);
            }
        });
        btnRoles.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Admin.this).navigate(R.id.action_admin_to_roles);
            }
        });
        btnCupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Admin.this).navigate(R.id.action_admin_to_admCupos);
            }
        });

        btnReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(Admin.this).navigate(R.id.action_admin_to_reportes);
            }
        });


        return v;
    }
}