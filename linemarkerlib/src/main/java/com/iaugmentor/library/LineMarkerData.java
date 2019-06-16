package com.iaugmentor.library;

import android.graphics.Paint;

class LineMarkerData {

    float startX, startY, stopX, stopY;
    Paint markerPaint;
    String label;

    public LineMarkerData(float startX, float startY, float stopX, float stopY, Paint markerPaint, String markerLabel) {
        this.startX = startX;
        this.startY = startY;
        this.stopX = stopX;
        this.stopY = stopY;
        this.markerPaint = markerPaint;
        this.label = markerLabel;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartY() {
        return startY;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getStopX() {
        return stopX;
    }

    public void setStopX(float stopX) {
        this.stopX = stopX;
    }

    public float getStopY() {
        return stopY;
    }

    public void setStopY(float stopY) {
        this.stopY = stopY;
    }

    public Paint getMarkerPaint() {
        return markerPaint;
    }

    public void setMarkerPaint(Paint markerPaint) {
        this.markerPaint = markerPaint;
    }
}
