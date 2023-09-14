package com.benicio.cheklist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.Toast;

import com.benicio.cheklist.databinding.ActivityMainBinding;
import com.benicio.cheklist.databinding.LayoutInserirPinBinding;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private Dialog dialogPin;
    private Boolean type = true;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

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
    

}