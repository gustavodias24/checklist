package com.benicio.cheklist.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.os.Bundle;

import com.benicio.cheklist.R;
import com.benicio.cheklist.databinding.ActivityListagemBinding;

public class ListagemActivity extends AppCompatActivity {
    private ActivityListagemBinding listagemBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        listagemBinding = ActivityListagemBinding.inflate(getLayoutInflater());
        setContentView(listagemBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

    }
}