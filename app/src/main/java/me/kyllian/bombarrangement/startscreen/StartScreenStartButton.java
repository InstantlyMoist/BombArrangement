package me.kyllian.bombarrangement.startscreen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import me.kyllian.bombarrangement.Panel;

/**
 * Created by Kylli on 11/28/2017.
 */

public class StartScreenStartButton {

    private Bitmap image;
    private Bitmap pressedImage;
    private Canvas canvas;
    private int x, y, right, bottom;
    private boolean pressed;

    public StartScreenStartButton(Bitmap res, Bitmap pressedImage) {
        image = res;
        this.pressedImage = pressedImage;
        x = (Panel.WIDTH / 2) - (res.getWidth() / 2);
        y = Panel.HEIGHT - 100;
    }

    public void update(Canvas canvas) {
        this.canvas = canvas;
        right = x + image.getScaledWidth(canvas);
        bottom = y + image.getScaledHeight(canvas);
        if (y != 1600) {
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

    public int getHeight() {
        return pressedImage.getHeight();
    }

    public void setPressed(boolean b) {
        pressed = b;
    }

    public Rect getRect() {
        System.out.println("x:" + x +" y:" + y + " right:" + right + " bottom:" + bottom);
        return new Rect(x, y, right, bottom );
    }
}
