package idv.hic.android.gojuon;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class Main extends Activity {
    /** Called when the activity is first created. */
    
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.main);
        
       
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
            Toast.makeText(this, "New quiz", 1000).show();
        	
        	//newGame();
            return true;
        case R.id.experience:
        	Toast.makeText(this, "experience", 1000).show();
        	
        	//OPEN EXPERIENCE
        	
        	 Intent i1 = new Intent();
             i1.setClass(this, ExperienseActivity.class);
             startActivity(i1);
             this.finish();
        	
        	
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
}