package com.example.cliente;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MenuPublicidadFragment extends Fragment {

    Button buttoncrear,buttonrevisar ;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        PublicidadFragment publicidadFragment= new PublicidadFragment();
        MisanunciosFragment misanunciosFragment = new MisanunciosFragment();
        View view =inflater.inflate(R.layout.fragment_menu_publicidad, container, false);

        buttoncrear = view.findViewById(R.id.btn_crearads);
        buttonrevisar = view.findViewById(R.id.btn_verads);

        buttoncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(publicidadFragment);
            }
        });

        buttonrevisar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createFragment(misanunciosFragment);
            }
        });

        return view;
    }

    private void createFragment(Fragment fragment){
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.nav_host_fragment, fragment)

                .commit();
    }
    private void showFragment(Fragment fragment){

        getActivity().getSupportFragmentManager().beginTransaction()
                .show(fragment)
                .commit();
    }
    private void hideFragment(Fragment fragment){

        getActivity().getSupportFragmentManager().beginTransaction()
                .hide(fragment)
                .commit();
    }
}