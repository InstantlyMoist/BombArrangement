package me.kyllian.bombarrangement.startscreen;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import me.kyllian.bombarrangement.Panel;

/**
 * Created by Kylli on 11/28/2017.
 */

public class StartScreenBackground {

    private Bitmap image;
    private int x;

    public StartScreenBackground(Bitmap res) {
        image = res;
    }

    public void update() {
        x += -2;
        if (x < -Panel.WIDTH) {
            x = 0;
        }
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(image, x, 0, null);
        if (x < 0) {
            canvas.drawBitmap(image, x + Panel.WIDTH, 0, null);
        }
    }
}
