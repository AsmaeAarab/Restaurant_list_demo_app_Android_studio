package com.example.tprestaurant.Activities;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.example.tprestaurant.Model.Restaurant;
import com.example.tprestaurant.R;
import com.example.tprestaurant.fragments.RestaurantAdditionalServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import butterknife.ButterKnife;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Restaurant getSelectedRestaurant = null;
    MarkerOptions mMarkerGoal=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSelectedRestaurant = (Restaurant) getIntent().getSerializableExtra("SelectedRestaurant");

        // fragment des fonctionnalités du restaurant
        RestaurantAdditionalServices fct = new RestaurantAdditionalServices().newInstance(getSelectedRestaurant);
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fragment_fct,fct).commit();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(getSelectedRestaurant.getLatitude(), getSelectedRestaurant.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title(getSelectedRestaurant.getNomRestaurant()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20));
        mMarkerGoal = new MarkerOptions().position(sydney).draggable(true);
    }
}