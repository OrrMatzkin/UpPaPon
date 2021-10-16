package com.orr.uppapon;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public abstract class Player 
{
    protected boolean dead;
	protected float x;
	protected float y;
	protected float r;
	protected Paint p;
	protected int c;
	 
	protected SecondActivity activity;
	protected int playerNum;
	protected int moveNum	;
	protected GestureDetector detector;
	public Player (float x, float y, float r, int c, SecondActivity activity)
	{
		// TODO Auto-generated constructor stub
		
		this.x=x;
		this.y=y;
		this.r=r;
		this.activity=activity;
		int color;
		
		this.dead=false;
		
		this.c=c;
		this.p = new Paint();
		this.p.setStyle(Paint.Style.FILL);
		this.p.setColor(c);
	
		this.p.setStrokeWidth(this.r/3);
	}	
		
	
	public Paint getPaint()
	{
		return this.p;
	}
	public int getColor()
	{
		return this.c;
	}
	public float getX()
	{
		return this.x;
	}
	public float getY()
	{
		return this.y;
	}
	public float getR()
	{
		return this.r;
	}
	public void lost ()
	{
		this.dead=true;
		this.r=0;
		
	}
	public void setR (float r)
	{
		this.r=r;
	}
	public boolean isDead (){
		return dead;
	}
	public int getNum (){
		return this.playerNum;
	}
	public int getMove (){
		return this.moveNum;
	}
	
	public abstract void on_up (boolean is_left);
	
	public abstract void on_pon (boolean is_left);
	
	public abstract void on_clap (Player destination);
	
}

class mainPlayer extends Player {

	public mainPlayer(float x, float y, float r, int c, SecondActivity activity) {
		super(x, y, r, c, activity);
		// TODO Auto-generated constructor stub
		playerNum=0;
		
		
	}
	
	public  void on_up (boolean is_left){
		final Player p;
		if(is_left)
			p = activity.getPlayer(activity.neighbourLeft(0));
		else
			p = activity.getPlayer(activity.neighbourRight(0));
		new CountDownTimer(activity.playersTime, activity.playersTime) {

		     public void onTick(long millisUntilFinished) {
		     }

		     public void onFinish() {
		 		activity.setActive(p);
				p.on_pon(activity.direction());
		     }
		  }.start();
				    		
		 
	}
	
	public  void on_pon (boolean is_left) {
		final	Player p;
		if(is_left)
			p = activity.getPlayer(activity.neighbourLeft(0));
		else
			p = activity.getPlayer(activity.neighbourRight(0));
		new CountDownTimer(activity.playersTime, activity.playersTime) {

		     public void onTick(long millisUntilFinished) {
		     }

		     public void onFinish() {
		    	 activity.setActive(p);
		 		p.on_clap(activity.getPlayer(activity.clapRandom(p.getNum())));
		     }
		  }.start();
		
	}
	
	public  void on_clap (final Player destination) {
		
		new CountDownTimer(activity.playersTime, activity.playersTime) {

		     public void onTick(long millisUntilFinished) {
		     }

		     public void onFinish() {
		    	 activity.setActive(destination);
		 		destination.on_up(activity.direction());
		     }
		  }.start();
		
	}


}



class normalPlayer extends Player {

	public normalPlayer(float x, float y, float r, int c, SecondActivity activity, int playerNum) {
		super(x, y, r, c, activity);
		// TODO Auto-generated constructor stub
		this.playerNum=playerNum;
	}
	
	public  void on_up (final boolean is_left){
		while (activity.Bpause){
			
		}
		
		if (activity.isGameOver())
			return;
		if (activity.loses>activity.max){
			activity.cleanPath();
			activity.die(playerNum);
			activity.setActive(activity.getPlayer(0));
			activity.move=1;
			activity.loses=0;
			activity.max++;
			activity.playersTime=activity.playersTime-25;
			activity.waitingTime=activity.waitingTime-200;
			activity.remaining--;
			if(activity.remaining==2)
				activity.winner();
	}
		else{	
		final Player p;
		if(is_left) 
			p = activity.getPlayer(activity.neighbourLeft(playerNum));
		else
			p = activity.getPlayer(activity.neighbourRight(playerNum));
		new CountDownTimer(activity.playersTime, activity.playersTime) {

		     public void onTick(long millisUntilFinished) {
		     }

		     public void onFinish() {
		 		
		 		if(is_left) 
					activity.upLeft(playerNum);
				else
					activity.upRight(playerNum);
		 		activity.setActive(p);
		 		
		 		
		 		if (p.getNum()==0){
		 			activity.move=2;
		 			Log.d("ORR", "should cahnge to "+ 2);
		 			activity.startNewTimer(activity.waitingTime);
		 		}
		 		
		 		else
		 			p.on_pon(activity.direction());
		 			activity.loses++;
		     }
		  }.start();}
		}


	public  void on_pon (final boolean is_left) {
		while (activity.Bpause){
			
		}
		if (activity.isGameOver())
			return;
		if (activity.loses>activity.max){
				activity.cleanPath();
				activity.die(playerNum);
				activity.setActive(activity.getPlayer(0));
				activity.move=1;
				activity.loses=0;
				activity.max++;
				activity.playersTime=activity.playersTime-25;
				activity.waitingTime=activity.waitingTime-200;
				activity.remaining--;
				if(activity.remaining==2)
					activity.winner();
		}
		else{	
		final	Player p;
	//	Log.d("ORR", "1 player number is " + playerNum);
		if(is_left)
			p = activity.getPlayer(activity.neighbourLeft(playerNum));
		else
			p = activity.getPlayer(activity.neighbourRight(playerNum));
		new CountDownTimer(activity.playersTime, activity.playersTime) {

		     public void onTick(long millisUntilFinished) {
		     }

		     public void onFinish() {
		    	
		    	 if(is_left) 
						activity.ponLeft(playerNum);
		    	 else
						activity.ponRight(playerNum);
		    	 
		    	 
		    	 
		    	 activity.setActive(p);
		    	if (p.getNum()==0){
		    		activity.move=3;
		    		Log.d("ORR", "should cahnge to "+ 3);
		    		activity.startNewTimer(activity.waitingTime);
		    	}
		    	else
		    		p.on_clap(activity.getPlayer(activity.clapRandom(p.getNum())));
		    	activity.loses++;
		     }
		  }.start();
		
		}
	}
	
	public  void on_clap (final Player destination) {
		while (activity.Bpause){
			
		}
		if (activity.isGameOver())
			return;
		if (activity.loses>activity.max){
			activity.cleanPath();
			activity.die(playerNum);
			activity.setActive(activity.getPlayer(0));
			activity.move=1;
			activity.loses=0;
			activity.max++;
			activity.playersTime=activity.playersTime-25;
			activity.waitingTime=activity.waitingTime-200;
			activity.remaining--;
			if(activity.remaining==2)
				activity.winner();
	}
		else{	
		new CountDownTimer(activity.playersTime, activity.playersTime) {

		     public void onTick(long millisUntilFinished) {
		     }

		     public void onFinish() {
		    	 activity.setActive(destination);
		    	 activity.clapMe(playerNum,destination.getNum());
		    	
		    		
			 		if (destination.getNum()==0){
			 			activity.move=1;
			 			Log.d("ORR", "should cahnge to "+ 1);
			 			activity.startNewTimer(activity.waitingTime);
			 		}
			 			
			 		
			 		else
			 			destination.on_up(activity.direction());
			 		activity.loses++;
			     }
			  }.start();
	}
		}


	
}