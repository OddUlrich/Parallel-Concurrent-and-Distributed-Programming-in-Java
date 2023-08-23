package org.oo.demo.model;

public class MutablePoint {

    private MutablePoint mutablePoint;
    public int x;
    public int y;

    public MutablePoint(MutablePoint mutablePoint) {
        this.mutablePoint = mutablePoint;
    }
}
