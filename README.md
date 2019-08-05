# LineMarkerView

A custom View to implement following UI

## Screen Shots:

![screen](../master/art/linemarkerart.png) 

![screen](../master/art/imp.jpg)

## How to use

### Find LineMarkerView
            LineMarkerView lmv =v.findViewById(R.id.lineMarkerView);
### Add Fill line value
            lmv.setFillLine(21);       // A value between 0-100;
### Make data markers
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
            
            
### Set Data markers to LineMarkerView            
            lmv.resetMarkers();
            lmv.addMarkers(list);

### Cutomization Options
In the XML you can customize the look and feel as well as size of this view
  

    <library.LineMarkerView
        android:layout_margin="8dp"
        android:id="@+id/lineMarkerView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        app:baseLineThickness="4dp"
        app:markerLineHeight="30dp"
        app:markerLineWThickness="4dp" />
        
        
### More customizations in future
- Marker label color override
- Marker and Marker label animations
- Fill line thumb customization 



