package com.orr.uppapon;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.media.AudioManager;
import android.media.SoundPool;
import android.media.MediaPlayer;

public class MainActivity extends Activity {

	View mDecorView = null;
	MainActivityView menuView;
	Button playB;
	Button settingB;
	int [] color = new int [16];
	Paint paint = new Paint();
 	private SoundPool sounds;
 	private int playclick;
	MediaPlayer backroundMusic;
	private ImageView image;
	
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
		
		backroundMusic = MediaPlayer.create(MainActivity.this, R.raw.backround);
		backroundMusic.setLooping(true);
		backroundMusic.setVolume(0.2f, 0.2f);
		backroundMusic.start();
		
		// sets the color bank in an array
		color [0]= Color.rgb(255,99,71); // tomato
		color [1]= Color.rgb(255,140,0); // dark orange
		color [2]= Color.rgb(85,107,47); // dark olive green
		color [3]= Color.rgb(34,139,34); // forest green
		color [4]= Color.rgb(255,215,0); // gold
		color [5]= Color.rgb(176,224,230); // powder blue
		color [6]= Color.rgb(70,130,180); // steel blue
		color [7]= Color.rgb(72,61,139); // dark slate blue
		color [8]= Color.rgb(216,191,216); // thistle
		color [9]= Color.rgb(188,143,143); // rosy brown
		color [10]= Color.rgb(255,182,193); // light pink
		color [11]= Color.rgb(25,25,112); // midnight blue
		color [12]= Color.rgb(95,158,160); // cadet blue
		color [13]= Color.rgb(189,183,107); // dark khaki
		color [14]= Color.rgb(123,104,238); // medium slate blue
		color [15]= Color.rgb(178,34,34); // firebrick
		
		Globals g = Globals.getInstance();
		g.setColors(color);		
		
		sounds= new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		playclick = sounds.load(getBaseContext(), R.raw.playclick, 1);
		
		
		setContentView(R.layout.activity_main);
		

		
		Button play = (Button) findViewById(R.id.playB);
	  //play.setVisibility(View.GONE);
		play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent n = new Intent(MainActivity.this, SecondActivity.class);
				sounds.play(playclick, 1.0f, 1.0f, 0, 0, 1.5f);
				startActivity(n);
				finish();
				
			}
		});
		Button howtoplay = (Button) findViewById(R.id.settingB);
		howtoplay.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent n = new Intent(MainActivity.this, HowToPlay.class);
				sounds.play(playclick, 1.0f, 1.0f, 0, 0, 1.5f);
				startActivity(n);
				onDestroy();
				finish();
			}
		});
		
		mDecorView = getWindow().getDecorView();
	}
	
	public void onPause(){
		super.onPause();
		backroundMusic.release();
		finish();
		
	}
	@Override
	public void onDestroy() {
	    super.onDestroy();  // Always call the superclass
	    
	    // Stop method tracing that the activity started during onCreate()
	    android.os.Debug.stopMethodTracing();
	}
}
