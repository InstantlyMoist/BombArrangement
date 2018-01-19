package me.kyllian.bombarrangement;

import android.graphics.Rect;

public abstract class Bomb {

    protected double dx, dy;
    protected int width, height, x, y, goalx, goaly;
    protected float timeCreated;
    protected boolean sorted, frozen, firstdone, pressed;

    public float getLivingTime() {
        float living = (System.nanoTime() - timeCreated) / 1000000;
        return living;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public void setSorted(boolean b) {
        sorted = b;
    }

    public boolean isSorted() {
        return sorted;
    }

    public void setFrozen(boolean b) {
        frozen = b;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public boolean isFirstdone() {
        return firstdone;
    }

    public void setFirstdone(boolean b) {
        firstdone = b;
    }

    public Rect getRectangle() {
        return new Rect(x, y, x + width, y + height);
    }

    public void setTimeCreated(float f) {
        timeCreated = f;
    }

    public void setPressed(Boolean b) {
        pressed = b;
    }

    public boolean isPressed() {
        return pressed;
    }

    public void setGoal(int x, int y) {
        goalx = x;
        goaly = y;
    }
}
