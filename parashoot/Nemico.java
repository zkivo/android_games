package com.msprojs.marcoschivo.parashoot;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by marcoschivo on 25/02/15.
 */
public class Nemico {

    protected static float SpostY_Caduta = -5;

    private float center_x,center_y;
    private float altezza,larghezza;
    private float px,py;
    private float spost_y,spost_x;
    private boolean colliso;
    private boolean exit;

    public Nemico(float alt,float larg){
        //py all'inizio e' sempre uguale al di fuori dello schermo, mentre px e' casuale
        altezza = alt;
        larghezza = larg;
        center_x = larghezza/2;
        center_y = altezza/2;
        py = -center_y;
        px = (float)(Math.random()) * MainPanel.Larghezza_Screen;
        spost_x = 0;
        spost_y = 0;
        colliso = false;
        exit = false;
    }

    public void refresh(){
        //e' come se ricreasse il nemico
        py = -center_y;
        px = (float)(Math.random()) * MainPanel.Larghezza_Screen;
        spost_x = 0;
        spost_y = 0;
        colliso = false;
        exit = false;
    }

    public void refresh(int difficolta){
        //e' come se ricreasse il nemico
        py = -center_y;
        px = (float)(Math.random()) * MainPanel.Larghezza_Screen;
        spost_x = 0;
        spost_y = 0;
        colliso = false;
        exit = false;
    }

    public boolean update(){
        //non fa niente
        return false;
    }

    public void draw(Canvas canvas){
        //non fa niente
    }

    public void collisione(){
        colliso = true;
        spost_x = 0;
        spost_y = SpostY_Caduta;
    }

    /*public boolean getExitNonPreso(){
        if(colliso == false && exit == true){
            return true;
        }else{
            return false;
        }
    }*/

    public float getPx(){
        return px;
    }

    public float getPy(){
        return py;
    }

    public boolean getColliso(){
        return colliso;
    }

    public void setPy(float b){
        py = b;
    }

    public void setPx(float b){
        px = b;
    }

    public void setColliso(boolean c){
        colliso = c;
    }

    public float getSpostY(){
        return spost_y;
    }

    public float getSpostX(){
        return spost_x;
    }

    public boolean isExit(){
        return exit;
    }

    public void setExit(boolean ex){
        exit = ex;
    }

    public void setSpostY(float a){
        spost_y = a;
    }

    public void setSpostX(float a){
        spost_x = a;
    }

    public boolean getCollissine(){
        return colliso;
    }

}
