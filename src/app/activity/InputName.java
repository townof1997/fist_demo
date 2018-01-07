package app.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import app.rank.Rank;

public class InputName extends Activity {

	String name;
	EditText et;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setFullScreen();
		setContentView(R.layout.inputname);
		
		et = (EditText)findViewById(R.id.editText1);
		et.setTextKeepState(Rank.playerName);
		Button complete = (Button)findViewById(R.id.button1);
		complete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (et.getText().toString().equals("")) {
					Rank.playerName = "������";
					Rank.names[SelectGame.levelIndex - 3][9] = "������";
				} else {
					Rank.playerName = et.getText().toString();
					Rank.names[SelectGame.levelIndex - 3][9] = et.getText()
							.toString();
				}
				Rank.scores[SelectGame.levelIndex - 3][9] = GameActivity.currentScore;
				Rank.sort(SelectGame.levelIndex - 3);
				Rank.saveRank(InputName.this);
				finish();
			}
		});
		
	}

	private void setFullScreen() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);// ȥ����Ϣ��
    }
}