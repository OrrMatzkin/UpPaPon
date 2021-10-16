package com.orr.uppapon;

import android.app.Application;
import android.graphics.Color;

class UpPaPon extends Application {

	  private int color = 		color=Color.argb(255, (int) (Math.random() * (255)), (int) (Math.random() * (255)), (int) (Math.random() * (255)));
;

	  public int getColor(){
	    return this.color;
	  }
	 
	}
