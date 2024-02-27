package com.example.appthongkeloi.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.appthongkeloi.R;
import com.example.appthongkeloi.models.MapheModel;

import java.util.ArrayList;
import java.util.List;

public class PheGridViewAdapter extends BaseAdapter {
    List<MapheModel> uneditList;
    List<MapheModel> editList=new ArrayList<>();
    Context context;
    public PheGridViewAdapter(List<MapheModel> mapheModelsList, Context context) {
        this.uneditList = mapheModelsList;
        this.context = context;
        notifyDataSetChanged();
    }
    public void filter(String filterText){
        if(filterText!=null&&filterText.length()!=0){
            List<MapheModel> filteredData=new ArrayList<>();
            for (MapheModel item:uneditList
                 ) {

                if(item.getMa().contains(filterText.toUpperCase())){
                    filteredData.add(item);
                }
            }
            editList=filteredData;
        }
        else {
            editList.clear();
        }
        notifyDataSetChanged();
    }
    public void addListData(List<MapheModel> mapheModels){
        uneditList.addAll(mapheModels);
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        if(editList.size()>0){
            return editList.size();
        }
        return uneditList.size();
    }

    @Override
    public MapheModel getItem(int i) {
        if(editList.size()>0){
            return editList.get(i);
        }
        return uneditList.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(view==null){
            LayoutInflater inflater=LayoutInflater.from(context);
            view=inflater.inflate(R.layout.phe_grid_view_item,viewGroup,false);
        }
        TextView textView=view.findViewById(R.id.phe_grid_view_item_select);
        MapheModel mapheModel=getItem(i);
        textView.setText(mapheModel.getMa());
        return view;
    }
}
