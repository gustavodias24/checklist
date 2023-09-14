package com.benicio.cheklist.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Menu;
import android.widget.Toast;

import com.benicio.cheklist.util.ChamadaUtil;
import com.benicio.cheklist.R;
import com.benicio.cheklist.databinding.ActivityMainBinding;
import com.benicio.cheklist.databinding.LayoutInserirPinBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private Dialog dialogPin;
    private Boolean type = true;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        mainBinding.checkinBtn.setOnClickListener( view -> {
            type = true;
            dialogPin.show();
        });
        mainBinding.checkoutBtn.setOnClickListener( view -> {
            type = false;
            dialogPin.show();
        });
        mainBinding.novoUsuarioBtn.setOnClickListener( view -> {});

        dialogPin = criarDialogInserirPin();

    }

    public Dialog criarDialogInserirPin(){
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);
        b.setCancelable(false);
        LayoutInserirPinBinding inserirPinBinding = LayoutInserirPinBinding.inflate(getLayoutInflater());
        
        inserirPinBinding.prontoBtn.setOnClickListener( view -> {

           Objects.requireNonNull(inserirPinBinding.pinField.getEditText()).setError(null);
           String pinString = inserirPinBinding.pinField.getEditText().getText().toString().trim();
           if ( !pinString.isEmpty() ){
               int pin = Integer.parseInt(pinString);
               if ( ChamadaUtil.verificarPin(getApplicationContext(), pin) ) {
                   ChamadaUtil.setCheckForUsuario(type, pin, getApplicationContext());
                   Toast.makeText(this, "feito!", Toast.LENGTH_SHORT).show();
                   dialogPin.dismiss();
               }else{
                   Toast.makeText(this, "Pin inválido!", Toast.LENGTH_SHORT).show();
               }
           }else{
               inserirPinBinding.pinField.getEditText().setError(
                       "Campo obrigatório"
               );
           }
        });
        
        
        b.setView(inserirPinBinding.getRoot());
        return b.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_planilha, menu);
        return super.onCreateOptionsMenu(menu);
    }
}