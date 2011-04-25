package idv.hic.android.gojuon;

import idv.hic.android.gojuon.dao.SQLite;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.GridView;
import android.widget.Toast;

public class QuizActivity extends FragmentActivity {

	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            
        setContentView(R.layout.quizact);
            
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
