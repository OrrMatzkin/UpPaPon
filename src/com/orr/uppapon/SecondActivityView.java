package com.orr.uppapon;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;

import android.R.color;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.BlurMaskFilter.Blur;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.RadialGradient;
import android.graphics.Shader.TileMode;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;

public class SecondActivityView extends View {
	
	float Width;
	float Height;
	
	Bitmap backround;
	Bitmap winner;
	Bitmap lost;
	Bitmap num;
	Bitmap reason;
	Bitmap up;
	Bitmap pon;
	Bitmap clap;
	
	
	float [] x = new float [16];
	float [] y = new float [16];
	float [] newx = new float [16];
	float [] newy = new float [16];
	float [] y1 = new float [16];
	float [] y2 = new float [16];
	
	ArrayList <Integer> check = new ArrayList <Integer>();
	
	float winnerX=-1000;
	float winnerY=-1000;
	
	float lostX=-1000;
	float lostY=-1000;
	
	float Xcircle;
	float Ycircle;
	float Rcircle;
	
	
	
	float radius;
	int random;
	
	float Pradius;
	
	Paint paint2 = new Paint();
	Paint PaintPlayer = new Paint();
	Paint PaintPlayer2 = new Paint();
	
	String sec="";
	public String test="";

	int i;
	float w=1000;
	
	
	
	public SecondActivityView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		game (context);
	}
	
	public SecondActivityView(Context context, AttributeSet attrs) { 
		super(context, attrs);
		game(context);
	}
	
	public SecondActivityView(Context context, AttributeSet attrs, int defStyle) { 
		super(context, attrs, defStyle);
		game(context);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
			
		Globals pl = Globals.getInstance();
	
		Player [] players = pl.getPlayers(); 
		//draws all 15 players
		for (int i=0;i<16;i++)	{                  
			canvas.drawCircle(players[i].getX(), players[i].getY() , players[i].getR() ,players[i].getPaint());
		}
		
		Globals p = Globals.getInstance();
		Path path = p.getPath(); 
		Globals c = Globals.getInstance();
		Paint paint = c.getPaint(); 
		Globals ki = Globals.getInstance();
		int killed = ki.getKilled();
		
		if (path!=null)
		canvas.drawPath(path, paint);
	//	canvas.drawText(sec, Xcircle, Ycircle, p); 	
		canvas.drawLine(0, (Height/7)*5, Width, (Height/7)*5, paint2);
		canvas.drawLine(0, (Height/7)*6, Width, (Height/7)*6 ,paint2); 	
		canvas.drawCircle(players[0].getX(), players[0].getY(), radius, players[0].getPaint());
		if (killed<2){
		up=getResizedBitmap(up, Height/30, Width/3);
		canvas.drawBitmap(up, Width/2-up.getWidth()/2, (Height/7)*5+((Height/7)/2)-up.getHeight()/2, null);
		
		pon=getResizedBitmap(pon, Height/30, Width/3);
		canvas.drawBitmap(pon, Width/2-pon.getWidth()/2, (Height/7)*6+((Height/7)/2)-pon.getHeight()/2, null);
		
		clap=getResizedBitmap(clap, Height/8, Width/2);
		canvas.drawBitmap(clap, Xcircle-clap.getWidth()/2, Ycircle-clap.getHeight()/2, null);
		}
		
		if (killed>=2){
			up.recycle();
			pon.recycle();
			clap.recycle();
		}
		
		
		
		Globals p2 = Globals.getInstance();
		Pradius=p2.getPradius(); 
		Paint pause = new Paint();
		pause.setStyle(Paint.Style.FILL);
		pause.setColor(Color.rgb(255,99,71));
		
	//	canvas.drawCircle(Width-40, 55, Pradius, players[0].getPaint());
	
		
		Globals wx = Globals.getInstance();
		winnerX = wx.getWinnerX(); 
		Globals wy = Globals.getInstance();
		winnerY = wy.getWinnerY();
		if (winnerX!=0)
		canvas.drawBitmap(winner, winnerX, winnerY, null);
		

		Globals lx = Globals.getInstance();
		lostX = lx.getLostX(); 
		Globals ly = Globals.getInstance();
		lostY = ly.getLostY();
		
		Globals re = Globals.getInstance();
		int death = re.getReason();
		if (lostX!=0){
			canvas.drawCircle(Xcircle, Ycircle, Rcircle/(10/3), players[0].getPaint());
			canvas.drawCircle(Xcircle+Rcircle/(10/3), Ycircle+Rcircle/10, Rcircle/(11/3), players[0].getPaint());
			canvas.drawCircle(Xcircle-Rcircle/(10/3), Ycircle+Rcircle/10, Rcircle/(11/3), players[0].getPaint());
			canvas.drawBitmap(lost, lostX-(lost.getWidth()/2), lostY-(lost.getHeight()/2), null);
			if (killed==0)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n0);
			if (killed==1)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n1);
			if (killed==2)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n2);
			if (killed==3)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n3);
			if (killed==4)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n4);
			if (killed==5)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n5);
			if (killed==6)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n6);
			if (killed==7)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n7);
			if (killed==8)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n8);
			if (killed==9)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n9);
			if (killed==10)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n10);
			if (killed==11)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n11);
			if (killed==12)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n12);
			if (killed==13)
			num=BitmapFactory.decodeResource(getResources(), R.drawable.n13);
			num=getResizedBitmap(num, num.getHeight()/2, num.getWidth()/2);
			canvas.drawBitmap(num, lostX-(num.getWidth()/2), lostY-(num.getHeight()/2), null);
			if (death==1)
				reason=BitmapFactory.decodeResource(getResources(), R.drawable.nedida);
			if (death==2)
				reason=BitmapFactory.decodeResource(getResources(), R.drawable.runoutoftime);
			if (death==3)
				reason=BitmapFactory.decodeResource(getResources(), R.drawable.wrongmove);
			reason=getResizedBitmap(reason, reason.getHeight()/2, reason.getWidth()/2);
			canvas.drawBitmap(reason, lostX-(reason.getWidth()/2), lostY-(reason.getHeight()/2), null);	
			
		}
			
		
		invalidate();
	}
	
	private void  game (Context context){
		
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		int navbar =  resources.getDimensionPixelSize(resourceId);
		final int newWidth = this.getResources().getDisplayMetrics().widthPixels;
		final int newHeight = this.getResources().getDisplayMetrics().heightPixels;
		winner=BitmapFactory.decodeResource(getResources(), R.drawable.winner);
		winner=getResizedBitmap(winner, w, w);
		lost=BitmapFactory.decodeResource(getResources(), R.drawable.gameover);
		up=BitmapFactory.decodeResource(getResources(), R.drawable.upp);
		pon=BitmapFactory.decodeResource(getResources(), R.drawable.ponn);
		clap=BitmapFactory.decodeResource(getResources(), R.drawable.clapp);
		
			

		
		
		Height = newHeight+navbar;
		Width = newWidth;
		Xcircle = Width/2;
		Ycircle = Height/3;
		Rcircle = Height/4;
		lost=getResizedBitmap(lost,Height/2 ,Height/2);
		
		
		//sets the text paint
		paint2.setStyle(Paint.Style.FILL);
		paint2.setStrokeWidth(25);
		paint2.setColor(Color.BLACK);
		paint2.setTextSize(66);
		
		 radius=Height;
		new CountDownTimer(3000, 20) {

		     public void onTick(long millisUntilFinished) {
		    	 radius=radius-50;
		     }

		     public void onFinish() {
		 		
		 		
		     }
		  }.start();
		
		
	}
	
	
	
	public Bitmap getResizedBitmap(Bitmap bm, double newHeight, double newWidth) {
	    int width = bm.getWidth();
	    int height = bm.getHeight();
	    float scaleWidth = ((float) newWidth) / width;
	    float scaleHeight = ((float) newHeight) / height;

	    // Create a matrix for the manipulation
	    Matrix matrix = new Matrix();

	    // Resize the bit map
	    matrix.postScale(scaleWidth, scaleHeight);

	    // Recreate the new Bitmap
	    Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

	    return resizedBitmap;
	}
	
	
}

