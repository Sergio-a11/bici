package com.example.sdjcomp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

        public static class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtColor, txtMarca;
        ImageView imgBicicleta;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtColor=(TextView) itemView.findViewById(R.id.txtColor);
            txtMarca=(TextView) itemView.findViewById(R.id.txtMarca);
            imgBicicleta=(ImageView) itemView.findViewById(R.id.imgCicla);
        }
    }

    public List<Bicicleta> lstCiclas;

    public RecyclerViewAdapter(List<Bicicleta> lstCiclas)
    {
        this.lstCiclas = lstCiclas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bicicleta,parent,false);
        return new ViewHolder(view);
    }

    //modificaciones de contenido
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtColor.setText(lstCiclas.get(position).getColor());
        holder.txtMarca.setText(lstCiclas.get(position).getMarca());
        //holder.imgBicicleta.setImageResource(lstCiclas.get(position).getImageCicla);
    }

    @Override
    public int getItemCount() {
        return lstCiclas.size();
    }

}
