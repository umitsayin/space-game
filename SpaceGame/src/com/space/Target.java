package com.space;

public class Target {

    private int X,Y,width,height,speed;
    public boolean way = false;
    public Target(int x, int y, int width, int height, int speed) {
        X = x;
        Y = y;
        this.width = width;
        this.height = height;
        this.speed = speed;
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

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
