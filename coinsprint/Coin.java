package com.mvs.coinsprint;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

public class Coin {
	
	private boolean visibility;
	private Bitmap bitmap;
	private int x;
	private int y;
	private int altezza;
	private int larghezza;
	
	public Coin (Resources res, int x, int y, int altezza,int larghezza) {
		//cordinate dell'oggetto
		this.x=x;
		this.y=y;
		this.bitmap = getResizedBitmap(BitmapFactory.decodeResource(res, R.drawable.coin),altezza,larghezza);
		this.altezza = altezza;
		this.larghezza = larghezza;
		setVisibility(false);
	}
	
	public void setVisibility(boolean v){
		this.visibility = v;
	}
	
	public boolean getVisibility(){
		return this.visibility;
	}
	
	public void setY(int a){
		this.y = a;
	}
	
	public int getY(){
		return this.y;
	}
	
	public int getAltezza(){
		return this.altezza;
	}
	
	public void onDraw(Canvas canvas){
		if(getVisibility()){
			canvas.drawBitmap(bitmap, x-(larghezza/2), y-(altezza/2), null);
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

}
