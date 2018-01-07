package app.activity;

import android.R.color;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import app.sound.Music;

//创建HelpActivity类继承ListActivity
public class HelpActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置全屏
		setFullScreen();
		//设置背景图片
		Drawable drawable = getResources().getDrawable(R.drawable.helpback);
		this.getWindow().setBackgroundDrawable(drawable);
		this.getListView().setCacheColorHint(Color.TRANSPARENT);
		//加载适配器
		setListAdapter(new HelpListAdapter(this));
	}
	//设置全屏
	private void setFullScreen() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// 去掉信息栏
	}
	//定义HelpListAdapter类
	public class HelpListAdapter extends BaseAdapter {
		//创建一个带参的构造方法
		public HelpListAdapter(Context context) {
			mContext = context;
		}

		//获取标题mTitles数组的数目
		@Override
		public int getCount() {
			return mTitles.length;
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
			HelpView hv;
			//如果convertView为空，实例化HelpView对象
			if (convertView == null) {
				hv = new HelpView(mContext, mTitles[position],
						mDialogue[position], mExpanded[position]);
			} else {
				//如果HelpView不为空，设置标题内容以及是否显示内容选项
				hv = (HelpView) convertView;
				hv.setTitle(mTitles[position]);
				hv.setDialogue(mDialogue[position]);
				hv.setExpanded(mExpanded[position]);
			}

			return hv;
		}
		//开关变化时更新适配器数据
		public void toggle(int position) {
			mExpanded[position] = !mExpanded[position];
			notifyDataSetChanged();
		}

		private Context mContext;
		//定义两个字符串数组mTitles、mDialogue
		private String[] mTitles = { "游戏操作", "游戏系统" };
		private String[] mDialogue = {

				"\n一、基本操作\n" + "点击欲移动格子进行移动。\n"+ "点击缩略图查看原图。\n"+"点击播放图标重放游戏步骤。\n"
						+ "游戏中按手机返回键：弹出菜单中选择返回选关界面。\n\n"
						+ "二、难度设定\n" + "游戏根据简单、普通、困难三个难度将拼图分割为3*3、4*4、5*5三种样式。\n\n",
						

				"\n在主菜单点击积分排名按钮可查看每个难度下过关的最短时间。\n",

		};
		//定义布尔型数组mExpanded
		private boolean[] mExpanded = { false, false, false, false, false,
				false, };
	}
	//定义HelpView类继承LinearLayout
	private class HelpView extends LinearLayout {
		//创建HelpView类的构造方法
		public HelpView(Context context, String title, String dialogue,
				boolean expanded) {
			super(context);
			//设置LinearLayout的显示方式为VERTICAL
			this.setOrientation(VERTICAL);
			//实例化标题TextView并设置其大小、内容、颜色
			mTitle = new TextView(context);
			mTitle.setTextSize(20);
			mTitle.setText(title);
			mTitle.setTextColor(Color.BLACK);
			//添加视图TextView
			addView(mTitle, new LinearLayout.LayoutParams(
					android.view.ViewGroup.LayoutParams.FILL_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			//实例化内容TextView并设置其内容、颜色、大小
			mDialogue = new TextView(context);
			mDialogue.setText(dialogue);
			mDialogue.setTextColor(Color.BLACK);
			mDialogue.setTextSize(18);
			addView(mDialogue, new LinearLayout.LayoutParams(
					android.view.ViewGroup.LayoutParams.FILL_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			//设置内容是否显示
			mDialogue.setVisibility(expanded ? VISIBLE : GONE);
		}
		//设置标题
		public void setTitle(String title) {
			//为TextView设置显示标题
			mTitle.setText(title);
		}
		//
		public void setDialogue(String words) {
			mDialogue.setText(words);

		}

		public void setExpanded(boolean expanded) {
			mDialogue.setVisibility(expanded ? VISIBLE : GONE);
		}

		private TextView mTitle;
		private TextView mDialogue;
	}
	//调用显示内容的开关
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		((HelpListAdapter) getListAdapter()).toggle(position);
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		
	if(keyCode==KeyEvent.KEYCODE_BACK){
			
			finish();
	}
	return true;
		
	}
	
}
