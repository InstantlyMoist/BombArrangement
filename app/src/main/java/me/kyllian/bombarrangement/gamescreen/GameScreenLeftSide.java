package me.kyllian.bombarrangement.gamescreen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameScreenLeftSide {

    private Bitmap image;
    private int top, left, right, bottom;

    public GameScreenLeftSide(Bitmap res) {
        image = res;
        top = 960 - (image.getHeight() / 2) + 400;
        left = 0;
        right = image.getWidth();
        bottom = top + image.getHeight();
    }

    public void update() {}

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, 0, 960 - (image.getHeight() / 2) + 400, null);
    }

    public Rect getRectangle() {
        int top = 960 - (image.getHeight() / 2) + 400;
        return new Rect(0,  top, image.getWidth(), top + image.getHeight());
    }

    public Rect getInnerRectangle() {
        return new Rect(left + 30 + 105, top + 30 + 105, right - 105, bottom - 30 - 105);
    }
}
