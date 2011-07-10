package idv.hic.android.gojuon;

import idv.hic.android.gojuon.dao.SQLite;
import idv.hic.android.util.AdmobUtils;
import idv.hic.android.util.ResourceUtil;
import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.ads.AdView;

public class ExperienseActivity extends TabActivity {
	
	
	AdView adView;
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.experiense);

	    //Resources res = getResources(); // Resource object to get Drawables
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	      // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	   
	    
	    //讀取分類項目
	    
	   	   	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    
	    
	    
	    SQLite dbHelper=new SQLite(this);
	    Cursor c=dbHelper.getAllVocalCat();
	    
	    c.moveToFirst();
	    int rowCount=c.getCount();
	    for(int i=0;i<rowCount;i++){
	    	Intent intent;
	    	intent = new Intent().setClass(this, LetterViewSwipeActivity.class);
	    	intent.putExtra("Cat", c.getString(0));
	    	
	    	
	    	
	    	
	    	spec = tabHost.newTabSpec("cat_"+c.getString(0)).setIndicator(ResourceUtil.getResurceString(this, c.getString(0))).setContent(intent);
		    tabHost.addTab(spec);
		 c.moveToNext();    
	    }
	   c.close();
	    
	    dbHelper.close();
	    
	    /*
	    //將大小改變
	    TabWidget tabwidget=tabHost.getTabWidget();
	    
	    for (int i = 0; i < tabwidget.getChildCount(); i++) {
	    	tabwidget.getChildAt(i).getLayoutParams().height = 30;
	    	                        final ImageView iv = (ImageView) tabwidget.getChildAt(i)
	    	                                        .findViewById(android.R.id.icon);
	    	                        iv.getLayoutParams().height = 10;
	    	}
	    */
	    
	   
	   //AD
	   
	   adView = (AdView)this.findViewById(R.id.ad);
	  AdmobUtils.SetAdView(adView);

	   
	  //show Tips
	  
	  Toast.makeText(this, R.string.exp_tips, Toast.LENGTH_SHORT).show();
	  
	}

	
	
	
	
}
