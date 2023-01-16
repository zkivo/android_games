package com.msprojs.marcoschivo.parashoot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by marcoschivo on 24/02/15.
 */
public class Atmosfera {

    /*
        le fasi della giornata sono 4 ed in quest'ordine:
            giorno,tramonto,notte,alba
     */

    private static final int MAX_ORARIO = 150; //secondi

    private Paint paint_sfondo;

    private Paint paint_luna;
    private float raggio_luna;
    private float x_luna;
    private float y_luna;
    private float spostY_luna;
    private int init_show_luna; //in quale secondo la luna si dovra' mostrare
    private boolean showluna;

    private Bitmap sfondo;

    private int orario_corrente;
    private int i_fps;

    private Context context;

    public Atmosfera(Context c){
        paint_sfondo = new Paint();
        paint_sfondo.setColor(Color.rgb(0,76,153));

        context = c;

        paint_luna = new Paint();
        paint_luna.setColor(Color.rgb(240,240,240));
        raggio_luna = MainPanel.Larghezza_Screen/100*7;
        init_show_luna = MAX_ORARIO/100*5;
        initLocationLuna();
        spostY_luna = (MainPanel.Altezza_Screen/MAX_ORARIO)/MainPanel.FPS; //in un secondo
        showluna = false;
        orario_corrente = 0;
        i_fps = 0;

        sfondo = IngranaggiBand.getResizedBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.sfondo),(int)MainPanel.Altezza_Screen,(int)MainPanel.Larghezza_Screen);


    }

    private void initLocationLuna(){
        y_luna = -raggio_luna;
        x_luna = MainPanel.Larghezza_Screen/2;
    }

    public void update(){
        if(showluna){
            y_luna+=spostY_luna;
        }else{
            if(orario_corrente >= init_show_luna){
                showluna = true;
            }else{
                if(i_fps == MainPanel.FPS){
                    orario_corrente++;
                    i_fps = 0;
                }else{
                    i_fps++;
                }
            }
        }
    }

    public void draw(Canvas canvas){
        canvas.drawPaint(paint_sfondo);
        //canvas.drawBitmap(sfondo,0,0,null);
        if(showluna){
            canvas.drawCircle(x_luna,y_luna,raggio_luna,paint_luna);
        }
    }

}
