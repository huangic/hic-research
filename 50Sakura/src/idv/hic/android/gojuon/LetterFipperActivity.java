package idv.hic.android.gojuon;

import idv.hic.android.gojuon.adapter.LetterAdapter;
import idv.hic.android.gojuon.service.QuizService;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.widget.GridView;

public class LetterFipperActivity extends Activity {

	
	GridView mGridView;

	//String mType = "type_1";// Letter type {type_1,type_2}

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

			String mType=getIntent().getStringExtra("Type");
			
			// 抓取vocal_cat 跟 mType的字母做GridView顯示

			// 如果vocal_cat==vocal_3 改唯一行三個

			if (vocal_cat.equals("vocal_3")) {
				this.mGridView.setNumColumns(3);

			} else {
				this.mGridView.setNumColumns(5);

			}

			List<Letter> itemList = quizService.getExpLetter(vocal_cat, mType);

			this.mGridView.setAdapter(new LetterAdapter(this, itemList));

		} catch (Exception ex) {

			Log.d("Extra", ex.getMessage());

		}

	}
}
