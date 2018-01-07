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

		// �����Ļ���
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
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȥ����Ϣ��
	}

	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(0, 0, 0, "������Ϸ");
		menu.add(0, 1, 0, "��Ϸ����");
		menu.add(0, 2, 0, "��Ϸ����");
		menu.add(0, 3, 0, "����ѡ��");
		return true;
	}

	Intent intent;
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			// ������Ϸ
			return true;
		case 1:
			// ��Ϸ����
			intent = new Intent();
			intent.setClass(GameActivity.this, HelpActivity.class);
			startActivity(intent);
			return true;
		case 2:
			// ��Ϸ����
			intent = new Intent();
			intent.setClass(GameActivity.this, OptionActivity.class);
			startActivity(intent);
			return true;
		case 3:
			new AlertDialog.Builder(GameActivity.this)
			.setTitle("�˳���Ϸ")
			// ���ô��ڵı�������
			.setIcon(R.drawable.about)
			// ���ô��ڵ�ͼ��
			// .setMessage("")
			// ������ʾ����
			.setMessage("�Ƿ񷵻�ѡ��")
			// ������ʾ����"
			.setPositiveButton("����",
					new DialogInterface.OnClickListener() {
						@Override
						public void onClick(
								DialogInterface dialgointerfance,
								int i) {
							Intent intent=new Intent(GameActivity.this,SelectGame.class);
							startActivity(intent);

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
			// ����ѡ�صĶ���ȷ�Ͻ���
			return true;
		}
		return false;
	}

}
