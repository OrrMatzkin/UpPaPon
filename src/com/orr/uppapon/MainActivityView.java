package com.orr.uppapon ;

import java.text.AttributedCharacterIterator.Attribute;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.SurfaceView;
import android.view.View;

public class MainActivityView extends View {

	float height, width;
	Bitmap backround;
	Bitmap uppapon;
	int 	random = (int) (Math.random()*16);
	
	public MainActivityView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		onstart(context);
	}
	
	public MainActivityView(Context context, AttributeSet attrs) { 
		super(context, attrs);
		onstart(context);
	}
	
	public MainActivityView(Context context, AttributeSet attrs, int defStyle) { 
		super(context, attrs, defStyle);
		onstart(context);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		
		Globals g = Globals.getInstance();
		int [] colors=g.getColors();
		
		
		Globals m = Globals.getInstance();
		m.setMainColor(random);
		canvas.drawColor(colors[random]);
		canvas.drawBitmap(uppapon,backround.getWidth()/2-uppapon.getWidth()/2 ,backround.getHeight()/5, null);
		
		}
	 	

	private void  onstart (Context context){
		
		uppapon=BitmapFactory.decodeResource(getResources(), R.drawable.uppapon3);
		backround=BitmapFactory.decodeResource(getResources(), R.drawable.backround);
		
		
		int newWidth = this.getResources().getDisplayMetrics().widthPixels;
		int newHeight = this.getResources().getDisplayMetrics().heightPixels;
		
		Resources resources = context.getResources();
		int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
		int navbar =  resources.getDimensionPixelSize(resourceId);
		height=newHeight+navbar;
		width = newWidth;
		backround= getResizedBitmap(backround, newHeight+navbar, newWidth);
	
		uppapon= getResizedBitmap(uppapon, (newWidth/1.2*303)/1641	, newWidth/1.2);
		
		
	
		new CountDownTimer(300000, 1) {

		     public void onTick(long millisUntilFinished) {
		    
		    		    		
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
