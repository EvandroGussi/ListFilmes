package com.example.evandro.app13mob.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.ShareActionProvider;
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
import com.google.android.gms.maps.model.MarkerOptions;

import java.lang.reflect.AnnotatedElement;
import java.util.List;

import static android.R.attr.phoneNumber;

public class EditFragment extends Fragment implements OnMapReadyCallback {

    View vEdit;
    TextInputLayout tilNome;
    Button btEditar;
    MapView mMapView;
    GoogleMap mGoogleMap;
    String id;
    Double lat, lon;
    private ShareActionProvider mShareActionProvider;

    public EditFragment() {

    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        vEdit = inflater.inflate(R.layout.edicao_layout, container, false);
        tilNome = (TextInputLayout) vEdit.findViewById(R.id.tilFilme);
        btEditar = (Button) vEdit.findViewById(R.id.btEditar);
        Button btLigar = (Button) vEdit.findViewById(R.id.btLigar);
        Button btComp = (Button) vEdit.findViewById(R.id.btECompartilhar);
        Button btDel = (Button) vEdit.findViewById(R.id.btDeletar);



        FragmentManager fragmentManager = null;
        RecyclerView.ViewHolder holder = null;

//        AnotacoesFragment anotacoesFragment = new AnotacoesFragment();
//        FragmentManager frag = getActivity().getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.remove(anotacoesFragment);


        id = getArguments().getString("id");
        final Anotacoes anotacoes = Anotacoes.findById(Anotacoes.class, Long.valueOf(id));

        lat = anotacoes.getLat();
        lon = anotacoes.getLon();
        tilNome.getEditText().setText(anotacoes.getNome());

        btEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                anotacoes.setNome(tilNome.getEditText().getText().toString());
                anotacoes.setDescricao("");
                anotacoes.setLat(lat);
                anotacoes.setLon(lon);
                anotacoes.save();

                AnotacoesFragment anotacoesFragment = new AnotacoesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.detach(anotacoesFragment);
                fragmentTransaction.attach(anotacoesFragment);
                fragmentTransaction.replace(R.id.content_frame, anotacoesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btComp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));

            }
        });

        btDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Anotacoes delete = Anotacoes.findById(Anotacoes.class, Long.valueOf(id));
                delete.delete();
                AnotacoesFragment anotacoesFragment = new AnotacoesFragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.detach(anotacoesFragment);
                fragmentTransaction.attach(anotacoesFragment);
                fragmentTransaction.replace(R.id.content_frame, anotacoesFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });

        btLigar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "123456789", null)));
            }
        });


        return vEdit;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMapView = (MapView) vEdit.findViewById(R.id.mapView);

        if (mMapView != null) {
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        final Anotacoes anotacoes = Anotacoes.findById(Anotacoes.class, (long) Integer.parseInt(id));

        MapsInitializer.initialize(getContext());
        mGoogleMap = googleMap;
        googleMap.setMapType(googleMap.MAP_TYPE_NORMAL);
        googleMap.addMarker(new MarkerOptions().position(new LatLng(anotacoes.getLat(), anotacoes.getLon())).title(anotacoes.getNome()));
        CameraPosition cameraPosition = CameraPosition.builder().target(new LatLng(anotacoes.getLat(), anotacoes.getLon())).zoom(16).bearing(0).build();
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
