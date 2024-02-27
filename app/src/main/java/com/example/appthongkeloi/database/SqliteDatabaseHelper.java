package com.example.appthongkeloi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.appthongkeloi.models.BangthongkeModel;

import java.util.List;

public class SqliteDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME="MyDatabase.db";
    private static final int DATABASE_VERSION=1;
    public SqliteDatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        // Tạo bảng và các truy vấn tạo bảng ở đây
        String createTableThongKeLoi="CREATE TABLE thongKeLoi(ma TEXT)";
        sqLiteDatabase.execSQL(createTableThongKeLoi);

        String createTableMaLoi = "CREATE TABLE maLoi (ma TEXT Primary Key,dat INTEGER,phe INTEGER,sua INTEGER,THONGKELOI_foreign_key TEXT REFERENCES thongKeLoi(ma))";
        sqLiteDatabase.execSQL(createTableMaLoi);
    }
    public void insertThongKeLoi(String ma){
        SQLiteDatabase db=this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values=new ContentValues();
            values.put("ma",ma);
            db.insert("thongKeLoi",null,values);
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            db.endTransaction();
            db.close();
        }
    }
    public void insertListMaloi(List<BangthongkeModel> bangthongkeModel,String ma){
        SQLiteDatabase db=this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (BangthongkeModel item:bangthongkeModel
            ) {
                ContentValues values=new ContentValues();
                values.put("ma",item.getMa());
                values.put("dat",item.getDat());
                values.put("phe",item.getPhe());
                values.put("sua",item.getSua());
                values.put("THONGKELOI_foreign_key",ma);
                db.insert("maLoi",null,values);
            }
            db.setTransactionSuccessful();
        }catch (Exception e){
            e.printStackTrace();
        }
        finally {
            db.endTransaction();
            db.close();
        }

    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS maLoi");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS thongKeLoi");
    }
}
