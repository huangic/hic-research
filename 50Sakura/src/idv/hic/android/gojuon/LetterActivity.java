package idv.hic.android.gojuon;

import idv.hic.android.gojuon.service.QuizService;

import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.GridView;
import android.widget.Toast;

import com.joanzapata.android.BaseAdapterHelper;
import com.joanzapata.android.QuickAdapter;

public class LetterActivity extends BaseActivity implements OnTouchListener,
		OnGestureListener {

	static GestureDetector mGestureDetector;
	final int FLING_MIN_DISTANCE = 100;
	final float FLING_MIN_VELOCITY = 200;
	GridView mGridView;

	String mType = "type_1";// Letter type {type_1,type_2}

	QuizService quizService = new QuizService(this);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.letter);

		mGridView = (GridView) findViewById(R.id.gridview);

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mGestureDetector = new GestureDetector(this);
		mGridView.setOnTouchListener(this);

	}

	@Override
	protected void onStart() {
		super.onStart();
		// The activity is about to become visible.

		this.initGridView();
	}

	private void initGridView() {
		// 抓看看有沒有extra
		try {
			String vocal_cat = getIntent().getStringExtra("Cat");

			// 抓取vocal_cat 跟 mType的字母做GridView顯示

			// 如果vocal_cat==vocal_3 改唯一行三個

			if (vocal_cat.equals("vocal_3")) {
				this.mGridView.setNumColumns(3);

			} else {
				this.mGridView.setNumColumns(5);

			}

			List<Letter> itemList = quizService.getExpLetter(vocal_cat, mType);

			//

			QuickAdapter<Letter> adapter = new QuickAdapter<Letter>(this,
					R.layout.letter_item) {

				@Override
				protected void convert(BaseAdapterHelper helper, Letter l) {
					// TODO Auto-generated method stub

					if (!l.getName().equals("")) {
						helper.setText(R.id.letter_item_name, l.getName());
						helper.setText(R.id.letter_item_phonic, l.getPhonics());
						helper.setText(R.id.letter_item_rate, l.getCorrectNum()
								+ "/" + l.getTotalNum());
					} else {
						helper.setText(R.id.letter_item_name, "");
						helper.setText(R.id.letter_item_rate, "");
						helper.setText(R.id.letter_item_phonic, "");

					}
					if (l.getTotalNum() != l.getCorrectNum()) {
						Log.d(TAG, "Total != Current");
						helper.setTextColor(R.id.letter_item_rate, Color.RED);
					} else {

						helper.setBackgroundColor(R.id.letter_item, Color.GRAY);
					}

				}
			};

			adapter.addAll(itemList);

			this.mGridView.setAdapter(adapter);

			// this.mGridView.setAdapter(new LetterAdapter(this, itemList));

		} catch (Exception ex) {

			Log.d("Extra", ex.getMessage());

		}

	}

	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {
		// TODO Auto-generated method stub

		int position = mGridView.getFirstVisiblePosition();

		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			// Fling left

			if (this.mType.equals("type_2")) {
				// 切換為 hiragana
				Toast.makeText(this, getString(R.string.hiragana), 100).show();
				this.mType = "type_1";
				initGridView();

				this.mGridView.setSelection(position);

			}
		} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			// Fling right
			if (this.mType.equals("type_1")) {
				Toast.makeText(this, getString(R.string.katakana), 100).show();
				this.mType = "type_2";
				initGridView();

				this.mGridView.setSelection(position);

			}
		}
		// this.mGridView.invalidateViews();
		return true;

	}

	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub

		return mGestureDetector.onTouchEvent(event);
		// return false;
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.quizService.close();
	}
}
