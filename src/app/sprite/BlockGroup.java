package app.sprite;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

public class BlockGroup {
	Random r;
	
	ImageRect gameImg[];
	Block myBlock[][];
	
	int myLevel,myLevel2;
	int whiteB;
	
	public int[][] steps; 
	public BlockGroup(Bitmap image,int level) {
		r = new Random();
		myLevel = level;
		myLevel2 = level*level;
		whiteB = myLevel2-1;
		
		gameImg = new ImageRect[myLevel2];
		for(int i=0;i<gameImg.length;i++){
			gameImg[i] = new ImageRect(image,myLevel,i);
		}
		myBlock = new Block[myLevel][myLevel];

		for(int i=0;i<myLevel2;i++){
			myBlock[i/myLevel][i%myLevel] = new Block(myLevel,i);
			myBlock[i/myLevel][i%myLevel].init();
		}
		
	}
	
	public void paint(Canvas cc,int x,int y,Paint p){
		for (int i = 0; i < myLevel2; i++) {
			Block tempB = myBlock[i / myLevel][i % myLevel];
			gameImg[tempB.id].paint(cc, x+tempB.x, y+tempB.y, p);
		}
	}
	public boolean onClick(float x,float y){
		for (int i = 0; i < myLevel2; i++) {
			Block tempB = myBlock[i / myLevel][i % myLevel];
			if(tempB.onClick(x, y)){
				if(checkBlock(tempB)){
					return checkWin();
				}else return false;
			}
		}
		return false;
	}
	private boolean checkWin() {
		for (int i = 0; i < myLevel2; i++) {
			Block tempB = myBlock[i / myLevel][i % myLevel];
			if(tempB.id!=i)return false;
		}
		return true;
	}

	private boolean checkBlock(Block blog) {
		if(blog.xline>0 && (myBlock[blog.yline][blog.xline-1].id == whiteB)) {
			int maopao = blog.id;
			blog.id = whiteB;
			myBlock[blog.yline][blog.xline-1].id = maopao;
			addSteps(blog,myBlock[blog.yline][blog.xline-1]);
			return true;
		}
		if(blog.xline<myLevel-1 && (myBlock[blog.yline][blog.xline+1].id == whiteB)) {
			int maopao = blog.id;
			blog.id = whiteB;
			myBlock[blog.yline][blog.xline+1].id = maopao;
			addSteps(blog,myBlock[blog.yline][blog.xline+1]);
			return true;
		}
		if(blog.yline>0 && (myBlock[blog.yline-1][blog.xline].id == whiteB)) {
			int maopao = blog.id;
			blog.id = whiteB;
			myBlock[blog.yline-1][blog.xline].id = maopao;
			addSteps(blog,myBlock[blog.yline-1][blog.xline]);
			return true;
		}
		if(blog.yline<myLevel-1 && (myBlock[blog.yline+1][blog.xline].id == whiteB)) {
			int maopao = blog.id;
			blog.id = whiteB;
			myBlock[blog.yline+1][blog.xline].id = maopao;
			addSteps(blog,myBlock[blog.yline+1][blog.xline]);
			return true;
		}
		return false;
	}
	
	private void addSteps(Block blog1, Block blog2) {
		if(steps==null){
			steps = new int[1][2];
			steps[0][0] = blog1.xline+blog1.yline*myLevel;
			steps[0][1] = blog2.xline+blog2.yline*myLevel;
		}else{
			steps = addStep(blog1.xline+blog1.yline*myLevel,
					blog2.xline+blog2.yline*myLevel);
		}
		
	}

	private int[][] addStep(int num1, int num2) {
		int temp[][] = new int[steps.length+1][];
		for(int i = 0;i<steps.length;i++){
			temp[i] = new int[steps[i].length];
			for(int j = 0;j<steps[i].length;j++){
				temp[i][j] = steps[i][j];
			}
		}
		temp[steps.length] = new int[2];
		temp[steps.length][0] = num1;
		temp[steps.length][1] = num2;
		return temp;
	}

	public void flushBlocks(int num){
		for(int i = 0;i<num;i++){
			autoChange();
		}
	}
	
	private void autoChange() {
		for (int i = 0; i < myLevel2; i++) {
			Block tempB = myBlock[i / myLevel][i % myLevel];
			if(tempB.id == whiteB){
				while(true){
					int index = r.nextInt(4);
					switch (index) {
					case 0:
						if(tempB.xline>0){
							int tempNum = tempB.id;
							tempB.id = myBlock[tempB.yline][tempB.xline-1].id;
							myBlock[tempB.yline][tempB.xline-1].id = tempNum;
							return;
						}
						break;
					case 1:
						if(tempB.xline<myLevel-1){
							int tempNum = tempB.id;
							tempB.id = myBlock[tempB.yline][tempB.xline+1].id;
							myBlock[tempB.yline][tempB.xline+1].id = tempNum;
							return;
						}
						break;
					case 2:
						if(tempB.yline>0){
							int tempNum = tempB.id;
							tempB.id = myBlock[tempB.yline - 1][tempB.xline].id;
							myBlock[tempB.yline - 1][tempB.xline].id = tempNum;
							return;
						}
						break;
					case 3:
						if(tempB.yline<myLevel-1){
							int tempNum = tempB.id;
							tempB.id = myBlock[tempB.yline+1][tempB.xline].id;
							myBlock[tempB.yline+1][tempB.xline].id = tempNum;
							return;
						}
						break;
					}
				}
			}
		}
		
	}

	public void setmap(int[] map){
		for (int i = 0; i < myLevel2; i++) {
			Block tempB = myBlock[i / myLevel][i % myLevel];
			tempB.id = map[i];
		}
	}
	
	public int[] getmap(){
		int[] map = new int[myLevel2];
		for (int i = 0; i < myLevel2; i++) {
			Block tempB = myBlock[i / myLevel][i % myLevel];
			map[i] = tempB.id;
		}
		return map;
	}

	public void doMove(int i, int j) {
		int tempNum = myBlock[i/myLevel][i%myLevel].id;
		myBlock[i/myLevel][i%myLevel].id = myBlock[j/myLevel][j%myLevel].id;
		myBlock[j/myLevel][j%myLevel].id = tempNum;
	}

}
