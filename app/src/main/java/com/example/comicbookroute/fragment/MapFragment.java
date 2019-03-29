package com.example.comicbookroute.fragment;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicbookroute.R;
import com.example.comicbookroute.model.BookRoute;
import com.example.comicbookroute.model.BookRouteDatabase;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerClickListener {
    MapView mMapView;
    private GoogleMap mGoogleMap;
    private final LatLng BRUSSEL = new LatLng(50.858712, 4.347446);
    private final int REQUEST_LOCATION = 1;
    private static final String SERVICE_URL_SA = "https://bruxellesdata.opendatasoft.com/api/records/1.0/search/?dataset=street-art&rows=23";
   /* double latitude, longitude;
    private int PROXIMITY_RADIUS = 10000;
    GoogleApiClient googleApiClient;


    //parameters nearby places
    private Button btnRestaurant;
    private Button btnParking;
    private Button btnWc;
    //design navigation drawer
    private ActionBar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;*/


    public MapFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        setHasOptionsMenu(true);
      /*  AppCompatActivity aca = (AppCompatActivity) getActivity();
        //toolbar = aca.getSupportActionBar();

        setHasOptionsMenu(true);

        DrawerLayout drawer = (DrawerLayout) view.findViewById(R.id.fragment_map_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(aca, drawer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView =  view.findViewById(R.id.fragment_map_nav_view);
        navigationView.setNavigationItemSelectedListener(this);*/

        SupportMapFragment supportMapFragment = SupportMapFragment.newInstance();
        mMapView = view.findViewById(R.id.mv_mapfragment);
        mMapView.getMapAsync(this);


        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();

        return view;
    }

    protected void addStreetArt() throws IOException {
        HttpURLConnection connection = null;
        final StringBuilder json = new StringBuilder();
        try {
            URL url = new URL(SERVICE_URL_SA);
            connection = (HttpURLConnection) url.openConnection();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());

            int read;
            char[] buff = new char[23];
            while ((read = inputStreamReader.read(buff)) != -1) {
                json.append(buff, 0, read);
            }
        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }

    }

    public void createMarkersFromJson(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            mGoogleMap.addMarker(new MarkerOptions()
                    .title(jsonObject.getString("fields"))
                    .position(new LatLng(jsonObject.getJSONArray("geocoordinates").getDouble(0),
                            jsonObject.getJSONArray("geocoordinates").getDouble(1)))
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }
    }

    /*@SuppressLint("RestrictedApi")
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_map_menu, menu);
        if (menu instanceof MenuBuilder) {
            MenuBuilder menuBuilder = (MenuBuilder) menu;
            menuBuilder.setOptionalIconsVisible(true);
        }
    }

    /*@Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_streetart) {

            try {
                addStreetArt();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return true;
        }

        if (id == R.id.menu_item_parking) {

            return true;
        }
        if (id == R.id.menu_item_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

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

       /* if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            buildGoogleApiClient();
            mGoogleMap.setMyLocationEnabled(true);


        }*/


        mGoogleMap.setOnMarkerClickListener(this);
        /*mGoogleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker marker) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {

                View mView = getLayoutInflater().inflate(R.layout.info_window, null, false);

                BookRoute item = (BookRoute) marker.getTag();

                TextView tvTitle = mView.findViewById(R.id.tv_info_window);
                tvTitle.setText(item.getPersonnage());


                ImageView iv = mView.findViewById(R.id.iv_info_window);
                String imageUrl = String.format("https://bruxellesdata.opendatasoft.com/explore/dataset/comic-book-route/files/%s", item.getPhoto()) + "/300/";
                Picasso.get().load(imageUrl).into(iv);


                return mView;
            }
        });*/
    }

    private void addMarkers() {

        List<BookRoute> data = BookRouteDatabase.getInstance(getContext()).getBookRouteDAO().selectAllBookRoutes();
        Log.d("DATA", data.toString());
        for (BookRoute br : data) {
            LatLng coord = new LatLng(br.getLatitude(), br.getLongitude());

            Marker m = mGoogleMap.addMarker(new MarkerOptions()
                    .title(br.getPersonnage()).position(coord).icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            m.setTag(br);
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
    public void onMapClick(LatLng latLng) {
        addMarkers();
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getActivity(), marker.getTitle(), Toast.LENGTH_LONG).show();
        return false;
    }


}


