package com.example.cliente;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.cliente.adapter.FragmentosFun;


public class PagoPublicidadFragment extends Fragment {


    Button btncerrar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pago_publicidad, container, false);
        // Inflate the layout for this fragment
        btncerrar = view.findViewById(R.id.btn_cerrarpago);
        FragmentosFun fragmentosFun = new FragmentosFun();
        PublicidadFragment publicidadFragment = new PublicidadFragment();
        Fragment me=this;


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