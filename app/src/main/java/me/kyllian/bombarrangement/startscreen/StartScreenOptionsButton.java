package me.kyllian.bombarrangement.startscreen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import me.kyllian.bombarrangement.Panel;


/**
 * Created by Kylli on 11/28/2017.
 */

public class StartScreenOptionsButton {

    private Bitmap image;
    private Bitmap pressedImage;
    private int x, y;
    private boolean pressed;

    public StartScreenOptionsButton(Bitmap res, Bitmap pressedImage) {
        image = res;
        this.pressedImage = pressedImage;
        x = (Panel.WIDTH / 2) - (res.getWidth() / 2);
        y = Panel.HEIGHT + 200;
    }

    public void update() {
        if (y != 1760) {
            y -= 10;
        }
    }

    public void draw(Canvas canvas) {
        if (pressed) {
            canvas.drawBitmap(pressedImage, x, y, null);
        } else {
            canvas.drawBitmap(image, x, y, null);
        }
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return pressedImage.getWidth();
    }

    public int getHeight() {
        return pressedImage.getHeight();
    }

    public void setPressed(boolean b) {
        pressed = b;
    }

    public boolean isPressed() {
        return pressed;
    }

    public Rect getRect() {
        return new Rect(x, y, x + image.getWidth(), y + image.getHeight());
    }
}
