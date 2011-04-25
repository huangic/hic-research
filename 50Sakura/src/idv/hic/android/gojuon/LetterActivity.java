package idv.hic.android.gojuon;

import idv.hic.android.gojuon.dao.SQLite;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.GridView;
import android.widget.Toast;

public class LetterActivity extends Activity implements OnTouchListener,OnGestureListener {

	
	static 
	
	GestureDetector mGestureDetector; 
	final int FLING_MIN_DISTANCE=100;
	final float FLING_MIN_VELOCITY=200;  
	GridView mGridView;
	
	String mType="type_1";// Letter type {type_1,type_2}
	SQLite dbHelper=new SQLite(this);
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.letter);
        
        mGridView=(GridView)findViewById(R.id.gridview);
        mGestureDetector=new GestureDetector(this);
        mGridView.setOnTouchListener(this);
       
    }
	
	
	
	@Override
    protected void onStart() {
        super.onStart();
        // The activity is about to become visible.
        
        this.initGridView();
    }

	
	
	
	private void initGridView(){
		//抓看看有沒有extra
	       try{
	        String vocal_cat=getIntent().getStringExtra("Cat");
	        
	      
	        //抓取vocal_cat 跟 mType的字母做GridView顯示
	        
	        // 如果vocal_cat==vocal_3 改唯一行三個
	        
	        if(vocal_cat.equals("vocal_3")){
	        	this.mGridView.setNumColumns(3);
	        	
	        }else{
	        	this.mGridView.setNumColumns(5);
	        	
	        }
	        
	        
	        
	        Cursor c=this.dbHelper.getLetterByVocalAndType(vocal_cat, mType);
	        
	        int rowNum=c.getCount();
	        c.moveToFirst();
	        
	        List<Letter> itemList=new LinkedList<Letter>();
	        
	        Letter perviousItem=null;
	        
	        for(int i=0;i<rowNum;i++){
	        	
	        	Letter currentItem=new Letter(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5));
	        	
	        	//取目前筆跟上壹筆決定插入幾筆mock字
	        	if(i>0){
	              	int minus=currentItem.order-perviousItem.order;
	        	    if(minus>1){
	        			//Add Mock Letter(null)
	        			for(int j=1;j<minus;j++){
	        				itemList.add(Letter.getMock());
	        			}
	        		}
	        	}
	        	perviousItem=currentItem;
	        	itemList.add(currentItem);
	        	c.moveToNext();
	        }
	        c.close();
	        
	        this.mGridView.setAdapter(new LetterAdapter(this, itemList));
	        
	        
	        
	       }catch(Exception ex){
	    	   
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
		
		 //記錄scroll x y
   	 int x,y;
   	 x=this.mGridView.getScrollX();
   	 y=this.mGridView.getScrollY();
		
		
		
	    if (e1.getX() - e2.getX() > FLING_MIN_DISTANCE
	            && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
	        // Fling left
	    	
	    	if(this.mType.equals("type_2")){
	    	//切換為 hiragana
	        Toast.makeText(this, getString(R.string.hiragana), 100).show();
	    	 this.mType="type_1";
	    	 initGridView();
	    	 
	    	this.mGridView.scrollTo(x, y);
	    	 
	    	 
	    	 
	    	 
	    	 
	    	}
	    } else if (e2.getX() - e1.getX() > FLING_MIN_DISTANCE
	            && Math.abs(velocityX) > FLING_MIN_VELOCITY) {
	        // Fling right
	    	if(this.mType.equals("type_1")){
	    	Toast.makeText(this, getString(R.string.katakana), 100).show();
	   	 this.mType="type_2";
    	 initGridView();
    		this.mGridView.scrollTo(x, y);
	    	}
	    }
	 
	    return false;
		
		
		
	
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
		//return false;
	}
	
	
}
