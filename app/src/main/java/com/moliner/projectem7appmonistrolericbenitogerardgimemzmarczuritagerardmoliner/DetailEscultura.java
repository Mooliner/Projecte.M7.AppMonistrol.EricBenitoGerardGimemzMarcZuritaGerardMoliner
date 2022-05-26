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

public class DetailEscultura extends AppCompatActivity {
    String nom, nomArtista,biografia, descripcio;
    String material, cognoms, anyArtista;
    Double altura;
    Double pes;
    Integer any;
    Blob img;
    String[] idArt = new String[1];


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_escultura);

            Escultura escultura = (Escultura) getIntent().getSerializableExtra("escultura");

            ImageView ivEscultures = findViewById(R.id.ivEscultura2);
            TextView tvNomEscultura = findViewById(R.id.tvNomEscultura2);
            TextView tvMaterial= findViewById(R.id.tvMaterial);
            TextView tvAlturaAmplada = findViewById(R.id.tvAltura);
            TextView tvPes = findViewById(R.id.tvPes);
            TextView tvAny = findViewById(R.id.tvAny);

            ImageView ivArtista = findViewById(R.id.ivArtista3);
            TextView tvNomArtista = findViewById(R.id.tvNomArtista3);
            TextView tvCognomsArtista= findViewById(R.id.tvCognomsArtista3);
            TextView tvAny2 = findViewById(R.id.tvAnyArtista3);
            TextView tvBiografiaArtista = findViewById(R.id.tvBiografiaArtista3);
            TextView tvDescripcioArtista = findViewById(R.id.tvDescripcioArtista3);

            //ivArtista.setBlob(artista.getFoto());



            FirebaseFirestore db = FirebaseFirestore.getInstance();

            db.collection("Escultures")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot doc : task.getResult()) {
                                    Escultura esc = doc.toObject(Escultura.class);
                                    if(esc.getNom().equals(escultura.getNom())){
                                        img = esc.getImatges().get(0);
                                        nom = esc.getNom().get("ca");
                                        material = esc.getMaterial().get("ca");
                                        altura = esc.getAltura() ;
                                        String altura2 = new Double(altura).toString();
                                        pes= esc.getPes();
                                        String pes2 = new Double(pes).toString();
                                        any = esc.getAny();
                                       // String any2 = new Integer(any).toString();

                                        byte[] foto = img.toBytes();
                                        Bitmap bmp = BitmapFactory.decodeByteArray(foto, 0, foto.length);
                                        ivEscultures.setImageBitmap(bmp);
                                        tvNomEscultura.setText(nom);
                                        tvMaterial.setText(material);
                                        tvAlturaAmplada.setText(altura2);
                                        tvPes.setText(pes2);
                                        tvAny.setText(""+any);
                                        idArt[0] = esc.getArtista().getId();

                                    }

                                    db.collection("Artistes")
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        for (QueryDocumentSnapshot doc : task.getResult()) {
                                                            Artista artist = doc.toObject(Artista.class);
                                                            if(artist.getIdArtista().equals(idArt[0])){
                                                                img = artist.getFoto();
                                                                nomArtista = artist.getNom();
                                                                cognoms = artist.getCognoms();
                                                                biografia = artist.getBiografia().get("ca");
                                                                descripcio= artist.getCorrentArtistic().get("ca");
                                                                anyArtista =  "(" + artist.getAnyNaixement() + " - " + artist.getAnyDefuncio() + ")";

                                                                byte[] foto = img.toBytes();
                                                                Bitmap bmp = BitmapFactory.decodeByteArray(foto, 0, foto.length);
                                                                ivArtista.setImageBitmap(bmp);
                                                                tvNomArtista.setText(nomArtista);
                                                                tvCognomsArtista.setText(cognoms);
                                                                tvAny2.setText(anyArtista);
                                                                tvBiografiaArtista.setText(biografia);
                                                                tvDescripcioArtista.setText(descripcio);

                                                            }

                                                        }
                                                    } else {

                                                    }
                                                }
                                            });


                                }
                            } else {

                            }
                        }
                    });


        }
    }
