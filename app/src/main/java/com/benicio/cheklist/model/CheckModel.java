package com.benicio.cheklist.model;

import java.io.Serializable;

public class CheckModel implements Serializable {
    String data;
    Boolean type;

    public CheckModel(String data, Boolean type) {
        this.data = data;
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }
}
