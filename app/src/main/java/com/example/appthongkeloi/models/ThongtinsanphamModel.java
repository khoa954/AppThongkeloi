package com.example.appthongkeloi.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ThongtinsanphamModel {
    private String masp;
    private String mau;
    private String lenhsx;
    private String nguoikiem;
    private String chuyen;

    public ThongtinsanphamModel(String masp, String mau, String lenhsx, String nguoikiem, String chuyen) {
        this.masp = masp;
        this.mau = mau;
        this.lenhsx = lenhsx;
        this.nguoikiem = nguoikiem;
        this.chuyen = chuyen;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getMau() {
        return mau;
    }

    public void setMau(String mau) {
        this.mau = mau;
    }

    public String getLenhsx() {
        return lenhsx;
    }

    public void setLenhsx(String lenhsx) {
        this.lenhsx = lenhsx;
    }

    public String getNguoikiem() {
        return nguoikiem;
    }

    public void setNguoikiem(String nguoikiem) {
        this.nguoikiem = nguoikiem;
    }

    public String getChuyen() {
        return chuyen;
    }

    public void setChuyen(String chuyen) {
        this.chuyen = chuyen;
    }
}
