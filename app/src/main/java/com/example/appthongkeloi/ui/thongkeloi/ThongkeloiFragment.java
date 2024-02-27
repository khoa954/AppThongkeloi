package com.example.appthongkeloi.ui.thongkeloi;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.appthongkeloi.R;
import com.example.appthongkeloi.adapter.BangthongkeloiAdapter;
import com.example.appthongkeloi.adapter.PheGridViewAdapter;
import com.example.appthongkeloi.adapter.SuaGridViewAdapter;
import com.example.appthongkeloi.database.SqliteDatabaseHelper;
import com.example.appthongkeloi.databinding.FragmentThongkeloiBinding;
import com.example.appthongkeloi.interfacePackage.NotifyData;
import com.example.appthongkeloi.models.BangthongkeModel;
import com.example.appthongkeloi.models.MapheModel;
import com.example.appthongkeloi.models.MasuaModel;
import com.example.appthongkeloi.models.ThongtinsanphamModel;

import java.util.ArrayList;
import java.util.List;

public class ThongkeloiFragment extends Fragment implements NotifyData {
    private FragmentThongkeloiBinding binding;
    TextView result_maloi;
    TextView textViewTheThongTinSp;
    TextView result_slsua;
    TextView result_slphe;
    TextView result_slhangloi;
    TextView result_sldat;
    TextView result_slkiem;
    EditText editTextMasp;
    EditText editTextMausp;
    EditText editTextLenhsx;
    EditText editTextNguoikiem;
    EditText editTextChuyen;
    Button btn_dat;
    Button btn_phe;
    Button btn_sua;
    Button btn_xacnhan;
    Button btn_edit;
    Button btn_hoantat;
    ThongkeloiViewModel thongkeloiViewModel;
    BangthongkeloiAdapter bangthongkeloiAdapter;
    List<BangthongkeModel> bangthongkeloiList;
    GridView bangthongkeloiGridview;
    GridView phe_gridView;
    GridView sua_gridView;
    PheGridViewAdapter pheGridViewAdapter;
    SuaGridViewAdapter suaGridViewAdapter;
    EditText pheSearchEdittext;
    EditText suaSearchEdittext;
    LinearLayout linearLayout_chua_gridview_va_nut_bam;
    LinearLayout linearLayout_chua_dien_thong_tin_sp;
    LinearLayout linearLayout_chua_the_thong_tin_sp;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        thongkeloiViewModel=
                new ViewModelProvider(this).get(ThongkeloiViewModel.class);

        binding=FragmentThongkeloiBinding.inflate(inflater,container,false);
        View root=binding.getRoot();
        bindingUi();
        //xu ly su kien 3 edittext và lưu data 3 edittext vào modelView
        TextWatcher textWatcher=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String ma=editTextMasp.getText().toString();
                String mau=editTextMausp.getText().toString();
                String lenh=editTextLenhsx.getText().toString();
                String nguoi=editTextNguoikiem.getText().toString();
                if(ma.length()>=3&&mau.length()>=3&&lenh.length()>=3&&nguoi.length()>=3){
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,  // Chiều rộng sẽ trở thành MATCH_PARENT
                            LinearLayout.LayoutParams.WRAP_CONTENT  // Chiều cao vẫn giữ nguyên
                    );
                    linearLayout_chua_gridview_va_nut_bam.setLayoutParams(layoutParams);
                    SaveThongtinsanphamVaoViewModel();
                }else {
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.MATCH_PARENT,  // Chiều rộng sẽ trở thành MATCH_PARENT
                            0  //ko đủ dữ liệu sẽ giấu linearlayout
                    );
                    linearLayout_chua_gridview_va_nut_bam.setLayoutParams(layoutParams);
                }
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        };
        editTextMasp.addTextChangedListener(textWatcher);
        editTextMausp.addTextChangedListener(textWatcher);
        editTextLenhsx.addTextChangedListener(textWatcher);
        editTextNguoikiem.addTextChangedListener(textWatcher);
        editTextChuyen.addTextChangedListener(textWatcher);
        SetAdapterChoBangthongkeloi();
        //xu ly su kien nut dat
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btn_dat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_datOnclick();
                    }
                });
            }
        });
        //xu ly su kien nhan nut edit
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btn_edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_editOnclick(view);
                    }
                });
            }
        });
        //xu ly su kien nut xac nhan cua thong tin san pham
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btn_xacnhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_xacnhanOnclick(view);
                    }
                });
            }
        });
        //xu ly su kien nut phe

        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btn_phe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_pheOnclick(view);
                    }
                });
            }
        });
        //xu ly su kien nut sua
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btn_sua.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_suaOnclick(view);
                    }
                });
            }
        });
        //xu ly su kien nut hoan tat
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                btn_hoantat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        btn_hoantatOnclick(view);
                    }
                });
            }
        });



        return root;
    }
    private void btn_hoantatOnclick(View view){
        SqliteDatabaseHelper helper=new SqliteDatabaseHelper(getActivity());
        LiveData<String> maspTheoThoiGian=thongkeloiViewModel.getMaspTheoThoiGian();
        List<BangthongkeModel> list=bangthongkeloiAdapter.getList();
        helper.insertThongKeLoi(maspTheoThoiGian.getValue());
        helper.insertListMaloi(list,maspTheoThoiGian.getValue());
    }
    private void btn_suaOnclick(View anchorView){
        //fake data
        List<MasuaModel> fakePheData=new ArrayList<>();
        for (int i = 1; i <= 80; i++) {
            if(i<10){
                MasuaModel item=new MasuaModel("S0"+i,"Sửa 0"+i);
                fakePheData.add(item);
            }else {
                MasuaModel item = new MasuaModel("S" + i, "Sửa " + i);
                fakePheData.add(item);
            }
        }
        PopupWindow popupWindow=new PopupWindow(getContext());
        View popupView=getLayoutInflater().inflate(R.layout.sua_grid_popup,null);
        sua_gridView=popupView.findViewById(R.id.sua_gridView);
        suaSearchEdittext=popupView.findViewById(R.id.sua_search_bar);
        suaGridViewAdapter =new SuaGridViewAdapter(fakePheData,getContext());
        sua_gridView.setAdapter(suaGridViewAdapter);
        //xu ly search
        StringBuilder stringBuilder=new StringBuilder();
        suaSearchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    stringBuilder.setLength(0);
                    stringBuilder.append(charSequence);
                } else if (charSequence.length()==0) {
                    stringBuilder.setLength(0);
                }
                String searchString=null;
                if(stringBuilder!=null&&stringBuilder.length()!=0){
                    searchString=stringBuilder.toString();
                }
                suaGridViewAdapter.filter(searchString);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        //set adapter
        sua_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MasuaModel selectedData=suaGridViewAdapter.getItem(i);
                BangthongkeModel bangthongkeModel=bangthongkeloiAdapter.findDataByMa(selectedData.getMa());
                if(bangthongkeModel!=null){
                    int soPheHientai=bangthongkeModel.getSua();
                    bangthongkeModel.setSua(soPheHientai+1);
                    bangthongkeloiAdapter.notifyDataSetChanged();
                    //xu ly su kien update result_slsua
                    updateResult_slsua();
                    updateResult_tongSLKiem();
                    updateResult_tongSLHangloi();
                    updateResult_soMaLoi();
                }else{
                    BangthongkeModel bangThongKeModelMoi=new BangthongkeModel(selectedData.getMa(),0,1,0);
                    bangthongkeloiAdapter.addData(bangThongKeModelMoi);
                    bangthongkeloiAdapter.notifyDataSetChanged();
                    //xu ly su kien update result_slsua
                    updateResult_slsua();
                    updateResult_tongSLKiem();
                    updateResult_tongSLHangloi();
                    updateResult_soMaLoi();
                }
                popupWindow.dismiss();
            }
        });
        // Cấu hình PopupWindow
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // Hiển thị popup gần nút bấm
        popupWindow.showAsDropDown(anchorView);
    }
    private void btn_editOnclick(View view){
        LinearLayout.LayoutParams layoutParams_hide = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,  // Chiều rộng sẽ trở thành MATCH_PARENT
                0  //ko đủ dữ liệu sẽ giấu linearlayout
        );
        LinearLayout.LayoutParams layoutParams_show = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,  // Chiều rộng sẽ trở thành MATCH_PARENT
                LinearLayout.LayoutParams.WRAP_CONTENT //chiều cao sẽ là wrap_content
        );
        linearLayout_chua_dien_thong_tin_sp.setLayoutParams(layoutParams_show);
        linearLayout_chua_the_thong_tin_sp.setLayoutParams(layoutParams_hide);
    }
    private void updateResult_soMaLoi() {
        List<BangthongkeModel> pheRecords=bangthongkeloiAdapter.findDataMaStartsWithString("P");
        List<BangthongkeModel> suaRecords=bangthongkeloiAdapter.findDataMaStartsWithString("S");
        int p=0;
        int s=0;
        if(pheRecords!=null){
            for (BangthongkeModel item:pheRecords
            ) {
                p+=1;
            }
        }
        if(suaRecords!=null){
            for (BangthongkeModel item:suaRecords
            ) {
                s+=1;
            }
        }

        result_maloi.setText(String.valueOf(p+s));
    }
    private void btn_xacnhanOnclick(View view){
        LiveData<ThongtinsanphamModel> data=thongkeloiViewModel.getValue();
        textViewTheThongTinSp.setText
                (data.getValue().getMasp()+"-"+data.getValue().getMau()+"-"
                        +data.getValue().getLenhsx()+"-"+data.getValue().getNguoikiem()+"-"+data.getValue().getChuyen());
        LinearLayout.LayoutParams layoutParams_hide = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,  // Chiều rộng sẽ trở thành MATCH_PARENT
                0  //ko đủ dữ liệu sẽ giấu linearlayout
        );
        LinearLayout.LayoutParams layoutParams_show = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,  // Chiều rộng sẽ trở thành MATCH_PARENT
                LinearLayout.LayoutParams.WRAP_CONTENT //chiều cao sẽ là wrap_content
        );
        linearLayout_chua_dien_thong_tin_sp.setLayoutParams(layoutParams_hide);
        linearLayout_chua_the_thong_tin_sp.setLayoutParams(layoutParams_show);
    }
    private void updateResult_tongSLKiem() {
        String tongSLKiem=bangthongkeloiAdapter.TinhTongSoLuongKiem();
        if(tongSLKiem!=null){
            result_slkiem.setText(tongSLKiem);
        }else {
            result_slkiem.setText(String.valueOf(0));
        }

    }

    private void updateResult_slsua() {
        List<BangthongkeModel> data=bangthongkeloiAdapter.findDataMaStartsWithString("S");
        if(data!=null){
            int i=0;
            for (BangthongkeModel item:data
            ) {
                i+=item.getSua();
            }
            result_slsua.setText(String.valueOf(i));
        }else {
            result_slsua.setText(String.valueOf(0));
        }

    }
    private void btn_pheOnclick(View anchorView){
        //Fake data
        List<MapheModel> fakePheData=new ArrayList<>();
        for (int i = 1; i <= 80; i++) {
            if(i<10){
                MapheModel item=new MapheModel("P0"+i,"Phế 0"+i);
                fakePheData.add(item);
            }else {
                MapheModel item=new MapheModel("P"+i,"Phế "+i);
                fakePheData.add(item);
            }
        }
        PopupWindow popupWindow=new PopupWindow(getContext());
        View popupView=getLayoutInflater().inflate(R.layout.phe_grid_popup,null);
        phe_gridView=popupView.findViewById(R.id.phe_gridView);
        pheSearchEdittext=popupView.findViewById(R.id.phe_search_bar);
        pheGridViewAdapter =new PheGridViewAdapter(fakePheData,getContext());
        phe_gridView.setAdapter(pheGridViewAdapter);
        //xu ly su kien search trong phe
        StringBuilder stringBuilder=new StringBuilder();
        pheSearchEdittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(charSequence.length()>0){
                    stringBuilder.setLength(0);
                    stringBuilder.append(charSequence);
                }else if(charSequence.length()==0){
                    stringBuilder.setLength(0);
                }
                String searchString=null;
                if(stringBuilder!=null&&stringBuilder.length()!=0){
                    searchString=stringBuilder.toString();
                }
                pheGridViewAdapter.filter(searchString);
            }
            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        //xu ly su kien click trong gridview
        phe_gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MapheModel selectedData=pheGridViewAdapter.getItem(i);
                BangthongkeModel bangthongkeModel=bangthongkeloiAdapter.findDataByMa(selectedData.getMa());
                if(bangthongkeModel!=null){
                    int soPheHientai=bangthongkeModel.getPhe();
                    bangthongkeModel.setPhe(soPheHientai+1);
                    bangthongkeloiAdapter.notifyDataSetChanged();
                    //xu ly su kien update result slphe
                    updateResult_slphe();
                    updateResult_tongSLKiem();
                    updateResult_tongSLHangloi();
                    updateResult_soMaLoi();
                }else{
                    BangthongkeModel bangThongKeModelMoi=new BangthongkeModel(selectedData.getMa(),1,0,0);
                    bangthongkeloiAdapter.addData(bangThongKeModelMoi);
                    bangthongkeloiAdapter.notifyDataSetChanged();
                    //xu ly su kien update result slphe
                    updateResult_slphe();
                    updateResult_tongSLKiem();
                    updateResult_tongSLHangloi();
                    updateResult_soMaLoi();
                }
                popupWindow.dismiss();
            }
        });
        // Cấu hình PopupWindow
        popupWindow.setContentView(popupView);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        // Hiển thị popup gần nút bấm
        popupWindow.showAsDropDown(anchorView);
    }
    private void updateResult_tongSLHangloi() {
        List<BangthongkeModel> pheRecords=bangthongkeloiAdapter.findDataMaStartsWithString("P");
        List<BangthongkeModel> suaRecords=bangthongkeloiAdapter.findDataMaStartsWithString("S");
        int p=0;
        int s=0;
        if(pheRecords!=null){
            for (BangthongkeModel item:pheRecords
            ) {
                p+=item.getPhe();
            }
        }
        if(suaRecords!=null){
            for (BangthongkeModel item:suaRecords
            ) {
                s+=item.getSua();
            }
        }

        result_slhangloi.setText(String.valueOf(p+s));
    }
    private void updateResult_slphe() {
        List<BangthongkeModel> pheRecords=bangthongkeloiAdapter.findDataMaStartsWithString("P");
        int i=0;
        if(pheRecords!=null){
            for (BangthongkeModel item:pheRecords
            ) {
                i+= item.getPhe();
            }
        }

        result_slphe.setText(String.valueOf(i));
    }
    private void SetAdapterChoBangthongkeloi(){
        bangthongkeloiList=new ArrayList<>();
        bangthongkeloiAdapter=new BangthongkeloiAdapter(bangthongkeloiList,getContext(),this);
        bangthongkeloiGridview.setAdapter(bangthongkeloiAdapter);
    }

    private void btn_datOnclick(){
        BangthongkeModel bangthongkeModelForDatBtn=bangthongkeloiAdapter.findDataByMa("Đạt");
        if(bangthongkeModelForDatBtn==null){
            BangthongkeModel bangThongKeModelMoi=new BangthongkeModel("Đạt",0,0,1);
            bangthongkeloiAdapter.addData(bangThongKeModelMoi);
            bangthongkeloiAdapter.notifyDataSetChanged();
        }else {
            int SoDatHienTai=bangthongkeModelForDatBtn.getDat();
            bangthongkeModelForDatBtn.setDat(SoDatHienTai+1);
            bangthongkeloiAdapter.notifyDataSetChanged();
        }
        updateResult_sldat();
        updateResult_tongSLKiem();
    }
    private void updateResult_sldat() {
        BangthongkeModel datRecord=bangthongkeloiAdapter.findDataByMa("Đạt");
        if(datRecord!=null){
            int resultDat=datRecord.getDat();
            result_sldat.setText(String.valueOf(resultDat));
        }else {
            result_sldat.setText(String.valueOf(0));
        }

    }
    private void SaveThongtinsanphamVaoViewModel() {
        thongkeloiViewModel.setValue(
                String.valueOf(editTextMasp.getText()),
                String.valueOf(editTextMausp.getText()),
                String.valueOf(editTextLenhsx.getText()),
                String.valueOf(editTextNguoikiem.getText()),
                String.valueOf(editTextChuyen.getText())
        );
        thongkeloiViewModel.taoMaspTheoThoiGian(
                String.valueOf(editTextMasp.getText()),
                String.valueOf(editTextMausp.getText()),
                String.valueOf(editTextLenhsx.getText()),
                String.valueOf(editTextNguoikiem.getText()),
                String.valueOf(editTextChuyen.getText()),
                "thoigian"
        );

    }

    private void bindingUi() {
        editTextMasp=binding.thongtinsanphamMasp;
        editTextMausp=binding.thongtinsanphamMausp;
        editTextLenhsx=binding.thongtinsanphamLenhsx;
        editTextNguoikiem=binding.thongtinsanphamNguoikt;
        editTextChuyen=binding.thongtinsanphamSochuyen;
        btn_dat=binding.btnDat;
        btn_phe=binding.btnPhe;
        btn_sua=binding.btnSua;
        btn_xacnhan=binding.thongtinsanphamNutxacnhan;
        btn_hoantat=binding.thongkeloiBtnHoantat;
        btn_edit=binding.thongtinsanphamNutedit;
        bangthongkeloiGridview=binding.gridViewBangThongKe;
        linearLayout_chua_gridview_va_nut_bam=binding.linearGridViewVaNutbam;
        result_maloi=binding.resultMaloi;
        result_sldat=binding.resultSldat;
        result_slhangloi=binding.resultSlhangloi;
        result_slkiem=binding.resultSlkiem;
        result_slphe=binding.resultSlphe;
        result_slsua=binding.resultSlsua;
        linearLayout_chua_dien_thong_tin_sp=binding.linearLayoutThongtinsanpham;
        linearLayout_chua_the_thong_tin_sp=binding.linearLayoutThethongtinsanpham;
        textViewTheThongTinSp=binding.textviewTheSp;
    }


    @Override
    public void tongSLKiem_giam() {
        updateResult_tongSLKiem();
    }

    @Override
    public void tongSLDat_giam() {
        updateResult_sldat();
    }

    @Override
    public void tongSLHangLoi_giam() {
        updateResult_tongSLHangloi();
    }

    @Override
    public void tongSLPhe_giam() {
        updateResult_slphe();
    }

    @Override
    public void tongSLSua_giam() {
        updateResult_slsua();
    }

    @Override
    public void tongMaLoi_giam() {
        updateResult_soMaLoi();
    }
}
