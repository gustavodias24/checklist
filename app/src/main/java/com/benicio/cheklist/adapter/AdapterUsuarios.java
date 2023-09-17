package com.benicio.cheklist.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.benicio.cheklist.R;
import com.benicio.cheklist.model.UsuarioModel;

import java.util.List;

public class AdapterUsuarios extends RecyclerView.Adapter<AdapterUsuarios.MyViewHolder>{

    List<UsuarioModel> lista;
    Context context;

    public AdapterUsuarios(List<UsuarioModel> lista, Context context) {
        this.lista = lista;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_usuario, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UsuarioModel usuarioModel = lista.get(position);
        holder.nomeUsuario.setText(
                String.format("%s | %s", usuarioModel.getPin(), usuarioModel.getNome())
        );
        RecyclerView r = holder.checkRecycler;
        if ( usuarioModel.getChekins().isEmpty() ){
            r.setVisibility(View.GONE);
        }else{
            r.setVisibility(View.VISIBLE);
        }
        r.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        r.addItemDecoration(new DividerItemDecoration(context, DividerItemDecoration.HORIZONTAL));
        r.setHasFixedSize(true);
        r.setAdapter( new AdapterChecks( usuarioModel.getChekins() ));
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public class MyViewHolder extends  RecyclerView.ViewHolder {
        TextView nomeUsuario;
        RecyclerView checkRecycler;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nomeUsuario = itemView.findViewById(R.id.nome_usuario_text);
            checkRecycler = itemView.findViewById(R.id.check_recycler);
        }
    }
}
