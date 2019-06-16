package com.iaugmentor.library;

public class LineMarker {

    public LineMarker(float positionPercentage, int color, float width, String label) {
        this.positionPercentage = positionPercentage;
        this.color = color;
        this.width = width;
        this.label = label;
    }
    public LineMarker(float positionPercentage, int color, String label) {
        this.positionPercentage = positionPercentage;
        this.color = color;
        this.width = 0;
        this.label = label;
    }

    public LineMarker(float positionPercentage, int markerColor) {
        this.positionPercentage = positionPercentage;
        this.color = color;
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
