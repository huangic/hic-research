package idv.hic.android.gojuon;

import idv.hic.android.gojuon.dao.SQLite;
import idv.hic.util.ResourceUtil;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;

public class ExperienseActivity extends TabActivity {
	
	SQLite dbHelper=new SQLite(this);
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.experiense);

	    Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	      // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	   
	    
	    //讀取分類項目
	    
	   	   	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    
	    
	    
	    
	    Cursor c=dbHelper.getAllVocalCat();
	    
	    c.moveToFirst();
	    int rowCount=c.getCount();
	    for(int i=0;i<rowCount;i++){
	    	Intent intent;
	    	intent = new Intent().setClass(this, LetterActivity.class);
	    	intent.putExtra("Cat", c.getString(0));
	    	
	    	
	    	
	    	
	    	spec = tabHost.newTabSpec("cat_"+c.getString(0)).setIndicator(ResourceUtil.getResurceString(this, c.getString(0))).setContent(intent);
		    tabHost.addTab(spec);
		 c.moveToNext();    
	    }
	   c.close();
	    
	    
	    
	    
	    //將大小改變
	    TabWidget tabwidget=tabHost.getTabWidget();
	    
	    for (int i = 0; i < tabwidget.getChildCount(); i++) {
	    	tabwidget.getChildAt(i).getLayoutParams().height = 30;
	    	                        final ImageView iv = (ImageView) tabwidget.getChildAt(i)
	    	                                        .findViewById(android.R.id.icon);
	    	                        iv.getLayoutParams().height = 10;
	    	}
	    
	    
	}

	
	
	
	
}
