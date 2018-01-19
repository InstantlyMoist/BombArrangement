package me.kyllian.bombarrangement.gamescreen;

import android.graphics.Bitmap;

import android.graphics.Canvas;

import me.kyllian.bombarrangement.Animation;

public class GameScreenDoor {

    private Animation animation = new Animation();
    private Bitmap spritesheet;
    private boolean playing;

    public GameScreenDoor(Bitmap res, int numFrames) {
        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        playing = false;

        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, 0, i * 198, res.getWidth(), 198);
        }

        animation.setFrames(image);
        animation.setDelay(20);
    }

    public void draw(Canvas canvas) {
        int factor = (canvas.getWidth() / 2) - 417;
        if (!playing) {
            animation.setFrame(animation.getFrame());
            canvas.drawBitmap(animation.getImage(), factor, 0, null);
            return;
        }
        if (animation.playedOnce()) {
            animation.setFrame(20);
            canvas.drawBitmap(animation.getImage(), factor, 0, null);
            playing = false;
        } else {
            canvas.drawBitmap(animation.getImage(), factor, 0, null);
        }
    }

    public void update() {
        if (playing) {
            animation.update();
        }
    }

    public void setPlaying(Boolean b) {
        playing = b;
    }
}
