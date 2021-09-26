package com.example.cliente;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AjustesActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AjustesActivity extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AjustesActivity() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment nav_ajustes.
     */
    // TODO: Rename and change types and number of parameters
    public static AjustesActivity newInstance(String param1, String param2) {
        AjustesActivity fragment = new AjustesActivity();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_ajustes,container,false);
        Switch sw1 = vista.findViewById(R.id.switch1);
        SharedPreferences sharedpref = getActivity().getSharedPreferences("ARCHIVOBIO", Context.MODE_PRIVATE);
        String valorx = sharedpref.getString("BIO","0");
        if (valorx.equals("0")){
            sw1.setChecked(false);
        }if (valorx.equals("1")){
            sw1.setChecked(true);
        }

        sw1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString("BIO","1");
                    editor.commit();
                }else {
                    SharedPreferences.Editor editor = sharedpref.edit();
                    editor.putString("BIO","0");
                    editor.commit();
                }
            }
        });

        return vista;

    }
}