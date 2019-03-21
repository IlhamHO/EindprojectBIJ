package com.example.comicbookroute.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.comicbookroute.R;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class MapFragment extends Fragment implements OnMapReadyCallback{

    MapView mMapView;
    private GoogleMap mGoogleMap;
    private final LatLng BRUSSEL = new LatLng(50.858712, 4.347446);
    private final int REQUEST_LOCATION = 1;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mMapView = view.findViewById(R.id.mv_mapfragment);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mMapView.getMapAsync(this);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mGoogleMap = googleMap;
        setupCamera();
        startLocationUpdate();
    }

    private void setupCamera() {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(BRUSSEL, 14);
        mGoogleMap.animateCamera(update);
        CameraPosition.Builder cameraPosition = new CameraPosition.Builder();
        CameraPosition position = cameraPosition.target(BRUSSEL).zoom(18).tilt(60).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(position);
        mGoogleMap.animateCamera(cameraUpdate);
    }

    private void startLocationUpdate() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissions, REQUEST_LOCATION);
            }else{
                mGoogleMap.setMyLocationEnabled(true);

            }
        }else {
            mGoogleMap.setMyLocationEnabled(true);
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_LOCATION){
            for(int result : grantResults)
                if(result == PackageManager.PERMISSION_GRANTED)
                    mGoogleMap.setMyLocationEnabled(true);
        }
    }
}
