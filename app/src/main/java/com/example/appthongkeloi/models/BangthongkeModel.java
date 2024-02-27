package com.example.appthongkeloi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter

@NoArgsConstructor
public class BangthongkeModel {
    private String ma;
    private int phe;
    private int sua;
    private int dat;

    public BangthongkeModel(String ma,  int phe, int sua,int dat) {
        this.ma = ma;
        this.phe = phe;
        this.sua = sua;
        this.dat=dat;
    }

    public int getDat() {
        return dat;
    }

    public void setDat(int dat) {
        this.dat = dat;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }



    public int getPhe() {
        return phe;
    }

    public void setPhe(int phe) {
        this.phe = phe;
    }

    public int getSua() {
        return sua;
    }

    public void setSua(int sua) {
        this.sua = sua;
    }
}
