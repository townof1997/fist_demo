package app.sprite;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ImageRect {
	Bitmap myRect;
	Paint p  = new Paint();
//	int myID;
	int xLine,yLine;
	public static int rectW,rectH;
	public ImageRect(Bitmap currentImg, int size,int id) {
//		myID = id;
		xLine = id%size;
		yLine = id/size;
		rectW = currentImg.getWidth()/size;
		rectH = currentImg.getHeight()/size;
		
		
		
		
		if(id == size*size-1){
			myRect = Bitmap.createBitmap(rectW, rectH, Bitmap.Config.ARGB_8888);

			Canvas tempC = new Canvas(myRect);
			tempC.drawColor(Color.WHITE);
		}else{
			myRect = Bitmap.createBitmap(currentImg, 
					rectW*xLine, rectH*yLine, rectW, rectH);
		}
	}
	
	public void paint(Canvas cc,int x,int y,Paint p){
		cc.drawBitmap(myRect, x, y, p);
	}
}
