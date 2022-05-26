package com.moliner.projectem7appmonistrolericbenitogerardgimemzmarczuritagerardmoliner;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EsculturaAdapterFirestore extends FirestoreRecyclerAdapter<Escultura, EsculturaAdapterFirestore.EsculturaHolder> {

    public EsculturaAdapterFirestore(@NonNull FirestoreRecyclerOptions<Escultura> options) {
        super(options);
    }


    @Override
    protected void onBindViewHolder(@NonNull EsculturaHolder holder, int position, @NonNull Escultura model) {
        holder.tvNomEscultura.setText(model.getNom().get("ca"));
        Bitmap bmp = BitmapFactory.decodeByteArray(model.getImatges().get(0).toBytes(), 0, model.getImatges().get(0).toBytes().length);
        holder.ivEscultura.setImageBitmap(
                Bitmap.createScaledBitmap(
                        bmp, 400, 400, true));
        holder.escultura = model;
    }

    @NonNull
    @Override
    public EsculturaHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.escultures, parent, false);
        return new EsculturaHolder(view);
    }

    public class EsculturaHolder extends RecyclerView.ViewHolder {
        ImageView ivEscultura;
        TextView tvNomEscultura;
        Escultura escultura;

        public EsculturaHolder(@NonNull View itemView) {
            super(itemView);
            ivEscultura = itemView.findViewById(R.id.ivEscultura);
            tvNomEscultura = itemView.findViewById(R.id.tvNomEscultura);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(itemView.getContext(), DetailEscultura.class);
                    intent.putExtra("escultura", escultura);

                    itemView.getContext().startActivity(intent);
                }
            });

        }

        public void bind(Escultura modelescultura){

            escultura = modelescultura;
            tvNomEscultura.setText(escultura.getNom().get("ca"));

        }
    }
}