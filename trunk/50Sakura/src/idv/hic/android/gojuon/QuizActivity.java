package idv.hic.android.gojuon;

import idv.hic.android.gojuon.adapter.QuizLetterAdapter;
import idv.hic.android.gojuon.service.QuizService;
import idv.hic.util.ProjectUtil;

import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateUtils;
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

	QuizService quizService=new QuizService(this);
	
	EditText mEt;
	GridView mGridView;
	Date mStartTime=new Date();
	boolean onQuiz=true;
	int mPosition = 0;
	// String mType="type_1";// Letter type {type_1,type_2}
	//SQLite dbHelper = new SQLite(this);
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

			List<Letter> itemList =this.quizService.getQuizLetter();

			this.mGridView.setAdapter(new QuizLetterAdapter(this, itemList));

		} catch (Exception ex) {

			Log.d("Extra", ex.toString());

		}

	}

	
	private AlertDialog getAlertDialog(String title,String message){
        //產生一個Builder物件
        Builder builder = new AlertDialog.Builder(this);
        //設定Dialog的標題
        builder.setTitle(title);
        //設定Dialog的內容
        builder.setMessage(message);
        //設定Positive按鈕資料
        builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //按下按鈕時顯示快顯
                
            }
        });
              
        //利用Builder物件建立AlertDialog
        return builder.create();
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
				
				//檢查字母正確性
				quizService.CheckQuizLetter(l, mEt.getText().toString().trim());
				
				
				
				if (mGridView.getAdapter().getCount() > mPosition+1) {
					mPosition++;

					Letter l2 = (Letter) mGridView
							.getItemAtPosition(mPosition);
					l2.setCurrent(true);
					
					mGridView.setSelection(mPosition);
					
					
					
				}else{
					//結算練習成果
					
					onQuiz=false;
					
					
					//秀練習時間
					
					Date endTime=new Date();
					
					int second=(int)(endTime.getTime()-mStartTime.getTime())/1000;
					
					AlertDialog endQuizDialog=getAlertDialog("練習結束", String.format("總共花費%d秒\n，可點選字母觀看讀音", second));
					endQuizDialog.show();
					
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

    
    //private class 

}