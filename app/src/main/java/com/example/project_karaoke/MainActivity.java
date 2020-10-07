package com.example.project_karaoke;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView lvbaihatgoc, lvbaihatyeuthich;
    ArrayList<Music> dsbaihatgoc;
    ArrayAdapter<Music> adapterbaihatgoc;

    ArrayList<Music> dsbaihatyeuthich;
    ArrayAdapter<Music> adapterbaihatyeuthich;
    TabHost tabHost;

    public  static String DATABASE_NAME = "Arirang.sqlite";
    String DB_PATH_SUFIX = "/databases/";
    public  static SQLiteDatabase database = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xulysaochepcosodulieuvaohethong();
        addcontrols();
        addenvents();
        xulybathatgoc();
    }


    private void xulysaochepcosodulieuvaohethong() {
        //private app
        File dbFile = getDatabasePath(DATABASE_NAME);
        if (!dbFile.exists())    {
            try   {
                CopyDataBaseFromAsset();
                Toast.makeText(this, "sao chép cơ sở dự liệu thành công vào hệ thống", Toast.LENGTH_LONG).show();
            } catch (Exception e)       {
                Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
            }
        }
    }
    private void CopyDataBaseFromAsset() {
        try {
            InputStream myInput = getAssets().open(DATABASE_NAME);
            String outFileName = layduongdan();
            File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFIX);
            if (!f.exists())   f.mkdir();
            OutputStream myOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = myInput.read(buffer)) > 0) {
                myOutput.write(buffer, 0, length);
            }
            myOutput.flush();
            myOutput.close();
            myInput.close();

        }catch (Exception e){
        }

    }

    private String layduongdan (){
        return getApplicationInfo().dataDir + DB_PATH_SUFIX+ DATABASE_NAME;
    }


    private void addenvents() {
        tabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                if (s.equalsIgnoreCase("t1")){
                    xulybathatgoc();

                }else if(s.equalsIgnoreCase("t2")){
                    xulybathatyeuthich();
                }
            }
        });

    }

    private void xulybathatyeuthich() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("ArirangSongList",null,"YEUTHICH=?",new  String[]{"1"},null,null,null);
        dsbaihatyeuthich.clear();
        while (cursor.moveToNext()){
            String mabh = cursor.getString(0);
            String tenbh = cursor.getString(1);
            String casi= cursor.getString(3);
            int yeuthich = cursor.getInt(5);
            Music music = new Music();
            music.setMa(mabh);
            music.setTen(tenbh);
            music.setCasi(casi);
            music.setThich(yeuthich == 0);
            dsbaihatyeuthich.add(music);
        }
        cursor.close();
        adapterbaihatyeuthich.notifyDataSetChanged();

    }

    private void xulybathatgoc() {
        database = openOrCreateDatabase(DATABASE_NAME,MODE_PRIVATE,null);
        Cursor cursor = database.query("ArirangSongList",null,null,null,null,null,null);
        dsbaihatgoc.clear();
        while (cursor.moveToNext()){
            String mabh = cursor.getString(0);
            String tenbh = cursor.getString(1);
            String casi= cursor.getString(3);
            int yeuthich = cursor.getInt(5);
            Music music = new Music();
            music.setMa(mabh);
            music.setTen(tenbh);
            music.setCasi(casi);
            music.setThich(yeuthich == 1);
            dsbaihatgoc.add(music);
        }
        cursor.close();
        adapterbaihatgoc.notifyDataSetChanged();

    }

    private void addcontrols() {
        tabHost = findViewById(R.id.tast);
        tabHost.setup();
        TabHost.TabSpec tab1 = tabHost.newTabSpec("t1");
        tab1.setContent(R.id.tab1);
        tab1.setIndicator("",getResources().getDrawable(R.drawable.music));
        tabHost.addTab(tab1);

        TabHost.TabSpec tab2 = tabHost.newTabSpec("t2");
        tab2.setContent(R.id.tab2);
        tab2.setIndicator("",getResources().getDrawable(R.drawable.yeuthich));
        tabHost.addTab(tab2);


        lvbaihatgoc = findViewById(R.id.lvbaihatgoc);
        dsbaihatgoc = new ArrayList<>();
        adapterbaihatgoc = new Musicadapter(MainActivity.this,R.layout.item,dsbaihatgoc);
        lvbaihatgoc.setAdapter(adapterbaihatgoc);


        lvbaihatyeuthich = findViewById(R.id.lvbaihatyeuthich);
        dsbaihatyeuthich = new ArrayList<>();
        adapterbaihatyeuthich = new Musicadapter(MainActivity.this,R.layout.item,dsbaihatyeuthich);
        lvbaihatyeuthich.setAdapter(adapterbaihatyeuthich);

//        gialapdulieu();
    }
    private void gialapdulieu() {
        dsbaihatgoc.add(new Music("1234534","không yêu đừng nói lời cay đấng","ngọt ngào",true));
        dsbaihatgoc.add(new Music("123452","Riêng một góc trời","đắng cay",false));
        dsbaihatgoc.add(new Music("12345324","trời đổ cơn mưa","ngọt bùi",true));
        adapterbaihatgoc.notifyDataSetChanged();

    }
}