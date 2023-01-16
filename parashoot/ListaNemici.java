package com.msprojs.marcoschivo.parashoot;

import android.graphics.Canvas;

/**
 * Created by marcoschivo on 25/02/15.
 */
public class ListaNemici {

    private static final int MAX_NEMICI = 50;
    private static final int INIT_MAX_SECONDI = 10;
    private int max_secondi;
    private Nemico nemici[];
    private int secondi, count;
    private boolean init_casualita;
    private int difficolta;
    private int meno_punti; //si rigenera ad ogni update, ed equivale al numero di paracadutisti usciti non presi

    public ListaNemici(){
        nemici = new Nemico[MAX_NEMICI];
        secondi = 0;
        count = 0;
        max_secondi = INIT_MAX_SECONDI;
        init_casualita = true;
        difficolta = 0;
        meno_punti = 0;
    }

    public boolean nuovoNemico(){
        for(int i = 0; i < MAX_NEMICI; i++){
            if(nemici[i] == null){
                //crea il nuovo nemico
                nemici[i] = new Paracadutista();
                return true;
            }else{
                if(nemici[i].isExit()){
                    //ripristina questo nemico
                    nemici[i].refresh();
                    return true;
                }
            }
        }
        return false;
    }

    public boolean nuovoNemico(int difficolta){
        for(int i = 0; i < MAX_NEMICI; i++){
            if(nemici[i] == null){
                //crea il nuovo nemico
                nemici[i] = new Paracadutista();
                return true;
            }else{
                if(nemici[i].isExit()){
                    //ripristina questo nemico
                    nemici[i].refresh(difficolta);
                    return true;
                }
            }
        }
        return false;
    }

    public int collisione(float cx,float cy,float raggio){
        int ret = 0;
        for(int i = 0; i < MAX_NEMICI; i++){
            if(nemici[i] == null){
                break;
            }else{
                if(!nemici[i].isExit()) {
                    if(!nemici[i].getColliso()) {
                        if (Collisione.collisione((Paracadutista) nemici[i], cx, cy, raggio)) {
                            nemici[i].collisione();
                            ret++;
                        }
                    }
                }
            }
        }
        return ret;
    }

    public void update(){
        //creazione nuovi nemici
        if(count == MainPanel.FPS) {
            if(secondi < max_secondi){
                if(init_casualita) {
                    if ((int) (Math.random() * 10) <= 7) {
                        //nuovo nemico
                        this.nuovoNemico();
                    }
                }else{
                    for(int i = 0; i < difficolta; i++) {
                        this.nuovoNemico(difficolta);
                    }
                    this.nuovoNemico(difficolta);
                }
                secondi++;
            }else{
                System.out.println(max_secondi + "secondi passati");
                max_secondi+=5;
                secondi = 0;
                init_casualita = false;
                if(difficolta < Paracadutista.getSpostsYLength()){
                    difficolta++;
                }
            }
            count = 0;
        }else{
            count++;
        }
        //aggiorna nemici esistenti
        int sum = 0;
        for(int i = 0; i < MAX_NEMICI; i++){
            if(nemici[i] == null){
                break;
            }else{
                if(nemici[i].update()){
                    //preciso momento quando e' fuori lo scermo
                    if(!nemici[i].getCollissine()){
                        //in questo caso non e' colliso nel preciso momento quando e' fuori
                        sum++;
                    }
                }
            }
        }
        meno_punti = sum;
    }

    public void updateNoCreate(){
        for(int i = 0; i < MAX_NEMICI; i++){
            if(nemici[i] == null){
                break;
            }else{
                nemici[i].update();
            }
        }
    }

    public void restart(){
        secondi = 0;
        count = 0;
        max_secondi = INIT_MAX_SECONDI;
        init_casualita = true;
        difficolta = 0;
        meno_punti = 0;
        for(int i = 0; i < MAX_NEMICI; i++){
            if(nemici[i] == null){
                break;
            }else{
                nemici[i] = null;
            }
        }
    }

    public int getMenoPunti(){
        return meno_punti;
    }

    public void draw(Canvas canvas){
        for(int i = 0; i < MAX_NEMICI; i++){
            if(nemici[i] == null){
                break;
            }else{
                nemici[i].draw(canvas);
            }
        }
    }

}
