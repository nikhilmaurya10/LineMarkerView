package com.iaugmentor.linemarkerview;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.iaugmentor.library.LineMarker;
import com.iaugmentor.library.LineMarkerView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    LinearLayout markerCont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         markerCont = findViewById(R.id.mc);        markerCont.removeAllViews();
        for (int i = 0; i <4 ; i++) {
            View v = getLayoutInflater().inflate(R.layout.layout_line_marker, markerCont, false);
            LineMarkerView lmv =v.findViewById(R.id.lineMarkerView);
            markerCont.addView(v);
            LineMarker m1 = new LineMarker(80, getResources().getColor(R.color.marker1Color),"80");
            LineMarker m2 = new LineMarker(0, getResources().getColor(R.color.marker2Color),  "10");
            LineMarker m3 = new LineMarker(70, getResources().getColor(R.color.marker3Color), "70");
            LineMarker m4 = new LineMarker(16, getResources().getColor(R.color.marker4Color),  "16");
            LineMarker m5 = new LineMarker(88, getResources().getColor(R.color.marker5Color), "86");
            LineMarker m6 = new LineMarker(100, getResources().getColor(R.color.marker2Color),  "100");
            ArrayList<LineMarker> list = new ArrayList<>();
            list.add(m1);
            list.add(m2);
            list.add(m3);
            list.add(m4);
            list.add(m5);
            list.add(m6);
            lmv.setFillLine((i+1)* 100/5);
            lmv.resetMarkers();
            lmv.addMarkers(list);
        }
        Button btn = findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setNewData();
            }
        });
    }

    void setNewData() {
        markerCont.removeAllViews();
        final int random = new Random().nextInt(9) + 1; // atleast 1
        for (int i = 0; i < random ; i++) {
            View v = getLayoutInflater().inflate(R.layout.layout_line_marker, markerCont, false);
            LineMarkerView lmv =v.findViewById(R.id.lineMarkerView);
            markerCont.addView(v);
            int random1  = new Random().nextInt(100);
            int random2  = new Random().nextInt(100);
            int random3  = new Random().nextInt(100);
            int random4  = new Random().nextInt(100);
            int random5  = new Random().nextInt(100);
            LineMarker m1 = new LineMarker(random1, getResources().getColor(R.color.marker1Color),String.valueOf(random1));
            LineMarker m2 = new LineMarker(random2, getResources().getColor(R.color.marker2Color),  String.valueOf(random2));
            LineMarker m3 = new LineMarker(random3, getResources().getColor(R.color.marker3Color), String.valueOf(random3));
            LineMarker m4 = new LineMarker(random4, getResources().getColor(R.color.marker4Color),  String.valueOf(random4));
            LineMarker m5 = new LineMarker(random5, getResources().getColor(R.color.marker5Color), String.valueOf(random5));
            ArrayList<LineMarker> list = new ArrayList<>();
            list.add(m1);
            list.add(m2);
            list.add(m3);
            list.add(m4);
            list.add(m5);
            lmv.setFillLine((i+1)* 100/8);
            lmv.resetMarkers();
            lmv.addMarkers(list);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
