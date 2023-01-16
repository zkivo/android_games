package com.mvs.coinsprint;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {

	private MainPanel mp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requesting to turn the title OFF
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// making it full screen
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		mp = new MainPanel(this);		
		setContentView(mp);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	 public void onPause(){
	     super.onPause();
	     System.out.println("Stoppato.");
	     mp.stopPLaying();
	 }


	 @Override
	 public void onStop() {
	  super.onStop();
	 }


	 @Override
	 public void onDestroy() {
	  super.onDestroy();
	 }
	 
	 public void onResume(){
		 super.onResume();
		 System.out.println("Resume.");
		 mp.resumeGame();
	 }

}
