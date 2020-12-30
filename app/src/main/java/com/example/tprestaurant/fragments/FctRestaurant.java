package com.example.tprestaurant.fragments;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.opengl.EGLExt;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tprestaurant.Activities.MenuActivity;
import com.example.tprestaurant.DialogBox.DialogReductionMsg;
import com.example.tprestaurant.Model.Restaurant;
import com.example.tprestaurant.R;
import com.google.android.gms.maps.model.LatLng;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FctRestaurant extends Fragment {

Context contextDuFragement;
   public Restaurant getSelectedRestaurant;
   public int reduction;
    public FctRestaurant() {
        // Required empty public constructor
    }

    public FctRestaurant newInstance(Restaurant getSelectedRestaurant) {
        FctRestaurant fragment = new FctRestaurant();
        Bundle args = new Bundle();
        args.putSerializable("getSelectedRestaurant", getSelectedRestaurant);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_fct_restaurant, container, false);
        ButterKnife.bind(this,view);
        contextDuFragement=view.getContext();
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public LatLng getCurrentLocation(){
        LocationManager lm = (LocationManager) getActivity().getSystemService(contextDuFragement.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(contextDuFragement, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(contextDuFragement, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( getActivity(), new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
        Location currentLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng current = new LatLng(currentLocation.getLatitude(),currentLocation.getLongitude());
        return current;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @OnClick(R.id.itineraire_btn)
    public void OnClick_iterinaire_btn() {
        Geocoder geocoder;
        List<Address> addresses;
        String address="";
        geocoder = new Geocoder(contextDuFragement, Locale.getDefault());
        getSelectedRestaurant = (Restaurant) getArguments().getSerializable("getSelectedRestaurant");
        LatLng sydney = new LatLng(getSelectedRestaurant.getLatitude(), getSelectedRestaurant.getLongitude());

        try {
            addresses = geocoder.getFromLocation(getSelectedRestaurant.getLatitude(),getSelectedRestaurant.getLongitude(), 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
            address = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
            String city = addresses.get(0).getLocality();
            String state = addresses.get(0).getAdminArea();
            String country = addresses.get(0).getCountryName();
            String postalCode = addresses.get(0).getPostalCode();
            String knownName = addresses.get(0).getFeatureName(); // Only if available else return NULL
        }catch (Exception e){

        }

        try {
            Uri uri = Uri.parse("http://maps.google.com/maps?saddr="+getCurrentLocation().latitude+","+getCurrentLocation().longitude+"&daddr="+address);
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

    @OnClick(R.id.appeler_btn)
    public void AppelerRestaurant(){
        Uri teleNum = Uri.parse("tel:0600000000");
        Intent call = new Intent(Intent.ACTION_CALL,teleNum);
        if (ActivityCompat.checkSelfPermission(contextDuFragement, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
            return;
        }
        startActivity(call);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        reduction=0;
        if(result != null){
            if(result.getContents() == null)
            {
                Toast.makeText(contextDuFragement,"result not found",Toast.LENGTH_LONG).show();
            }else{
                try{
                    JSONObject obj=new JSONObject(result.getContents());
                    reduction=Integer.parseInt(obj.getString("reduction"));
                    Toast.makeText(contextDuFragement,"le code de reduction: "+obj.getString("code"),Toast.LENGTH_LONG).show();

                }catch (JSONException e){
                    Toast.makeText(contextDuFragement,"QR code est invalide",Toast.LENGTH_LONG).show();//"le code de reduction: "+result.getContents()
                }
            }
        }else {
            Toast.makeText(contextDuFragement,"camera non active",Toast.LENGTH_LONG).show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @OnClick(R.id.afficherMenu_btn)
    public void AfficherMenu(){
        getSelectedRestaurant = (Restaurant) getArguments().getSerializable("getSelectedRestaurant");
        Intent menuLayout = new Intent(contextDuFragement, MenuActivity.class);
        // menuLayout.putExtra("menu", MenuData.MenuPizza(1));
        Toast.makeText(contextDuFragement, "retaurant category: "+getSelectedRestaurant.getIdCategory(), Toast.LENGTH_SHORT).show();
        menuLayout.putExtra("Restaurant", (Serializable)getSelectedRestaurant);
        startActivity(menuLayout);
       // getActivity().finish();
    }


    @OnClick(R.id.ScanCodeQR_btn)
    public void ScannerCodeQR(){
        Toast.makeText(contextDuFragement,"Scanne QRCode",Toast.LENGTH_LONG).show();
       // IntentIntegrator qrScan = new IntentIntegrator((Activity) contextDuFragement);
       // qrScan.initiateScan();
        IntentIntegrator.forSupportFragment(FctRestaurant.this).initiateScan();
        //if(reduction!=0)
        openDialog(reduction);
    }
    public void openDialog(int reduction){
        DialogReductionMsg dialogMsg = new DialogReductionMsg(reduction);
        dialogMsg.show(getActivity().getSupportFragmentManager(),"dialog message");
    }

}