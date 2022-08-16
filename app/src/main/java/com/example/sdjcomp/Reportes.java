package com.example.sdjcomp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.fragment.NavHostFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import retrofit2.Retrofit;


public class Reportes extends Fragment {


    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private ViewPageAdapter viewPageAdapter;
    private Button btnReportes;
    public String[] titles = new String[]{"Parqueaderos","Bicicletas","Usuarios"};


    public Reportes() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_reportes, container, false);
       tabLayout = v.findViewById(R.id.tabReportes);
       viewPager2 = v.findViewById(R.id.viewPager);

        viewPageAdapter = new ViewPageAdapter(getActivity());
                viewPager2.setAdapter(viewPageAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            tab.setText(titles[position]);
        }).attach();

        btnReportes = v.findViewById(R.id.btnReportesParam);
        btnReportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(v, "Seleccione un tipo de reporte y rellene os campos", Snackbar.LENGTH_LONG).show();
                NavHostFragment.findNavController(Reportes.this).navigate(R.id.action_reportes_to_reporteParametrizados);
            }
        });

        return v;
    }
}