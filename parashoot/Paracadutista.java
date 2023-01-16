package com.msprojs.marcoschivo.parashoot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;

/**
 * Created by marcoschivo on 25/02/15.
 */
public class Paracadutista extends Nemico{

    private static Bitmap btm;
    private static float Altezza,Larghezza;
    private static float Sposts_Y[];

    public Paracadutista(){
        super(Altezza,Larghezza);
        this.setSpostX(0);
        this.setSpostY(Sposts_Y[0]);
    }

    @Override
    public void refresh(){
        setPy(-(Altezza/2));
        setPx((float)(Math.random()) * MainPanel.Larghezza_Screen);
        setSpostX(0);
        setSpostY(Sposts_Y[0]);
        setColliso(false);
        setExit(false);
    }

    public void refresh(int difficolta){
        setPy(-(Altezza/2));
        setPx((float)(Math.random()) * MainPanel.Larghezza_Screen);
        setSpostX(0);
        setSpostY(Sposts_Y[(int)(Math.random() * (difficolta-1))]);
        setColliso(false);
        setExit(false);
    }

    @Override
    public boolean update(){
        /* ritorna true nel momento in cui sa che e' andato fuori lo scermo*/
        if(!isExit()) {
            if (getPy() < MainPanel.Altezza_Screen + Altezza / 2) {
                if (!getCollissine()) {
                    setPy(getPy() + getSpostY());
                    setPx(getPx() + getSpostX());
                } else {
                    setSpostY(getSpostY() + GamePanel.GRAVITA);
                    setPy(getPy() + getSpostY());
                    setPx(getPx() + getSpostX());
                }
            } else {
                //
                setExit(true);
                return true;
            }
        }
        return false;
    }

    @Override
    public void collisione(){
        //si lascia far cadere dalla forza di gravita'
        super.collisione();
    }

    @Override
    public void draw(Canvas canvas){
        if(!isExit()) {
            canvas.drawBitmap(btm, super.getPx() - (Larghezza / 2), super.getPy() - (Altezza / 2), null);
        }
    }

    public static void setStaticBitmap(Bitmap bm){
        btm = IngranaggiBand.getResizedBitmap(bm,(int)Altezza,(int)Larghezza);
    }

    public static void setStaticAltezza(float a){
        Paracadutista.Altezza = a;
    }

    public static void setStaticLarghezza(float a){
        Paracadutista.Larghezza = a;
    }

    public float getXPrimoPunto(){
        return super.getPx() - (Larghezza / 2);
    }

    public float getYPrimoPunto(){
        return super.getPy() - (Altezza / 2);
    }

    public float getXSecPunto(){
        return super.getPx() + (Larghezza / 2);
    }

    public float getYSecPunto(){
        return super.getPy() + (Altezza / 2);
    }

    public static void setStaticSpostsY(float a[]){
        Sposts_Y = new float[a.length];
        for(int i = 0; i < a.length; i++){
            Sposts_Y[i] = a[i];
        }
    }

    public static int getSpostsYLength(){
        return Sposts_Y.length;
    }

}
