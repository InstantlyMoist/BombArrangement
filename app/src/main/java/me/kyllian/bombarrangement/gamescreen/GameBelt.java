package me.kyllian.bombarrangement.gamescreen;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import me.kyllian.bombarrangement.Animation;

/**
 * Created by Kylli on 11/30/2017.
 */

public class GameBelt {

    private Animation animation = new Animation();
    private Bitmap spritesheet;
    private boolean playing;
    private int middle = 446;

    public GameBelt(Bitmap res, int numFrames) {
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        playing = false;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, 0, i * 157, res.getWidth(), 157);
        }

        animation.setFrames(image);
        animation.setDelay(10);
    }

    public void draw(Canvas canvas) {
        if (!playing) {
            animation.setFrame(animation.getFrame());
            canvas.drawBitmap(animation.getImage(), middle, 130, null);
            return;
        }
        canvas.drawBitmap(animation.getImage(), middle, 130, null);
    }

    public void update() {
        if (playing) {
            animation.update();
        }
    }

    public void setPlaying(Boolean b) {
        playing = b;
    }
    public Rect getRectangle() {
        return new Rect(middle, 130, middle + spritesheet.getWidth(), 130 + 157);
    }
}
