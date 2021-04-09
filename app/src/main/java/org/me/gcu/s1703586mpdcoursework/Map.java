//Student Name: Sofi Bambrick
//Student ID: S1703586

package org.me.gcu.s1703586mpdcoursework;

import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    GoogleMap google;
    SupportMapFragment mapFrag;
    ArrayList<ItemClass> mapsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mapsArray = (ArrayList<ItemClass>) getIntent().getExtras().getSerializable("Earthquake");
        setContentView(R.layout.activity_map);

        mapFrag = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFrag.getMapAsync(this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        google = googleMap;

        LatLng uk = new LatLng(54,2);
        for (int i = 0; i<mapsArray.size(); i++) {
            String Mag = mapsArray.get(i).getMagnitude().substring(11);
            BitmapDescriptor icon = null;
            if (Float.parseFloat(Mag) < 1) {
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW);
            }
            else if (Float.parseFloat(Mag) >=1  && Float.parseFloat(Mag) < 2) {
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE);
            }
            else if (Float.parseFloat(Mag) >= 2 && Float.parseFloat(Mag) >= 3)   {
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED);
            }
            google.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(mapsArray.get(i).getLat()), Double.parseDouble(mapsArray.get(i).getLon())))
                    .title(mapsArray.get(i).getLocation() + "," + mapsArray.get(i).getMagnitude())
                    .icon(icon));
        }
        google.moveCamera(CameraUpdateFactory.newLatLng(uk));
    }
}