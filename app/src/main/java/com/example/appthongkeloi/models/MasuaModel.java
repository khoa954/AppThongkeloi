package com.example.appthongkeloi.models;

import androidx.annotation.NonNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MasuaModel {
    @NonNull
    public String ma;
    public String mota;

    public MasuaModel(@NonNull String ma, String mota) {
        this.ma = ma;
        this.mota = mota;
    }

    @NonNull
    public String getMa() {
        return ma;
    }

    public void setMa(@NonNull String ma) {
        this.ma = ma;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }
}
