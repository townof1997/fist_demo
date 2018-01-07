package app.view;

import java.util.Random;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.EditText;
import app.activity.GameActivity;
import app.activity.R;
import app.activity.SelectGame;
import app.sound.Music;
import app.sprite.Block;
import app.sprite.BlockGroup;

public class GameView extends SurfaceView implements SurfaceHolder.Callback,Runnable{
	public static final boolean DEBUG = false;
	Random r;	
	Paint myPaint;
	Matrix myMatr;
	Thread gameThread;

	Bitmap currentImg;
	Bitmap smallImg;
	RectF smallRect;

	Bitmap bigImg;
	float bigScale;
	
	Bitmap clockImg;
	long startTime;
	
	Bitmap replayImg;
	RectF replayRect;
	
	BlockGroup bg;
	BlockGroup rg;
	int GameMapX = 10;
	int GameMapY = 120;
	
	
	int gemeState;
	public static final int GAMESTATE_READY = 0;
	public static final int GAMESTATE_RUN = 1;
	public static final int GAMESTATE_WIN = 2;
	public static final int GAMESTATE_SHOW = 3;
	public static final int GAMESTATE_REPLAY = 4;
	
	int readyCount;
	int winCount;
	int replayCount;
	int replayCurrentStep;
	
	String winStr,readyStr,rfinalStr;

	int readyTime = 30;
	
	int[] replayStart; 
	int[][] replayStep;
	int replayState;
	public GameView(Context context) {
		super(context);
		
		r = new Random();
		myPaint = new Paint();
		myMatr = new Matrix();

		winStr = context.getString(R.string.winText);
		readyStr = context.getString(R.string.readyText);
		
		clockImg = BitmapFactory.decodeResource(context.getResources(), 
				R.drawable.k);
		currentImg = BitmapFactory.decodeResource(context.getResources(), 
				SelectGame.GAME_IMAGE[SelectGame.imageIndex]);
		smallImg = Bitmap.createScaledBitmap(currentImg, 100, 100, true);
		smallRect = new RectF(10,10,110,110);
		
		replayImg = BitmapFactory.decodeResource(context.getResources(), 
				R.drawable.up);
		replayRect = new RectF(220,60,270,110);
		
		bigScale = (GameActivity.ScreenW - 20) /currentImg.getWidth();
		bigImg = Bitmap.createScaledBitmap(currentImg,
				(int) GameActivity.ScreenW-20,
				(int) (currentImg.getHeight() * bigScale), true);
		
		bg = new BlockGroup(bigImg, SelectGame.levelIndex);
		rg = new BlockGroup(bigImg, SelectGame.levelIndex);
				
		gemeState = GAMESTATE_READY;
		readyCount = 0;
		readyTime = DEBUG?1:SelectGame.levelIndex*10;
		
		gameThread = new Thread(this);
		gameThread.start();
	}

	public boolean finishGame;
	@Override
	public void run() {
		while(!finishGame){
			try {
				event();
				update();
				render();
				Thread.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	int eventType;
	private int score;
	public static final int EVENT_NON = 0;
	public static final int EVENT_TOUCHDOWN = 1;
	public static final int EVENT_TOUCHUP = 2;
	
	private void event() {
		if(eventType == EVENT_NON)return;
		switch (eventType) {
		case EVENT_TOUCHDOWN:
			switch (gemeState) {
			case GAMESTATE_RUN:
				if (bg.onClick(touchX-GameMapX, touchY-GameMapY)) {
					if (bg.steps != null) {
						rg.setmap(replayStart);
						gemeState = GAMESTATE_REPLAY;
						replayCount = 0;
						replayCurrentStep = 0;
						replayState = GAMESTATE_WIN;
					}
				}
				break;
			}
			eventType = EVENT_NON;
			break;
		case EVENT_TOUCHUP:
			switch (gemeState) {
			case GAMESTATE_RUN:
				if (smallRect.contains(touchX, touchY)) {
					gemeState = GAMESTATE_SHOW;
				}else if(replayRect.contains(touchX, touchY)){
					if (bg.steps != null) {
						rg.setmap(replayStart);
						gemeState = GAMESTATE_REPLAY;
						replayCount = 0;
						replayCurrentStep = 0;
						replayState = GAMESTATE_RUN;
					}
				}
				break;
			case GAMESTATE_SHOW:
				gemeState = GAMESTATE_RUN;

				break;
//			case GAMESTATE_REPLAY:
//				gemeState = GAMESTATE_RUN;
//
//				break;
			}
			eventType = EVENT_NON;
			break;
		}
	}

	private void update() {
		switch (gemeState) {
		case GAMESTATE_READY:
			if(readyCount<readyTime){
				bg.flushBlocks(DEBUG?1:SelectGame.levelIndex);
				rfinalStr = readyStr;
				for(int i=0;i<=readyCount%3;i++){
					rfinalStr += ".";
				}
				readyCount++;
			}else{
				replayStart = bg.getmap();
				readyCount = 0;
				startTime = System.currentTimeMillis();
				gemeState = GAMESTATE_RUN;
			}
			break;

		case GAMESTATE_WIN:
			winCount++;
			if(winCount>90){
				finishGame = true;

				GameActivity.instance.finishGame(score);
				
			}
			break;

		case GAMESTATE_REPLAY:
			replayCount++;
			if(replayCount>9){
				if(replayCount%2 == 0 && replayCurrentStep<bg.steps.length){
				rg.doMove(bg.steps[replayCurrentStep][0],
						bg.steps[replayCurrentStep][1]);
				replayCurrentStep++;
				}else if(replayCurrentStep >= bg.steps.length){
					if(replayState == GAMESTATE_RUN){
						gemeState = GAMESTATE_RUN;
					}else{
						winCount = 0;
						score = (int)((System.currentTimeMillis() - startTime)/1000);
						gemeState = GAMESTATE_WIN;
						Music.stop();
						Music.start(GameActivity.instance, R.raw.win, false);
					}
				}
			}
			break;
		}
	}

	private void render() {
		SurfaceHolder holder = getHolder();
		Canvas canvas = holder.lockCanvas();
		if (canvas == null) {
			return;
		}

		canvas.drawColor(Color.BLACK);
		switch (gemeState) {
		case GAMESTATE_READY:
			renderREADY(canvas);
			break;
		case GAMESTATE_RUN:
			renderRUN(canvas);
			break;
		case GAMESTATE_SHOW:
			renderSHOW(canvas);
			break;
		case GAMESTATE_REPLAY:
			renderREPLAY(canvas);
			break;
		case GAMESTATE_WIN:
			renderWIN(canvas);
			break;
		}

		holder.unlockCanvasAndPost(canvas);
	}

	float touchX,touchY;
	
	Block touchBlock,leaveBlock;
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if(eventType != EVENT_NON)return true;
		if (event.getActionMasked() == MotionEvent.ACTION_DOWN) {
			eventType = EVENT_TOUCHDOWN;
			touchX = event.getX();
			touchY = event.getY();
			return true;
		}
		
		if (event.getActionMasked() == MotionEvent.ACTION_UP) {
			eventType = EVENT_TOUCHUP;
			touchX = event.getX();
			touchY = event.getY();
			return true;
		}
		return false;
	}
	
	private boolean checkDistance(Block b1, Block b2) {
		return ((Math.abs(b1.xline-b2.xline)+Math.abs(b1.yline-b2.yline) == 1)&&
				(b2.id == 8));
		
	}


	private void renderWIN(Canvas canvas) {
		canvas.drawBitmap(bigImg, GameMapX, GameMapX, myPaint);
		myPaint.setColor(Color.YELLOW);
		myPaint.setTextSize(32);
		canvas.drawText(winStr, 400 - (winCount<<2), bigImg.getHeight()+40, myPaint);
	}

	private void renderRUN(Canvas canvas) {
		canvas.drawBitmap(smallImg,10,10,myPaint);
		canvas.drawBitmap(clockImg,220,10,myPaint);
		canvas.drawBitmap(replayImg,220,60,myPaint);
		
		myPaint.setColor(Color.YELLOW);
		myPaint.setTextSize(40);
		
		canvas.drawText(getCurrentTime(), 270, 50, myPaint);
		bg.paint(canvas, GameMapX, GameMapY,myPaint);
	}

	private void renderREPLAY(Canvas canvas) {
		rg.paint(canvas, GameMapX, GameMapY,myPaint);
	}

	private String getCurrentTime() {
		int secends = (int)((System.currentTimeMillis() - startTime)/1000);
		int secend = secends%60;
		int min = ((secends)/60)%60;
		int hour = secends/3600;
		
		return (hour<10?"0"+hour:hour)+":"+(min<10?"0"+min:min)+":"+(secend<10?"0"+secend:secend);
	}

	private void renderREADY(Canvas canvas) {
		myPaint.setColor(Color.YELLOW);
		myPaint.setTextSize(32);
		canvas.drawText(rfinalStr, 50, 110, myPaint);
		bg.paint(canvas, GameMapX, GameMapY,myPaint);
		
	}
	private void renderSHOW(Canvas canvas) {
		canvas.drawBitmap(bigImg, 10, 10, myPaint);
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}

}
