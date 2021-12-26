package com.space;

public class Bullet {
    private int X,Y,size;
    public boolean isVisible;
    public Bullet(int x, int y, int size,boolean isVisible) {
        X = x;
        Y = y;
        this.size = size;
        this.isVisible = isVisible;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public void setY(int y) {
        Y = y;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
