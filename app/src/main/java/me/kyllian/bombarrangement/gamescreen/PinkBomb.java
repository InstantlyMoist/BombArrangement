package me.kyllian.bombarrangement.gamescreen;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.concurrent.ThreadLocalRandom;

import me.kyllian.bombarrangement.Animation;
import me.kyllian.bombarrangement.Bomb;
import me.kyllian.bombarrangement.Panel;

public class PinkBomb extends Bomb {

    private Bitmap spritesheet;
    private Animation animation = new Animation();

    public PinkBomb(Bitmap res, int x, int y, int numFrames) {
        super.x = x;
        super.y = y;

        this.setTimeCreated(System.nanoTime());

        Bitmap[] image = new Bitmap[numFrames];
        spritesheet = res;
        this.width = res.getWidth();
        this.height = 105;
        setGoal(540 - (spritesheet.getWidth() / 2), 300);
        for (int i = 0; i < image.length; i++) {
            image[i] = Bitmap.createBitmap(spritesheet, 0, i * 105, res.getWidth() - 50, 105);
        }

        animation.setFrames(image);
        animation.setDelay(500);
    }

    public void update() {
        if (!pressed) {
            x += x > goalx ? -5 : 5;
            y += y > goaly ? -5 : 5;
            if (!Panel.isLegalMovement(this)) {
                if (!isSorted()) {
                    setRandomGoal();
                    update();
                    return;
                } else {
                    this.setGoal(ThreadLocalRandom.current().nextInt(0, 468), ThreadLocalRandom.current().nextInt(1069, 1597));
                    update();
                    return;
                }
            }
            if ((x - goalx > -5 && x - goalx < 5) && (y - goaly > -5 && y - goaly < 5)) {
                if (!isSorted()) {
                    setRandomGoal();
                    this.firstdone = true;
                } else {
                    this.setGoal(ThreadLocalRandom.current().nextInt(612, 1080), ThreadLocalRandom.current().nextInt(296, 824));
                }
            }
        }
        if (this.getLivingTime() < 5000) {
            animation.setFrame(animation.getFrame() == 0 ? 1 : 0);
        } else if (this.getLivingTime() < 10000) {
            animation.setFrame(animation.getFrame() == 2 ? 3 : 2);
        } else {
            animation.setFrame(animation.getFrame() == 4 ? 5 : 4);
        }
        //animation.update();
    }

    public void draw(Canvas canvas) {
        canvas.drawBitmap(animation.getImage(), x, y, null);
    }

    public void setRandomGoal() {
        if (isSorted()) {
            // goal somewhere in the cube
        } else {
            this.goalx = ThreadLocalRandom.current().nextInt(0,1080);
            this.goaly = ThreadLocalRandom.current().nextInt(0,1920);
        }
    }
}
