package idv.hic.android.gojuon;

import idv.hic.android.gojuon.adapter.QuizLetterAdapter;
import idv.hic.android.gojuon.dao.SQLite;
import idv.hic.android.gojuon.service.QuizService;
import idv.hic.util.ProjectUtil;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.EditText;
import android.widget.GridView;


/**
 * @author Hic
 *	練習主畫面
 *
 *	呼叫QuizService 取得練習字母LIST
 *	
 *
 */
public class QuizActivity extends BaseActivity  {

	private static String LOGTAG = ProjectUtil.LOGTAG;

	QuizService quizService=new QuizService();
	
	EditText mEt;
	GridView mGridView;
	int mPosition = 0;
	// String mType="type_1";// Letter type {type_1,type_2}
	SQLite dbHelper = new SQLite(this);
	private Handler mHandler;  

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.quizact);

		mGridView = (GridView) this.findViewById(R.id.quiz_gridview);

		// mGridView.setSelected(true);

		mEt = (EditText) this.findViewById(R.id.editText1);

		this.initGridView();

		 mHandler = new Handler();  
	     mHandler.post(redrawGridViewRedraw);  
						
		mGridView.setSelected(true);

		mEt.setOnKeyListener(new OnEnterKeyPressListener());

		// 設定item
		Letter l = (Letter) this.mGridView.getItemAtPosition(mPosition);
		l.setCurrent(true);
		Log.d(LOGTAG, "Init Position=" + mPosition);

	}

	private void initGridView() {

		
		
		
		try {

			List<Letter> itemList =this.quizService.getQuizLetter(this);

			this.mGridView.setAdapter(new QuizLetterAdapter(this, itemList));

		} catch (Exception ex) {

			Log.d("Extra", ex.getMessage());

		}

	}

	// 設定目錄
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_menu, menu);
		return true;
	}

	// 設定按鈕
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle item selection
		switch (item.getItemId()) {

		case R.id.new_quiz:
			// Toast.makeText(this, "New quiz", 1000).show();

			// newGame();

			Intent i3 = new Intent();
			i3.setClass(this, QuizActivity.class);
			startActivity(i3);
			this.finish();

			return true;
		case R.id.experience:
			// Toast.makeText(this, "experience", 1000).show();

			// OPEN EXPERIENCE

			Intent i1 = new Intent();
			i1.setClass(this, ExperienseActivity.class);
			startActivity(i1);
			this.finish();

			return true;

		case R.id.menu_setting:
			Intent i2 = new Intent();
			i2.setClass(this, QuizSettingActivity.class);
			startActivity(i2);
			this.finish();

		default:
			return super.onOptionsItemSelected(item);
		}
	}

	//設定GridView 重繪
	private Runnable redrawGridViewRedraw = new Runnable() {  
        public void run() {  
            mGridView.invalidateViews();  
            Log.d(LOGTAG, "handler trigger");
            mHandler.postDelayed(redrawGridViewRedraw, 500);  
        }  
    };  

    //設定 enter key press event
    private class OnEnterKeyPressListener implements OnKeyListener{

    	@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {
			
			Log.d(LOGTAG, "keycode=" + keyCode);

			if (keyCode == KeyEvent.KEYCODE_ENTER
					&& event.getAction() == KeyEvent.ACTION_DOWN) {
				
				Log.d(LOGTAG, "Enter key Position=" + mPosition);

				// 設定Item
				Letter l = (Letter) mGridView.getItemAtPosition(mPosition);
				l.setCurrent(false);
				l.setUsed(true);
				
				if (mGridView.getAdapter().getCount() > mPosition+1) {
					mPosition++;

					Letter l2 = (Letter) mGridView
							.getItemAtPosition(mPosition);
					l2.setCurrent(true);
					
					mGridView.setSelection(mPosition);
					
					
					
				}else{
					//結算練習成果
					
					
					
				}
				
				return true;
			}

			if (keyCode == KeyEvent.KEYCODE_ENTER
					&& event.getAction() == KeyEvent.ACTION_UP) {
				mEt.setText("");

			}

			return false;
		}
    	
    	
    }
}
