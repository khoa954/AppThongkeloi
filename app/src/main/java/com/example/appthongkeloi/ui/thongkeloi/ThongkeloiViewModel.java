package com.example.appthongkeloi.ui.thongkeloi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.appthongkeloi.models.ThongtinsanphamModel;

public class ThongkeloiViewModel extends ViewModel {
    private final MutableLiveData<ThongtinsanphamModel> thongTinSP;
    private final MutableLiveData<String> maSpTheoThoiGian;
    public ThongkeloiViewModel(){
        thongTinSP=new MutableLiveData<>();
        maSpTheoThoiGian=new MutableLiveData<>();
    }
    public void setValue(String masp,String mausp,String lenhsx,String nguoikiem,String chuyen){
        thongTinSP.setValue(new ThongtinsanphamModel(masp,mausp,lenhsx,nguoikiem,chuyen));
    }
    public void taoMaspTheoThoiGian(String masp,String mausp,String lenhsx,String nguoikiem,String chuyen,String thoigian){
        maSpTheoThoiGian.setValue(new String(masp+"-"+mausp+"-"+lenhsx+"-"+nguoikiem+"-"+chuyen));
    }
    public LiveData<ThongtinsanphamModel> getValue(){
        return thongTinSP;
    }
    public LiveData<String> getMaspTheoThoiGian(){
        return maSpTheoThoiGian;
    }

}