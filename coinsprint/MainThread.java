package com.mvs.coinsprint;

import android.annotation.SuppressLint;
import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

@SuppressLint("WrongCall")
public class MainThread extends Thread{

	private static final String TAG = MainThread.class.getSimpleName();

	private SurfaceHolder surfaceHolder;
	private MainPanel gamePanel;
	private boolean running;

	public MainThread(SurfaceHolder surfaceHolder, MainPanel gamePanel) {
		super();
		this.surfaceHolder = surfaceHolder;
		this.gamePanel = gamePanel;
	}

	public void setRunning (boolean running) {
		this.running = running;
	}

	@Override
	public void run() {
		Canvas canvas;
		Log.d(TAG, "Starting game loop");
		long time1 = System.currentTimeMillis();
		long time2 = 0;
		long diff = 0;
		int frame = 40;
		int intervallo = 1000/frame; //mille secondi diviso i frame, sono quanti millisecondi deve avere un frame
		
		while (running) {
			canvas=null;
			time2 = System.currentTimeMillis();
			diff = time2 - time1;
			if(diff >= intervallo){
				//Proviamo a bloccare la "tela" per la modifica dei pixel sulla superficie
				try {
					canvas = this.surfaceHolder.lockCanvas();
				    synchronized (surfaceHolder) {
					    // Aggiornamento dello stato di gioco
				    	this.gamePanel.update();
				    	this.gamePanel.onDraw(canvas);
				    }
				} finally {
					// Se scatta l'eccezione la superficie non viene lasciata
			        // in uno stato incoerente
					if (canvas != null) {
						surfaceHolder.unlockCanvasAndPost(canvas);
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
