package com.iaugmentor.linemarkerview;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.iaugmentor.library.LineMarker;
import com.iaugmentor.library.LineMarkerView;

import java.util.ArrayList;

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
            LineMarker m1 = new LineMarker(100, getResources().getColor(R.color.marker1Color),"label");
            LineMarker m2 = new LineMarker(0, getResources().getColor(R.color.marker2Color),  "10");
            LineMarker m3 = new LineMarker(70, getResources().getColor(R.color.marker1Color), "70");
            LineMarker m4 = new LineMarker(16, getResources().getColor(R.color.marker2Color),  "16");
            LineMarker m5 = new LineMarker(88, getResources().getColor(R.color.marker1Color), "86");
            LineMarker m6 = new LineMarker(95, getResources().getColor(R.color.marker2Color),  "95");
            ArrayList<LineMarker> list = new ArrayList<>();
            list.add(m1);
            list.add(m2);
            list.add(m3);
            list.add(m4);
            list.add(m5);
            list.add(m6);
            if (i%2 == 0)
            lmv.setFillLine(2);
            else lmv.setFillLine(100);
            lmv.resetMarkers();
            lmv.addMarkers(list);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
