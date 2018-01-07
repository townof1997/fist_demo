package app.activity;

import android.app.ExpandableListActivity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ExpandableListAdapter;
import android.widget.SimpleCursorTreeAdapter;
import app.rank.RankDBHelper;

public class RankActivity extends ExpandableListActivity{
	private ExpandableListAdapter mAdapter;
	RankDBHelper dbHelper;
	int mGroupIdColumnIndex;
	SQLiteDatabase sd;
	int groupPos ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		dbHelper = new RankDBHelper(this);
		sd = dbHelper.getReadableDatabase();
		Cursor cursor = sd.rawQuery("select _id,dif_dif from Dif",
				new String[] {});
		// while(cursor.moveToNext()){
		// cursor.getColumnIndex("name");
		// cursor.getColumnIndex("score");
		// }

		mGroupIdColumnIndex = cursor.getColumnIndexOrThrow("_id");
		Log.d("mGroupIdColumnIndex", mGroupIdColumnIndex+"");
		mAdapter = new MyExpandableListAdapter(cursor, this,
				android.R.layout.simple_expandable_list_item_1,
				R.layout.itemlayout, new String[] { "dif_dif" },
				new int[] { android.R.id.text1 }, new String[] { "ranking",
						"score", "name" }, //
				new int[] { R.id.tvDifficulty, R.id.tvTime, R.id.tvName });
		setListAdapter(mAdapter);
	}
	
	@Override
	public void onGroupCollapse(int groupPosition) {
		// TODO Auto-generated method stub
		super.onGroupCollapse(groupPosition);
		
	}
	
	@Override
	public void onGroupExpand(int groupPosition) {
		// TODO Auto-generated method stub
		super.onGroupExpand(groupPosition);
		Log.d("groupPosition", groupPosition+"");
		setSelectedGroup(1);
	}
	

	public class MyExpandableListAdapter extends SimpleCursorTreeAdapter {

		public MyExpandableListAdapter(Cursor cursor, Context context,
				int groupLayout, int childLayout, String[] groupFrom,
				int[] groupTo, String[] childrenFrom, int[] childrenTo) {
			super(context, cursor, groupLayout, groupFrom, groupTo,
					childLayout, childrenFrom, childrenTo);
		}

		@Override
		protected Cursor getChildrenCursor(Cursor groupCursor) {
			// TODO Auto-generated method stub
			String pid = groupCursor.getLong(mGroupIdColumnIndex) + "";
			System.out.println(groupCursor.getString(1));
			Cursor cursor = sd.rawQuery("select * from Info where difficulty=? order by ranking asc", new String[]{groupPos+""});
			System.out.println("Count:"+cursor.getCount());
			return cursor;
		}
		
		@Override
		public long getGroupId(int groupPosition) {
			// TODO Auto-generated method stub
			groupPos = groupPosition;
			return super.getGroupId(groupPosition);
		}
		
		

	}
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(sd !=null){
			sd.close();
			System.out.println("¹Ø±ÕÊý¾Ý¿â");
		}
	}
}
