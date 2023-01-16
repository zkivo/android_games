package com.msprojs.marcoschivo.parashoot;

/**
 * Created by marcoschivo on 25/02/15.
 */
public class Collisione {

    public static boolean collisione(float img_x_p1,float img_y_p1,float img_x_p2,float img_y_p2,float cerchio_cx,float cerchio_cy,float cerchio_raggio){
        /*
            verifica una collissione tra un quadrato ed un cerchio
            ...in effetti e' come se confrontasse due quadrati. Non effettuo quella con il cerchio, perche' richiederebbe una formula
                piu' complessa; e calcolarla ad ogni update forzerebbe la cpu
         */
        float cerchio_x_p1 = cerchio_cx-cerchio_raggio ,cerchio_y_p1 = cerchio_cy-cerchio_cy;
        float cerchio_x_p2 = cerchio_cx+cerchio_raggio,cerchio_y_p2 = cerchio_cy+cerchio_raggio;
        if(cerchio_x_p1 >= img_x_p1 && cerchio_x_p1 <= img_x_p2){
            if(cerchio_y_p1 >= img_y_p1 && cerchio_y_p1 <= img_y_p2){
                //collissione
                return true;
            }
        }else if(img_x_p1 >= cerchio_x_p1 && img_x_p1 <= cerchio_x_p2){
            if(img_y_p2 >= cerchio_y_p1 && img_y_p2 <= cerchio_y_p2){
                return true;
            }
        }
        return false;
    }

    public static boolean collisione(Paracadutista par,Ball ball){

        float cerchio_cx = ball.getPx();
        float cerchio_cy = ball.getPy();
        float cerchio_raggio = ball.getRaggio();
        float cerchio_x_p1 = cerchio_cx-cerchio_raggio ,cerchio_y_p1 = cerchio_cy-cerchio_raggio;
        float cerchio_x_p2 = cerchio_cx+cerchio_raggio,cerchio_y_p2 = cerchio_cy+cerchio_raggio;
        float img_x_p1 = par.getXPrimoPunto(),img_y_p1 = par.getYPrimoPunto();
        float img_x_p2 = par.getXSecPunto(),img_y_p2 = par.getYSecPunto();

        if(cerchio_x_p1 >= img_x_p1 && cerchio_x_p1 <= img_x_p2){
            if(cerchio_y_p1 >= img_y_p1 && cerchio_y_p1 <= img_y_p2){
                //collissione
                return true;
            }
        }else if(img_x_p1 >= cerchio_x_p1 && img_x_p1 <= cerchio_x_p2){
            if(img_y_p2 >= cerchio_y_p1 && img_y_p2 <= cerchio_y_p2){
                return true;
            }
        }
        return false;
    }

    public static boolean collisione(Paracadutista par ,float cerchio_cx,float cerchio_cy,float cerchio_raggio){
        float cerchio_x_p1 = cerchio_cx-cerchio_raggio ,cerchio_y_p1 = cerchio_cy-cerchio_raggio;
        float cerchio_x_p2 = cerchio_cx+cerchio_raggio,cerchio_y_p2 = cerchio_cy+cerchio_raggio;
        float img_x_p1 = par.getXPrimoPunto(),img_y_p1 = par.getYPrimoPunto();
        float img_x_p2 = par.getXSecPunto(),img_y_p2 = par.getYSecPunto();

        if(cerchio_x_p1 >= img_x_p1 && cerchio_x_p1 <= img_x_p2){
            if(cerchio_y_p1 >= img_y_p1 && cerchio_y_p1 <= img_y_p2){
                //collissione
                return true;
            }
        }else if(img_x_p1 >= cerchio_x_p1 && img_x_p1 <= cerchio_x_p2){
            if(img_y_p2 >= cerchio_y_p1 && img_y_p2 <= cerchio_y_p2){
                return true;
            }
        }
        return false;
    }

}
