package com.msprojs.marcoschivo.parashoot;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by marcoschivo on 19/02/15.
 */
public class Elastico {

    //private final float Accellerazione = (float) 0;

    private Paint paint;
    private float center_x,center_y;
    private float sx_x,sx_y;
    private float dx_x,dx_y;
    private float px, py;
    private boolean tenuto;
    private boolean tensione;
    private float dist_y_to_center,dist_x_to_center;
    private float spost_y,spost_x;
    private float spost_rif_y; /*questa e' la variabile di riferimento per la velocita' dell'elastico, e quindi anche della palla
        . Viene presa da riferimento, perche' si faranno tutte le proporzioni con essa.*/
    private float dist_rif_y;
    private boolean neg_y;
    private boolean tenuto_ball;

    public Elastico(float s_x,float s_y,float cent_x,float cent_y,float d_x,float d_y){
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(10);
        sx_x = s_x;
        sx_y = s_y;
        center_x = cent_x;
        center_y = cent_y;
        dx_x = d_x;
        dx_y = d_y;
        px = cent_x;
        py = cent_y;
        tenuto = false;
        spost_rif_y = (MainPanel.Altezza_Screen*(float)2.5)/MainPanel.FPS; /*questa indica il pezzetto di y che verra' sommato o sottrato
            alla variabile punto dell'elastico per arrivare alla center_y.
           --- Per cambiare velocita' bisogna modificare questa variabile --- */
        dist_rif_y = MainPanel.Altezza_Screen-center_y; /*indica che la variabile sopra verra' effettuata quando ci sara' questa distanza
            ,le due variabili serviranno per le proporzioni future*/
        neg_y = false;
        tenuto_ball = true;
    }

    public void setWidth(float larghe){
        paint.setStrokeWidth(larghe);
    }

    public void setColor(int color){
        paint.setColor(color);
    }

    public void toCenter(){
        px = center_x;
        py = center_y;
    }

    public void update(){
        if((tensione == true) && (tenuto == false)){
            if(py < center_y - spost_y || py > center_y + spost_y){
                //spost_y+= Accellerazione;
                px += spost_x;
                if(neg_y){
                    py -= spost_y;
                }else{
                    py += spost_y;
                }
            }else{
                toCenter();
                tensione = false;
                tenuto_ball = false;
            }
        }
    }

    public boolean getTenutoBall(){
        return tenuto_ball;
    }

    public void setTenutoBall(boolean a){
        tenuto_ball = a;
    }

    public float getSpost_x(){
        return spost_x;
    }

    public float getPx(){
        return px;
    }

    public float getPy(){
        return py;
    }

    public float getSpost_y(){
        return spost_y;
    }

    public boolean getNegY(){
        return neg_y;
    }

    public void rilascio(float x,float y){
        /* qua si calcola di quanto si dovra' spostare per le x e le y ad ogni frame l'elastico per arrivare alla posizione centrale*/
        tenuto = false;
        tensione = true;
        dist_y_to_center = 0;
        dist_x_to_center = 0;
        spost_y = 0;
        spost_x = 0;
        float n_frame = 0;
        neg_y = false;
        boolean neg_x = false;

        if(y < center_y){
            dist_y_to_center = center_y - y;
        }else if(y > center_y){
            dist_y_to_center = y - center_y;
            neg_y = true;
        }
        spost_y = (spost_rif_y*dist_y_to_center)/dist_rif_y;
        n_frame = dist_y_to_center/spost_y;

        if(x > center_x){
            dist_x_to_center = x - center_x;
            neg_x = true;
        }else if(x < center_x){
            dist_x_to_center = center_x - x;
        }

        spost_x = dist_x_to_center/n_frame;
        if(neg_x){
            spost_x*=-1;
        }

    }

    public void setPunto(float x, float y){
        px = x;
        py = y;
    }

    public void setTenuto(boolean a){
        tenuto = a;
    }

    public boolean getTenuto(){
        return tenuto;
    }

    public void draw(Canvas canvas){
        canvas.drawLine(sx_x,sx_y,px,py,paint);
        canvas.drawLine(dx_x,dx_y,px,py,paint);
    }

}
