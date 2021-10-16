package com.orr.uppapon;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

public class HowToPlay extends Activity {
	
	View mDecorView ;
	private SoundPool sounds;
 	private int playclick;
	MainActivityView menuView;
	
	// This snippet hides the system bars.
	public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mDecorView=  getWindow().getDecorView();
        
        if (hasFocus) {
        	mDecorView.setSystemUiVisibility(
	            View.SYSTEM_UI_FLAG_LAYOUT_STABLE
	            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
	            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
	            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
	            | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
	            | View.SYSTEM_UI_FLAG_IMMERSIVE);}
	}

	// This snippet shows the system bars. It does this by removing all the flags
	// except for the ones that make the content appear under the system bars.
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// no status bar
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		WindowManager.LayoutParams.FLAG_FULLSCREEN);
				
		setContentView(R.layout.activity_howtoplay);
		
		sounds= new SoundPool(10, AudioManager.STREAM_MUSIC, 0);
		playclick = sounds.load(getBaseContext(), R.raw.playclick, 1);
		
		Button back = (Button) findViewById(R.id.backButton);
		back.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent n = new Intent(HowToPlay.this, MainActivity.class);
				sounds.play(playclick, 1.0f, 1.0f, 0, 0, 1.5f);
				startActivity(n);
				
				finish();
			}
		});
	}
	
	
	
}
