package com.benicio.cheklist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.benicio.cheklist.R;
import com.benicio.cheklist.adapter.AdapterUsuarios;
import com.benicio.cheklist.databinding.ActivityListagemBinding;
import com.benicio.cheklist.model.UsuarioModel;
import com.benicio.cheklist.util.ChamadaUtil;

import java.util.List;

public class ListagemActivity extends AppCompatActivity {
    private ActivityListagemBinding listagemBinding;
    private RecyclerView recyclerUsuarios;
    private List<UsuarioModel> lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listagemBinding = ActivityListagemBinding.inflate(getLayoutInflater());
        setContentView(listagemBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        configuarRecyclerUsuarios();
        ActionBar bar = getSupportActionBar();
        bar.setTitle("Usuários");
        bar.setDisplayShowHomeEnabled(true);
        bar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void configuarRecyclerUsuarios(){
        lista = ChamadaUtil.loadList(getApplicationContext());
        recyclerUsuarios = listagemBinding.recyclerUsuarios;
        recyclerUsuarios.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerUsuarios.setHasFixedSize(true);
        recyclerUsuarios.addItemDecoration(new DividerItemDecoration(getApplicationContext(), DividerItemDecoration.VERTICAL));
        recyclerUsuarios.setAdapter(new AdapterUsuarios(
                lista, getApplicationContext()
        ));
    }
}