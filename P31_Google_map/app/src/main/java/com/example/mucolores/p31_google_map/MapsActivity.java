package com.example.mucolores.p31_google_map;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback
{
    private GoogleMap mMap;

    private Spinner mSpnLocation;
    private static String[][] mLocations={
            {"台北101","25.0336110,121.5650000"},
            {"中國長城","40.0000350,119.7672800"},
            {"紐約自由女神像","40.689240,-74.0445000"},
            {"巴黎鐵塔","48.8582220,2.2945000"}
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mSpnLocation = (Spinner)this.findViewById(R.id.spnLocation);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);

        for(int i=0;i<mLocations.length;i++){
            arrayAdapter.add(mLocations[i][0]);
        }

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpnLocation.setAdapter(arrayAdapter);
        mSpnLocation.setOnItemSelectedListener(mSpnLocationOISL);
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

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    private AdapterView.OnItemSelectedListener mSpnLocationOISL = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            updateMapLocation();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };

    private void updateMapLocation (){
        int iSelected = mSpnLocation.getSelectedItemPosition();
        String[] sLocation = mLocations[iSelected][1].split(",");
        double dLat = Double.parseDouble(sLocation[0]);
        double dLon = Double.parseDouble(sLocation[1]);
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(dLat,dLon),14));
    }
}
