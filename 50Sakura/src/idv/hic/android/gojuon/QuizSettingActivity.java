package idv.hic.android.gojuon;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class QuizSettingActivity extends PreferenceActivity {
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.addPreferencesFromResource(R.xml.quiz_perference);
		
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
