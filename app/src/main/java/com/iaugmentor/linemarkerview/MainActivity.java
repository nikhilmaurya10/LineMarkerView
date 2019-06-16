package com.iaugmentor.linemarkerview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.iaugmentor.library.LineMarker;
import com.iaugmentor.library.LineMarkerView;

public class MainActivity extends AppCompatActivity {

    LinearLayout markerCont;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         markerCont = findViewById(R.id.mc);
    }

    @Override
    protected void onStart() {
        super.onStart();
        markerCont.removeAllViews();
        for (int i = 0; i <15 ; i++) {

            View v = getLayoutInflater().inflate(R.layout.layout_line_marker, markerCont, false);
            LineMarkerView lmv = (LineMarkerView) v.findViewById(R.id.lineMarkerView);
            TextView tv = (TextView) v.findViewById(R.id.text);
            tv.setText("dda" + i +">>>"+ i*i);
            LineMarker m1 = new LineMarker(80, getResources().getColor(R.color.marker1Color));
            LineMarker m2 = new LineMarker(10, getResources().getColor(R.color.marker2Color));

            lmv.setMarker(m1);
            lmv.setMarker(m2);
            markerCont.addView(v);
        }
    }
}
