package com.example.comicbookroute.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicbookroute.R;
import com.example.comicbookroute.model.BookRoute;
import com.example.comicbookroute.model.BookRouteDataSource;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener{

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
        addMarkers();
        mGoogleMap.setOnMapClickListener(this);
        mGoogleMap.setOnMarkerClickListener(this);
      /*  mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View mView = getLayoutInflater().inflate(R.layout.info_window,null,false);

                BookRoute current = (BookRoute)marker.getTag();

                TextView tvTitle = mView.findViewById(R.id.tv_info_window);
                tvTitle.setText(current.getPersonnage());

                ImageView iv = mView.findViewById(R.id.iv_info_window);

                return mView;
            }
        });*/
    }

    private void addMarkers() {

        List<BookRoute> data = BookRouteDataSource.getInstance().getBookRoutes();
        Log.d("DATA", data.toString());
        for(BookRoute br : data){
            LatLng coord = new LatLng(br.getLatitude(), br.getLongitude());

            mGoogleMap.addMarker(new MarkerOptions()
                    .title(br.getPersonnage()).position(coord).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));

        }


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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION};
                requestPermissions(permissions, REQUEST_LOCATION);
            } else {
                mGoogleMap.setMyLocationEnabled(true);

            }
        } else {
            mGoogleMap.setMyLocationEnabled(true);
        }

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            for (int result : grantResults)
                if (result == PackageManager.PERMISSION_GRANTED)
                    mGoogleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {
        addMarkers();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getActivity(),marker.getTitle(),Toast.LENGTH_LONG).show();
        return false;
    }
}
