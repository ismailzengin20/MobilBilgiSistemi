package com.example.ismail.balikbilgisistemi;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    private  veritabani vt;
    ImageView iv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        vt=new veritabani(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        TextView tv1=(TextView) findViewById(R.id.textView);
        TextView tv=(TextView) findViewById(R.id.textView7);
        TextView tv5=(TextView) findViewById(R.id.textView5);
        TextView tv9=(TextView) findViewById(R.id.textView9);
        EditText tv13=(EditText) findViewById(R.id.editText7);
        EditText et5=(EditText) findViewById(R.id.editText6);
        iv=(ImageView)findViewById(R.id.imageView2);
        Bundle extras = getIntent().getExtras();
        String adi = extras.getString("adi");
        try
        {SQLiteDatabase db = vt.getWritableDatabase();
            String sql="select *from bilgi where turkce_ad='"+adi+"'";
            Cursor cursor=db.rawQuery(sql,null);
            while(cursor.moveToNext()){
                tv.setText(adi);
                tv5.setText(cursor.getString(cursor.getColumnIndex("ingilizce_ad")));
                tv9.setText(cursor.getString(cursor.getColumnIndex("turkce_ad")));
                tv1.setText(cursor.getString(cursor.getColumnIndex("latince_ad")));
                tv13.setText(cursor.getString(cursor.getColumnIndex("diagnostik_ozellik")));
                et5.setText(cursor.getString(cursor.getColumnIndex("aciklama1")));
                String resimadi=cursor.getString(cursor.getColumnIndex("resim1"));
                String ad="";
                if(resimadi.length()<14) {
                    ad = resimadi.substring(0, 9);
                }
                if(resimadi.length()<13) {
                    ad = resimadi.substring(0, 8);
                }

                String uri = "@drawable/"+ad.toString()+"";
                int imageResource = getResources().getIdentifier(uri, null, getPackageName());
                iv= (ImageView)findViewById(R.id.imageView2);
                Drawable res = getResources().getDrawable(imageResource);
                iv.setImageDrawable(res);
                //  int id=R.drawable.balik12;
                //  iv.setImageResource(id);
            }
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e+"asa",Toast.LENGTH_LONG).show();
        }
    }
}
