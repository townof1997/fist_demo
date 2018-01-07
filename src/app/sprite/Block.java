package app.sprite;

import android.graphics.RectF;

public class Block {

	public int x,y,xline,yline;
	public int id;
	
	RectF myRect;
	public Block(int level, int i) {
		xline = i%level;
		yline = i/level;
		x = (i%level)*(ImageRect.rectW+1);
		y = (i/level)*(ImageRect.rectH+1);
		
		id = i;
	}

	public void init(){
		myRect = new RectF(x,y,x+ImageRect.rectW,y+ImageRect.rectH);
	}
	
	public boolean onClick(float x,float y){
		return myRect.contains(x, y);
	}
}
