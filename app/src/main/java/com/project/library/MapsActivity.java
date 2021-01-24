package com.project.library;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.example.library.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
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

        LatLng Bucharest = new LatLng(44.426181137166914, 26.102516981315425);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(Bucharest));

        // Add a marker in Sydney and move the camera
        LatLng CarturestiCarusel = new LatLng(44.43213024302457, 26.10159206928568);
        mMap.addMarker(new MarkerOptions().position(CarturestiCarusel).title("Carturesti Carusel"));

        LatLng Librarul = new LatLng(44.45376950589799, 26.083635611042624 );
        mMap.addMarker(new MarkerOptions().position(Librarul).title("Librarul"));

        LatLng HumanitasCismigiu = new LatLng(44.43589642665158, 26.09502444280224);
        mMap.addMarker(new MarkerOptions().position(HumanitasCismigiu).title("Humanitas Cismigiu"));

        LatLng IonCreanga = new LatLng(44.449189694835, 26.077025317270934);
        mMap.addMarker(new MarkerOptions().position(IonCreanga).title("Libraria Ion Creanga"));

        LatLng MihaiEminescu = new LatLng(44.43525865695096, 26.099355540449693);
        mMap.addMarker(new MarkerOptions().position(MihaiEminescu).title("Libraria Mihai Eminescu"));

        LatLng LibUniversala = new LatLng(44.45060144573086, 26.072998369800484);
        mMap.addMarker(new MarkerOptions().position(LibUniversala).title("Libraria Universala"));

        LatLng LibCLB = new LatLng(44.42704921518632, 26.0960457178611);
        mMap.addMarker(new MarkerOptions().position(LibCLB).title("Librarie CLB"));

        LatLng Diverta = new LatLng(44.420680748511565, 26.126577930578403);
        mMap.addMarker(new MarkerOptions().position(Diverta).title("Librarie Diverta"));
    }
}