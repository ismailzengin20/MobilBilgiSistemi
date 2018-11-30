package com.example.ismail.balikbilgisistemi;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ismail on 2.11.2017.
 */

public class veritabani extends SQLiteOpenHelper {
    private  static  String veritabaniadi="databasee";
    private  static  int versiyon=1;
    public veritabani(Context c) {
        super(c,veritabaniadi,null,versiyon);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE bilgi(id INTEGER,takim_id TEXT,familya_id TEXT,sinonim TEXT,turkce_ad TEXT ,ingilizce_ad TEXT,latince_ad TEXT,diagnostik_ozellik TEXT,ekolojik_ozellik TEXT,genelyayilis_id TEXT,ekonomikonem_id TEXT,aciklama1 TEXT,aciklama2 TEXT,aciklama3 TEXT,resim1 TEXT,resim2 TEXT,resim3 TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST balıkbilgi");
        onCreate(db);
    }
}
