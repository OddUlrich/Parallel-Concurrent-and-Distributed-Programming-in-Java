package org.oo.demo.model;

public class Board {

    public void commitNewValues() {

    }

    public Board getSubBoard(int count, int i) {
        return new Board();
    }

    public boolean hasConverged() {
        return false;
    }

    public int getMaxX() {
        return 0;
    }

    public int getMaxY() {
        return 0;
    }

    public void setNewValue(int x, int y, int value) {

    }

    public void waitForConvergence() {

    }
}
