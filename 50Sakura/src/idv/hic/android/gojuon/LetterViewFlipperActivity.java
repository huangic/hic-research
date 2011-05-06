package idv.hic.android.gojuon;

import idv.hic.android.gojuon.adapter.LetterAdapter;
import idv.hic.android.gojuon.service.QuizService;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.GestureDetector.OnGestureListener;
import android.view.View.OnTouchListener;
import android.view.animation.AnimationUtils;
import android.widget.GridView;
import android.widget.Toast;
import android.widget.ViewFlipper;

public class LetterViewFlipperActivity extends BaseActivity implements OnTouchListener,
OnGestureListener {
	
	
	
	


	//touch
	static GestureDetector mGestureDetector;
	final int FLING_MIN_DISTANCE = 150;
	final float FLING_MIN_VELOCITY = 200;
	//
	
	
	
	ViewFlipper mViewFlipper;

	QuizService quizService = new QuizService(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.letter_viewflipper);
		this.mViewFlipper=(ViewFlipper)findViewById(R.id.letter_vf);
		
		//Add View
		
		setLetterView();
			
		
		
		
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mGestureDetector = new GestureDetector(this);
		//this.mViewFlipper.setOnTouchListener(this);
		//this.mViewFlipper.setLongClickable(true);
	}
	
	
	private void setLetterView(){
			//取EXTRA
			String vocal_cat = getIntent().getStringExtra("Cat");
			
			LayoutInflater layout=(LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);
			
			String[] types={"type_2","type_1"};
			
			for(String mType:types){
			//Set mType
			View letter= layout.inflate(R.layout.letter, null);
			GridView mGrid=(GridView)letter.findViewById(R.id.gridview);
			this.initGridView(mGrid, vocal_cat, mType);		
			mGrid.setOnTouchListener(this);
			this.mViewFlipper.addView(letter);
			}
			
	}
	
	private void initGridView(GridView mGridView,String vocal_cat,String mType) {
		// 抓看看有沒有extra
		try {
			
			// 抓取vocal_cat 跟 mType的字母做GridView顯示

			// 如果vocal_cat==vocal_3 改唯一行三個

			if (vocal_cat.equals("vocal_3")) {
				mGridView.setNumColumns(3);

			} else {
				mGridView.setNumColumns(5);

			}

			List<Letter> itemList = quizService.getExpLetter(vocal_cat, mType);

			mGridView.setAdapter(new LetterAdapter(this, itemList));

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

		
		//int position = mGridView.getFirstVisiblePosition();

		if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			// Fling left
			this.mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.left_in));
			this.mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.left_out));
			
			
			//Toast.makeText(this, getString(R.string.hiragana), 100).show();
			this.mViewFlipper.showPrevious();
			
		} else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
				&& Math.abs(velocityX) > FLING_MIN_VELOCITY) {
			// Fling right
			//Toast.makeText(this, getString(R.string.katakana), 100).show();
			
			this.mViewFlipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.right_in));
			this.mViewFlipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.right_out));
			
			
			this.mViewFlipper.showNext();
		}
		//this.mGridView.invalidateViews();
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
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.quizService.close();
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		
		return mGestureDetector.onTouchEvent(event);
		//return false;
	}


	

	
	
}
