package me.kyllian.bombarrangement.gamescreen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class GameScreenRightSide {

    private Bitmap image;
    private int top, left, right, bottom;

    public GameScreenRightSide(Bitmap res) {
        image = res;
        top = 960 - (image.getHeight() / 2) + 400;
        left = 1080 - image.getWidth();
        right = 1080;
        bottom = top + image.getHeight();
    }

    public void update() {}

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, canvas.getWidth() - image.getWidth(), (image.getHeight() / 2) + 400, null);
    }

    public Rect getRectangle() {
        return new Rect(left, top, right, bottom);
    }

    public Rect getInnerRectangle() {
        return new Rect(left + 30 + 105, top + 30 + 105, right - 105, bottom - 30 - 105);
    }
}
