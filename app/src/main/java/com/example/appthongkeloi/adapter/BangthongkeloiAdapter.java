package com.example.appthongkeloi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.appthongkeloi.R;
import com.example.appthongkeloi.interfacePackage.NotifyData;
import com.example.appthongkeloi.models.BangthongkeModel;
import com.example.appthongkeloi.ui.thongkeloi.ThongkeloiFragment;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class BangthongkeloiAdapter extends BaseAdapter {
    List<BangthongkeModel> list;
    List<BangthongkeModel> sortList;
    NotifyData notifyData;
    Context context;

    public BangthongkeloiAdapter(List<BangthongkeModel> list, Context context,NotifyData notifyData) {
        this.list = list;
        this.context = context;
        this.sortList=list;
        this.notifyData=notifyData;
    }
    public List<BangthongkeModel> getList(){
        return list;
    }
    public void addData(BangthongkeModel data){
        sortList.add(data);
        Collections.sort(sortList, new Comparator<BangthongkeModel>() {
            @Override
            public int compare(BangthongkeModel t1, BangthongkeModel t2) {
                String ma1=t1.getMa();
                String ma2=t2.getMa();
                if(ma1.equals("Đạt")&&!ma2.equals("Đạt")){
                    return -1;
                }else if(!ma1.equals("Đạt")&&ma2.equals("Đạt")){
                        return 1;
                } else if (ma1.startsWith("P")&&!ma2.startsWith("P")) {
                        return -1;
                } else if (!ma1.startsWith("P")&&ma2.startsWith("P")) {
                    return 1;
                } else if (ma1.startsWith("P")&&ma2.startsWith("P")) {
                    int num1=Integer.parseInt(ma1.substring(1));
                    int num2=Integer.parseInt(ma2.substring(1));
                    return Integer.compare(num1,num2);
                } else if (ma1.startsWith("S")&&!ma2.startsWith("S")) {
                    return -1;
                }else if(!ma1.startsWith("S")&&ma2.startsWith("S")){
                    return 1;
                } else if (ma1.startsWith("S")&&ma2.startsWith("S")) {
                    int num1=Integer.parseInt(ma1.substring(1));
                    int num2=Integer.parseInt(ma2.substring(1));
                    return Integer.compare(num1,num2);
                }else {
                    return 0;
                }
            }
        });
        list=sortList;
        notifyDataSetChanged();
    }

    public  BangthongkeModel findDataByMa(String ma){
        for (BangthongkeModel data : list) {
            if (data.getMa().equals(ma)) {
                return data;
            }
        }
        return null;
    }
    public List<BangthongkeModel> findDataMaStartsWithString(String Ma){
        List<BangthongkeModel> newList=new ArrayList<>();
        for (BangthongkeModel data:list
             ) {
            if(data.getMa().startsWith(Ma)){
                newList.add(data);
            }
        }
        return newList;
    }

    public String TinhTongSoLuongKiem(){
        int tongSoLuong=0;
        for (BangthongkeModel item:list
             ) {
            if(item.getMa()=="Đạt"){
                tongSoLuong+=item.getDat();
            }else if(item.getMa().startsWith("P")){
                tongSoLuong+=item.getPhe();
            }else {
                tongSoLuong+=item.getSua();
            }
        }
        return String.valueOf(tongSoLuong);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public BangthongkeModel getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            view=inflater.inflate(R.layout.grid_view_item_bangthongkeloi,viewGroup,false);
        }
        TextView ma=view.findViewById(R.id.grid_item_ma);
        TextView soluong=view.findViewById(R.id.grid_item_soluong);
        TextView dat=view.findViewById(R.id.grid_item_dat);
        TextView phe=view.findViewById(R.id.grid_item_phe);
        TextView sua=view.findViewById(R.id.grid_item_sua);
        Button giam=view.findViewById(R.id.grid_item_nutgiam);
        //gắn data vao bang thong ke

        BangthongkeModel bangthongkeModel=getItem(i);
        ma.setText(bangthongkeModel.getMa());
        if(bangthongkeModel.getDat()!=0){
            soluong.setText(String.valueOf(bangthongkeModel.getDat()));
        } else if (bangthongkeModel.getPhe()!=0) {
            soluong.setText(String.valueOf(bangthongkeModel.getPhe()));
        }else {
            soluong.setText(String.valueOf(bangthongkeModel.getSua()));
        }

        dat.setText(String.valueOf(bangthongkeModel.getDat()));
        phe.setText(String.valueOf(bangthongkeModel.getPhe()));
        sua.setText(String.valueOf(bangthongkeModel.getSua()));
        giam.setTag(bangthongkeModel.getMa());
        //xu ly nut giam
        giam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Iterator<BangthongkeModel> iterator = list.iterator();
                while(iterator.hasNext()) {
                    BangthongkeModel item = iterator.next();
                    String str=item.getMa();
                    if(str.equals(giam.getTag())){
                        if (str == "Đạt") {
                            if (item.getDat() > 1) {
                                item.setDat(item.getDat() - 1);
                                notifyDataSetChanged();
                                notifyData.tongSLKiem_giam();
                                notifyData.tongSLDat_giam();
                            } else {
                                iterator.remove();
                                notifyDataSetChanged();
                                notifyData.tongSLKiem_giam();
                                notifyData.tongSLDat_giam();
                            }
                        }else if(str.startsWith("P")){
                            if(item.getPhe()>1){
                                item.setPhe(item.getPhe()-1);
                                notifyDataSetChanged();
                                notifyData.tongSLKiem_giam();
                                notifyData.tongSLHangLoi_giam();
                                notifyData.tongSLPhe_giam();
                                notifyData.tongMaLoi_giam();
                            }else {
                                iterator.remove();
                                notifyDataSetChanged();
                                notifyData.tongSLKiem_giam();
                                notifyData.tongSLHangLoi_giam();
                                notifyData.tongSLPhe_giam();
                                notifyData.tongMaLoi_giam();
                            }
                        }else {
                            if(item.getSua()>1){
                                item.setSua(item.getSua()-1);
                                notifyDataSetChanged();
                                notifyData.tongSLKiem_giam();
                                notifyData.tongSLHangLoi_giam();
                                notifyData.tongSLSua_giam();
                                notifyData.tongMaLoi_giam();
                            }else {
                                iterator.remove();
                                notifyDataSetChanged();
                                notifyData.tongSLKiem_giam();
                                notifyData.tongSLHangLoi_giam();
                                notifyData.tongSLSua_giam();
                                notifyData.tongMaLoi_giam();
                            }
                        }
                    }
                }
            }
        });

        return view;
    }
}
