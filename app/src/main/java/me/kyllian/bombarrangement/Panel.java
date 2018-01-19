package me.kyllian.bombarrangement;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import me.kyllian.bombarrangement.gamescreen.BlackBomb;
import me.kyllian.bombarrangement.gamescreen.GameBelt;
import me.kyllian.bombarrangement.gamescreen.GameScreenDoor;
import me.kyllian.bombarrangement.gamescreen.GameScreenLeftSide;
import me.kyllian.bombarrangement.gamescreen.GameScreenRightSide;
import me.kyllian.bombarrangement.gamescreen.PinkBomb;
import me.kyllian.bombarrangement.startscreen.StartScreenBackground;
import me.kyllian.bombarrangement.startscreen.StartScreenOptionsButton;
import me.kyllian.bombarrangement.startscreen.StartScreenStartButton;

public class Panel extends SurfaceView implements SurfaceHolder.Callback {

    public static final int WIDTH = 1080, HEIGHT = 1920;
    public static boolean startScreen, gameScreen, optionScreen;
    private MainThread thread;
    private Canvas canvas;
    // Start screen
    public StartScreenBackground ssbg;
    private StartScreenStartButton sssb;
    private StartScreenOptionsButton ssob;
    private GameScreenDoor gsc;

    // Game screen
    public static GameScreenLeftSide gsls;
    public static GameScreenRightSide gsrs;
    private static GameBelt gb;
    private ArrayList<BlackBomb> bbs;
    private ArrayList<PinkBomb> pbs;
    private float countdownStart, spawnTimer, spawnStart;
    private int bombsSpawned;
    private static Rect backgroundRect;


    public Panel(Context context) {
        super(context);
        getHolder().addCallback(this);
        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        startScreen = true;
        gameScreen = false;
        optionScreen = false;
        thread = new MainThread(getHolder(), this);
        // Start screen
        ssbg = new StartScreenBackground(BitmapFactory.decodeResource(getResources(), R.drawable.background_startscreen));
        sssb = new StartScreenStartButton(BitmapFactory.decodeResource(getResources(), R.drawable.button_start), BitmapFactory.decodeResource(getResources(), R.drawable.button_start_pressed));
        ssob = new StartScreenOptionsButton(BitmapFactory.decodeResource(getResources(), R.drawable.button_options), BitmapFactory.decodeResource(getResources(), R.drawable.button_options_pressed));

        // Game screen
        gsls = new GameScreenLeftSide(BitmapFactory.decodeResource(getResources(), R.drawable.leftside));
        gsrs = new GameScreenRightSide(BitmapFactory.decodeResource(getResources(), R.drawable.rightside));
        gsc = new GameScreenDoor(BitmapFactory.decodeResource(getResources(), R.drawable.door_animation), 21);
        gb = new GameBelt(BitmapFactory.decodeResource(getResources(), R.drawable.belt), 13);
        bbs = new ArrayList<>();
        pbs = new ArrayList<>();
        backgroundRect = new Rect(126, 307, 964, 1794);
        spawnTimer = 3500;
        spawnStart = System.nanoTime();
        bombsSpawned = 0;

        thread.setRunning(true);
        thread.start();
    }

    public void update(Canvas canvas) {
        this.canvas = canvas;
        if (startScreen) {
            ssbg.update();
            sssb.update(canvas);
            ssob.update();
        }
        if (gameScreen) {
            float elapsed = (System.nanoTime() - countdownStart) / 1000000;
            if (elapsed > 2000) {
                gsc.setPlaying(true);
            }
            if (elapsed > 2500) {
                gb.setPlaying(true);
            }
            gsc.update();
            gsls.update();
            gsrs.update();
            gb.update();
            for (BlackBomb bb : bbs) {
                bb.update();
            }
            for (PinkBomb pb : pbs) {
                pb.update();
            }
            if ((System.nanoTime() - spawnStart) / 1000000 > spawnTimer) {
                spawnStart = System.nanoTime();
                bombsSpawned++;
                if (bombsSpawned % 5 == 0) {
                    spawnTimer -= 200;
                    if (spawnTimer < 200) spawnTimer = 200;
                }
                if (ThreadLocalRandom.current().nextBoolean()) {
                    bbs.add(new BlackBomb(BitmapFactory.decodeResource(getResources(), R.drawable.bomb_black), 512, 100, 6));
                } else {
                    pbs.add(new PinkBomb(BitmapFactory.decodeResource(getResources(), R.drawable.bomb_pink), 512, 100, 6));
                }
            }
        }
    }

    public void draw(Canvas canvas) {
        super.draw(canvas);
        final float scaleX = getWidth() / (WIDTH * 1.f);
        final float scaleY = getHeight() / (HEIGHT * 1.f);
        if (canvas != null) {
            final int saved = canvas.save();
            canvas.scale(scaleX, scaleY);
            if (startScreen) {
                ssbg.draw(canvas);
                sssb.draw(canvas);
                ssob.draw(canvas);
            }
            if (gameScreen) {
                canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.background_game_screen), 0, 0, null);
                gsls.draw(canvas);
                gsrs.draw(canvas);
                gb.draw(canvas);
                gsc.draw(canvas);
                for (BlackBomb bb : bbs) {
                    bb.draw(canvas);
                }
                for (PinkBomb pb : pbs) {
                    pb.draw(canvas);
                }
            }
            canvas.restoreToCount(saved);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        Rect touchRect = new Rect(x - 1, y - 1, x + 1, y + 1);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (startScreen) {
                if (Rect.intersects(sssb.getRect(), touchRect)) {
                    System.out.println("x:"  + x + " y:" + y);
                    sssb.setPressed(true);
                }
                if (Rect.intersects(ssob.getRect(), touchRect)) {
                    ssob.setPressed(true);
                }
            }
            if (gameScreen) {
                for (BlackBomb blackBomb : bbs) {
                    if (Rect.intersects(blackBomb.getRectangle(), touchRect)) {
                        if (!blackBomb.isSorted()) {
                            blackBomb.setPressed(true);
                            blackBomb.setFirstdone(true);
                            blackBomb.setX(x);
                            blackBomb.setY(y);
                            break;
                        }
                    }
                }
                for (PinkBomb pinkBomb : pbs) {
                    if (Rect.intersects(pinkBomb.getRectangle(), touchRect)) {
                        if (!pinkBomb.isSorted()) {
                            pinkBomb.setPressed(true);
                            pinkBomb.setFirstdone(true);
                            pinkBomb.setX(x);
                            pinkBomb.setY(y);
                            break;
                        }
                    }
                }
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (startScreen) {
                if (Rect.intersects(sssb.getRect(), touchRect)) {
                    startScreen = false;
                    gameScreen = true;
                    countdownStart = System.nanoTime();
                }
                if (Rect.intersects(ssob.getRect(), touchRect)) {
                    // Options button pressed
                }
                sssb.setPressed(false);
                ssob.setPressed(false);
            }
            if (gameScreen) {
                for (BlackBomb blackBomb : bbs) {
                    if (blackBomb.isPressed()) {
                        blackBomb.setPressed(false);
                        if (touchRect.intersect(gsrs.getRectangle())) {
                            blackBomb.setSorted(true);
                        }
                    }
                }
                for (PinkBomb pb : pbs) {
                    if (pb.isPressed()) {
                        pb.setPressed(false);
                        if (touchRect.intersect(gsls.getRectangle())) {
                            pb.setSorted(true);
                        }
                    }
                }
            }
            return true;
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (gameScreen) {
                for (BlackBomb blackBomb : bbs) {
                    if (blackBomb.pressed) {
                        blackBomb.setX(x);
                        blackBomb.setY(y);
                    }
                }
                for (PinkBomb pb : pbs) {
                    if (pb.pressed) {
                        pb.setX(x);
                        pb.setY(y);
                    }
                }
            }
        }
        return super.onTouchEvent(event);
    }

    public static boolean isLegalMovement(BlackBomb b) {
        if (!b.firstdone) return true;
        if (Rect.intersects(b.getRectangle(), gb.getRectangle())) return false;
        if (b.isSorted()) {
            if (!b.getRectangle().intersect(gsrs.getInnerRectangle())) {
                return false;
            } else {
                return true;
            }
        }
        if (gsls.getRectangle().intersect(b.getRectangle()) || gsrs.getRectangle().intersect(b.getRectangle()))
            return false;
        if (Rect.intersects(b.getRectangle(), backgroundRect)) return true;
        return false;
    }

    public static boolean isLegalMovement(PinkBomb b) {
        if (!b.firstdone) return true;
        if (Rect.intersects(b.getRectangle(), gb.getRectangle())) return false;
        if (b.isSorted()) {
            if (!b.getRectangle().intersect(gsls.getInnerRectangle())) {
                return false;
            } else {
                return true;
            }
        }
        if (gsls.getRectangle().intersect(b.getRectangle()) || gsrs.getRectangle().intersect(b.getRectangle()))
            return false;
        if (Rect.intersects(b.getRectangle(), backgroundRect)) return true;
        return false;
    }
}
