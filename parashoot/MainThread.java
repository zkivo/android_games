package com.msprojs.marcoschivo.parashoot;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Schivoz on 19/02/2015.
 */
public class MainThread extends Thread {

    private MainPanel mp;
    private SurfaceHolder sh;
    private boolean running;
    private int fps;

    public MainThread(MainPanel a,int b){
        System.out.println("Costructor MainThread");
        mp = a;
        sh = mp.getHolder();
        running = false;
        fps = b;
    }

    public void setRunning(boolean b){
        running = b;
        System.out.println("set running " + b);
    }

    public void run(){
        Canvas canvas;
        long time1 = System.currentTimeMillis();
        long time2 = 0;
        long diff = 0;
        int intervallo = 1000/fps; //mille secondi diviso i frame, sono quanti millisecondi deve avere un frame

        while (running) {
            canvas=null;
            time2 = System.currentTimeMillis();
            diff = time2 - time1;
            if(diff >= intervallo){
                //Proviamo a bloccare la "tela" per la modifica dei pixel sulla superficie
                try {
                    canvas = this.sh.lockCanvas();
                    synchronized (sh) {
                        // Aggiornamento dello stato di gioco
                        this.mp.update();
                        this.mp.draw(canvas);
                    }
                } finally {
                    // Se scatta l'eccezione la superficie non viene lasciata
                    // in uno stato incoerente
                    if (canvas != null) {
                        sh.unlockCanvasAndPost(canvas);
                    }
                }
                time1 = time2;
            }else{
                //lasciamolo riposare finche' non dovra' stampare il prossimo frame
                try {
                    Thread.sleep(diff);
                } catch (InterruptedException e) {	}
            }
        }
    }

}
