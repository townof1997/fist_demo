package app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import app.sound.Music;

public class SelectGame extends Activity {

	public static int imageIndex;
	public static int levelIndex;

	/**
	 * 游戏中图片名称
	 * */
	public static final int GAME_IMAGE[] = { R.drawable.b0, R.drawable.b1,
			R.drawable.b2, R.drawable.b3, R.drawable.b4, R.drawable.b5,
			R.drawable.b6, R.drawable.b7, R.drawable.b8, R.drawable.b9 };
	/**
	 * 选关图片名称
	 * */
//	public static final int GAME_IMAGE_SWITCH[] = { R.drawable.a00,
//			R.drawable.a11, R.drawable.a22, R.drawable.a33, R.drawable.a44,
//			R.drawable.a55, R.drawable.a66, R.drawable.a77, R.drawable.a88,
//			R.drawable.a99 };

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setFullScreen();
	    setContentView(R.layout.select);
	    
	    Gallery g = (Gallery) findViewById(R.id.gallery1);
		g.setAdapter(new ImageAdapter(this, g));
		g.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				imageIndex = position;
				new AlertDialog.Builder(SelectGame.this).setTitle(getString(R.string.level))
				.setIcon(R.drawable.about)
				.setPositiveButton(R.string.easy,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialgointerfance,
											int i) {
										levelIndex = 3;
										enterGame();
									}
								})
				.setNegativeButton(R.string.hard,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialgointerfance,
											int i) {
										levelIndex = 5;
										enterGame();
									}
								})
				.setNeutralButton(R.string.normal,
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialgointerfance,
											int i) {

										levelIndex = 4;
										enterGame();
									}
								})
								
				.show();
				
				
				
			}
		});
		
	}


	private void enterGame() {
		Music.stop();
		System.out.println("imageIndex = " + imageIndex);
		Intent game = new Intent(SelectGame.this, GameActivity.class);
		Music.start(SelectGame.this,R.raw.play,true);
		startActivity(game); // 跳转到game.class
//		finish();
		
	}
	
    private void setFullScreen(){
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏 
    }
	
	public class ImageAdapter extends BaseAdapter {
		// int mGalleryItemBackground;
		Gallery g;

		public ImageAdapter(Context c, Gallery g) {
			mContext = c;
			this.g = g;
		}

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView i = new ImageView(mContext);
			i.setImageResource(GAME_IMAGE[position]);
			// 设置背景
			i.setAlpha(255);
			i.setScaleType(ImageView.ScaleType.FIT_CENTER);
			i.setLayoutParams(new Gallery.LayoutParams(320, 480));

			
			return i;
		}

		private Context mContext;

	}
}
