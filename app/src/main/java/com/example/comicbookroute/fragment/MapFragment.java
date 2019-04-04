package com.example.comicbookroute.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.comicbookroute.DetailActivity;
import com.example.comicbookroute.R;
import com.example.comicbookroute.model.BookRoute;
import com.example.comicbookroute.model.BookRouteDatabase;
import com.example.comicbookroute.model.StreetArt;
import com.example.comicbookroute.util.StreetArtHandler;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener, GoogleMap.OnInfoWindowClickListener {
    private GoogleMap mGoogleMap;
    private final LatLng BRUSSEL = new LatLng(50.858712, 4.347446);
    private final int REQUEST_LOCATION = 1;
    MapView mMapView;
    private Spinner spinner;
    transient private LatLng coord;
    StreetArtHandler mStreetArtHandler;

    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        setHasOptionsMenu(true);

        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        mMapView = view.findViewById(R.id.mv_mapfragment);
        mMapView.getMapAsync(this);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        mStreetArtHandler = new StreetArtHandler(getActivity());
        spinner = view.findViewById(R.id.sp_maptype);

        //changing type map with spinner
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 1:
                        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case 2:
                        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 3:
                        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                    default:
                        mGoogleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        downloadDataSA();

        return view;
    }



    private void downloadDataSA() {
        Thread backTread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://bruxellesdata.opendatasoft.com/api/records/1.0/search/?dataset=street-art&rows=23")
                            .get()
                            .build();
                    Response response = client.newCall(request).execute();
                    if (response.body() != null) {
                        String responseBody = response.body().string();
                        Message message = new Message();
                        message.obj = responseBody;
                        mStreetArtHandler.sendMessage(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        backTread.start();
    }


    @SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_map_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_streetart:
                if (mGoogleMap != null) {
                    mGoogleMap.clear();
                    List<StreetArt> datast = BookRouteDatabase.getInstance(getContext()).getStreetArtDAO().selectAllStreetArts();
                    Log.d("DATA", datast.toString());
                    for (StreetArt st : datast) {
                        LatLng coord = new LatLng(st.getLatitude(), st.getLongitude());
                        Marker streetartMarkers = mGoogleMap.addMarker(new MarkerOptions()
                                .title(st.getKunstenaar()).position(coord).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        streetartMarkers.setTag(st);
                    }
                }
                break;
            case R.id.menu_item_bookroute:
                if (mGoogleMap != null) {
                    mGoogleMap.clear();
                    addMarkers();
                }
                break;
        }

        return super.onOptionsItemSelected(item);
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
        mGoogleMap.setOnMarkerClickListener(this);
        mGoogleMap.setOnInfoWindowClickListener(this);
        mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                View infoWindowView = getLayoutInflater().inflate(R.layout.info_window, null, false);
                BookRoute item = (BookRoute) marker.getTag();
                ImageView ivPhotoWindow = infoWindowView.findViewById(R.id.iv_photo_window);
                TextView tvPersonageWindow = infoWindowView.findViewById(R.id.tv_personage_window);
                TextView tvAddressWindow = infoWindowView.findViewById(R.id.tv_adres_window);

                Geocoder geocoder = new Geocoder(infoWindowView.getContext(), Locale.getDefault());;
                List<Address> addresses = null;

                try {
                    FileInputStream fis = getContext().openFileInput(item.getPhoto());
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    ivPhotoWindow.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                tvPersonageWindow.setText(item.getPersonnage());
                try {
                    addresses = geocoder.getFromLocation(item.getLatitude(), item.getLongitude(), 1);
                    String address = addresses.get(0).getAddressLine(0);
                    tvAddressWindow.setText(address);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return infoWindowView;

             /* StreetArt saitem = (StreetArt) marker.getTag();
                tvTitle = mView.findViewById(R.id.tv_info_window);
                tvTitle.setText(saitem.getKunstenaar());
                iv = mView.findViewById(R.id.iv_info_window);
                try {
                    FileInputStream fis = getContext().openFileInput(saitem.getPhoto());
                    Bitmap bitmap = BitmapFactory.decodeStream(fis);
                    iv.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
            }
        });
    }

    private void addMarkers() {

        List<BookRoute> data = BookRouteDatabase.getInstance(getContext()).getBookRouteDAO().selectAllBookRoutes();
        Log.d("DATA", data.toString());
        for (BookRoute br : data) {
            LatLng coord = new LatLng(br.getLatitude(), br.getLongitude());

            Marker comicbookMarkers = mGoogleMap.addMarker(new MarkerOptions()
                    .title(br.getPersonnage()).position(coord).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            comicbookMarkers.setTag(br);
        }

    }

    private void setupCamera() {
        CameraUpdate update = CameraUpdateFactory.newLatLngZoom(BRUSSEL, 14);
        mGoogleMap.animateCamera(update);
        CameraPosition.Builder cameraPosition = new CameraPosition.Builder();
        CameraPosition position = cameraPosition.target(BRUSSEL).zoom(14).tilt(60).build();
        CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(position);
        mGoogleMap.animateCamera(cameraUpdate);

    }

    private void startLocationUpdate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                    || ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
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
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_LOCATION) {
            for (int result : grantResults)
                if (result == PackageManager.PERMISSION_GRANTED)
                    mGoogleMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        // Calculate required horizontal shift for current screen density
        final int dX = getResources().getDimensionPixelSize(R.dimen.map_dx);
        // Calculate required vertical shift for current screen density
        final int dY = getResources().getDimensionPixelSize(R.dimen.map_dy);
        final Projection projection = mGoogleMap.getProjection();
        final Point markerPoint = projection.toScreenLocation(marker.getPosition());
        // Shift the point we will use to center the map
        markerPoint.offset(dX, dY);
        final LatLng newLatLng = projection.fromScreenLocation(markerPoint);
        // Buttery smooth camera swoop :)
        mGoogleMap.animateCamera(CameraUpdateFactory.newLatLng(newLatLng));
        // Show the info window
        marker.showInfoWindow();
        return true;
    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent detailsIntent = new Intent(getContext(), DetailActivity.class);
        detailsIntent.putExtra("item", (BookRoute) marker.getTag());
        startActivity(detailsIntent);

    }

    @Override
    public void onMapClick(LatLng latLng) {
        addMarkers();
    }
}





