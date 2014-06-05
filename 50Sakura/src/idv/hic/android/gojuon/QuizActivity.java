package idv.hic.android.gojuon;

import idv.hic.android.gojuon.adapter.QuizLetterAdapter;
import idv.hic.android.gojuon.service.QuizService;
import idv.hic.android.util.ProjectUtil;

import java.util.Date;
import java.util.List;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;

/**
 * @author Hic 練習主畫面
 * 
 *         呼叫QuizService 取得練習字母LIST
 * 
 * 
 */
public class QuizActivity extends BaseActivity {

	private static String LOGTAG = ProjectUtil.LOGTAG;

	QuizService quizService = new QuizService(this);

	EditText mEt;
	GridView mGridView;
	Date mStartTime = new Date();
	boolean onQuiz = true;
	int mPosition = 0;
	// String mType="type_1";// Letter type {type_1,type_2}
	// SQLite dbHelper = new SQLite(this);
	private Handler mHandler;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		try {
			setContentView(R.layout.quizact);

			mGridView = (GridView) this.findViewById(R.id.quiz_gridview);

			// mGridView.setSelected(true);

			mEt = (EditText) this.findViewById(R.id.editText1);

			this.initGridView();

			mGridView.setSelected(true);

			mEt.setOnKeyListener(new OnEnterKeyPressListener());

			
			mEt.addTextChangedListener(new TextWatcher(){

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					  String result = s.toString().replaceAll(" ", "");
					    if (!s.toString().equals(result)) {
					    	mEt.setText(result);
					    	mEt.setSelection(result.length());
					         // alert the user
					    	
					    	
					    	// do set data;
					    	checkLatter();
					    }
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start,
						int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onTextChanged(CharSequence s, int start,
						int before, int count) {
					// TODO Auto-generated method stub
					
				}
				
			});
			
			// 設定item
			Letter l = (Letter) this.mGridView.getItemAtPosition(mPosition);
			l.setCurrent(true);
			Log.d(LOGTAG, "Init Position=" + mPosition);

			mGridView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> paramAdapterView,
						View paramView, int positioin, long paramLong) {
					// TODO Auto-generated method stub
					if (onQuiz)
						return;

					Letter l = (Letter) mGridView.getItemAtPosition(positioin);

					if (l != Letter.MOCK) {

						Dialog dialog = DialogUtils.getLetterDialog(
								QuizActivity.this, l);

						dialog.show();
					}
				}
			});
		} catch (Exception ex) {
			AlertDialog endQuizDialog = getAlertDialog(
					getString(R.string.alertDialog_title),
					String.format(getString(R.string.alertDialog_msg)));
			endQuizDialog.show();
		}

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		this.onQuiz = false;

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		this.onQuiz = true;
		mHandler = new Handler();
		mHandler.post(redrawGridViewRedraw);

	}

	private void initGridView() {

		try {

			List<Letter> itemList = this.quizService.getQuizLetter();

			this.mGridView.setAdapter(new QuizLetterAdapter(this, itemList));

		} catch (Exception ex) {

			Log.d("Extra", ex.toString());

		}

	}

	private AlertDialog getAlertDialog(String title, String message) {
		// 產生一個Builder物件
		Builder builder = new AlertDialog.Builder(this);
		// 設定Dialog的標題
		builder.setTitle(title);
		// 設定Dialog的內容
		builder.setMessage(message);
		// 設定Positive按鈕資料
		builder.setPositiveButton(getString(R.string.ok),
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// 按下按鈕時顯示快顯

					}
				});

		// 利用Builder物件建立AlertDialog
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

	// 設定GridView 重繪
	private Runnable redrawGridViewRedraw = new Runnable() {
		public void run() {
			mGridView.invalidateViews();
			Log.d(LOGTAG, "redraw handler trigger");
			if (QuizActivity.this.onQuiz) {
				mHandler.postDelayed(redrawGridViewRedraw, 1000);
			}
		}
	};

	
	
	private void checkLatter(){
		
		// 設定Item
		Letter l = (Letter) mGridView.getItemAtPosition(mPosition);

		//必須要有輸入過
		if (!mEt.getText().toString().trim().equals("")) {

			// 檢查字母正確性
			quizService.CheckQuizLetter(l, mEt.getText().toString()
					.trim());

			if (mGridView.getAdapter().getCount() > mPosition + 1) {
				mPosition++;

				Letter l2 = (Letter) mGridView
						.getItemAtPosition(mPosition);
				l2.setCurrent(true);

				mGridView.setSelection(mPosition);

			} else {
				// 結算練習成果

				onQuiz = false;

				// 秀練習時間

				Date endTime = new Date();

				int second = (int) (endTime.getTime() - mStartTime
						.getTime()) / 1000;

				AlertDialog endQuizDialog = getAlertDialog(
						getString(R.string.EndDialog_title),
						String.format(
								getString(R.string.EndDialog_msg),
								second));
				endQuizDialog.show();

			}
			// mGridView.invalidateViews();
		}
		
		mEt.setText("");
		
	}
	
	// 設定 enter key press event
	private class OnEnterKeyPressListener implements OnKeyListener {

		@Override
		public boolean onKey(View v, int keyCode, KeyEvent event) {

			Log.d(LOGTAG, "keycode=" + keyCode);

			//KeyEvent
			
			if ((keyCode == KeyEvent.KEYCODE_ENTER|| keyCode== KeyEvent.KEYCODE_SPACE)
					&& event.getAction() == KeyEvent.ACTION_DOWN) {

				Log.d(LOGTAG, "Enter key Position=" + mPosition);

				checkLatter();
				return true;
			}
			
			/*
			if (keyCode == KeyEvent.KEYCODE_ENTER
					&& event.getAction() == KeyEvent.ACTION_UP) {
				mEt.setText("");

			}
			 */
			return false;
		}

	}

	// private class

}
