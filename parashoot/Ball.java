package com.msprojs.marcoschivo.parashoot;

import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by marcoschivo on 22/02/15.
 */
public class Ball {

    private float center_x,center_y;
    private float px,py;
    private float spost_x,spost_y;
    private float raggio;
    private Paint paint;
    boolean tenuto;
    boolean inaria;
    boolean neg_y;
    boolean exit;
    boolean rilascio = false;

    public Ball(float x,float y,float rad){
        center_x = x;
        center_y = y;
        px = x;
        py = y;
        raggio = rad;
        paint = new Paint();
        tenuto = true;
        inaria = false;
        neg_y = false;
        rilascio = false;
    }

    public void setColor(int color){
        paint.setColor(color);
    }

    public void toCenter(){
        px = center_x;
        py = center_y;
    }

    public void setRilascio(boolean a){
        rilascio = a;
    }

    public boolean getRilascio(){
        return rilascio;
    }

    public void setInaria(boolean a){
        inaria = a;
        if(a){
            exit = false;
        }
    }

    public boolean getInaria(){
        return inaria;
    }

    public void setPunto(float x,float y){
        px = x;
        py = y;
    }

    public boolean getExit(){
        return exit;
    }

    public void setSpost(float x, float y,boolean neg){
        spost_x = x;
        spost_y = y;
        neg_y = neg;
    }

    public void setTenuto(boolean a){
        tenuto = a;
    }

    public boolean getTenuto(){
        return tenuto;
    }

    public void reset(){
        tenuto = true;
        inaria = false;
        exit = true;
    }

    public void update(){
        if(inaria){
            if(px+raggio < 0 || px-raggio > MainPanel.Larghezza_Screen || py+raggio < 0 || py-raggio > MainPanel.Altezza_Screen){
                this.reset();
            }else{
                px += spost_x;
                if(neg_y){
                    py -= spost_y;
                    spost_y-=GamePanel.GRAVITA;
                }else{
                    py += spost_y;
                    spost_y+=GamePanel.GRAVITA;
                }
            }
        }
    }

    public void draw(Canvas canvas){
        canvas.drawCircle(px,py,raggio,paint);
    }

    public float getPx(){
        return px;
    }

    public float getPy(){
        return py;
    }

    public float getRaggio(){
        return raggio;
    }

}
