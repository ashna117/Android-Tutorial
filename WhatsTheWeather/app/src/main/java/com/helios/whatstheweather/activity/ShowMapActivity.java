package com.helios.whatstheweather.activity;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.helios.whatstheweather.R;

public class ShowMapActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    //LatLng location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
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

        double latitude = getIntent().getDoubleExtra("latitude",-1);
         double longitude = getIntent().getDoubleExtra("longitude",-1);
         String city = getIntent().getStringExtra("city");

        if(latitude>0 && longitude>0) {
            showMyMap(latitude, longitude, "Current Location");
        }
        else
        {
            if (city.equals("Dubai"))
                showMyMap(25.2048, 55.2708, city);
            else if (city.equals("Venice"))
                showMyMap(45.4408, 12.3155, city);
            else if (city.equals("Tokyo"))
                showMyMap(35.6895, 139.6917, city);
            else if (city.equals("Seoul"))
                showMyMap(37.5665, 126.9780, city);
            else if (city.equals("Singapore"))
                showMyMap(1.3521, 103.8198, city);
            else if (city.equals("Beijing"))
                showMyMap(39.9042, 116.4074, city);
            else if (city.equals("Kathmandu"))
                showMyMap(27.7172, 85.3240, city);
        }

    }
    public void showMyMap(double latitude, double longitude, String cityname)
    {
        LatLng location = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(location).title("Marker in "+cityname));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(location));
        if(cityname.equals("Current Location"))
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 16.0f ) );
        else
            mMap.animateCamera( CameraUpdateFactory.zoomTo( 12.0f ) );
    }
}
