package com.mvs.coinsprint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class MainPanel extends SurfaceView implements Callback{
	
	private MainThread thread;
	private Box box;
	private int score;
	private int altezza;
	private int larghezza;
	private int altezzaBar;
	private int altezzaTerreno;
	private int pointCenterX;
	private int pointCenterY;
	private int altezzaObj;  //altezza di un oggetto
	private int larghezzaObj;  //larghezza di un oggetto
	private Coin coin[][];
	private Coin coin_finali[];
	private int righe;
	private int colonne;
	private Bitmap terreno;
	private Bitmap initImage;
	private long time;
	private boolean gameover;
	private boolean initGame;
	private long giri;
	private Bitmap gameoverImage;
	private Bitmap bestPointImage;
	private Bitmap barimage;
	private Bitmap sfondoScuro;
	private int altezza_2pc;
	private int altezza_4pc;
	private int altezza_5pc;
	private int altezza_15pc;
	private int larghezza_2pc;
	private int larghezza_35pc;
	private int larghezza_70pc;
	private int n_initimage;
	private Bitmap number[];
	private Bitmap redo;
	//private int bestPoint;
	
	
	@SuppressWarnings("deprecation")
	public MainPanel(Context context){
		super(context);
		
		  //Aggiungiamo callback(this) alla superficie per intercettare gli eventi
		  getHolder().addCallback(this);
		  
		  // Creiamo il thread per il ciclo di gioco
		  thread = new MainThread(getHolder(), this);		  
		  
		  time = System.currentTimeMillis();
		  
		  n_initimage = 0;
		  righe = 7;
		  colonne = 5;
		  coin = new Coin[righe-1][colonne];	
		  coin_finali = new Coin[colonne];
		  number = new Bitmap[10];
		  score = 0;
		  gameover = false;
		  giri = 0;
		  initGame = true;
		  
		  
	    setFocusable(true);
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		// TODO Auto-generated method stub
		
	}
	
	

	@Override
	public void surfaceCreated(SurfaceHolder arg0) {
		// TODO Auto-generated method stub
		
		if(thread.getState() == Thread.State.NEW){
			
			/*
			 * Questo if serve a controllare se il thread principale e' nuovo o meno
			 * ,cioe' se il gioco e' stato appena startato. Se si crea sistema tutte le variabili, altrimenti non fa niente.
			 * Questo perche' questo metodo viene richiamato quando per esempio dalla home, si riapre il gioco.
			 * Per non far si che tutte le variabili non perdano il loro valore si fa questa condizione.
			 * Anche perche' il thread e' gia' attivo grazie al metodo onResume, e potrebbe generare eccezioni idesiderate.
			 */
			
			thread = new MainThread(getHolder(),this); 
		    
			altezza = this.getHeight();
			larghezza = this.getWidth();
			
			altezza_5pc = (int)((altezza/100) * 5);
			altezza_2pc = (int)((altezza/100) * 2);
			altezza_4pc = (int)((altezza/100) * 4);
			altezza_15pc = (int)((altezza/100) * 15);
			larghezza_2pc = (int)((larghezza/100) * 2);
			larghezza_70pc = (int)(larghezza/100*70);
			larghezza_35pc = (int)(larghezza/100*35);
			
			 //crea l'altezza dello status bar
			altezzaBar = (int)((altezza/100) * 5);
			altezzaTerreno = (int)((altezza/100) * 5);
			altezza = altezza-(altezzaBar+altezzaTerreno);
			System.out.println("altezzabar: " + altezzaBar);
			System.out.println("altezza: " + altezza);
			//calcolo i punti del centro della view
			pointCenterX = (int)(larghezza / 2);
			pointCenterY = (int)(altezza / 2);
			
			altezzaObj = (int)altezza/righe;
			larghezzaObj = (int)larghezza/colonne;
			
			terreno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.terreno),altezzaTerreno,larghezza);
			initImage = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.initimagesx),this.getHeight(),this.getWidth());
			sfondoScuro = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.sfondoscuro),this.getHeight(),this.getWidth());
			
			box = new Box(this.getResources(),pointCenterX,altezza-(altezzaObj/2)+altezzaBar,altezzaObj,larghezzaObj);
			
			//immagini gameover
			gameoverImage = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.gameover),altezza_5pc,larghezza_70pc);
			bestPointImage = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.score),altezza_5pc,larghezza_35pc);
			redo = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.redo),altezza_15pc,altezza_15pc);
			barimage = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(), R.drawable.sfondoscuro),getAltezzaBar(),getLarghezza());
			
			//crea tutti i coin con visibilita' a false
			int i = 0, j = 0;
			int appx = 0;
			int appy = altezzaObj/2 + altezzaBar;
			
			for(i = 0; i < righe-1; i++){
				appx = larghezzaObj/2;
				for(j = 0; j < colonne; j++){
					coin[i][j] = new Coin(this.getResources(),appx,appy,altezzaObj,larghezzaObj);
					appx+=larghezzaObj;
				}
				appy+=altezzaObj;
			}
			
			//azzera vettore coin_finali
			appx = larghezzaObj/2;
			for(i = 0; i < colonne; i++){
				coin_finali[i] = new Coin(this.getResources(),appx,appy,altezzaObj,larghezzaObj);
				appx+=larghezzaObj;
				
			}
			
			//inizializzo le bitmap delle immagini
			number[0] = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.zero),altezza_4pc,larghezza_2pc);
			number[1] = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.uno),altezza_4pc,larghezza_2pc);
			number[2] = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.due),altezza_4pc,larghezza_2pc);
			number[3] = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.tre),altezza_4pc,larghezza_2pc);
			number[4] = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.quattro),altezza_4pc,larghezza_2pc);
			number[5] = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.cinque),altezza_4pc,larghezza_2pc);
			number[6] = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.sei),altezza_4pc,larghezza_2pc);
			number[7] = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.sette),altezza_4pc,larghezza_2pc);
			number[8] = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.otto),altezza_4pc,larghezza_2pc);
			number[9] = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.nove),altezza_4pc,larghezza_2pc);
			
			
			thread.setRunning(true);
		    thread.start();
		}
		
	}
	
	public int getAltezza(){
		return this.altezza;
	}
	
	public int getAltezzaBar(){
		return this.altezzaBar;
	}
	
	public int getLarghezza(){
		return this.larghezza;
	}
	
	 public boolean onTouchEvent(MotionEvent event) {
		 if(initGame){
			 if (event.getAction() == MotionEvent.ACTION_DOWN){
				 initGame = false;
				 time = System.currentTimeMillis();
			 }
		 }else if(gameover){
			 if (event.getAction() == MotionEvent.ACTION_DOWN){
				 if(event.getX() >= pointCenterX - altezza_15pc/2 && event.getX() <= pointCenterX + altezza_15pc/2){
					 if(event.getY() >= pointCenterY + altezza_2pc + altezza_5pc *2 && event.getY() <= pointCenterY + altezza_2pc + altezza_5pc *2 + altezza_15pc){
						//qui verranno riazzerate tutte le variabili, e quindi per poter rigiocare
						 gameover = false;
						 initGame = false; // non si sa mai
						 score = 0;
						 giri = 0;
						 time = System.currentTimeMillis();
						 for(int i = 0; i < righe-1; i++){
							 for(int j = 0; j < colonne; j++){
								 coin[i][j].setVisibility(false);
							 }
						 }
						 for(int i = 0; i < colonne; i++){
							 coin_finali[i].setVisibility(false);
						 }
						 box = new Box(this.getResources(),pointCenterX,altezza-(altezzaObj/2)+altezzaBar,altezzaObj,larghezzaObj);
					 }
			     }
			 }
		 }else{
			 if (event.getAction() == MotionEvent.ACTION_DOWN){
				    if (event.getX() >= pointCenterX){
				    	//ha toccato a destra
				    	box.goRight();
				    }else if(event.getX() < pointCenterX){
				    	//ha toccato a sinistra
				    	box.goLeft();
				    }
				    System.out.println("Coords: x=" + event.getX() + ",y=" + event.getY());
				 } 
		 }
		 return true;
	} 
	 
	public void update(){
		
		if(initGame){
			
			long time2 = System.currentTimeMillis();
			
			if(time2 - time >= 500){
				time = time2;
				if(n_initimage == 0){
					//image sx
					initImage = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.initimagedx),this.getHeight(),this.getWidth());
					n_initimage = 1;
				}else if(n_initimage == 1){
					//image dx
					initImage = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.initimagesx ),this.getHeight(),this.getWidth());
					n_initimage = 0;
				}
			}
			
		}else if(gameover){
			
		}else{
			long casi_tempo = 1000;
			int casi_exist = 1;

			casi_tempo -= giri * 2;

			// if(giri >= 10){
			// 	casi_tempo-=50;
			// 	if(giri >= 20){
			// 		casi_tempo-=50;
			// 		if(giri >= 25){
			// 			casi_tempo-=50;
			// 			if(giri >= 30){
			// 				casi_tempo-=50;
			// 				if(giri >= 35){
			// 					casi_tempo-=25;
			// 					if(giri >= 40){
			// 						casi_tempo-=20;
			// 						if(giri >= 50){
			// 							casi_tempo-=40;
			// 							if(giri >= 60){
			// 								casi_tempo-=20;
			// 								if(giri>= 70){
			// 									casi_tempo-=20;
			// 									if(giri>= 80){
			// 										casi_tempo-=20;
			// 										if(giri>= 100){
			// 											casi_tempo-=20;
			// 											if(giri>=120){
			// 												casi_tempo-=20;
			// 												if(giri>=130){
			// 													casi_tempo-=20;
			// 													if(giri>=140){
			// 														casi_tempo-=20;
			// 														if(giri>=150){
			// 															casi_tempo-=20;
			// 															if(giri>=160){
			// 																casi_tempo-=20;
			// 																if(giri>=160){
			// 																	casi_tempo-=20;
			// 																	if(giri>=170){
			// 																		casi_tempo-=20;
			// 																		if(giri>=180){
			// 																			casi_tempo-=20;
			// 																			if(giri>=200){
			// 																				casi_tempo-=20;
			// 																				if(giri >= 210){
			// 																					casi_tempo-=10;
			// 																					if(giri >= 220){
			// 																						casi_tempo-=10;
			// 																						if(giri >= 230){
			// 																							casi_tempo-=10;
			// 																							if(giri>=240){
			// 																								casi_tempo-=10;
			// 																								if(giri>=250){
			// 																									casi_tempo-=10;
			// 																									if(giri>=260){
			// 																										casi_tempo-=10;
			// 																										if(giri>=270){
			// 																											casi_tempo-=10;
			// 																											if(giri>=280){
			// 																												casi_tempo-=10;
			// 																												if(giri>=290){
			// 																													casi_tempo-=10;
			// 																													if(giri>=300){
			// 																														casi_tempo-=10;
			// 																													}
			// 																												}
			// 																											}
			// 																										}
			// 																									}
			// 																								}
			// 																							}
			// 																						}
			// 																					}
			// 																				}
			// 																			}
			// 																		}
			// 																	}
			// 																}
			// 															}
			// 														}
			// 													}
			// 												}
			// 											}
			// 										}
			// 									}
			// 								}
			// 							}
			// 						}
			// 					}
			// 				}
			// 			}
			// 		}
			// 	}
			// }
			
			
			
			long time2 = System.currentTimeMillis();
			long diff = time2 - time;
			
			if(diff >= casi_tempo){ 
				
				System.out.println("giri: " + giri);
				giri++;
				time = time2;
				
				int i = 0, j = 0;
				boolean game_o = false;
				boolean coinapp[][] = new boolean[righe-1][colonne];
				
				//sposta tutti i coin alla righa successiva
				
				for(i=0;i<righe-1;i++){
					for(j=0;j<colonne;j++){
						coinapp[i][j] = false;
					}
				}
				
				for(i = 0; i < righe-1; i++){
					if(i < righe-2){
						for(j = 0; j < colonne;j++){
							if(coin[i][j].getVisibility()){
								coinapp[i+1][j] = true;
								break;
							}
						}
					}else{
						for(j = 0; j < colonne;j++){
							if(coin[i][j].getVisibility()){
								if(box.getPosizione() == j){
									//un punto in piu'
									score++;
									break;
								}else{
									//hai perso
									game_o = true;
									break;
								}
							}
						}
					}
				}
				
				if(game_o == false){
					/* se non e' in game over, aggiungere un nuovo coin a random nel coinapp[][]
					 * -alla fine copiare il vettore alle visibility dei coin
				     */
					
					//33.3%, di spownare un coin
					int caso_exist = (int)(Math.random() * casi_exist);
					if(caso_exist == 0){
						//le possibilita' sono quante sono le colonne
						int caso_pos = (int)(Math.random() * colonne);
						coinapp[0][caso_pos] = true;
					}
					
					//System.out.println("caso_exist: " + caso_exist);
					//copia vettore
					for(i = 0; i < righe-1;i++){
						for(j=0;j<colonne;j++){
							if(coinapp[i][j]){
								coin[i][j].setVisibility(true);
							}else{
								coin[i][j].setVisibility(false);
							}
						}
					}
					
				}else{
					/*e' in gameover e chiama l'apposito metodo
					 * e sposta tutti i coin alla riga successiva, copiando il vettore e all'ultimo coin cambiamo le y*/
					for(i = 0; i < righe-1;i++){
						if(i < righe-2){
							for(j=0;j<colonne;j++){
								if(coinapp[i][j]){
									coin[i][j].setVisibility(true);
								}else{
									coin[i][j].setVisibility(false);
								}														
							}
						}else{
							for(j=0;j<colonne;j++){
								if(coin[i][j].getVisibility()){
									coin_finali[j].setVisibility(true);
								}
								
								if(coinapp[i][j]){
									coin[i][j].setVisibility(true);
								}else{
									coin[i][j].setVisibility(false);
								}		
							}
						}
					}
					gameOver();
				}
			}
		}
	}
	
	public void gameOver(){
		/*da adesso il gioco e' in gameover
		 * -Non deve terminare il thread
		 * -Stampa la scermata del gameover
		 * -Quando toccera' l'utente di nuovo lo scermo, il gioco ripartira'.(Questo viene gestito dal metodo onTouchEvent())
		 * 
		 */ 
		gameover = true;
		//oltre a settare questo valore a true, si fara' il controllo dei punti.
		/*
		 * se point e' maggiore di bestpoint, si sovrascrivera' nel file il punteggio,
		 */
	}
	
	public void onDraw(Canvas canvas){
			if(initGame){
				canvas.drawColor(Color.rgb(102, 204, 252));
				canvas.drawBitmap(terreno, 0, altezza+altezzaBar, null);
				box.onDraw(canvas);
				canvas.drawBitmap(initImage, 0, 0,null);
			}else if(gameover){
				//stampa la schermata di gioco piu' la schermata di gameover
				
				//stamp game
				canvas.drawColor(Color.rgb(102, 204, 252));
				canvas.drawBitmap(barimage,0,0,null);
				canvas.drawBitmap(terreno, 0, altezza+altezzaBar, null);
				box.onDraw(canvas);
				for(int i = 0; i < righe-1; i++){
					for(int j = 0; j < colonne; j++){
						coin[i][j].onDraw(canvas);
					}
				}
				for(int j = 0; j < colonne; j++){
					coin_finali[j].onDraw(canvas);
				}
				//stampgameover now
				//codici...
				
				canvas.drawBitmap(sfondoScuro, 0, 0,null);
				canvas.drawBitmap(gameoverImage, pointCenterX - (larghezza_70pc/2),pointCenterY - altezza_2pc - altezza_5pc,null);
				canvas.drawBitmap(bestPointImage, pointCenterX - larghezza_35pc, pointCenterY + altezza_2pc,null);
				displayScore(canvas,score,pointCenterX + (larghezza_35pc/100*33),pointCenterY + altezza_2pc + (altezza_5pc/2),altezza_5pc,larghezza_2pc,larghezza_2pc);
				canvas.drawBitmap(redo, pointCenterX - altezza_15pc/2,pointCenterY + altezza_2pc + altezza_5pc *2, null);
				
			}else{
				//se non e' nella pagina iniziale e se non e' al gameover
				canvas.drawColor(Color.rgb(102, 204, 252));
				canvas.drawBitmap(barimage,0,0,null);
				canvas.drawBitmap(terreno, 0, altezza+altezzaBar, null);
				box.onDraw(canvas);
				for(int i = 0; i < righe-1; i++){
					for(int j = 0; j < colonne; j++){
						coin[i][j].onDraw(canvas);
					}
				}
				displayScore(canvas,score,pointCenterX,altezzaBar/2,altezza_4pc,larghezza_2pc,larghezza_2pc);
			}
	}
	
	public void displayScore(Canvas canvas,int punti, int x, int y,int altezza,int larghezza,int distanza){
			int length_p = 1;
			int alt_n = altezza;
			int larg_n = larghezza;
			int dist = distanza;
			int dist_2 = dist / 2;
			int cp = punti;
			int puntoy = y - (alt_n / 2);
			
			int i = 0,cont = 0;
			if(punti > 0){
				//controlla di quanti caratteri e' formato il numero
				while(true){
					int div = cp/10;
					if(div > 0){
						length_p++;
						cp = cp/10;
					}else{
						break;
					}
				}
				
				//per sapere come deve essere disposto dato un punto di riferimento
				boolean pari = false;
				if(length_p%2 == 0){
					pari = true;
				}
				
				if(pari){
					//al centro x e y ci sara' un punto
					int leng_sx = length_p / 2;
					// int leng_dx = leng_sx;  e' pari la lunghezza del numero
					int sum = 0;
					int punto_primo_num = 0;
					int puntox = 0;
					//int puntoy = 0;
					
					sum = dist_2 + (leng_sx * larg_n) + (dist * leng_sx - 1);
					punto_primo_num = x - (sum - (larg_n / 2));
										
					puntox = punto_primo_num;
					i = (int) Math.pow(10, length_p);
					i/=10;
					int app_p = punti;
					while(cont < length_p){
						canvas.drawBitmap(getNumberImage((int)(app_p / i),alt_n,larg_n), puntox - (larg_n / 2),puntoy,null);
						puntox+=larg_n + dist;
						app_p-=(int)(app_p/i)*i;
						i/=10;
						cont++;
					}
					
				}else{
					//al centro x e y ci sara un numero
					
					if(length_p > 1){
						//c'e' piu' di una cifra
						int length_m1 = length_p -1;
						int leng_sx = length_m1 / 2; //numero di cifre a sinistra scartando il centro
						int sum = 0;
						int punto_primo_num = 0;
						int puntox = 0;
						
						sum = (larg_n / 2) + (dist * length_m1 -1) + (leng_sx * larg_n);
						punto_primo_num = x - (sum - (larg_n / 2));
						
						puntox = punto_primo_num;
						i = (int) Math.pow(10, length_p);
						i/=10;
						int app_p = punti;
						while(cont < length_p){
							canvas.drawBitmap(getNumberImage((int)(app_p / i),alt_n,larg_n), puntox - (larg_n / 2),puntoy,null);
							puntox+=larg_n + dist;
							app_p-=(int)(app_p/i)*i;
							i/=10;
							cont++;
						}
						
					}else{
						canvas.drawBitmap(getNumberImage((int)(punti),alt_n,larg_n), x - (larg_n /2),puntoy,null);
					}
					
				}
				
			}else{
				canvas.drawBitmap(getNumberImage((int)(punti),alt_n,larg_n), x - (larg_n /2),puntoy,null);
			}
	}
	
	public void stopPLaying(){
		thread.setRunning(false);
	}
	
	public void resumeGame(){
		if(thread.getState() == Thread.State.TERMINATED){
			thread = new MainThread(getHolder(),this); 
		      thread.setRunning(true);
		      thread.start();
		}
	}
	
	public Bitmap getNumberImage(int p,int alt,int larg){
		
		Bitmap ritorno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.zero),alt,larg);
		
		if(p == 0){
			ritorno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.zero),alt,larg);
		}else if(p == 1){
			ritorno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.uno),alt,larg);
		}else if(p == 2){
			ritorno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.due),alt,larg);
		}else if(p == 3){
			ritorno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.tre),alt,larg);
		}else if(p == 4){
			ritorno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.quattro),alt,larg);
		}else if(p == 5){
			ritorno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.cinque),alt,larg);
		}else if(p == 6){
			ritorno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.sei),alt,larg);
		}else if(p == 7){
			ritorno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.sette),alt,larg);
		}else if(p == 8){
			ritorno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.otto),alt,larg);
		}else if(p == 9){
			ritorno = getResizedBitmap(BitmapFactory.decodeResource(this.getResources(),R.drawable.nove),alt,larg);
		}
		
		return ritorno;
	}
	
	@Override
	public void surfaceDestroyed(SurfaceHolder arg0) {
		
		
		
	}

	public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;
	    // CREATE A MATRIX FOR THE MANIPULATION
	    Matrix matrix = new Matrix();
	    // RESIZE THE BIT MAP
	    matrix.postScale(scaleWidth, scaleHeight);

	    // "RECREATE" THE NEW BITMAP
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);
	    return resizedBitmap;
	}

}
