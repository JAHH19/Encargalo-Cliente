package com.example.cliente.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.cliente.Model.HomeModel;
import com.example.cliente.R;
import com.example.cliente.adapter.HomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;

    HomeAdapter adapter;
    private List<HomeModel> list;
    private String user;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        //final TextView textView = root.findViewById(R.id.text_home);
        /*homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

         */
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        init(view);

        list = new ArrayList<>();
        adapter = new HomeAdapter(list, getContext());
        recyclerView.setAdapter(adapter);

        loadDataFromFirestore();

    }
    private void init(View view){
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        user = "Tiendita de don Pepe";
    }
    private void loadDataFromFirestore(){
        list.add(new HomeModel("La Tiendita de Don Pepe","","","a","Esta",100,12,1100));
        list.add(new HomeModel("ETE SECH","","","a","Esta",10,1,100));
        list.add(new HomeModel("EL PEPE","","","pli","Esta",2,2,10));
        list.add(new HomeModel("UCHURUS","","","ve","Esta",1,3,110));

        adapter.notifyDataSetChanged();
    }
}