package app.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Window;
import android.view.WindowManager;
import app.rank.Rank;
import app.view.LogoView;

public class LogoActivity extends Activity {
	public static LogoActivity instans;
    LogoView lv;

	public static float ScreenW,ScreenH;
	
    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        instans = this;
        setFullScreen();


		// 获得屏幕宽高
		DisplayMetrics metrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(metrics);

		ScreenW = metrics.widthPixels;
		ScreenH = metrics.heightPixels;
		
        lv = new LogoView(this);
        setContentView(lv);
    }
    private void setFullScreen(){
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);//去掉标题栏 
    	this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
    			WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏 
    }
    public void gotoMenu(){
    	Rank.loadRank(this);
    	Intent menu = new Intent(LogoActivity.this, MenuActivity.class);
		startActivity(menu); // 跳转到菜单页面
		this.finish();// 抛弃
    }
}