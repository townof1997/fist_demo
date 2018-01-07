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
			fos = cont.openFileOutput("hirank.and1", Context.MODE_PRIVATE);// ��ע2
			dos = new DataOutputStream(fos);
			dos.writeUTF(playerName);
			ContentValues cv = new ContentValues();
			for (int i = 0; i < 3; i++) {
				sqlDB.delete("Info", "difficulty=?", new String[]{i+""});
				for (int j = 0; j < 10; j++) {
					// dos.writeUTF(names[i][j]);
					// dos.writeInt(scores[i][j]);
					cv.put("name", names[i][j]);   //����
					cv.put("score", scores[i][j]); //����
					cv.put("difficulty", i);       //�Ѷȼ���
					cv.put("ranking", j);		//����	
					sqlDB.insert(RankDBHelper.TABLE_NAME, "_id", cv);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// ��finally�йر��� ������ʹtry�����쳣����Ҳ�ܶ�����йرղ��� ;
			try {
				dos.close();
				fos.close();
				sqlDB.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	//������Ϸʱ��������Ϸ��¼
	public static void loadRank(Context cont) {
		RankDBHelper dbHelper = new RankDBHelper(cont);
		SQLiteDatabase sd = dbHelper.getReadableDatabase();
		FileInputStream fis = null;
		DataInputStream dis = null;
		Cursor cursor  = null;
		try {
			if(sd.query("Dif", null, null, null, null, null, null).getCount()<1){
				sd.execSQL("insert into Dif values (0,'��')");
				sd.execSQL("insert into Dif values (1,'��ͨ')");
				sd.execSQL("insert into Dif values (2,'����')");
			}
			// �����Ҳ��������ļ��ͻᱨ�쳣,����finally��ر�����Ϊ��Ҫ!!!
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
				playerName = "������";
				reset();
				saveRank(cont);
			}
		} catch (FileNotFoundException e) {
			playerName = "������";
			reset();
			saveRank(cont);
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// ��finally�йر���!��Ϊ����Ҳ������ݾͻ��쳣����Ҳ�ܶ�����йرղ��� ;
			try {
				if(cursor !=null)
					cursor.close();
					sd.close();
				if (fis != null) {
					// ����ҲҪ�жϣ���Ϊ�Ҳ���������£�������Ҳ����ʵ������
					// ��Ȼû��ʵ��������ȥ����close�ر���,�϶�"��ָ��"�쳣������
					fis.close();
					
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}
	//ð������
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
	//�޼�¼ʱ�����ɼ�¼�ķ���
	public static void reset() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 10; j++) {
				names[i][j] = "������";
				scores[i][j] = 60 + 100 * j;
			}
		}
	}
}