package com.iaugmentor.library;

public class LineMarker {

    public LineMarker(float positionPercentage, int markerColor, float width, String markerLabel) {
        this.positionPercentage = positionPercentage;
        this.color = markerColor;
        this.width = width;
        this.label = markerLabel;
    }
    public LineMarker(float positionPercentage, int markerColor, String markerLabel) {
        this.positionPercentage = positionPercentage;
        this.color = markerColor;
        this.width = 0;
        this.label = markerLabel;
    }

    public LineMarker(float positionPercentage, int markerColor) {
        this.positionPercentage = positionPercentage;
        this.color = markerColor;
        this.width = 0;
        this.label = "";
    }

    public float getPositionPercentage() {
        return positionPercentage;
    }

    public void setPositionPercentage(float positionPercentage) {
        this.positionPercentage = positionPercentage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    private float positionPercentage = 0;
    private int color;
    private float width = 0;
    private String label = "";

}
