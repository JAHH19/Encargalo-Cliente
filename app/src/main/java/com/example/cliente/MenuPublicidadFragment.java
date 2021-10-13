package com.example.cliente;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.cliente.adapter.FragmentosFun;

public class MenuPublicidadFragment extends Fragment {

    Button buttoncrear,buttonrevisar ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        FragmentosFun fragmentosFun = new FragmentosFun();
        PublicidadFragment publicidadFragment= new PublicidadFragment();
        MisanunciosFragment misanunciosFragment = new MisanunciosFragment();
        View view =inflater.inflate(R.layout.fragment_menu_publicidad, container, false);

        buttoncrear = view.findViewById(R.id.btn_crearads);
        buttonrevisar = view.findViewById(R.id.btn_verads);

        buttoncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(publicidadFragment.isAdded()){
                    fragmentosFun.showFragment(publicidadFragment,getContext());
                }else{
                    fragmentosFun.createFragment(publicidadFragment,getContext());

                }

            }
        });

        buttonrevisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(misanunciosFragment.isAdded()){
                    fragmentosFun.showFragment(misanunciosFragment,getContext());

                }else{
                    fragmentosFun.createFragment(misanunciosFragment,getContext());

                }

            }
        });

        return view;
    }


}