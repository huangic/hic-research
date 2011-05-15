package idv.hic.android.lazycontacts;


import java.util.LinkedList;
import java.util.List;

import roboguice.activity.RoboTabActivity;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class MainTabActivity extends RoboTabActivity {
	
	
	private class TabItem{
		String name;
		int drawableId;
		Class cls;
		
		public TabItem(String name,int drawable,Class clz){
			this.name=name;
			this.drawableId=drawable;
			this.cls=clz;
		}
	}
	
	
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    setContentView(R.layout.tab_layout);

	    
	    TabHost tabHost = getTabHost();  // The activity TabHost
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	      // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	   
	    
	    //讀取分類項目
	    //建立tabitem
	    
	    List<TabItem> tabs=new LinkedList<TabItem>();
	   	
	    tabs.add(new TabItem("聯絡人", R.drawable.contacts, ListContactActivity.class));
	    tabs.add(new TabItem("通話記錄", R.drawable.call_log, ListContactActivity.class));
	    tabs.add(new TabItem("我的最愛", R.drawable.fave_contacts, ListContactActivity.class));
	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    
	    
	    
	    
	  
	    for(TabItem tab:tabs){
	    	Intent intent;
	    	intent = new Intent().setClass(this, tab.cls);
	    	//intent.putExtra("Cat", c.getString(0));
	    	
	    	
	    	
	    	
	    	spec = tabHost.newTabSpec(tab.name).setIndicator(tab.name,getResources().getDrawable(tab.drawableId)).setContent(intent);
		    tabHost.addTab(spec);
		  
	    }
	 
	    
	   
	    
	   
	  
	  
	    
	    
	}

	
	
	
	
}
