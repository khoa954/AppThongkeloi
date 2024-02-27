package com.example.appthongkeloi.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appthongkeloi.R;
import com.example.appthongkeloi.models.MapheModel;
import com.example.appthongkeloi.models.MasuaModel;

import java.util.ArrayList;
import java.util.List;

public class SuaGridViewAdapter extends BaseAdapter {
    List<MasuaModel> uneditList;
    List<MasuaModel> editList=new ArrayList<>();
    Context context;
    public SuaGridViewAdapter(List<MasuaModel> list, Context context) {
        this.uneditList = list;
        this.context = context;
        notifyDataSetChanged();
    }

    public void filter(String filterText){
        if(filterText!=null && filterText !=""){
            List<MasuaModel> filteredData=new ArrayList<>();
            for (MasuaModel item:uneditList
                 ) {
                if(item.getMa().contains(filterText.toUpperCase())){
                    filteredData.add(item);
                }
            }
            editList=filteredData;
        } else {
            editList.clear();
        }

        notifyDataSetChanged();
    }
    public MasuaModel findByMa(String text){
        for (MasuaModel item:uneditList
        ) {
            if(item.getMa().equals(text)){
                return item;
            }
        }
        return null;
    }
    @Override
    public int getCount() {
        if(editList.size()>0){
            return editList.size();
        }
        return uneditList.size();
    }

    @Override
    public MasuaModel getItem(int i) {
        if(editList.size()>0){
            return editList.get(i);
        }
        return uneditList.get(i);
    }
    public MasuaModel getItemOfEditList(int i) {
        return editList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            view=inflater.inflate(R.layout.sua_grid_view_item,viewGroup,false);
        }
        TextView textView=view.findViewById(R.id.sua_grid_view_item_select);
        MasuaModel masuaModel=getItem(i);
        textView.setText(masuaModel.getMa());
        return view;
    }
}
