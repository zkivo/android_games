package com.msprojs.marcoschivo.parashoot;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.MotionEvent;

/**
 * Created by marcoschivo on 19/02/15.
 */
public class GamePanel {

    protected static final float GRAVITA = 1;

    private SoundPool soundpool;
    private int soundId[];

    private float perc_larg_35;

    private RectF rect_terreno;
    private float alt_terreno;
    private float larg_terreno;

    private RectF rect_palo_sx;
    private RectF rect_palo_dx;
    private float alt_palo;
    private float larg_palo;

    private Elastico elastico;
    private float center_elastico_x;
    private float center_elatico_y;

    private Paint paint_primopiano; //il paint per tutti gli oggetti che stanno in primo piano

    private Paint paint_menu;
    private Paint paint_score;
    private float alt_menu;
    private float alt_score;
    private RectF rect_menu;
    private float x_score,y_score;
    private float larghezza_testo;
    private int n_cifre_score;
    private int score;
    private int vita;

    private Bitmap cuore_pieno;
    private Bitmap cuore_vuoto;
    private float larg_cuore;
    private float dist_cuori;
    private float y_cuori;
    private float x_cuori[];

    private Ball ball;

    //private IngranaggiBand ingranaggiband;

    private Atmosfera atmosfera;

    private ListaNemici listaNemici;

    //private Paint paint_sfondo;

    private boolean temp_init;

    private Bitmap bitmap_replay;
    private Bitmap bitmap_replay_press;
    private float replay_x,replay_y;
    private boolean replay_over;

    private boolean game_over;
    private Paint paint_game_over;
    private Bitmap bitmap_game_over;
    private float x_score_game_over;
    private float y_score_game_over;

    private boolean init_game;
    private Paint paint_tap_to_start;
    private Paint paint_parashoot;
    private float x_parashoot;
    private float y_parashoot;
    private float x_tap_to_start;
    private float y_tap_to_start;

    private int n_colpiti;
    private int temp;

    private Context context;

    public GamePanel(Context c){

        init_game = true; //sara' vero solo qui(ovvero all'inizio) e non al refresh game
        paint_parashoot = new Paint();
        paint_parashoot.setColor(Color.rgb(245,245,245));
        paint_parashoot.setTextSize(MainPanel.Altezza_Screen/10);
        paint_parashoot.setStyle(Paint.Style.FILL);
        x_parashoot = (MainPanel.Larghezza_Screen/2) - (paint_parashoot.measureText("Parashoot")/2);
        y_parashoot = MainPanel.Altezza_Screen/100*25;

        paint_tap_to_start = new Paint();
        paint_tap_to_start.setColor(Color.rgb(245,245,245));
        paint_tap_to_start.setTextSize(MainPanel.Altezza_Screen/100*5);
        paint_tap_to_start.setStyle(Paint.Style.FILL);
        x_tap_to_start = (MainPanel.Larghezza_Screen/2) - (paint_tap_to_start.measureText("tap to start")/2);
        y_tap_to_start = MainPanel.Altezza_Screen/100*25 + paint_tap_to_start.getTextSize() + MainPanel.Altezza_Screen/10;

        context = c;

        paint_primopiano = new Paint();
        paint_primopiano.setColor(Color.BLACK);

        perc_larg_35 = MainPanel.Larghezza_Screen/100*35;

        alt_terreno = MainPanel.Altezza_Screen/100*5;
        larg_terreno = MainPanel.Larghezza_Screen;
        rect_terreno = new RectF(0,MainPanel.Altezza_Screen-alt_terreno,MainPanel.Larghezza_Screen,MainPanel.Altezza_Screen);

        alt_palo = MainPanel.Altezza_Screen/100*24;
        larg_palo = MainPanel.Larghezza_Screen/100*2;

        rect_palo_sx = new RectF(perc_larg_35,MainPanel.Altezza_Screen-alt_terreno-alt_palo,perc_larg_35+larg_palo,MainPanel.Altezza_Screen-alt_terreno);
        rect_palo_dx = new RectF(MainPanel.Larghezza_Screen - perc_larg_35,MainPanel.Altezza_Screen-alt_terreno-alt_palo,MainPanel.Larghezza_Screen - perc_larg_35-larg_palo,MainPanel.Altezza_Screen-alt_terreno);

        center_elastico_x = MainPanel.percLargOf(50);
        center_elatico_y = MainPanel.Altezza_Screen-alt_terreno-(alt_palo/100*85);
        elastico =  new Elastico(perc_larg_35+(larg_palo/2),MainPanel.Altezza_Screen-alt_terreno-(alt_palo/100*85),center_elastico_x,center_elatico_y,MainPanel.Larghezza_Screen-perc_larg_35-(larg_palo/2),MainPanel.Altezza_Screen-alt_terreno-(alt_palo/100*85));
        elastico.setColor(paint_primopiano.getColor());

        ball = new Ball(center_elastico_x,center_elatico_y,MainPanel.Larghezza_Screen/100*3);
        ball.setColor(paint_primopiano.getColor());

        alt_menu = MainPanel.Altezza_Screen/100*5;
        alt_score = alt_menu/100*80;
        rect_menu = new RectF(0,0,MainPanel.Larghezza_Screen,alt_menu);
        paint_menu = new Paint();
        paint_menu.setColor(Color.argb(37,0,0,0));
        paint_score = new Paint();
        paint_score.setTextSize(alt_score);
        paint_score.setStyle(Paint.Style.FILL);
        paint_score.setColor(Color.rgb(160,160,160));
        larghezza_testo = paint_score.measureText("0");
        n_cifre_score = 1;
        x_score = MainPanel.Larghezza_Screen/2;
        y_score = alt_menu - ((alt_menu-alt_score));

        //ingranaggiband = new IngranaggiBand(context,alt_terreno,center_elatico_y);
        atmosfera = new Atmosfera(context);

        Paracadutista.setStaticAltezza(MainPanel.Altezza_Screen/100*10);
        Paracadutista.setStaticLarghezza(MainPanel.Larghezza_Screen/100*20);
        Paracadutista.setStaticBitmap(BitmapFactory.decodeResource(context.getResources(), R.drawable.paracadutista));
        Paracadutista.setStaticSpostsY(new float[]{MainPanel.Altezza_Screen / (MainPanel.FPS * 10),MainPanel.Altezza_Screen/(MainPanel.FPS*7),MainPanel.Altezza_Screen/(MainPanel.FPS*5),MainPanel.Altezza_Screen/(MainPanel.FPS*4)});
        listaNemici = new ListaNemici();

        score = 0;
        vita = 4;
        larg_cuore = MainPanel.Larghezza_Screen/100*5;
        dist_cuori = (MainPanel.Larghezza_Screen/100*2);
        cuore_pieno = IngranaggiBand.getResizedBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.cuore),(int)alt_score,(int)larg_cuore);
        cuore_vuoto = IngranaggiBand.getResizedBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.cuorevuoto),(int)alt_score,(int)larg_cuore);
        y_cuori = (alt_menu-alt_score)/2;
        x_cuori = new float[4];
        x_cuori[0] = dist_cuori;
        for(int i = 1; i < 4; i++){
            x_cuori[i] = x_cuori[i-1] + dist_cuori + larg_cuore;
        }

        temp_init = false;
        n_colpiti = 0;

        //paint_sfondo = new Paint();
        //genSfondoCasuale();

        game_over = false;
        paint_game_over = new Paint();
        paint_game_over.setColor(Color.BLACK);
        paint_game_over.setStyle(Paint.Style.FILL);
        paint_game_over.setTextSize(MainPanel.Altezza_Screen/100*15);
        bitmap_game_over = IngranaggiBand.getResizedBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.gameover),(int)MainPanel.Altezza_Screen,(int)MainPanel.Larghezza_Screen);
        paint_game_over.setColor(Color.rgb(255,255,255));
        paint_game_over.setTextSize(MainPanel.Altezza_Screen/100*20);
        paint_game_over.setStyle(Paint.Style.FILL);
        y_score_game_over = (MainPanel.Altezza_Screen/2) + (paint_game_over.getTextSize()/2);
        x_score_game_over = (MainPanel.Larghezza_Screen/2) - (paint_game_over.measureText("0")/2);

        bitmap_replay = IngranaggiBand.getResizedBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.replay),(int)MainPanel.Altezza_Screen/10,(int)MainPanel.Altezza_Screen/10);
        bitmap_replay_press = IngranaggiBand.getResizedBitmap(BitmapFactory.decodeResource(context.getResources(),R.drawable.replay_press),bitmap_replay.getHeight(),bitmap_replay.getWidth());
        replay_over = false;
        replay_x = MainPanel.Larghezza_Screen/2 - (bitmap_replay.getWidth()/2);
        replay_y = y_score_game_over + MainPanel.Altezza_Screen/10;

        soundpool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        soundId = new int[10];
        soundId[0] = soundpool.load(context, R.raw.primo, 1);
        soundId[1] = soundpool.load(context, R.raw.secondo, 1);
        soundId[2] = soundpool.load(context, R.raw.terzo, 1);
        soundId[3] = soundpool.load(context, R.raw.quarto, 1);
        soundId[4] = soundpool.load(context, R.raw.quinto, 1);
        soundId[5] = soundpool.load(context, R.raw.sesto, 1);
        soundId[6] = soundpool.load(context, R.raw.settimo, 1);
        soundId[7] = soundpool.load(context, R.raw.ottavo, 1);
        soundId[8] = soundpool.load(context, R.raw.gameover, 1);
        soundId[9] = soundpool.load(context, R.raw.heartlost, 1);
    }

   /* private void genSfondoCasuale(){
        int temp = (int)(Math.random() * n_colori_sfondo);
        paint_sfondo.setColor(Color.rgb(colori_sfondo[temp][0],colori_sfondo[temp][1],colori_sfondo[temp][2]));
    }*/

    public void touchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(!init_game) {
                    if (!game_over) {
                        if (!elastico.getTenuto()) {
                            if (x >= perc_larg_35 && x <= MainPanel.Larghezza_Screen - perc_larg_35) {
                                if (y >= MainPanel.Altezza_Screen - alt_terreno - alt_palo && y <= MainPanel.Altezza_Screen - alt_terreno - (alt_palo / 2)) {
                                    elastico.setTenuto(true);
                                    elastico.setPunto(x, y);
                                }
                            }
                        }
                    } else {
                        if (!elastico.getTenuto()) {
                            if (x >= replay_x && x <= replay_x + bitmap_replay.getWidth()) {
                                if (y >= replay_y && y <= replay_y + bitmap_replay.getHeight()) {
                                    replay_over = true;
                                }
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if(!init_game) {
                    if (!game_over) {
                        if (elastico.getTenuto()) {
                            elastico.setPunto(x, y);
                            //ingranaggiband.setPunto(x,y);
                        }
                    } else {
                        if (replay_over) {
                            if (x < replay_x || x > replay_x + bitmap_replay.getWidth()) {
                                replay_over = false;
                            }
                            if (y < replay_y || y > replay_y + bitmap_replay.getWidth()) {
                                replay_over = false;
                            }
                        }
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                if(!init_game) {
                    if (!game_over) {
                        if (elastico.getTenuto()) {
                            elastico.rilascio(x, y);
                            if (ball.getTenuto()) {
                                ball.setRilascio(true);
                            }
                        }
                    } else {
                        if (replay_over) {
                            this.restartGame();
                            replay_over = false;
                        }
                    }
                }else{
                    init_game = false;
                }
                break;
        }
    }

    public void riproduciSuonoCollisione(){
        if(n_colpiti < 8){
            soundpool.play(soundId[n_colpiti-1],0.5f,0.5f,0,0,1.0f);
        }else{
            soundpool.play(soundId[7],0.5f,0.5f,0,0,1.0f);
        }
    }

    private static int pow(int x,int y){
        int ret = 1;
        for(int i = 1; i <= y; i++){
            ret = ret * x;
        }
        return ret;
    }

    public void update(){
        if(!init_game) {
            //se si e' oltrepassata la schermata iniziale
            atmosfera.update();
            temp = GamePanel.pow(10, n_cifre_score);
            if (score >= temp) {
                larghezza_testo = paint_score.measureText(temp + "");
                n_cifre_score++;
            }
            temp = listaNemici.getMenoPunti();
            vita -= temp;
            if (!game_over) {
                if (vita <= 0) {
                    //ha perso vita morendo
                    game_over = true;
                    elastico.toCenter();
                    elastico.setTenutoBall(true);
                    ball.toCenter();
                    ball.setInaria(false);
                    soundpool.play(soundId[8], 0.5f, 0.5f, 0, 0, 1.0f);
                    x_score_game_over = (MainPanel.Larghezza_Screen / 2) - (paint_game_over.measureText(score + "") / 2);
                } else {
                    //ha perso vita senza morire
                    if (temp >= 1) {
                        soundpool.play(soundId[9], 0.5f, 0.5f, 0, 0, 1.0f);
                    }
                }
            }
            if (!game_over) {
                listaNemici.update();
            } else {
                listaNemici.updateNoCreate();
            }
            //atmosfera.update();
            elastico.update();
            ball.setTenuto(elastico.getTenutoBall());
            if (!ball.getTenuto()) {

                if (!temp_init) {
                    ball.setSpost(elastico.getSpost_x(), elastico.getSpost_y(), elastico.getNegY());
                    ball.setInaria(true);
                    temp_init = true;
                    ball.toCenter();
                    //ingranaggiband.toCenter();
                } else {
                    ball.update();
                    //temp = 0;
                    if (ball.getExit()) {
                        //la palla ritorna all'inizio perche' e' uscita dallo schermo
                        elastico.setTenutoBall(true);
                        ball.setRilascio(false);
                        temp_init = false;
                        n_colpiti = 0;
                    } else {
                        //controlla se collide con qualche nemico
                        if (!game_over) {
                            temp = listaNemici.collisione(ball.getPx(), ball.getPy(), ball.getRaggio());
                            score += temp;
                            if (n_colpiti < 8) {
                                n_colpiti += temp;
                            }
                            if (temp != 0) {
                                riproduciSuonoCollisione();
                            }
                        }
                    }
                }
            } else {
                if (ball.getRilascio()) {
                    //se la palla e' tenuta dall'elastico, ma rilasciata dal dito, puo' colpire i paracadutisti
                    if (!game_over) {
                        temp = listaNemici.collisione(ball.getPx(), ball.getPy(), ball.getRaggio());
                        score += temp;
                        if (n_colpiti < 8) {
                            n_colpiti += temp;
                        }
                        if (temp != 0) {
                            riproduciSuonoCollisione();
                        }
                    }
                }
                ball.setPunto(elastico.getPx(), elastico.getPy());
                //ingranaggiband.setPunto(elastico.getPx(),elastico.getPy());
            }
        }
    }

    public void restartGame(){
        vita = 4;
        score = 0;
        game_over = false;
        ball.setTenuto(true);
        ball.setRilascio(false);
        ball.toCenter();
        ball.setInaria(false);
        elastico.toCenter();
        elastico.setTenutoBall(true);
        elastico.setTenuto(false);
        listaNemici.restart();
        temp_init = false;
        larghezza_testo = paint_score.measureText("0");
        n_cifre_score = 1;
        replay_over = false;
        //genSfondoCasuale();
    }

    public void draw(Canvas canvas){
        if(!init_game) {
            atmosfera.draw(canvas);
            listaNemici.draw(canvas);
            //ingranaggiband.draw(canvas);
            canvas.drawRect(rect_menu, paint_menu);
            canvas.drawText(String.valueOf(score), x_score - (larghezza_testo / 2), y_score, paint_score);
            this.drawCuori(canvas);
            canvas.drawRect(rect_terreno, paint_primopiano);
            canvas.drawRect(rect_palo_sx, paint_primopiano);
            canvas.drawRect(rect_palo_dx, paint_primopiano);
            elastico.draw(canvas);
            ball.draw(canvas);
            if (game_over) {
                gameoverDraw(canvas);
            }
        }else{
            atmosfera.draw(canvas);
            canvas.drawRect(rect_terreno, paint_primopiano);
            canvas.drawRect(rect_palo_sx, paint_primopiano);
            canvas.drawRect(rect_palo_dx, paint_primopiano);
            elastico.draw(canvas);
            ball.draw(canvas);
            canvas.drawText("Parashoot",x_parashoot,y_parashoot,paint_parashoot);
            canvas.drawText("tap to start",x_tap_to_start,y_tap_to_start,paint_tap_to_start);
        }
    }

    public void gameoverDraw(Canvas canvas){
        canvas.drawColor(Color.argb(200,0,0,0));
        canvas.drawBitmap(bitmap_game_over,0,0,null);
        canvas.drawText(String.valueOf(score),x_score_game_over,y_score_game_over,paint_game_over);
        if(replay_over){
            canvas.drawBitmap(bitmap_replay_press,replay_x,replay_y,null);
        }else{
            canvas.drawBitmap(bitmap_replay,replay_x,replay_y,null);
        }
    }

    public void drawCuori(Canvas canvas){
        for(int i = 0; i < 4; i++){
            if(i <= vita-1){
                //cuore pieno
                canvas.drawBitmap(cuore_pieno,x_cuori[i],y_cuori,null);
            }else{
                //vuore vuoto
                canvas.drawBitmap(cuore_vuoto,x_cuori[i],y_cuori,null);
            }
        }
    }

    public static Bitmap RotateBitmap(Bitmap source, float angle)
    {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
    }

}
