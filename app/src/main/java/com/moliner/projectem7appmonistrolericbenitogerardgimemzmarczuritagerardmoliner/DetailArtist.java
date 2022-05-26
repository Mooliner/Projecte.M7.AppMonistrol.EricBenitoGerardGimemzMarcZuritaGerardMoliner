package com.moliner.projectem7appmonistrolericbenitogerardgimemzmarczuritagerardmoliner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.Blob;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class DetailArtist extends AppCompatActivity{

    String nom;
    String cognoms;
    String biografia;
    String descripcio;
    String any;
    Blob img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailartist);

        Artista artista = (Artista) getIntent().getSerializableExtra("artista");

        ImageView ivArtista = findViewById(R.id.ivArtista2);
        TextView tvNomArtista = findViewById(R.id.tvNomArtista2);
        TextView tvCognomsArtista= findViewById(R.id.tvCognomsArtista2);
        TextView tvAny = findViewById(R.id.tvAny);
        TextView tvBiografiaArtista = findViewById(R.id.tvBiografiaArtista);
        TextView tvDescripcioArtista = findViewById(R.id.tvDescripcioArtista);

        //ivArtista.setBlob(artista.getFoto());



        FirebaseFirestore db = FirebaseFirestore.getInstance();

        db.collection("Artistes")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot doc : task.getResult()) {
                                Artista art = doc.toObject(Artista.class);
                                if(art.getNom().equals(artista.getNom())){
                                    img = art.getFoto();
                                    nom = art.getNom();
                                    cognoms = art.getCognoms();
                                    biografia = art.getBiografia().get("ca");
                                    descripcio= art.getCorrentArtistic().get("ca");
                                    any =  "(" + art.getAnyNaixement() + " - " + art.getAnyDefuncio() + ")";

                                    byte[] foto = img.toBytes();
                                    Bitmap bmp = BitmapFactory.decodeByteArray(foto, 0, foto.length);
                                    ivArtista.setImageBitmap(bmp);
                                    tvNomArtista.setText(nom);
                                    tvCognomsArtista.setText(cognoms);
                                    tvAny.setText(any);
                                    tvBiografiaArtista.setText(biografia);
                                    tvDescripcioArtista.setText(descripcio);


                                }

                            }
                        } else {

                        }
                    }
                });
    }
}