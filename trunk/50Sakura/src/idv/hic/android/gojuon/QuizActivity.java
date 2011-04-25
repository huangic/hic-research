package idv.hic.android.gojuon;

import idv.hic.android.gojuon.adapter.QuizLetterAdapter;
import idv.hic.android.gojuon.dao.SQLite;
import idv.hic.util.ProjectUtil;

import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

public class QuizActivity extends BaseActivity {

	private static String LOGTAG=ProjectUtil.LOGTAG;
	
	EditText mEt;
	GridView mGridView;
	int mPosition=0;
	//String mType="type_1";// Letter type {type_1,type_2}
	SQLite dbHelper=new SQLite(this);
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            
        setContentView(R.layout.quizact);
            
        mGridView=(GridView)this.findViewById(R.id.quiz_gridview);
		
        //mGridView.setSelected(true);
        
		mEt=(EditText)this.findViewById(R.id.editText1);
        
        
        this.initGridView();
        
		
        mGridView.setSelected(true);
		
		mEt.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				Log.d(LOGTAG, "keycode="+keyCode);
				
				if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_DOWN ){
					//mGridView.scrollTo(mGridView.getScrollX()+20, mGridView.getScrollY());
					
					Log.d(LOGTAG,"Enter key Position="+mPosition);
					
					//設定Item
					Letter l=(Letter)  mGridView.getItemAtPosition(mPosition);
					l.setCurrent(false);
					l.setUsed(true);
					
					
					mPosition++;
					
					Letter l2=(Letter)  mGridView.getItemAtPosition(mPosition);
					l2.setCurrent(true);
				
					mGridView.setSelection(mPosition);
					
					mGridView.refreshDrawableState();
					
				}
				
				if(keyCode==KeyEvent.KEYCODE_ENTER && event.getAction()==KeyEvent.ACTION_UP){
					mEt.setText("");
					
				}
				
				
				
				return false;
			}
		});
		
		
		//設定item
		Letter l=(Letter)  this.mGridView.getItemAtPosition(mPosition);
		l.setCurrent(true);
		Log.d(LOGTAG,"Init Position="+mPosition);
		
		
		
		mGridView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int postition,
					long arg3) {
				// TODO Auto-generated method stub
				
				//Log.d(LOGTAG,"Item Position:"+postition);
				//Letter l=(Letter)  mGridView.getItemAtPosition(postition);
				//l.setCurrent(true);
				//Log.d(LOGTAG,"Item:"+l.getName());
			}
		});
		
    }
	
	
	
	
	
	private void initGridView(){
		
	       try{
	        
	        
	        
	        Cursor c=this.dbHelper.getLetterByVocalAndType("vocal_1", "type_1");
	        
	        int rowNum=c.getCount();
	        c.moveToFirst();
	        
	        List<Letter> itemList=new LinkedList<Letter>();
	        
	        
	        for(int i=0;i<rowNum;i++){
	        	
	        	Letter currentItem=new Letter(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5));
	        	
	        	
	        	itemList.add(currentItem);
	        	c.moveToNext();
	        }
	        c.close();
	        
	        this.mGridView.setAdapter(new QuizLetterAdapter(this, itemList));
	        
	        
	        
	       }catch(Exception ex){
	    	   
	    	 Log.d("Extra", ex.getMessage());
	    	   
	       }
		
	}
	


	
	//設定目錄
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }
    //設定按鈕
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        
        
        case R.id.new_quiz:
            //Toast.makeText(this, "New quiz", 1000).show();
        	
        	//newGame();
        	
        	Intent i3 = new Intent();
            i3.setClass(this, QuizActivity.class);
            startActivity(i3);
            this.finish();
        	
            return true;
        case R.id.experience:
        	//Toast.makeText(this, "experience", 1000).show();
        	
        	//OPEN EXPERIENCE
        	
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
	
	
	
	
	
	
}
