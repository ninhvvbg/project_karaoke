package com.example.project_karaoke;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class Musicadapter extends ArrayAdapter<Music> {
    Activity context;
    int resource;
    List<Music> objects;
    public Musicadapter(Activity context, int resource,  List<Music> objects) {
        super(context, resource, objects);
        this.context = context;
        this.objects = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = this.context.getLayoutInflater();
        View row = inflater.inflate(this.resource,null);
        TextView ma = row.findViewById(R.id.edtma);
        TextView ten = row.findViewById(R.id.edtten);
        TextView casi = row.findViewById(R.id.edtcasi);
        ImageButton btnlike = row.findViewById(R.id.btnlike);
        ImageButton dislike = row.findViewById(R.id.btndislike);
        final Music music = this.objects.get(position);

        ma.setText(music.getMa());
        ten.setText(music.getTen());
        casi.setText(music.getCasi());

        if (music.isThich()){
            btnlike.setVisibility(View.INVISIBLE);
            dislike.setVisibility(View.VISIBLE);
        }else {
            btnlike.setVisibility(View.VISIBLE);
            dislike.setVisibility(View.INVISIBLE);
        }
        btnlike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulythich(music);
            }
        });
        dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xulykhongthich(music);
            }
        });
        return row;

    }

    private void xulykhongthich(Music music) {
        ContentValues row = new ContentValues();
        row.put("YEUTHICH",0);
        MainActivity.database.update("ArirangSongList",row,"MABH=?",new String[]{music.getMa()});
    }

    private void xulythich(Music music) {
        ContentValues row = new ContentValues();
        row.put("YEUTHICH",1);
       MainActivity.database.update("ArirangSongList",row,"MABH=?",new String[]{music.getMa()});
    }
}
