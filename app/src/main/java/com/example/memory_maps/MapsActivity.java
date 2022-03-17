package com.example.memory_maps;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.memory_maps.R;

import java.util.ArrayList;
import java.util.Arrays;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {

    // Objects.
    private Button button;
    private GoogleMap mMap;
    private ArrayList<ArrayList<Double>> savedMarkers = new ArrayList<ArrayList<Double>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Create on click listener to take you to the second activity when the button
        // is pressed.
        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                openActivity2();
            }
        });

    }

    public void openActivity2() {
        // Create the intent to go to the second activity (Photo Gallery).
        Intent intent = new Intent(this, photoGallery.class);
        startActivity(intent);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        // Create googleMap object.
        mMap = googleMap;

        // Set up long click listener.
        mMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

        @Override
        public void onMapLongClick(LatLng point) {

            // Create marker at location of long press.
            MarkerOptions marker = new MarkerOptions().position(new LatLng(point.latitude, point.longitude));

            // Put the marker on the map.
            mMap.addMarker(marker);

            // Add the marker lat long coordinates to array.
            savedMarkers.add(new ArrayList<Double>(Arrays.asList(point.latitude, point.longitude)));

            System.out.println(point.latitude + "------" + point.longitude);

            // Print the size of the multi dimensional array list.
            System.out.println(savedMarkers.size());

            // Stop the animation for the icon.
            mMap.stopAnimation();
        }

    });

    }

}