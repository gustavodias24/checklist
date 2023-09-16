package com.benicio.cheklist.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.benicio.cheklist.model.CheckModel;
import com.benicio.cheklist.model.UsuarioModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChamadaUtil {

    public static final String PREF_NAME = "usuario_prefs";
    public static final String KEY_NAME = "usuario_key";
    public static List<UsuarioModel> loadList(Context c){
        SharedPreferences preferences = c.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        String usuarios = preferences.getString(KEY_NAME, "");

        if ( !usuarios.isEmpty() ){
            Gson gson = new Gson();
            Type type = new TypeToken<List<UsuarioModel>>(){}.getType();
            return gson.fromJson(usuarios, type);
        }
        return new ArrayList<>();
    }

    public static void saveList(Context c, List<UsuarioModel> lista){
        SharedPreferences preferences = c.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        Gson gson = new Gson();
        String usuariosString = gson.toJson(lista);

        editor.putString(KEY_NAME, usuariosString);
        editor.apply();
    }

    public static Boolean verificarPin(Context c, int pin){
        if ( loadList(c) != null){
            for ( UsuarioModel u : loadList(c)){
                if ( u.getPin() == pin){
                    return true;
                }
            }
        }

        return false;
    }
    public static void setCheckForUsuario( Boolean type, int pin, Context c){
        UsuarioModel usuarioModel = null;
        List<UsuarioModel> listaAntiga = loadList(c);

        int pos = 0;
        for ( UsuarioModel u : listaAntiga){
            if ( u.getPin() == pin){
                usuarioModel = u;
                listaAntiga.remove(pos);
                break;
            }
            pos++;
        }
        SimpleDateFormat format =  new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        assert usuarioModel != null;
        usuarioModel.getChekins().add(
                new CheckModel(
                        format.format(new Date()),
                        type
                )
        );

        listaAntiga.add(usuarioModel);
        saveList(c, listaAntiga);

    }
}
