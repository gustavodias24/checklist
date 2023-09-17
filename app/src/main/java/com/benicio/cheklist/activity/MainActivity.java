package com.benicio.cheklist.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.benicio.cheklist.databinding.LayoutAdicionarUsuarioBinding;
import com.benicio.cheklist.model.UsuarioModel;
import com.benicio.cheklist.util.ChamadaUtil;
import com.benicio.cheklist.R;
import com.benicio.cheklist.databinding.ActivityMainBinding;
import com.benicio.cheklist.databinding.LayoutInserirPinBinding;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding mainBinding;
    private Dialog dialogPin, dialogInfos;
    private Dialog dialogAdicionarUsuario;
    private Boolean type = true;
    private int pinAtribuido = 0;
    private Random random;
    private TextView pinText;
    @SuppressLint("DefaultLocale")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        mainBinding.infosGeraisText.setOnClickListener( view -> {
            dialogInfos.show();
        });

        mainBinding.checkinBtn.setOnClickListener( view -> {
            type = true;
            dialogPin.show();
        });
        mainBinding.checkoutBtn.setOnClickListener( view -> {
            type = false;
            dialogPin.show();
        });
        mainBinding.novoUsuarioBtn.setOnClickListener( view -> dialogAdicionarUsuario.show());

        dialogPin = criarDialogInserirPin();
        dialogInfos = criarDialogInserirInfos();
        dialogAdicionarUsuario = criarDialogAdicionarUsuario();

        random = new Random();
        dialogAdicionarUsuario.setOnShowListener(dialogInterface ->  {

           pinAtribuido = random.nextInt(9000) + 1000;

           while (ChamadaUtil.verificarPin(getApplicationContext(), pinAtribuido) ){
               pinAtribuido = random.nextInt(9000) + 1000;
           }
           
           pinText.setText(
                   String.format("O pin atribuído foi: %d", pinAtribuido)
           );

        });
    }
    public Dialog criarDialogInserirInfos(){
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);

        LayoutAdicionarUsuarioBinding usuarioBinding = LayoutAdicionarUsuarioBinding.inflate(getLayoutInflater());
        usuarioBinding.textView.setText("Insira as informações");

        usuarioBinding.nomeUsuarioField.setVisibility(View.GONE);
        usuarioBinding.editTextTextMultiLine.setVisibility(View.VISIBLE);
        usuarioBinding.pinAtribuidoText.setVisibility(View.GONE);


        usuarioBinding.prontoBtn.setOnClickListener( view -> {
            String nomeCliente = usuarioBinding.editTextTextMultiLine.getText().toString();
            if ( !nomeCliente.isEmpty() ){
               mainBinding.infosGeraisText.setText(nomeCliente);
               dialogInfos.dismiss();
            }else{
                Toast.makeText(this, "Campo não pode ser vazio", Toast.LENGTH_SHORT).show();
            }
        });

        b.setView(usuarioBinding.getRoot());

        return b.create();
    }
    public Dialog criarDialogInserirPin(){
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);

        b.setCancelable(false);
        LayoutInserirPinBinding inserirPinBinding = LayoutInserirPinBinding.inflate(getLayoutInflater());
        b.setPositiveButton("Cancelar", (dialogInterface, i) -> {

            dialogPin.dismiss();
            inserirPinBinding.pinField.getEditText().setText("");
            inserirPinBinding.pinField.setError(null);

        });
        inserirPinBinding.prontoBtn.setOnClickListener( view -> {
           Objects.requireNonNull(inserirPinBinding.pinField.getEditText()).setError(null);
           String pinString = inserirPinBinding.pinField.getEditText().getText().toString().trim();
           if ( !pinString.isEmpty() ){
               int pin = Integer.parseInt(pinString);
               if ( ChamadaUtil.verificarPin(getApplicationContext(), pin) ) {
                   ChamadaUtil.setCheckForUsuario(type, pin, getApplicationContext());
                   Toast.makeText(this, "feito!", Toast.LENGTH_SHORT).show();
                   inserirPinBinding.pinField.getEditText().setText("");
                   Objects.requireNonNull(inserirPinBinding.pinField.getEditText()).setError(null);
                   dialogPin.dismiss();
               }else{
                   Toast.makeText(this, "Pin inválido!", Toast.LENGTH_SHORT).show();
                   inserirPinBinding.pinField.setError(
                           "Pin inválido!"
                   );
               }
           }else{
               inserirPinBinding.pinField.setError(
                       "Campo obrigatório"
               );
           }
        });
        
        
        b.setView(inserirPinBinding.getRoot());
        return b.create();
    }

    public Dialog criarDialogAdicionarUsuario(){
        AlertDialog.Builder b = new AlertDialog.Builder(MainActivity.this);

        LayoutAdicionarUsuarioBinding usuarioBinding = LayoutAdicionarUsuarioBinding.inflate(getLayoutInflater());
        pinText = usuarioBinding.pinAtribuidoText;

        usuarioBinding.prontoBtn.setOnClickListener( view -> {
            String nomeCliente = usuarioBinding.nomeUsuarioField.getEditText().getText().toString();
            if ( !nomeCliente.isEmpty() ){
                UsuarioModel usuarioModel = new UsuarioModel();
                usuarioModel.setNome(nomeCliente);
                usuarioModel.setPin(pinAtribuido);
                List<UsuarioModel> listaAtt = ChamadaUtil.loadList(getApplicationContext());
                listaAtt.add(usuarioModel);
                ChamadaUtil.saveList(getApplicationContext(), listaAtt);
                dialogAdicionarUsuario.dismiss();
                usuarioBinding.nomeUsuarioField.getEditText().setText("");
                Toast.makeText(this, "Usuário adicionado com sucesso!", Toast.LENGTH_SHORT).show();
            }else{
                usuarioBinding.nomeUsuarioField.setError("Campo obrigatório");
            }
        });

        b.setView(usuarioBinding.getRoot());

        return b.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_planilha, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if ( item.getItemId() == R.id.listagem){
            startActivity(new Intent(getApplicationContext(), ListagemActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }
}