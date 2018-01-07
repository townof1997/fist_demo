package app.activity;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.Toast;
import app.rank.Rank;
import app.sound.Music;

public class OptionActivity extends Activity {
	ImageView mState;
	
	AudioManager audio;
	int volume;
	SeekBar vb;

	boolean hasSound;
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setFullScreen();
	    audio = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
	    
	    setContentView(R.layout.option);
	    mState = (ImageView)findViewById(R.id.showSound);
	    if(Music.playing()){
	    	hasSound = true;
	    	mState.setImageResource(R.drawable.audio_on);
	    }else{
	    	hasSound = false;
	    	mState.setImageResource(R.drawable.audio_off);
	    }
	    ImageView mStart = (ImageView)findViewById(R.id.soundOn);
	    
	    mStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setOnMusic();
				Music.saveMusic(volume);		
			}

			
		});
	    
	    ImageView mStop = (ImageView)findViewById(R.id.sonudoff);
	    
	    mStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setOffMusic();
				Music.saveMusic(volume);
			}

		});
	    ImageView mUp = (ImageView)findViewById(R.id.soundup);
	    
	    mUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setOnMusic();
				audio.adjustVolume(AudioManager.ADJUST_RAISE, 0);
				volume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
				vb.setProgress(volume);
				Music.saveMusic(volume);
			}
		});
	    
	    ImageView mDown = (ImageView)findViewById(R.id.sounddown);
	    
	    mDown.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setOnMusic();
				audio.adjustVolume(AudioManager.ADJUST_LOWER, 0);
				volume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
				vb.setProgress(volume);
				if(volume == 0){
					setOffMusic();
				}
				Music.saveMusic(volume);
			}
		});
	    
	    Button reset = (Button)findViewById(R.id.resetbutton);
	    reset.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Rank.reset();
				Rank.saveRank(OptionActivity.this);
				showToast();				
			}
		});
	    
	    Button rb = (Button)findViewById(R.id.returnBtn);
	    rb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(hasSound){

					Music.start(OptionActivity.this,R.raw.heros,true);	
				}
				finish();
			}
		});
	    volume = audio.getStreamVolume(AudioManager.STREAM_MUSIC);
//	    volume = Music.myVolume;
	    vb = (SeekBar) findViewById(R.id.volumebar);
	    vb.setMax(audio.getStreamMaxVolume(AudioManager.STREAM_MUSIC));
	    vb.setProgress(volume);
	    vb.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				setOnMusic();
				audio.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
				if(progress == 0){
					setOffMusic();
				}
				Music.saveMusic(volume);
			}
		});
	}
	
	public void setOnMusic() {
		hasSound = true;
		mState.setImageResource(R.drawable.audio_on);
		Music.MusicOn = true;
		Music.start(OptionActivity.this,R.raw.win,false);		
	}

	public void setOffMusic() {
		hasSound = false;
		mState.setImageResource(R.drawable.audio_off);
		Music.MusicOn = false;
		Music.stop();
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			if(hasSound){

				Music.start(OptionActivity.this,R.raw.heros,true);	
			}
			finish();
			return true;
		}
		return false;
	}
	private void setFullScreen(){
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏 
    }


	private void showToast() {
		Toast.makeText(this, R.string.resetText, Toast.LENGTH_SHORT)
		.show();
	}
}
