package com.orr.uppapon;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.gesture.GestureOverlayView;
import android.gesture.GestureOverlayView.OnGestureListener;
import android.graphics.Color;
import android.graphics.ComposePathEffect;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class SecondActivity extends Activity implements OnGestureListener, android.view.GestureDetector.OnGestureListener {
	 
	View mDecorView = null;
	MainActivityView menuView;
	TextView inputtextview;
	TextView gametextview;
	private ImageView image;
	GestureDetector detector;
	int width, height;
	
	boolean Bpause =false;
	float lostX =0;
	float lostY= -1000;
	
	float Pr=125;
	
	float [] x = new float [16];
	float [] y = new float [16];
	float [] newx = new float [16];
	float [] newy = new float [16];
	float [] y1 = new float [16];
	float [] y2 = new float [16];
	
	float [] sx = new float [16];
	float [] sy = new float [16];
	float [] snewx = new float [16];
	float [] snewy = new float [16];
	float [] sy1 = new float [16];
	float [] sy2 = new float [16];
	
	ArrayList <Integer> check = new ArrayList <Integer>();
	
	Player [] players = new Player [16];
	
	int player;
	
	float Xcircle;
	float Ycircle;
	float Rcircle;
	Player active;
	
	int death=0;
	int playerclap=0;
	
	int random;
	
	boolean gameover = false;
	boolean winner = false;
	
	int waitingTime =4000;
	int playersTime =600;
	
	int max=10;
	
	int move; //0=nothing 1=up 2=pon 3=clap
	
	int loses=0;
	
	int remaining=15;
	
	int littlecircle=1;
			
	CountDownTimer mainPlayerTimer = null;
	
	private SoundPool sounds;
 	private int up;
	private int pon;
	private int clap;
	private int end;
	private int whoosh;
	
	final Globals p = Globals.getInstance();
	  Button pause;
	  Button pause2;
	 
	//fullscreen
	@Override
	// immersive mode+sticky
	public void onWindowFocusChanged(boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		if (hasFocus) {
			mDecorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
					| View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
					| View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
					| View.SYSTEM_UI_FLAG_FULLSCREEN
					| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// no status bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_second);
		mDecorView = getWindow().getDecorView();
		
		inputtextview = (TextView) findViewById(R.id.textView1);
		gametextview = (TextView) findViewById(R.id.textView2);
		lostX =0;
		lostY= -1000;
		Globals lx = Globals.getInstance();
		lx.setLostX(lostX);	
		
		cleanPath();
	    // gets the screen scale
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point(); 
		display.getRealSize(size);
		width = size.x;
		height = size.y;
		Xcircle = width/2;
		Ycircle = height/3;
		Rcircle = height/4;
		
		gametextview.setX(Xcircle-150);
		gametextview.setY(Ycircle);
		
		detector = new GestureDetector(this);
		
		//makes x cordinates for a circle
	    for (int i = 0; i < 16; i++){				
	        x[i] = Rcircle	 * (float)Math.cos((2*Math.PI)/16*i) + Xcircle;        
	    }
	    
	    //makes y cordinates for a circle
	    for (int i=0;i<16;i++){                     
	    	double delta = 2*Ycircle*2*Ycircle-4*(x[i]*x[i]-2*Xcircle*x[i]+Xcircle*Xcircle+Ycircle*Ycircle-Rcircle*Rcircle);
	    	y1[i] = (float) ((2*Ycircle+Math.sqrt(delta))/2);
	    	y2[i] = (float) ((2*Ycircle-Math.sqrt(delta))/2);
	    	if(i>8)
		    	y[i] = Math.max(y1[i], y2[i]);
		    else
		    	y[i] = Math.min(y1[i], y2[i]);
	    	} 
	    
	    //rearange the x & y arrayes
	    for (int i = 0; i < 3; i++){
	    	newx[i]=x[i+13];		 
	    	newy[i]=y[i+13];
		    }
		for (int i = 3; i < 16; i++){
	    	newx[i]=x[Math.abs(3-i)];
		    newy[i]=y[Math.abs(3-i)];
		  	}
		for (int i = 0; i < 16; i++){
	    	x[i]=newx[15-i];
		   	y[i]=newy[15-i];
		    }
		
		
		Globals g = Globals.getInstance();
		int [] colors=g.getColors(); 
		Globals m = Globals.getInstance();
		int MainColor=m.getMainColor();
	
		
		//creates all 16 players 
		for (int i=0;i<16;i++)	{   
			
			random = (int) (Math.random()*16);
			while (check(random))
				random = (int) (Math.random()*16);
			if (i==0)	{	
				players[i]=new mainPlayer(x[i], y[i],height/20, colors [MainColor], this);
				check.add(MainColor);	
			}
			else {
				players[i]=new normalPlayer(x[i], y[i],height/38 , colors [random], this, i );
				check.add(random);
			}
		}
			
		
	
		p.setPradius(Pr);		
		
		  pause = (Button) findViewById(R.id.pause);
		  pause.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent n = new Intent(SecondActivity.this, SecondActivity.class);
					
					startActivity(n);
					onDestroy();
					finish();
					
					
				}
			});
		
		  pause2 = (Button) findViewById(R.id.pause2);
		  pause2.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					Intent n = new Intent(SecondActivity.this, MainActivity.class);
					
					startActivity(n);
					
					finish();
				
					
					
				}
			});
		
		
		
		waitingAninamtion();
		
		Globals pl = Globals.getInstance();
		pl.setPlayers(players);		
		
		sounds= new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		up = sounds.load(getBaseContext(), R.raw.up, 1);
		pon = sounds.load(getBaseContext(), R.raw.pon, 1);
		clap = sounds.load(getBaseContext(), R.raw.clap, 1);
		end = sounds.load(getBaseContext(), R.raw.gameover, 1);
		whoosh = sounds.load(getBaseContext(), R.raw.whoosh, 1);
		
		setActive(players[0]);
		move=1;
	
		
		
	}
	
	 
	
	   @Override
	   public boolean onTouchEvent(MotionEvent event) {
		   Point pp = new Point();
		   float xm =Xcircle-Rcircle/(10/3);
		   float ym =Ycircle+Rcircle/10;
		   float rm = Rcircle/(11/3);
		   float xr = Xcircle+Rcircle/(10/3);
		   float yr = Ycircle+Rcircle/10;
		   float rr = Rcircle/(11/3);
		
		   
		
		
		pp.x= (int) event.getX();
	       pp.y= (int) event.getY();
		   if (gameover){
		   Log.d("ORR", "testx");
		   if (pp.x>xm-rm&&pp.x<xm+rm&&pp.y>ym-rm&&pp.y<ym+rm){
		   Intent n = new Intent(SecondActivity.this, MainActivity.class);
			
			startActivity(n);}
		   if (pp.x>xr-rr&&pp.x<xr+rr&&pp.y>yr-rr&&pp.y<yr+rr){
			   Intent n = new Intent(SecondActivity.this, SecondActivity.class);
				
				startActivity(n);}
		   }
		   else{
			   new CountDownTimer(500, 10) {

				     public void onTick(long millisUntilFinished) {
				    	 if(Pr >125){
				    		 if (millisUntilFinished>300)
				    			 Pr=Pr-22;
				    		 else
				    			 Pr=Pr-8;	
				    	 }
				    	 p.setPradius(Pr);	
				    	 
				     }

				     public void onFinish() {
				    	 pause.setEnabled(true);;
				 		Bpause=false;
				 		Log.d("ORR", "play now ");
				     }
				  }.start();
		   }
		   return detector.onTouchEvent(event);
	   }

	   public boolean onDown(MotionEvent e) {
	   
		   Log.d("ORR", "click ");
		   //  textview.setText("OnDown Gesture");
	       return false;
	   }

	  
	public boolean onFling(MotionEvent fingerDown, MotionEvent fingerUp, float velocityX, float velocityY) {
		if (isGameOver())
			return true;
		//	stopTimer();
	       Point down = new Point();
	       Point up = new Point();
	       
	       down.x= (int) fingerDown.getX();
	       down.y= (int) fingerDown.getY();
	       up.x= (int) fingerUp.getX();
	       up.y= (int) fingerUp.getY();
	    

	       boolean direction; //right=true, left=false
	       boolean UP;
	       boolean PON;
	       boolean CLAPout;
	       boolean [] CLAPin = new boolean [16];;
	      
	       
	       
	     
	       
	       
	       //defines the direction of the move
	       if (down.x < up.x) 
	           direction = true;
	       else 
	           direction = false;
	       
	       //defines when an up move is done
	       if (down.y > (height/7)*5 && down.y < (height/7)*6 && up.y > (height/7)*5 && up.y < (height/7)*6) {
	           UP = true;
	           PON = false;
	         
	       }
	     //defines when a pon move is done
	       else if (down.y > (height/7)*6 && up.y > (height/7)*6 ) {
	           UP = false;
	           PON = true;
	           
	       }
	       else {
	           UP = false;
	           PON = false;
	          
	       }
	     //defines when a clap starts
	        if (down.x >x[0]-(height/20) && down.x < x[0]+ (height/20) && down.y > y[0]- (height/20) && down.y < y[0]+ (height/20)) 
	           CLAPout=true;
	        else 
	           CLAPout = false;
	 
	        //defins where the clap ends
	        CLAPin[0]=false;
	        for (int i=1; i<16;i++){
	        	 if (up.x >x[i]-(height/20) && up.x < x[i]+ (height/20) && up.y > y[i]- (height/20) && up.y < y[i]+ (height/20)) 
	        		 CLAPin[i]=true;
	        	 else
	        		 CLAPin[i]=false;
	        }
	        
	       if (CLAPout){
	      	   UP=false;
	       	   PON=false;
	       }
	       if (direction && UP && !PON) {
	    	   Log.d("ORR", "move "+ move);
    		   if (move==1){
    			   Log.d("ORR", "move stoping "+ move);
    			   stopTimer(); 
    		   }
    		    if (move!=1){
    		    	death=wrongMove();
    		    	gameOver();	
    		    }
    		    	
	    	 //  inputtextview.setText("right up!"); 
	    	   if (active == players[0]){
	    		  upRight(0);
	    		  active.on_up(false);
	    	   }
	    	   else{
	        	   players[0].lost();
	        	   gameOver();
	        	   
	           }
	       }
	       else if (!direction && UP && !PON) {
	    	   Log.d("ORR", "move "+ move);
    		   if (move==1){
    			   Log.d("ORR", "move stoping "+ move);
    			   stopTimer(); 
    		   }
    		   if (move!=1){
   		    		death=wrongMove();
    			   gameOver();
    		   }	
	         //  inputtextview.setText("left up!");
	           if (active == players[0]){
	        	   upLeft(0);
	        	   active.on_up(true);
	           }
	           else{
	        	   players[0].lost();
	        	   gameOver();
	        	  
	           }
	       }
	       else if (direction && !UP && PON) {
	    	   Log.d("ORR", "move "+ move);
    		   if (move==2){
    			   Log.d("ORR", "move stoping "+ move);
    			   stopTimer(); 
    		   }
    		   if (move!=2){
   		    		death=wrongMove();
    			   gameOver();
    		   }
	          // inputtextview.setText("right pon!");
	           if (active == players[0]){
	        	   ponRight(0);
	        	   active.on_pon(false);
	           }
	           else{
	        	   players[0].lost();
	        	   gameOver();
	        	 
	           }
	       }
	       else if (!direction && !UP && PON) {
	    	   Log.d("ORR", "move "+ move);
    		   if (move==2){
    			   Log.d("ORR", "move stoping "+ move);
    			   stopTimer(); 
    		   }
    		   if (move!=2){
    			   death=wrongMove();
    			   gameOver();
    		   }
   		    		
	         //  inputtextview.setText("left pon!");
	           ponLeft(0);
	           if (active == players[0]){
	        	   ponLeft(0);
	        	   active.on_pon(true);
	           }
	           else{
	        	   players[0].lost();
	        	   gameOver();
	        	  
	           }
	       }
	       	
	       
	       else if (CLAPout){
	    	   for (int i=1; i<16;i++){
	    		   if (CLAPin[i])
	    			   playerclap =i;
	    	   }
	    	   if (playerclap==0){
	    		 //  inputtextview.setText("out of range");
	    		   death=nedida();
	    		   gameOver();
	    	   	
	    	   }
	    	   else{
	    		   Log.d("ORR", "move "+ move);
	    		   if (move==3){
	    			   Log.d("ORR", "move stoping "+ move);
	    			   stopTimer(); 
	    		   }
	    		   if (move!=3)
	    		    	gameOver();  
	    		//   inputtextview.setText("clap to: "+ playerclap ); 
	    		   if (playerclap==neighbourLeft(0)||playerclap==neighbourRight(0))
	    			   gameOver();
	    		   clapMe(0,playerclap);
	    		   active.on_clap(getPlayer(playerclap));;
	    		   
	    		   
	    	   }
	       }       
	       else 
	           inputtextview.setText("out of range");
	      
	   	
	       
	       new CountDownTimer(waitingTime+100, 1000) {

		     public void onTick(long millisUntilFinished) {
		     }

		     public void onFinish() {
		    	
		    	// inputtextview.setText("waiting....");
		    	
		    	 playerclap=0;
		 		
		     }
		  }.start();
		return true;
		
	      
	       
	   }

	   public void onLongPress(MotionEvent e) {
	  //     textview.setText("Long Press Gesture");
	   }

	   public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
	   //    textview.setText("Scroll Gesture");
	       return false;
	   }

	   public void onShowPress(MotionEvent e) {
	     //  textview.setText("Show Press gesture");
	   }

	   public boolean onSingleTapUp(MotionEvent e) {
	     //  textview.setText("Single Tap Gesture");
	       return true;
	   }

	@Override
	public void onGesture(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGestureCancelled(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGestureEnded(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onGestureStarted(GestureOverlayView overlay, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	public  boolean check (int random){
		for (int i=0; i<check.size()-1;i++){
			if (check.contains(random))
				return true;	
		}
		return false;
}
	
	public  boolean direction (){
		if (1+(int) (Math.random()*2)==1)
			return true;
		else 
			return false;		
	}
	
	
	public  int neighbourRight (int playing){
		int i;
		if (playing==0)
			i=15;
		else
			i = playing-1;
		while (players[i].isDead()){
			if (i==0)
				i=15;
			else
				i--;
		}
		return i;
	}
	public  int neighbourLeft ( int playing){
		int i;
		if (playing==15)
			i=0;
		else
			i = playing+1;
		
		while (players[i].isDead()){
			if (i==15)
				i=0;
			else
				i++;	
		}
		return i;
	}

	public  int clapRandom ( int playing){
		int random = (int) (Math.random()*16);
		while (players[random].isDead()|| random==neighbourRight(playing)||random==neighbourLeft( playing))
			random = (int) (Math.random()*16);
		return random;
	}

	public  void upRight ( int playing){
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(30);
		PathEffect effect = new CornerPathEffect(100);
		
		paint.setPathEffect(effect);
		
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setColor(players[playing].getColor());
		
		Path path = new Path();
		float x=players[playing].getX();
		float y=players[playing].getY();
		path.moveTo(x, y);	
		int i;
		if (playing==0)
			i=15;
		else
			i=playing-1;
		float xt = players[neighbourRight(playing)].getX();
		float yt = players[neighbourRight(playing)].getY();
		while (x!=xt || y!=yt){
			x=players[i].getX();
			y=players[i].getY();
			path.lineTo(x,y);
			if (i==0)
				i=15;
			else
				i--;
		}
		Globals p = Globals.getInstance();
		p.setPath(path);	
		Globals c = Globals.getInstance();
		c.setPaint(paint);		
		sounds.play(up, 1.0f, 1.0f, 0, 0, 1.5f);
		
		
	}
	public  void upLeft ( int playing){
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(30);
		PathEffect effect = new CornerPathEffect(100);
		
		paint.setPathEffect(effect);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setColor(players[playing].getColor());
		
		Path path = new Path();
		float x=players[playing].getX();
		float y=players[playing].getY();
		path.moveTo(x, y);	
		int i;
		if (playing==15)
			i=0;
		else
			i=playing+1;
		float xt = players[neighbourLeft(playing)].getX();
		float yt = players[neighbourLeft(playing)].getY();
		while (x!=xt || y!=yt){
			x=players[i].getX();
			y=players[i].getY();
			path.lineTo(x,y);
			if (i==15)
				i=0;
			else
				i++;
			
		}
		Globals p = Globals.getInstance();
		p.setPath(path);	
		Globals c = Globals.getInstance();
		c.setPaint(paint);	
		sounds.play(up, 1.0f, 1.0f, 0, 0, 1.5f);
	}
	public  void ponRight (int playing){
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(30);
		PathEffect effect = new CornerPathEffect(100);
		PathEffect effect2 = new DashPathEffect( new float[]{10,80},0);
		PathEffect botheffect = new ComposePathEffect( effect, effect2);
		paint.setPathEffect(botheffect);
		
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setColor(players[playing].getColor());
		
		Path path = new Path();
		float x=players[playing].getX();
		float y=players[playing].getY();
		path.moveTo(x, y);	
		int i;
		if (playing==0)
			i=15;
		else
			i=playing-1;
		float xt = players[neighbourRight(playing)].getX();
		float yt = players[neighbourRight(playing)].getY();
		while (x!=xt || y!=yt){
			x=players[i].getX();
			y=players[i].getY();
			path.lineTo(x,y);
			if (i==0)
				i=15;
			else
				i--;
		}
		Globals p = Globals.getInstance();
		p.setPath(path);	
		Globals c = Globals.getInstance();
		c.setPaint(paint);		
		sounds.play(pon, 1.0f, 1.0f, 0, 0, 1.5f);
		
		
	}
	public  void ponLeft ( int playing){
		Paint paint = new Paint();
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeWidth(30);
		PathEffect effect = new CornerPathEffect(100);
		PathEffect effect2 = new DashPathEffect( new float[]{10,80},0);
		PathEffect botheffect = new ComposePathEffect( effect, effect2);
		paint.setPathEffect(botheffect);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setColor(players[playing].getColor());
		
		Path path = new Path();
		float x=players[playing].getX();
		float y=players[playing].getY();
		path.moveTo(x, y);	
		int i;
		if (playing==15)
			i=0;
		else
			i=playing+1;
		float xt = players[neighbourLeft( playing)].getX();
		float yt = players[neighbourLeft(playing)].getY();
		while (x!=xt || y!=yt){
			x=players[i].getX();
			y=players[i].getY();
			path.lineTo(x,y);
			if (i==15)
				i=0;
			else
				i++;
		}
		Globals p = Globals.getInstance();
		p.setPath(path);	
		Globals c = Globals.getInstance();
		c.setPaint(paint);	
		sounds.play(pon, 1.0f, 1.0f, 0, 0, 1.5f);
	}
	
	public  void clap (int playing){
		Paint paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(30);
		PathEffect effect = new CornerPathEffect(100);
		paint.setPathEffect(effect);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setColor(players[playing].getColor());
		
		Path path = new Path();
		float x=players[playing].getX();
		float y=players[playing].getY();
		path.moveTo(x, y);	
		int r = clapRandom(playing);
		path.lineTo(players[r].getX(), players[r].getY());
		Globals p = Globals.getInstance();
		p.setPath(path);	
		Globals c = Globals.getInstance();
		c.setPaint(paint);	
		sounds.play(clap, 1.0f, 1.0f, 0, 0, 1.5f);
		
	}
	public  void clapMe ( int playing, int desire){
		if (players[desire].isDead())
			gameOver();
		else{
		Paint paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(30);
		PathEffect effect = new CornerPathEffect(100);
		paint.setPathEffect(effect);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setColor(players[playing].getColor());
		
		Path path = new Path();
		float x=players[playing].getX();
		float y=players[playing].getY();
		path.moveTo(x, y);	
	
		path.lineTo(players[desire].getX(), players[desire].getY());
		Globals p = Globals.getInstance();
		p.setPath(path);	
		Globals c = Globals.getInstance();
		c.setPaint(paint);	
		sounds.play(clap, 1.0f, 1.0f, 0, 0, 1.5f);
		}	
	}
				
	public void setActive (Player p) {
	
		active =p;
	}

	public  Player getPlayer (int playNum){
		
		return players[playNum];
	}

	public void gameOver(){
		
		players[0].lost();
		cleanPath();
		
		//inputtextview.setText("game over");	
		gameover=true;
	
		Log.d("ORR", "gameover " );
		lostX =Xcircle;
		lostY= Ycircle;
		Globals lx = Globals.getInstance();
		lx.setLostX(lostX);	
		Globals ly = Globals.getInstance();
		ly.setLostY(lostY);	
		Globals ki = Globals.getInstance();
		ly.setKilld(killed());
		Globals re = Globals.getInstance();
		ly.setReason(death);
		sounds.play(end, 1.0f, 1.0f, 0, 0, 1.5f);
		
		stopTimer();
		cleanPath();
			
	
}
	
	public void die (int playing){
	//	inputtextview.setText(playing +" have lost");
		sounds.play(whoosh, 1.0f, 1.0f, 0, 0, 1.5f);
		players[playing].lost();
		Globals ki = Globals.getInstance();
		ki.setKilld(killed());
		
		
	}
	
	public void startNewTimer (final int interval){
	//	Log.d("ORR", "Start timer " + interval);
		stopTimer();
		final Paint paint = new Paint();
		paint.setStyle(Style.STROKE);
		paint.setStrokeWidth(30);
		PathEffect effect = new CornerPathEffect(100);
		paint.setPathEffect(effect);
		paint.setStrokeCap(Paint.Cap.ROUND);
		paint.setColor(Color.BLACK);
		
		final Path path = new Path();
		
		path.moveTo(sx[0], sy[0]);	
		littlecircle=1;
		mainPlayerTimer=new CountDownTimer(interval, 250) {
			
			@Override
			public void onTick(long millisUntilFinished) {
				// TODO Auto-generated method stub
			//	inputtextview.setText("time left: "+ millisUntilFinished);
				int i=interval/16;
				Log.d("ORR", "int"+interval);
				
					path.lineTo(sx[littlecircle], sy[littlecircle]);
					Globals p = Globals.getInstance();
					p.setPath(path);	
					Globals c = Globals.getInstance();
					c.setPaint(paint);	
					Log.d("ORR", "time animation"+millisUntilFinished);
					littlecircle++;
					if (littlecircle==15){
						littlecircle=1;
					}
					if (isGameOver())
						gameOver();
				
				
			}
			
			@Override
			public void onFinish() {
				// TODO Auto-generated method stub
				Log.d("ORR", "End game");
				death=timeOut();
				gameOver();
			}
		};
		mainPlayerTimer.start();
	}

	public void stopTimer (){
		if (mainPlayerTimer!=null)
			mainPlayerTimer.cancel();
	}
	
	public boolean isGameOver (){
		return gameover;
	}

	public void winner(){
		winner=true;
		//inputtextview.setText("you are a winner!!!");
		gameover=true;
		float winnerX =Xcircle-250;
		float winnerY= Ycircle-250;
		Globals wx = Globals.getInstance();
		wx.setWinnerX(winnerX);	
		Globals wy = Globals.getInstance();
		wy.setWinnerY(winnerY);		
		
	}
	
	public void onBackPressed (){

		Path path = new Path();
	
		
	
		
		Globals p = Globals.getInstance();
		p.setPath(path);	
		Intent n = new Intent(SecondActivity.this, MainActivity.class);
		
		startActivity(n);
	}
	
	public void cleanPath(){
		Path path = new Path();
		Globals p = Globals.getInstance();
		p.setPath(path);	
	}
	
	public void waitingAninamtion(){
		//makes x cordinates for a circle
	    for (int i = 0; i < 16; i++){				
	        sx[i] = players[0].getR()	 * (float)Math.cos((2*Math.PI)/16*i) + players[0].getX();        
	    }
	    
	    //makes y cordinates for a circle
	    for (int i=0;i<16;i++){                     
	    	double delta = 2*players[0].getY()*2*players[0].getY()-4*(sx[i]*sx[i]-2*players[0].getX()*sx[i]+players[0].getX()*players[0].getX()+players[0].getY()*players[0].getY()-players[0].getR()*players[0].getR());
	    	sy1[i] = (float) ((2*players[0].getY()+Math.sqrt(delta))/2);
	    	sy2[i] = (float) ((2*players[0].getY()-Math.sqrt(delta))/2);
	    	if(i>8)
		    	sy[i] = Math.max(sy1[i], sy2[i]);
		    else
		    	sy[i] = Math.min(sy1[i], sy2[i]);
	    	} 
	    
	    //rearange the x & y arrayes
	    for (int i = 0; i < 3; i++){
	    	snewx[i]=sx[i+13];		 
	    	snewy[i]=sy[i+13];
		    }
		for (int i = 3; i < 16; i++){
	    	snewx[i]=sx[Math.abs(3-i)];
		    snewy[i]=sy[Math.abs(3-i)];
		  	}
		for (int i = 0; i < 16; i++){
	    	sx[i]=snewx[15-i];
		   	sy[i]=snewy[15-i];
		    }
	}

	public boolean isWinner (){
		return winner;
	}

	public int killed (){
		int i=0;
		for (int j=1;j<=15;j++){
			if (players[j].isDead())
				i++;
		}
		return i;
	}
	
	public int nedida(){
		return 1;
	}
	public int timeOut(){
		return 2;
	}
	public int wrongMove(){
		return 3;
	}

	
}
