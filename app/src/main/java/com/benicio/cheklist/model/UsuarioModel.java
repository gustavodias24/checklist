package com.benicio.cheklist.model;

import com.benicio.cheklist.model.CheckModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UsuarioModel implements Serializable {
    String nome;
    int pin;
    List<CheckModel> chekins=  new ArrayList<>();

    public int getPin() {
        return pin;
    }

    public void setPin(int pin) {
        this.pin = pin;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<CheckModel> getChekins() {
        return chekins;
    }

    public void setChekins(List<CheckModel> chekins) {
        this.chekins = chekins;
    }
}
