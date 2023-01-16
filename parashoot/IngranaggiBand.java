package com.msprojs.marcoschivo.parashoot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by marcoschivo on 23/02/15.
 */
public class IngranaggiBand {

    private static final int N_IMAGE = 25;

    private Bitmap btm_ing[];
    private Bitmap btm_ing_source;
    private Context context;
    private float center_x;
    private float center_y;
    private float py,px;
    private float alt_terreno;
    private float lato;
    private float left_dx,top_dx; //coordinate bitmap dx
    private float center_elastico_y;

    public IngranaggiBand(Context cont,float alt_terr,float cent_ele_y){
        context = cont;
        btm_ing = new Bitmap[IngranaggiBand.N_IMAGE];
        center_x = MainPanel.Larghezza_Screen/2;
        center_y = MainPanel.Altezza_Screen/2;
        center_elastico_y = cent_ele_y;
        py = cent_ele_y;
        px = center_x;
        alt_terreno = alt_terr;

        lato = MainPanel.Larghezza_Screen/100*30;
        left_dx = MainPanel.Larghezza_Screen-(lato/2);
        top_dx = MainPanel.Altezza_Screen-alt_terreno-(lato/2);


        //inizializziamo ogni bitmap

        //btm_ing_source = getResizedBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.ingranaggio_band),(int)lato,(int)lato);
        btm_ing[(IngranaggiBand.N_IMAGE/2)-1] = btm_ing_source;
        for(int i = (IngranaggiBand.N_IMAGE/2),j = 1; i < IngranaggiBand.N_IMAGE; i++,j+=1){
            btm_ing[i] = getResizedBitmap(IngranaggiBand.RotateBitmap(btm_ing_source,j),(int)lato,(int)lato);
        }

        for(int i = (IngranaggiBand.N_IMAGE/2)-1,j = -1; i >= 0; i--,j-=1){
            btm_ing[i] = getResizedBitmap(IngranaggiBand.RotateBitmap(btm_ing_source,j),(int)lato,(int)lato);
        }

    }


    public void toCenter(){
        py = center_elastico_y;
        px = center_x;
    }

    public void setPunto(float x,float y){
        px = x;
        py = y;
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(btm_ing[(int)(px*((IngranaggiBand.N_IMAGE/2)-1)/center_x)],MainPanel.Larghezza_Screen-(lato/2),MainPanel.Altezza_Screen-alt_terreno-(lato/2),null); //ingranaggio di destra
        canvas.drawBitmap(btm_ing[(int)(py*((IngranaggiBand.N_IMAGE/2)-1)/center_y)],0-(lato/2),MainPanel.Altezza_Screen-alt_terreno-(lato/2),null);
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

    public static Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
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
