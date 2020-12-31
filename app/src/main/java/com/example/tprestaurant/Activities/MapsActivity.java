package com.example.tprestaurant.Activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.tprestaurant.DialogBox.DialogReductionMsg;
import com.example.tprestaurant.Model.Restaurant;
import com.example.tprestaurant.R;
import com.example.tprestaurant.TaskLoadedCallback;
import com.example.tprestaurant.fragments.FctRestaurant;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Restaurant getSelectedRestaurant = null;
    MarkerOptions mMarkerGoal=null;
    MarkerOptions mMarkerStart=null;
    private Polyline currentPolyline;
    private int reduction;
    private View viewbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSelectedRestaurant = (Restaurant) getIntent().getSerializableExtra("SelectedRestaurant");

        FctRestaurant fct = new FctRestaurant().newInstance(getSelectedRestaurant);
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