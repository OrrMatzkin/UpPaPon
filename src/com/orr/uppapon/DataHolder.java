package com.orr.uppapon;

import android.graphics.Paint;

public class DataHolder {
	
	private int color;
	
	public int getColor (){
		return color;
	}
	
	private static final DataHolder holder = new DataHolder();
	public void setData (int data) {
	this.color = data;
	}
	public static DataHolder getInstance() {
		return holder;
	}
	public int getData() {
		return color;
		// TODO Auto-generated method stub
	}
	
}