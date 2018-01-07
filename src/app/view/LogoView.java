package app.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
//import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import app.activity.LogoActivity;
import app.activity.R;

public class LogoView extends View implements Runnable {
	int logoID[]={R.drawable.mmlogo,R.drawable.and1,R.drawable.logo};
	Bitmap[] pic;
	Paint myPaint;
	int num;
	
	public LogoView(Context context) {
		super(context);
		
		myPaint = new Paint();

		myPaint.setAntiAlias(true);
		pic = new Bitmap[3];
		for(int i=0;i<pic.length;i++){
			pic[i] = BitmapFactory.decodeResource(
					context.getResources(), 
					logoID[i]);
			pic[i] = Bitmap.createScaledBitmap(pic[i], (int)LogoActivity.ScreenW, (int)LogoActivity.ScreenH, true);
		}
		new Thread(this).start();
	}

	@Override
	public void onDraw(Canvas canvas){
		super.onDraw(canvas);
		canvas.drawBitmap(pic[num/20>2?2:(num/20)], 0, 0, myPaint);
	}
	
	boolean threadStop;
	@Override
	public void run() {
		while(!threadStop){
			num++;
			postInvalidate();
			if(num>59){
				threadStop = true;
				LogoActivity.instans.gotoMenu();
			}
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}

}
