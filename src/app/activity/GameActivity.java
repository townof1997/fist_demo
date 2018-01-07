package app.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import app.rank.Rank;
import app.sound.Music;
import app.view.GameView;

public class GameActivity extends Activity {

	GameView gv;
	public static float ScreenW, ScreenH;
	public static GameActivity instance;
	public static int currentScore;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setFullScreen();
		instance = this;

		// 获得屏幕宽高
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		ScreenW = metrics.widthPixels;
		ScreenH = metrics.heightPixels;
		gv = new GameView(this);
		setContentView(gv);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		switch (keyCode) {
		case KeyEvent.KEYCODE_BACK:
			gv.finishGame = true;
			Music.start(this, R.raw.heros, true);
			finish();
			return true;
		}
		return false;
	}

	public void finishGame(int score) {
		if (score < Rank.scores[SelectGame.levelIndex - 3][9]) {
			currentScore = score;
			Intent input = new Intent(GameActivity.this, InputName.class);
			startActivity(input);
		}
		Music.start(this, R.raw.heros, true);
		finish();
	}

	private void setFullScreen() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, "返回游戏");
		menu.add(0, 1, 0, "游戏帮助");
		menu.add(0, 2, 0, "游戏设置");
		menu.add(0, 3, 0, "返回选关");
		return true;
	}

	Intent intent;
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			// 返回游戏
			return true;
		case 1:
			// 游戏帮助
			intent = new Intent();
			intent.setClass(GameActivity.this, HelpActivity.class);
			startActivity(intent);
			return true;
		case 2:
			// 游戏设置
			intent = new Intent();
			intent.setClass(GameActivity.this, OptionActivity.class);
			startActivity(intent);
			return true;
		case 3:
			new AlertDialog.Builder(GameActivity.this)
			.setTitle("退出游戏")
			// 设置窗口的标题文字
			.setIcon(R.drawable.about)
			// 设置窗口的图标
			// .setMessage("")
			// 设置显示内容
			.setMessage("是否返回选关")
			// 设置显示内容"
			.setPositiveButton("返回",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
								DialogInterface dialgointerfance,
								int i) {
							Intent intent=new Intent(GameActivity.this,SelectGame.class);
							startActivity(intent);

						}
					})
			.setNegativeButton("退出",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
								DialogInterface dialgointerfance,
								int i) {
							Music.stop();
							finish();
						}
					}).show();
			// 返回选关的二次确认界面
			return true;
		}
		return false;
	}

}
