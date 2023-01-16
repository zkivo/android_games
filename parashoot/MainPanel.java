package com.msprojs.marcoschivo.parashoot;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Schivoz on 19/02/2015.
 */
public class MainPanel extends SurfaceView implements SurfaceHolder.Callback{

    protected static final int FPS = 70;
    protected static float Altezza_Screen;
    protected static float Larghezza_Screen;

    private MainThread thread;
    private GamePanel game_panel;

    public MainPanel(Context context) {
        super(context);
        this.getHolder().addCallback(this);
        System.out.println("Costructor main panel");
        thread = new MainThread(this,MainPanel.FPS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        System.out.println("Surface Created metod");
        if(thread.getState() == Thread.State.NEW) {
            Altezza_Screen = this.getHeight();
            Larghezza_Screen = this.getWidth();
            game_panel = new GamePanel(getContext());

            thread.setRunning(true);
            thread.start();
        }/*else{
            thread = new MainThread(this,MainPanel.FPS);
            thread.setRunning(true);
            thread.start();
        }*/
    }

    public void update(){
        game_panel.update();
    }

    public void draw(Canvas canvas){
        game_panel.draw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        game_panel.touchEvent(event);
        return true;
    }

    public void pause(){
        thread.setRunning(false);
    }

    public void resume(){
        if(thread.getState() == Thread.State.TERMINATED){
            thread = new MainThread(this,MainPanel.FPS);
            thread.setRunning(true);
            thread.start();
        }
    }

    public static float percAltOf(float percentuale){
        return Altezza_Screen/100*percentuale;
    }

    public static float percLargOf(float percentuale){
        return Larghezza_Screen/100*percentuale;
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        System.out.println("SurfaceChanged");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        System.out.println("SurfaceDestroyed");
        /*while(true){
            try {
                thread.join();
                break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }*/
    }
}
