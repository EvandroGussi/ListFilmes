package com.example.evandro.app13mob.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.evandro.app13mob.R;
import com.example.evandro.app13mob.model.Anotacoes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class CadastrarFragment extends Fragment implements OnMapReadyCallback {

    View vCadastrar;
    TextInputLayout tilNome, tilDescricao;
    Button btCadastrar;
    Double lat, lon;
    MapView mMapView;
    GoogleMap mGoogleMap;

    public CadastrarFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vCadastrar = inflater.inflate(R.layout.cadastrar_layout, container, false);

        tilNome = (TextInputLayout) vCadastrar.findViewById(R.id.tilnomeFilme);
        btCadastrar = (Button) vCadastrar.findViewById(R.id.btCadastrar);

        btCadastrar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Anotacoes anotacoes = new Anotacoes(tilNome.getEditText().getText().toString(),
                        "", lat, lon);
                anotacoes.save();
            }
        });

        return vCadastrar;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) vCadastrar.findViewById(R.id.mapView);

        if (mMapView != null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        MapsInitializer.initialize(getContext());

        mGoogleMap = googleMap;
        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(-23.55895192, -46.6053772)).zoom(16).bearing(0).build();

        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

        mGoogleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                googleMap.clear();
                lon = latLng.longitude;
                lat = latLng.latitude;
                googleMap.addMarker(new MarkerOptions().position(new LatLng(lat, lon)).title(""));

            }

//            @Override
//            public void onMapClick(LatLng point) {
//                Log.d("DEBUG","Map clicked [" + point.latitude + " / " + point.longitude + "]");
//                //Do your stuff with LatLng here
//                //Then pass LatLng to other activity
//            }
        });
    }
}
