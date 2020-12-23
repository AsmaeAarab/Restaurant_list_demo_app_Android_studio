package com.example.tprestaurant.Activities;

import android.Manifest;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.tprestaurant.FetchURL;
import com.example.tprestaurant.ItineraireTask;
import com.example.tprestaurant.Model.Restaurant;
import com.example.tprestaurant.R;
import com.example.tprestaurant.TaskLoadedCallback;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, TaskLoadedCallback {

    private GoogleMap mMap;
    Restaurant getSelectedRestaurant = null;
    MarkerOptions mMarkerGoal=null;
    MarkerOptions mMarkerStart=null;
    private Polyline currentPolyline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        ButterKnife.bind(this);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        getSelectedRestaurant = (Restaurant) getIntent().getSerializableExtra("SelectedRestaurant");
        Toast.makeText(this, "coucou from " + getSelectedRestaurant.getLatitude() + " , " + getSelectedRestaurant.getLongitude(), Toast.LENGTH_SHORT).show();
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(getSelectedRestaurant.getLatitude(), getSelectedRestaurant.getLongitude());
        mMap.addMarker(new MarkerOptions().position(sydney).title(getSelectedRestaurant.getNomRestaurant()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 20));
        mMarkerGoal = new MarkerOptions().position(sydney).draggable(true);
        mMarkerStart = new MarkerOptions().position(getCurrentLocation()).draggable(true);

        //String url = getUrl(mMarkerStart.getPosition(),mMarkerGoal.getPosition(),"driving");
        //new FetchURL(this).execute(url,"driving");
       // new FetchURL(this).execute("https://maps.googleapis.com/maps/api/directions/json?origin=Disneyland&destination=Universal+Studios+Hollywood&key="+getString(R.string.google_maps_key), "driving");
    }

   @OnClick(R.id.itineraire_btn)
    public void OnClick_iterinaire_btn(){
       getSelectedRestaurant = (Restaurant) getIntent().getSerializableExtra("SelectedRestaurant");
        LatLng sydney = new LatLng(getSelectedRestaurant.getLatitude(), getSelectedRestaurant.getLongitude());
       // mMap.addPolyline(new PolylineOptions()
        //        .add(sydney).
     //  Polyline mPolyline = mMap.addPolyline(new PolylineOptions().geodesic(true));
       Toast.makeText(this, "from itinéraire btn"+sydney.latitude, Toast.LENGTH_SHORT).show();

       /*mMap.addPolyline((new PolylineOptions())
               .add(current, sydney)
               .color(Color.GREEN)
               .width(5f));*/
        mMap.addMarker(mMarkerStart);
        mMap.addMarker(mMarkerGoal);
       //new FetchURL(this).execute("https://maps.googleapis.com/maps/api/directions/json?origin=sale&destination=KFC+kenitra&key="+getString(R.string.google_maps_key), "driving");
       //Toast.makeText(this,"after the call of fetchURL",Toast.LENGTH_LONG);


       //Appel de la méthode asynchrone
       //new ItineraireTask(this, mMap, mMarkerStart.getPosition(), mMarkerGoal.getPosition()).execute();
      try {
          Uri uri = Uri.parse("http://maps.google.com/maps?saddr="+getCurrentLocation().latitude+"&daddr="+getSelectedRestaurant.getNomRestaurant());
          Intent intent = new Intent(Intent.ACTION_VIEW, uri);
          intent.setPackage("com.google.android.apps.maps");
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
      }catch (ActivityNotFoundException e){
          Uri uri = Uri.parse("https://www.google.com/store/apps/details?id=com.google.android.apps.maps");
          Intent intent = new Intent(Intent.ACTION_VIEW, uri);
          intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
          startActivity(intent);
      }
    }
    private String getUrl(LatLng origin, LatLng dest, String directionMode) {
        // Origin of route
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        // Destination of route
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        // Mode
        String mode = "mode=" + directionMode;
        // Building the parameters to the web service
        String parameters = str_origin + "&" + str_dest + "&" + mode;
        // Output format
        String output = "json";
        // Building the url to the web service
        String url = "https://maps.googleapis.com/maps/api/directions/" + output + "?" + parameters + "&key=" + getString(R.string.google_maps_key);
        return url;
    }
    @Override
    public void onTaskDone(Object... values) {
        if (currentPolyline != null)
            currentPolyline.remove();
        currentPolyline = mMap.addPolyline((PolylineOptions) values[0]);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mMarkerGoal.getPosition(), 1));
        Toast.makeText(this,"after task done",Toast.LENGTH_LONG);

    }

    public LatLng getCurrentLocation(){
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        Location currentLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng current = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        return current;
    }
    @OnClick(R.id.appeler_btn)
    public void AppelerRestaurant(){
        Uri teleNum = Uri.parse("tel:0600000000");
        Intent call = new Intent(Intent.ACTION_CALL,teleNum);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        startActivity(call);
    }
}