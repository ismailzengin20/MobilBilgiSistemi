package com.example.ismail.balikbilgisistemi;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
public class MainActivity extends AppCompatActivity {
    private ListView lv;
    public ArrayList liste=new ArrayList();
    public EditText editText;
    private ArrayAdapter<String> adapter;
    private ProgressDialog  progressDialog;
    private  static String URL="http://iskenderunteknik.com/ornekxml/balikbilgiXML.xml";
    private  static String URL_version="http://iskenderunteknik.com/ornekxml/versionXML.xml";
    Button bt;
    String [] idd; String [] takim_idd;String [] familya_idd;String [] sinonimm;String [] turkce_add;
    String [] ingilizce_add; String [] latince_add;String [] diagnostik_ozellikk;String [] ekolojik_ozellikk;String [] habitat_idd;
    String [] genelyayilis_idd; String [] ekonomikonem_idd;String [] aciklama11;String [] aciklama22;String [] aciklama33;
    String [] resim11; String [] resim22;String [] resim33;
    ImageView iv;
    static boolean durum=true;
    ArrayList<String> veri=new ArrayList<String>();
    static  String version="793";
    private  veritabani vt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        vt = new veritabani(getApplicationContext());
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       Button bt2=(Button)findViewById(R.id.button2) ;
         bt=(Button)findViewById(R.id.button);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(getApplicationContext(), Main3Activity.class);
                startActivity(i);
            }
        });
        editText = (EditText) findViewById(R.id.editText2);
        lv = (ListView) findViewById(R.id.listview);
        kayitgöster(kayitgetir());

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(MainActivity.this, Main2Activity.class);
                i.putExtra("adi", lv.getItemAtPosition(position).toString());
                startActivity(i);
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Aramayap(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });version();

    }
    private  void Aramayap(String deger)
    {
        try
        {
            SQLiteDatabase db = vt.getWritableDatabase();
            String sql="select turkce_ad from bilgi WHERE turkce_ad LIKE '%"+deger+"%' OR ingilizce_ad LIKE '%"+deger+"%' OR latince_ad LIKE '%"+deger+"%'";
            Cursor cursor=db.rawQuery(sql,null);
            String adi="";
            veri.removeAll(veri);
            while(cursor.moveToNext()){
                adi=cursor.getString(cursor.getColumnIndex("turkce_ad"));
                veri.add(adi);}
            ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,android.R.id.text1,veri);
            lv.setAdapter(adapter);
        }
        catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),e+"asa",Toast.LENGTH_LONG).show();
        }
    }
    String  [] SELECT={"id","takim_id","familya_id","sinonim","turkce_ad","ingilizce_ad","latince_ad","diagnostik_ozellik","ekolojik_ozellik","genelyayilis_id","ekonomikonem_id","aciklama1","aciklama2","aciklama3","resim1","resim2","resim3"};
    private Cursor  kayitgetir ()
    {
        SQLiteDatabase db=vt.getReadableDatabase();
        Cursor c= db.query("bilgi",SELECT,null,null,null,null,null);
        return c;
    }
    public void kayitgöster(Cursor c)
    {
        String adi="";
        ArrayList<String> veri=new ArrayList<String>();
        while(c.moveToNext())
        {
            adi=c.getString(c.getColumnIndex("turkce_ad"));
            veri.add(adi);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,android.R.id.text1,veri);
        lv.setAdapter(adapter);
    }
 /*   private  class version extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected Void doInBackground(Void... params) {*/
 public void version ()
 {
            try{
                Document docu= Jsoup.connect(URL_version).timeout(0).get();
                Elements version_no=docu.select("version_no");
                if(!(version.toString()==version.toString()))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("!!! GÜNCELLEME VAR !!!");
                    builder.setMessage("Verileri güncellemek ister mi siniz?");
                    builder.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //İptal butonuna basılınca yapılacaklar.Sadece kapanması isteniyorsa boş bırakılacak
                        }
                    });
                    builder.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            new verigetir().execute();
                        }
                    });

                    builder.show();
                    version=version_no.get(0).text();
                }
            }
            catch(Exception e)
            {
                Toast.makeText(getApplicationContext(),e+"hattaaaa",Toast.LENGTH_LONG).show();
            }


        }

        private  class verigetir extends AsyncTask<Void,Void,Void>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("BALIKLAR");
            progressDialog.setMessage("Lütfen bekleyiniz");
            progressDialog.setIndeterminate(false);
            progressDialog.show();
        }
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc= Jsoup.connect(URL).timeout(30*1000).get();
                Elements id=doc.select("id");
                Elements takim_id=doc.select("takim_id");
                Elements familya_id=doc.select("familya_id");
                Elements sinonim=doc.select("sinonim");
                Elements turkce_ad=doc.select("turkce_ad");
                Elements ingilizce_ad=doc.select("ingilizce_ad");
                Elements latince_ad=doc.select("latince_ad");
                Elements diagnostik_ozellik=doc.select("diagnostik_ozellik");
                Elements ekolojik_ozellik=doc.select("ekolojik_ozellik");
                Elements habitat_id=doc.select("habitat_id");
                Elements genelyayilis_id=doc.select("genelyayilis_id");
                Elements ekonomikonem_id=doc.select("ekonomikonem_id");
                Elements aciklama1=doc.select("aciklama1");
                Elements aciklama2=doc.select("aciklama2");
                Elements aciklama3=doc.select("aciklama3");
                Elements resim1=doc.select("resim1");
                Elements resim2=doc.select("resim2");
                Elements resim3=doc.select("resim3");
                idd= new String[id.size()];takim_idd= new String[id.size()];familya_idd= new String[id.size()];sinonimm= new String[id.size()];turkce_add= new String[id.size()];
                ingilizce_add= new String[id.size()];latince_add= new String[id.size()];diagnostik_ozellikk= new String[id.size()];ekolojik_ozellikk= new String[id.size()];
                habitat_idd= new String[id.size()];genelyayilis_idd= new String[id.size()];ekonomikonem_idd= new String[id.size()];aciklama11= new String[id.size()];aciklama22= new String[id.size()];
                aciklama33= new String[id.size()];resim11= new String[id.size()];resim33= new String[id.size()];resim22= new String[id.size()];
                for (int i=0;i<id.size();i++)
                {
                    idd[i]=id.get(i).text();
                    takim_idd[i]=takim_id.get(i).text();
                    familya_idd[i]=familya_id.get(i).text();
                    sinonimm[i]=sinonim.get(i).text();
                    turkce_add[i]=turkce_ad.get(i).text();
                    ingilizce_add[i]=ingilizce_ad.get(i).text();
                    latince_add[i]=latince_ad.get(i).text();
                    diagnostik_ozellikk[i]=diagnostik_ozellik.get(i).text();
                    ekolojik_ozellikk[i]=ekolojik_ozellik.get(i).text();
                    habitat_idd[i]=habitat_id.get(i).text();
                    genelyayilis_idd[i]=genelyayilis_id.get(i).text();
                    ekonomikonem_idd[i]=ekonomikonem_id.get(i).text();
                    aciklama11[i]=aciklama1.get(i).text();
                    aciklama22[i]=aciklama2.get(i).text();
                    aciklama33[i]=aciklama3.get(i).text();
                    resim11[i]=resim1.get(i).text();
                    resim22[i]=resim2.get(i).text();
                    resim33[i]=resim3.get(i).text();
                }
            } catch (IOException e) {
                Toast.makeText(getApplicationContext(), "HATA"+e, Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            lv.setAdapter(adapter);
            progressDialog.dismiss();
            try
            {SQLiteDatabase dbb = vt.getWritableDatabase();
                String sqll="SELECT * FROM bilgi ORDER BY id DESC LIMIT 1  ";
                Cursor cursorr=dbb.rawQuery(sqll,null);
                while (cursorr.moveToNext()) {
                    if(!cursorr.getString(cursorr.getColumnIndex("id")).toString().equals(idd[idd.length-1]))
                    {durum=false;}
                }
            }
            catch (Exception e)
            {
                Toast.makeText(getApplicationContext(),e+"asa",Toast.LENGTH_LONG).show();
            }
                if(!durum){
                    durum=true;
                try
                {
                    String sqll="DELETE FROM bilgi";
                    for(int i=0;i<idd.length;i++)
                    {
                        SQLiteDatabase db=vt.getWritableDatabase();
                        db.execSQL(sqll);
                        ContentValues cv=new ContentValues();
                        cv.put("id",idd[i]);
                        cv.put("takim_id",takim_idd[i]);
                        cv.put("familya_id",familya_idd[i]);
                        cv.put("sinonim",sinonimm[i]);
                        cv.put("turkce_ad",turkce_add[i]);
                        cv.put("ingilizce_ad",ingilizce_add[i]);
                        cv.put("latince_ad",latince_add[i]);
                        cv.put("diagnostik_ozellik",diagnostik_ozellikk[i]);
                        cv.put("ekolojik_ozellik",ekolojik_ozellikk[i]);
                        cv.put("genelyayilis_id",genelyayilis_idd[i]);
                        cv.put("ekonomikonem_id",ekonomikonem_idd[i]);
                        cv.put("aciklama1",aciklama11[i]);
                        cv.put("aciklama2",aciklama22[i]);
                        cv.put("aciklama3",aciklama33[i]);
                        cv.put("resim1",resim11[i]);
                        cv.put("resim2",resim22[i]);
                        cv.put("resim3",resim33[i]);
                        db.insertOrThrow("bilgi",null,cv);}
                    Toast.makeText(getApplicationContext(), " veri tabanına veriler eklendi", Toast.LENGTH_SHORT).show();
                    kayitgöster(kayitgetir());
                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), " hata", Toast.LENGTH_SHORT).show();
                }
                }kayitgöster(kayitgetir());


        }

    }
}
