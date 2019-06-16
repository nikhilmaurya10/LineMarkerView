package com.iaugmentor.library;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class LineMarkerView extends View {

    private float defaultBaseWidth = 0;
    private float defaultMarkerHeight = 0;
    private float defaultBaseThickness = 0;
    private float defaultMarkerThickness = 0;


    private float baseWidth = 0;
    private float markerHeight = 0;
    private float baseThickness = 0;
    private float markerThickness = 0;
    private float fillLineWidth=0;

    private float defaultViewHeight = 0;
    private float defaultViewWidht = 0;


    private float viewHeight = 0;
    private float viewWidht = 0;

    private float defaultExtraPadding = 0;
    private float defaultLabelSize = 16;
    int defaultLabelColor = Color.BLACK;

    int markerLabelColor;
    boolean overrideLabelColor = false;

    boolean measureCompleted = false;

    private Paint baseLinePaint, fillLinePaint, fillCirclePaint, markerPaint1, markerPaint2;

    ArrayList<LineMarkerData> lineMarkerDataList = new ArrayList<>();
    ArrayList<LineMarker> lineMarkerList = new ArrayList<>();



    public LineMarkerView(Context context) {
        super(context);
    }

    private void init(AttributeSet attributeSet) {
        measureCompleted = false;
        Resources res = getResources();
        if(res != null) {
            defaultBaseWidth = res.getDimension(R.dimen.defaultBaseWidth);
            defaultBaseThickness = res.getDimension(R.dimen.defaultBaseThickness);
            defaultMarkerHeight = res.getDimension(R.dimen.defaultMarkerHeight);
            defaultMarkerThickness = res.getDimension(R.dimen.defaultMarkerThickness);
            defaultExtraPadding = res.getDimension(R.dimen.defaultExtraPadding);
            defaultLabelSize = res.getDimension(R.dimen.defaultLabelSize);
            defaultLabelColor = res.getColor(R.color.defaultLabelColor);
        }
        Context context = getContext();

        TypedArray ta = context.obtainStyledAttributes(attributeSet, R.styleable.LineMarkerView);
        baseWidth = ta.getDimension(R.styleable.LineMarkerView_baseLineWidth, defaultBaseWidth);
        baseThickness = ta.getDimension(R.styleable.LineMarkerView_baseLineThickness, defaultBaseThickness);
        markerHeight = ta.getDimension(R.styleable.LineMarkerView_markerLineHeight, defaultMarkerHeight);
        markerThickness = ta.getDimension(R.styleable.LineMarkerView_markerLineWThickness, defaultMarkerThickness);
        markerLabelColor = ta.getColor(R.styleable.LineMarkerView_markerLabelTextColor, defaultLabelColor);
        overrideLabelColor = ta.getBoolean(R.styleable.LineMarkerView_markerLabelTextColor, false);
        ta.recycle();

        defaultViewWidht = defaultBaseWidth;
        if (markerHeight < defaultBaseThickness) {
            markerHeight = baseThickness;
            defaultViewHeight = markerHeight;
        }

        if (res != null) {
            baseLinePaint = new Paint();
            baseLinePaint.setColor(res.getColor(R.color.baseLineColor));
            baseLinePaint.setStrokeCap(Paint.Cap.BUTT);
            baseLinePaint.setStrokeWidth(baseThickness);
            baseLinePaint.setStyle(Paint.Style.STROKE);
            baseLinePaint.setAntiAlias(true);

            fillLinePaint = new Paint();
            fillLinePaint.setColor(res.getColor(R.color.fillLineColor));
            fillLinePaint.setStrokeCap(Paint.Cap.ROUND);
            fillLinePaint.setStrokeWidth(baseThickness);
            baseLinePaint.setAntiAlias(true);

            fillCirclePaint = new Paint();
            fillCirclePaint.setStyle(Paint.Style.FILL);
            fillCirclePaint.setColor(res.getColor(R.color.fillLineColor));

            markerPaint1 = new Paint();
            markerPaint1.setColor(res.getColor(R.color.marker1Color));
            markerPaint1.setStrokeWidth(markerThickness);

            markerPaint2 = new Paint();
            markerPaint2.setColor(res.getColor(R.color.marker2Color));
            markerPaint2.setStrokeWidth(markerThickness);
        }

    }

    public LineMarkerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }
    public void setFillLine(float percentage)
    {
        fillLineWidth=percentage;
        invalidate();
    }

    public LineMarkerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public LineMarkerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);


        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        viewWidht = widthSize;

        switch (heightMode) {
            case MeasureSpec.EXACTLY:
                if (heightSize > markerHeight) viewHeight = heightSize;
                break;
            case MeasureSpec.AT_MOST:
                viewHeight = heightSize;
            case MeasureSpec.UNSPECIFIED:
                viewHeight = markerHeight;
                break;
            default:
                viewHeight = markerHeight;
                break;
        }
        viewHeight += defaultExtraPadding*2;
        setMeasuredDimension((int)viewWidht, (int)viewHeight);
        measureCompleted = true;
        refreshData();

    }

    private void refreshData() {
        lineMarkerDataList.clear();
        for (LineMarker marker : lineMarkerList) {
            float startX = markerThickness/2;
            if ((viewWidht*marker.getPositionPercentage())/100  -markerThickness/2 >= 0) startX = (viewWidht*marker.getPositionPercentage())/100 - markerThickness/2;
            float startY = 0.0f + defaultExtraPadding;
            float stopY = viewHeight - defaultExtraPadding;
            Paint p = new Paint();
            p.setColor(marker.getColor());
            if (marker.getWidth() > 0) p.setStrokeWidth(marker.getWidth());
            else p.setStrokeWidth(markerThickness);
            LineMarkerData data = new LineMarkerData(startX, startY, startX, stopY, p,marker.getLabel());
            lineMarkerDataList.add(data);
        }
        Collections.sort(lineMarkerDataList, new Comparator<LineMarkerData>() {
            public int compare(LineMarkerData offer1, LineMarkerData offer2) {
                return offer1.getStartX().compareTo(offer2.getStartX());
            }
        });

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBaseLine(canvas);
        drawFillLine(canvas);
        drawMarkers(canvas);
    }

    private void drawMarkers(Canvas canvas) {
        int i = 0;
        for(LineMarkerData data : lineMarkerDataList) {
            canvas.drawLine(data.startX , data.startY, data.stopX, data.stopY, data.markerPaint);
            String text = data.label;
            if (!text.isEmpty()) {
                TextPaint textPaint = new TextPaint();
                textPaint.setAntiAlias(true);
                textPaint.setTextSize(defaultLabelSize);
    //            if (overrideLabelColor) {
    //                textPaint.setColor(data.markerPaint.getColor());
    //            } else
    //                textPaint.setColor(markerLabelColor);
                textPaint.setColor(getResources().getColor(R.color.defaultLabelColor));
                float markerThickness = data.stopX - data.startX;
                int width = (int) textPaint.measureText(text);
                StaticLayout layout;
                //Check if we're running on Android 6.0 or higher
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    StaticLayout.Builder sb = StaticLayout.Builder.obtain(text,  0, text.length(), textPaint, width)
                            .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                            .setLineSpacing(0, 1.0f)
                            .setIncludePad (false);
                    layout = sb.build();
                } else {
                    layout = new StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
                }
                float tvHeight = layout.getHeight();
                float textY = data.startY - tvHeight;
                float textX = data.startX - width/2;
                if (i%2 == 0) textY = data.stopY;
                if (data.startX + width > viewWidht) textX = data.startX - width;
                canvas.save();
                canvas.translate(textX,  textY);
                layout.draw(canvas);
                canvas.restore();
            }
            i++;

        }

    }

    private void drawFillLine(Canvas canvas) {
        canvas.drawLine(0.0f, viewHeight/2,  viewWidht*fillLineWidth/100, viewHeight/2, fillLinePaint);
        float circleCx = (viewWidht*fillLineWidth/100);
        if (circleCx < baseThickness) circleCx += baseThickness;
        else if (viewWidht - baseThickness < circleCx) circleCx -= baseThickness;
        canvas.drawCircle(circleCx, viewHeight/2, baseThickness, fillCirclePaint);
        drawFillLineText(canvas, String.valueOf((int)fillLineWidth), circleCx, viewHeight/2 + baseThickness);
    }

    private void drawBaseLine(Canvas canvas) {
        canvas.drawLine(0.0f, viewHeight/2 , viewWidht, viewHeight/2, baseLinePaint);
    }


    public void addMarkers(@NonNull ArrayList<LineMarker> markers) {
        lineMarkerList.addAll(markers);
        if (measureCompleted)refreshData();
    }

    public void addMarker(@NonNull LineMarker marker) {
        lineMarkerList.add(marker);
        if (measureCompleted)refreshData();
    }

    public void resetMarkers() {
        if (lineMarkerList != null) lineMarkerList.clear();
        else lineMarkerList = new ArrayList<>();
        if (lineMarkerDataList != null) lineMarkerDataList.clear();
        else lineMarkerDataList = new ArrayList<>();
    }

    private void drawFillLineText(Canvas canvas, String text, float x, float y) {
        TextPaint textPaint = new TextPaint();
        textPaint.setAntiAlias(true);
        textPaint.setTextSize(defaultLabelSize);
        textPaint.setColor(getResources().getColor(R.color.fillLineColor));
        int width = (int) textPaint.measureText(text);
        StaticLayout layout;
        //Check if we're running on Android 6.0 or higher
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            StaticLayout.Builder sb = StaticLayout.Builder.obtain(text,  0, text.length(), textPaint, width)
                    .setAlignment(Layout.Alignment.ALIGN_NORMAL)
                    .setLineSpacing(0, 1.0f)
                    .setIncludePad (false);
            layout = sb.build();
        } else {
            layout = new StaticLayout(text, textPaint, width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
        }
        float tvHeight = layout.getHeight();
        float textY = y - tvHeight;
        float textX = x +markerThickness;
        if (x+ width > viewWidht) textX =x - width;
        canvas.save();
        canvas.translate(textX,  textY - 2*baseThickness);
        layout.draw(canvas);
        canvas.restore();
    }
}
