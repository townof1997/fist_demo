package app.activity;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import app.sound.Music;

public class MenuActivity extends Activity {

	public static MenuActivity instance;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullScreen();
		instance = this;
		setContentView(R.layout.menu);

		Music.start(this,R.raw.heros,true);
		
		ImageView imgbtn_menu_1 = (ImageView) findViewById(R.id.imgbtn_menu_start);
		ImageView imgbtn_menu_2 = (ImageView) findViewById(R.id.imgbtn_menu_rank);
		ImageView imgbtn_menu_3 = (ImageView) findViewById(R.id.imgbtn_menu_audio);
		ImageView imgbtn_menu_4 = (ImageView) findViewById(R.id.imgbtn_menu_help);
		ImageView imgbtn_menu_5 = (ImageView) findViewById(R.id.imgbtn_menu_about);
		ImageView imgbtn_menu_6 = (ImageView) findViewById(R.id.imgbtn_menu_stop);

		imgbtn_menu_1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(MenuActivity.this, SelectGame.class);
				startActivity(intent);
			}
		});
		imgbtn_menu_2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(MenuActivity.this, RankActivity.class);
				startActivity(intent);
			}
		});
		imgbtn_menu_3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(MenuActivity.this, OptionActivity.class);
				startActivity(intent);
			}
		});
		imgbtn_menu_4.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(MenuActivity.this, HelpActivity.class);
				startActivity(intent);
			}
		});
		imgbtn_menu_5.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MenuActivity.this).setTitle("����")
				// // ���ô��ڵı�������
						.setIcon(R.drawable.about)
						// // ���ô��ڵ�ͼ��
						// // .setMessage("")
						// // ������ʾ����
						.setMessage(R.string.About)// ������ʾ����"
						.setPositiveButton("����",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(
											DialogInterface dialgointerfance,
											int i) {
										System.out
												.println("click back!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
									}
								}).show();
			}
		});
		imgbtn_menu_6.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				exitGame();
			}
		});
	}
	private void setFullScreen(){
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);//ȥ�������� 
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FULLSCREEN);//ȥ����Ϣ�� 
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case 4:
			exitGame();
			return true;
		}
		return false;
	}
	private void exitGame() {
		new AlertDialog.Builder(MenuActivity.this)
		.setTitle(R.string.quit_title)
		// ���ô��ڵı�������
		.setIcon(R.drawable.about)
		// ���ô��ڵ�ͼ��
		// .setMessage("")
		// ������ʾ����
		.setMessage(R.string.quit_string)
		// ������ʾ����"
		.setPositiveButton("����",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							DialogInterface dialgointerfance,
							int i) {
						

					}
				})
		.setNegativeButton("�˳�",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(
							DialogInterface dialgointerfance,
							int i) {
						Music.stop();
						finish();
					}
				}).show();
		
	}
}
