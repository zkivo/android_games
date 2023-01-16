package com.msprojs.marcoschivo.parashoot;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.view.WindowManager;


public class MainActivity extends Activity {

    private MainPanel mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mp = new MainPanel(this);
        setContentView(mp);
    }

    @Override
    public boolean onKeyUp(final int keyCode, final KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //do whatever you need for the hardware 'back' button
            new AlertDialog.Builder(this)
                    .setTitle("Exit")
                    .setMessage("Are you sure to quit?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            backButton(keyCode,event);
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //non fa niente
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    public void backButton(final int keyCode, KeyEvent event){
        super.onKeyUp(keyCode,event);
    }

    public void onPause(){
        System.out.println("onPause()");
        super.onPause();
        mp.pause();
    }

    public void onResume(){
        System.out.println("onResume()");
        super.onResume();
        mp.resume();
    }

    public void onStart(){
        System.out.println("onStrat()");
        super.onStart();
    }

    public void onStop(){
        System.out.println("onStop()");
        super.onStop();
    }

}
