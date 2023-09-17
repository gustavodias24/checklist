package com.benicio.cheklist.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.benicio.cheklist.R;
import com.benicio.cheklist.model.CheckModel;

import java.util.List;

public class AdapterChecks extends RecyclerView.Adapter<AdapterChecks.MyViewHolder> {
    List<CheckModel> lista;

    public AdapterChecks(List<CheckModel> lista){
        this.lista = lista;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_usuario, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        CheckModel check = lista.get(position);

        String tipo = check.getType() ? "ChekIn" : "CheckOut";
        if ( check.getType() ){
            holder.infos.setTextColor(Color.GREEN);
        }else{
            holder.infos.setTextColor(Color.RED);
        }
        holder.r.setVisibility(View.GONE);

        holder.infos.setText(
                String.format("%s no dia %s", tipo, check.getData())
        );
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView infos;
        RecyclerView r;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            infos = itemView.findViewById(R.id.nome_usuario_text);
            r = itemView.findViewById(R.id.check_recycler);
        }
    }
}
