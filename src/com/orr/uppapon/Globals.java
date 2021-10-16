package com.orr.uppapon;

import android.graphics.Paint;
import android.graphics.Path;

public class Globals {
	 private static Globals instance;
	 
	   // Global variable
	   private int [] colors;
	   private Player [] players;
	   private int Maincolor;
	   private Path path;
	   private Paint paint;
	   private float winnerX;
	   private float winnerY;
	   private float lostX;
	   private float lostY;
	   private float Pradius;
	   private int killed;
	   private int reason;
	 
	   // Restrict the constructor from being instantiated
	   private Globals(){}
	 
	   public void setColors(int [] d){
	     this.colors=d;
	   }
	   public int[] getColors(){
	     return this.colors;
	   }
	   
	   public void setPlayers(Player [] d){
		     this.players=d;
	   }
	   public Player[] getPlayers(){
		     return this.players;
	   }
		   
	   public void setMainColor(int  d){
			 this.Maincolor=d;
	   }
	   public int getMainColor(){
		   	return this.Maincolor;
	   }	
	   public void setPath(Path d){
			 this.path=d;
	   }
	   public Path getPath(){
		   	return this.path;
	   }		
	   public void setPaint(Paint d){
			 this.paint=d;
	   }
	   public Paint getPaint(){
		   	return this.paint;
	   }		
	   public void setWinnerX(float d){
			 this.winnerX=d;
	   }
	   public float getWinnerX(){
		   	return this.winnerX;
	   }	
	   public void setWinnerY(float d){
			 this.winnerY=d;
	   }
	   public float getWinnerY(){
		   	return this.winnerY;
	   }		   
	   public void setLostX(float d){
			 this.lostX=d;
	   }
	   public float getLostX(){
		   	return this.lostX;
	   }	
	   public void setLostY(float d){
			 this.lostY=d;
	   }
	   public float getLostY(){
		   	return this.lostY;
	   }		  
	   public void setPradius(float d){
			 this.Pradius=d;
	   }
	   public float getPradius(){
		   	return this.Pradius;
	   }		
	   public void setKilld(int d){
			 this.killed=d;
	   }
	   public int getKilled(){
		   	return this.killed;
	   }
	   public void setReason(int d){
			 this.reason=d;
	   }
	   public int getReason(){
		   	return this.reason;
	   }
	   
	   public static synchronized Globals getInstance(){
	     if(instance==null){
	       instance=new Globals();
	     }
	     return instance;
	   }
	}
