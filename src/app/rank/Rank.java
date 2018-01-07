package app.rank;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class Rank {
	public static String names[][] = new String[3][10];
	public static int scores[][] = new int[3][10];
	public static String playerName;

	public static void saveRank(Context cont) {
		RankDBHelper dbHelper = new RankDBHelper(cont);
		SQLiteDatabase sqlDB = dbHelper.getWritableDatabase();
		FileOutputStream fos = null;
		DataOutputStream dos = null;
		try {
			fos = cont.openFileOutput("hirank.and1", Context.MODE_PRIVATE);// 备注2
			dos = new DataOutputStream(fos);
			dos.writeUTF(playerName);
			ContentValues cv = new ContentValues();
			for (int i = 0; i < 3; i++) {
				sqlDB.delete("Info", "difficulty=?", new String[]{i+""});
				for (int j = 0; j < 10; j++) {
					// dos.writeUTF(names[i][j]);
					// dos.writeInt(scores[i][j]);
					cv.put("name", names[i][j]);   //名字
					cv.put("score", scores[i][j]); //分数
					cv.put("difficulty", i);       //难度级别
					cv.put("ranking", j);		//排名	
					sqlDB.insert(RankDBHelper.TABLE_NAME, "_id", cv);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 在finally中关闭流 这样即使try中有异常我们也能对其进行关闭操作 ;
			try {
				dos.close();
				fos.close();
				sqlDB.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	//进入游戏时，加载游戏记录
	public static void loadRank(Context cont) {
		RankDBHelper dbHelper = new RankDBHelper(cont);
		SQLiteDatabase sd = dbHelper.getReadableDatabase();
		FileInputStream fis = null;
		DataInputStream dis = null;
		Cursor cursor  = null;
		try {
			if(sd.query("Dif", null, null, null, null, null, null).getCount()<1){
				sd.execSQL("insert into Dif values (0,'简单')");
				sd.execSQL("insert into Dif values (1,'普通')");
				sd.execSQL("insert into Dif values (2,'困难')");
			}
			// 这里找不到数据文件就会报异常,所以finally里关闭流尤为重要!!!
			if (cont.openFileInput("hirank.and1") != null) {
				
				fis = cont.openFileInput("hirank.and1");
				dis = new DataInputStream(fis);
				playerName = dis.readUTF();
				for (int i = 0; i < 3; i++) {
//					for (int j = 0; j < 10; j++) {
//						 names[i][j] = dis.readUTF();
//						 scores[i][j] = dis.readInt();
//					}
					int j = 0;
					cursor = sd.rawQuery("select * from Info where difficulty = ? order by ranking asc", new String[]{i+""});
					while(cursor.moveToNext()){
						names[i][j]=cursor.getString(cursor.getColumnIndex("name"));
						scores[i][j] = cursor.getInt(cursor.getColumnIndex("score"));
						j++;
					}
				}
			} else {
				playerName = "无名氏";
				reset();
				saveRank(cont);
			}
		} catch (FileNotFoundException e) {
			playerName = "无名氏";
			reset();
			saveRank(cont);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 在finally中关闭流!因为如果找不到数据就会异常我们也能对其进行关闭操作 ;
			try {
				if(cursor !=null)
					cursor.close();
					sd.close();
				if (fis != null) {
					// 这里也要判断，因为找不到的情况下，两种流也不会实例化。
					// 既然没有实例化，还去调用close关闭它,肯定"空指针"异常！！！
					fis.close();
					
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	//冒泡排序
	public static void sort(int levelIndex) {
		for (int i = 0; i < scores[levelIndex].length - 1; i++) {
			for (int j = scores[levelIndex].length - 1; j > i; j--) {
				if (scores[levelIndex][j] < scores[levelIndex][j - 1]) {
					int tempS = scores[levelIndex][j];
					scores[levelIndex][j] = scores[levelIndex][j - 1];
					scores[levelIndex][j - 1] = tempS;
					String tempN = names[levelIndex][j];
					names[levelIndex][j] = names[levelIndex][j - 1];
					names[levelIndex][j - 1] = tempN;
				}
			}
		}
	}
	//无记录时，生成记录的方法
	public static void reset() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				names[i][j] = "无名氏";
				scores[i][j] = 60 + 100 * j;
			}
		}
	}
}