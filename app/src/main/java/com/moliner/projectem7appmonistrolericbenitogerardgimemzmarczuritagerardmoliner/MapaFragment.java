package com.moliner.projectem7appmonistrolericbenitogerardgimemzmarczuritagerardmoliner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MapaFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    String nomArtista;


    GoogleMap mMap;

    public MapaFragment() {

    }


    public static MapaFragment newInstance(String param1, String param2) {
        MapaFragment fragment = new MapaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_mapa, container, false);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {

                mMap = googleMap;
                String[] idArt = new String[1];

                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                mMap.clear(); //clear old markers
                BitmapDrawable bitmapdraw=(BitmapDrawable)getResources().getDrawable(R.drawable.mapaicone);
                Bitmap b=bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 44, 44, false);

                CameraPosition googlePlex = CameraPosition.builder()
                        .target(new LatLng(41.760524,2.015460))
                        .zoom(15)
                        .bearing(0)
                        .build();
                mMap.animateCamera(CameraUpdateFactory.newCameraPosition(googlePlex), 1000, null);
                FirebaseFirestore db = FirebaseFirestore.getInstance();


                db.collection("Escultures")
                        //.document()
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                   @Override
                                                   public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                       if (task.isSuccessful()) {
                                                           for(QueryDocumentSnapshot doc: task.getResult()) {
                                                               Escultura esc = doc.toObject(Escultura.class);
                                                               idArt[0] = esc.getArtista().getId();
                                                               Log.e("REFERENCIA", idArt[0]);
                                                           }



                                                       }
                                                   }
                                               }
                        );

                db.collection("Artistes")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot doc : task.getResult()) {
                                        Artista art = doc.toObject(Artista.class);
                                        //Log.e("REFERENCIA1", idArt[0]);


                                        if(art.getIdArtista().equals(idArt[0])){
                                            nomArtista = art.getNom() + " " + art.getCognoms();
                                            //Log.e("NOMARTISTA", nomArtista);
                                        }
                                        db.collection("Escultures")
                                                //.document()
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                                           @Override
                                                                           public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                                               if (task.isSuccessful()) {
                                                                                   for(QueryDocumentSnapshot doc: task.getResult()) {
                                                                                       Escultura esc = doc.toObject(Escultura.class);
                                                                                       //Log.e("NOMARTISTA2", nomArtista);
                                                                                       mMap.addMarker(new MarkerOptions()
                                                                                               .position(new LatLng(esc.getLatitud(), esc.getLongitud()))
                                                                                               .title(esc.getNom().get("ca"))
                                                                                               .snippet("Nom")
                                                                                               .icon(BitmapDescriptorFactory.fromBitmap(smallMarker)));
                                                                                       Prova(esc.getNom().get("ca"), nomArtista);
                                                                                   }



                                                                               }
                                                                           }
                                                                       }
                                                );
                                    }

                                } else {

                                }
                            }
                        });



                mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(@NonNull Marker marker) {
                        marker.showInfoWindow();
                            return true;
                    }
                });


            }
        });


        return rootView;
    }

    private void Prova(String nomTitle, String nomAuthor){
        mMap.setInfoWindowAdapter(new CustomInfoWindowsAdapter(
                MapaFragment.this.getActivity(),
                nomTitle,
                nomAuthor,
                (BitmapDrawable) getResources().getDrawable(R.drawable.foto1)
        ));
    }
}