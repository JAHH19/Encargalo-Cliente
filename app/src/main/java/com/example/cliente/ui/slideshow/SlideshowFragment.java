package com.example.cliente.ui.slideshow;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.cliente.R;
import com.example.cliente.EsctablecimientosActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class SlideshowFragment extends Fragment {

    private SlideshowViewModel slideshowViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        slideshowViewModel =
                ViewModelProviders.of(this).get(SlideshowViewModel.class);
        View view = inflater.inflate(R.layout.fragment_slideshow, container, false);

     //   SupportMapFragment supportMapFragment = (SupportMapFragment)
     //            getChildFragmentManager().findFragmentById(R.id.map);

    //    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
    //        @Override
     /*       public void onMapReady(final GoogleMap googleMap) {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        markerOptions.title("Tu cerro :v");
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
                        googleMap.addMarker(markerOptions);

                    }
                });
            }
        });*/
        CardView cardBodegas = view.findViewById(R.id.cardBodegas);
        CardView cardFarmacias = view.findViewById(R.id.cardFarmacias);
        CardView cardRestaurante = view.findViewById(R.id.cardRest);
        CardView cardFerreteria = view.findViewById(R.id.cardFerreteria);
        cardBodegas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EsctablecimientosActivity.class);
                i.putExtra("establecimiento","BODEGAS");
                startActivity(i);
            }
        });
        cardFarmacias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EsctablecimientosActivity.class);
                i.putExtra("establecimiento","FARMACIAS");
                startActivity(i);
            }
        });
        cardRestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EsctablecimientosActivity.class);
                i.putExtra("establecimiento","RESTAURANTES");
                startActivity(i);
            }
        });
        cardFerreteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getActivity(), EsctablecimientosActivity.class);
                i.putExtra("establecimiento","FERRETERIA");
                startActivity(i);
            }
        });

        return view;
    }


}