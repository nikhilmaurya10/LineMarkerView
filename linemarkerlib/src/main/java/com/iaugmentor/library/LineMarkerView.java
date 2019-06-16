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
import android.util.Log;
import android.view.View;

import java.util.ArrayList;

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
            fillLinePaint.setColor(res.getColor(R.color.fillLinePaint));
            fillLinePaint.setStrokeCap(Paint.Cap.ROUND);
            fillLinePaint.setStrokeWidth(baseThickness);
            baseLinePaint.setAntiAlias(true);

            fillCirclePaint = new Paint();
            fillCirclePaint.setStyle(Paint.Style.FILL);
            fillCirclePaint.setColor(res.getColor(R.color.fillLinePaint));

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
            float startx = (viewWidht*marker.getPositionPercentage())/100;
            float startY = 0.0f + defaultExtraPadding;
            float stopY = viewHeight - defaultExtraPadding;
            Paint p = new Paint();
            p.setColor(marker.getColor());
            if (marker.getWidth() > 0) p.setStrokeWidth(marker.getWidth());
            else p.setStrokeWidth(markerThickness);
            LineMarkerData data = new LineMarkerData(startx, startY, startx, stopY, p,marker.getLabel());
            lineMarkerDataList.add(data);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBaseLine(canvas);
        drawFillLine(canvas);
        drawMarkers(canvas);
//        drawMarker1(canvas);
//        drawMarker2(canvas);

    }

    private void drawMarkers(Canvas canvas) {
        for(LineMarkerData data : lineMarkerDataList) {
            if (data.startX >= viewWidht - 20) {
                data.startX -= 20;
                data.stopX -= 20;
            }
            canvas.drawLine(data.startX, data.startY, data.stopX, data.stopY, data.markerPaint);
            Log.d("Color>>>>>>>>>", data.markerPaint.getColor() + "");
            canvas.save();

            String text = data.label;
            if (text==null)
            {
                text="";
            }
            TextPaint textPaint = new TextPaint();
            textPaint.setAntiAlias(true);
            textPaint.setTextSize(defaultLabelSize);
//            if (overrideLabelColor) {
//                textPaint.setColor(data.markerPaint.getColor());
//            } else
//                textPaint.setColor(markerLabelColor);
            textPaint.setColor(getResources().getColor(R.color.white));
            float markerThickness = data.stopX - data.startX;
            canvas.translate(data.startX - defaultMarkerThickness,  data.stopY);
            int width = (int) textPaint.measureText(text);
            StaticLayout staticLayout = new StaticLayout(text, textPaint, (int) width, Layout.Alignment.ALIGN_NORMAL, 1.0f, 0, false);
            staticLayout.draw(canvas);
            canvas.restore();

        }

    }

    private void drawFillLine(Canvas canvas) {
        canvas.drawLine(0.0f, viewHeight/2,  viewWidht*fillLineWidth/100, viewHeight/2, fillLinePaint);
        float cx = viewWidht*fillLineWidth/100;
        if (fillLineWidth < baseThickness) cx += baseThickness;
        else if (viewWidht*fillLineWidth/100 >= viewWidht-baseThickness) cx -= baseThickness;
        canvas.drawCircle(cx, viewHeight/2, baseThickness, fillCirclePaint);
    }

    private void drawBaseLine(Canvas canvas) {
        canvas.drawLine(0.0f, viewHeight/2 , viewWidht, viewHeight/2, baseLinePaint);
    }

    private void drawMarker1(Canvas canvas) {
        canvas.drawLine(100.0f, 0.0f, 100.0f, viewHeight, markerPaint1);
    }
    private void drawMarker2(Canvas canvas) {
        canvas.drawLine(200.0f, 0.0f, 200.0f, viewHeight, markerPaint2);
    }

//    public void setMarkers(@NonNull ArrayList<LineMarker> markers) {
//        for (LineMarker marker : markers) {
//            float startx = (viewWidht*marker.getPositionPercentage())/100;
//            float startY = viewHeight/2;
//            float stopY = viewHeight/2;
//            Paint p = new Paint();
//            p = new Paint();
//            p.setColor(marker.getColor());
//            if (marker.getWidth() > 0) p.setStrokeWidth(marker.getWidth());
//            else p.setStrokeWidth(markerThickness);
//            LineMarkerData data = new LineMarkerData(startx, startY, startx, stopY, p);
//            lineMarkerDataList.add(data);
//        }
//        invalidate();
//    }

//    public void setMarker(@NonNull LineMarker marker) {
//        float startx = (viewWidht*marker.getPositionPercentage())/100;
//        float startY = viewHeight/2;
//        float stopY = viewHeight/2;
//        Paint p = new Paint();
//        p = new Paint();
//        p.setColor(marker.getColor());
//        if (marker.getWidth() > 0) p.setStrokeWidth(marker.getWidth());
//        else p.setStrokeWidth(markerThickness);
//        LineMarkerData data = new LineMarkerData(startx, startY, startx, stopY, p);
//        lineMarkerDataList.add(data);
//        invalidate();
//    }

    public void setMarker(@NonNull LineMarker marker) {
        lineMarkerList.add(marker);
        refreshData();
        if (measureCompleted) invalidate();
    }

    public void resetMarkers() {
        if (lineMarkerDataList == null) lineMarkerDataList = new ArrayList<>();
        else lineMarkerDataList.clear();
    }

}
