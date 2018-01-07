package app.rank;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class RankDBHelper extends SQLiteOpenHelper {

	// 数据库的名字 rank.db
	// 表名
	public static final String TABLE_NAME = "Info";
	public RankDBHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	public RankDBHelper(Context context) {
		super(context, "rank.db", null, 1);
	}

	// 创建表，ID，难度级别，时间，姓名
	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE Info (" // 创建详细信息数据库表
				+ "_id INTEGER PRIMARY KEY autoincrement,"
				+ "name TEXT,"
				+ "score integer," + "difficulty integer,ranking integer" + ");");
		
		db.execSQL("CREATE TABLE Dif (" // 创建难度数据库表
				+ "_id INTEGER,"
				+ "dif_dif TEXT" + ");");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

	}

}
