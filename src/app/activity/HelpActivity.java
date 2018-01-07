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

//����HelpActivity��̳�ListActivity
public class HelpActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//����ȫ��
		setFullScreen();
		//���ñ���ͼƬ
		Drawable drawable = getResources().getDrawable(R.drawable.helpback);
		this.getWindow().setBackgroundDrawable(drawable);
		this.getListView().setCacheColorHint(Color.TRANSPARENT);
		//����������
		setListAdapter(new HelpListAdapter(this));
	}
	//����ȫ��
	private void setFullScreen() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȥ����Ϣ��
	}
	//����HelpListAdapter��
	public class HelpListAdapter extends BaseAdapter {
		//����һ�����εĹ��췽��
		public HelpListAdapter(Context context) {
			mContext = context;
		}

		//��ȡ����mTitles�������Ŀ
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
			//���convertViewΪ�գ�ʵ����HelpView����
			if (convertView == null) {
				hv = new HelpView(mContext, mTitles[position],
						mDialogue[position], mExpanded[position]);
			} else {
				//���HelpView��Ϊ�գ����ñ��������Լ��Ƿ���ʾ����ѡ��
				hv = (HelpView) convertView;
				hv.setTitle(mTitles[position]);
				hv.setDialogue(mDialogue[position]);
				hv.setExpanded(mExpanded[position]);
			}

			return hv;
		}
		//���ر仯ʱ��������������
		public void toggle(int position) {
			mExpanded[position] = !mExpanded[position];
			notifyDataSetChanged();
		}

		private Context mContext;
		//���������ַ�������mTitles��mDialogue
		private String[] mTitles = { "��Ϸ����", "��Ϸϵͳ" };
		private String[] mDialogue = {

				"\nһ����������\n" + "������ƶ����ӽ����ƶ���\n"+ "�������ͼ�鿴ԭͼ��\n"+"�������ͼ���ط���Ϸ���衣\n"
						+ "��Ϸ�а��ֻ����ؼ��������˵���ѡ�񷵻�ѡ�ؽ��档\n\n"
						+ "�����Ѷ��趨\n" + "��Ϸ���ݼ򵥡���ͨ�����������ѶȽ�ƴͼ�ָ�Ϊ3*3��4*4��5*5������ʽ��\n\n",
						

				"\n�����˵��������������ť�ɲ鿴ÿ���Ѷ��¹��ص����ʱ�䡣\n",

		};
		//���岼��������mExpanded
		private boolean[] mExpanded = { false, false, false, false, false,
				false, };
	}
	//����HelpView��̳�LinearLayout
	private class HelpView extends LinearLayout {
		//����HelpView��Ĺ��췽��
		public HelpView(Context context, String title, String dialogue,
				boolean expanded) {
			super(context);
			//����LinearLayout����ʾ��ʽΪVERTICAL
			this.setOrientation(VERTICAL);
			//ʵ��������TextView���������С�����ݡ���ɫ
			mTitle = new TextView(context);
			mTitle.setTextSize(20);
			mTitle.setText(title);
			mTitle.setTextColor(Color.BLACK);
			//�����ͼTextView
			addView(mTitle, new LinearLayout.LayoutParams(
					android.view.ViewGroup.LayoutParams.FILL_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			//ʵ��������TextView�����������ݡ���ɫ����С
			mDialogue = new TextView(context);
			mDialogue.setText(dialogue);
			mDialogue.setTextColor(Color.BLACK);
			mDialogue.setTextSize(18);
			addView(mDialogue, new LinearLayout.LayoutParams(
					android.view.ViewGroup.LayoutParams.FILL_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			//���������Ƿ���ʾ
			mDialogue.setVisibility(expanded ? VISIBLE : GONE);
		}
		//���ñ���
		public void setTitle(String title) {
			//ΪTextView������ʾ����
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
	//������ʾ���ݵĿ���
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
