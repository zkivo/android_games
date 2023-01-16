package com.mvs.coinsprint;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Box {
	
	private Bitmap bitmap;
	private int x;
	private int y;
	private int altezza;
	private int larghezza;
	private int posizione; //da 0 a 4
	
	public Box (Resources res, int x, int y, int altezza,int larghezza) {
		//cordinate dell'oggetto
		this.x=x;
		this.y=y;
		this.bitmap = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.box),altezza,larghezza);
		this.altezza = altezza;
		this.larghezza = larghezza;
		this.posizione = 2;
	}
	
	public void onDraw(Canvas canvas){
		canvas.drawBitmap(bitmap, x-(larghezza/2), y-(altezza/2),null);
	}
	
	public void goRight(){
		if(getPosizione() < 4){
			posizione++;
			setX(getX() + larghezza);
		}
	}
	
	public void goLeft(){
		if(getPosizione() > 0){
			posizione--;
			setX(getX() - larghezza);
		}
	}
	
	public void setX(int nX){
		this.x = nX;
	}
	
	public int getX(){
		return this.x;
	}
	
	public int getPosizione(){
		return this.posizione;
	}
	
	public void setBitmap(Bitmap b){
		this.bitmap = b;
	}
	
	public Bitmap getBitmap(){
		return this.bitmap;
	}
	
	public void setPosizione(int p){
		if(p >= 0 && p <= 5){
			//ha un determinato range 
			int gp = this.getPosizione();
			if(gp > p){
				while(gp != p){
					setX(getX() - larghezza);
				}
			}else if(gp < p){
				while(gp != p){
					setX(getX() + larghezza);
				}
			}
			System.out.println("getx " + getX());
			this.posizione = p;
		}
	}
	
	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}
	
	public Bitmap getRotazioneBitmap(Bitmap bm, int rotazione){
	    int width = bm.getWidth();
	    int height = bm.getHeight();
		
	    Matrix matrix = new Matrix();
	    matrix.setRotate(rotazione);
		
	    return Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	}
	
}
