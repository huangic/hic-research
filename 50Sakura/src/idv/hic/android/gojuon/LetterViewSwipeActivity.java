package idv.hic.android.gojuon;

import idv.hic.android.gojuon.adapter.LetterAdapter;
import idv.hic.android.gojuon.service.QuizService;

import java.util.List;


import uk.co.jasonfry.android.tools.ui.SwipeView;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.GridView;



public class LetterViewSwipeActivity extends BaseActivity {
	
	
	
	


	//touch
	//static GestureDetector mGestureDetector;
	//final int FLING_MIN_DISTANCE = 200;
	//final float FLING_MIN_VELOCITY = 500;
	//
	
	
	
	//ViewFlipper mViewFlipper;
	
	 SwipeView mSwipeView;
	
	
	QuizService quizService = new QuizService(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.letter_viewswipe);
		this.mSwipeView=(SwipeView)findViewById(R.id.swipe_view);
		
		//Add View
		
		setLetterView();
			
		
		
		
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		//mGestureDetector = new GestureDetector(this);
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
			//mGrid.setOnTouchListener(this);
			this.mSwipeView.addView(letter);
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
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		this.quizService.close();
	}


	


	

	
	
}
