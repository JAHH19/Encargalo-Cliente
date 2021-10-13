package com.example.cliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cliente.adapter.FragmentosFun;


public class TerminosCondicionesFragment extends Fragment {

    Button btncerrar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        PublicidadFragment publicidadFragment = new PublicidadFragment();
        Fragment me=this;
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_terminos_condiciones, container, false);
        btncerrar=view.findViewById(R.id.cerrarterm);


        FragmentosFun fragmentosFun = new FragmentosFun();

        btncerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentosFun.hideFragment(me,getContext());
                fragmentosFun.showFragment(publicidadFragment,getContext());
            }
        });



        return view;
    }
}