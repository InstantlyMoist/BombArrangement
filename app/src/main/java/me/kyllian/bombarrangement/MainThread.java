package me.kyllian.bombarrangement;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Kylli on 11/28/2017.
 */

public class MainThread extends Thread {

    private SurfaceHolder surfaceHolder;
    private Panel panel;
    private boolean running;
    public static Canvas canvas;
    double interpolation = 0;
    final int TICKS_PER_SECOND = 30;
    final int SKIP_TICKS = 1000 / TICKS_PER_SECOND;
    final int MAX_FRAMESKIP = 1;

    public MainThread(SurfaceHolder surfaceHolder, Panel panel) {
        super();
        this.surfaceHolder = surfaceHolder;
        this.panel = panel;
    }

    @Override
    public void run() {
        double next_game_tick = System.currentTimeMillis();
        int loops;

        while (true) {
            loops = 0;
            canvas = null;
            while (System.currentTimeMillis() > next_game_tick
                    && loops < MAX_FRAMESKIP) {

                try {
                    canvas = this.surfaceHolder.lockCanvas();
                        this.panel.update(canvas);
                        this.panel.draw(canvas);
                } catch (Exception e) {
                } finally {
                    if (canvas != null) {
                        try {
                            surfaceHolder.unlockCanvasAndPost(canvas);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                next_game_tick += SKIP_TICKS;
                loops++;
            }

            interpolation = (System.currentTimeMillis() + SKIP_TICKS - next_game_tick
                    / (double) SKIP_TICKS);
        }
    }

    public void setRunning(boolean b) {
        running = b;
    }
}
