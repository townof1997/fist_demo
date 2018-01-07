package app.sound;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import app.activity.MenuActivity;

public class Music {
	static Context cont;
	
	static MediaPlayer player;
	static int MusicId = -1;
	static AudioManager audio;
	
	public static int myVolume = 10;

	public static boolean MusicOn = true;
	
	public static void start(Context context, int musicId,boolean loop){
		if(cont == null){
			cont = context;
			loadMusic();
			audio = (AudioManager) MenuActivity.instance.getSystemService(Context.AUDIO_SERVICE);
			audio.setStreamVolume(AudioManager.STREAM_MUSIC, myVolume, 0);
		}
		if(!MusicOn)return;
		if(MusicId == musicId && player.isPlaying())return;
		MusicId = musicId;
		if(player != null){
			if(player.isPlaying()){
				player.stop();
			}
			player.reset();
			player = null;
		}
		player = MediaPlayer.create(context, musicId);
		if(loop){
			player.setLooping(true);
		}
		try {
			player.prepare();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		player.start();
	}
	public static void stop(){
		if(player!=null)player.stop();
	}
	
	public static boolean playing(){
		if(player!=null && player.isPlaying()){
			return true;
		}
		return false;
	}

	public static void saveMusic(int volume) {
		FileOutputStream fos = null;		  
	    DataOutputStream dos = null;
		try {    
			fos = cont.openFileOutput("music.and1",Context.MODE_PRIVATE);//��ע2   
            dos = new DataOutputStream(fos); 
            dos.writeBoolean(MusicOn);
            dos.writeInt(volume);   
            
        } catch (FileNotFoundException e) {     
            e.printStackTrace();   
        } catch (IOException e) {      
            e.printStackTrace();   
        } finally {   
            // ��finally�йر��� ������ʹtry�����쳣����Ҳ�ܶ�����йرղ��� ;   
            try {   
                dos.close();   
                fos.close();   
            } catch (IOException e) {     
                e.printStackTrace();   
            }   
        }   

	}

	public static void loadMusic() {
	    FileInputStream fis = null;   
	    DataInputStream dis = null;
		try {    
            // �����Ҳ��������ļ��ͻᱨ�쳣,����finally��ر�����Ϊ��Ҫ!!!   
            if (cont.openFileInput("music.and1") != null) {     
                 
                fis = cont.openFileInput("music.and1");  
                dis = new DataInputStream(fis);
                MusicOn = dis.readBoolean();
                myVolume = dis.readInt();        
            }
        } catch (FileNotFoundException e) {    
            e.printStackTrace();   
        } catch (IOException e) {    
            e.printStackTrace();   
        } finally {   
            // ��finally�йر���!��Ϊ����Ҳ������ݾͻ��쳣����Ҳ�ܶ�����йرղ��� ;   
            try {   
                if (cont.openFileInput("music.and1") != null) {   
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
}
