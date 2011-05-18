package idv.hic.android.lazycontacts;


import java.util.LinkedList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;

import com.mobyfactory.uiwidgets.RadioStateDrawable;
import com.mobyfactory.uiwidgets.ScrollableTabActivity;

public class MainTabActivity extends ScrollableTabActivity {
	
	
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
	    //setContentView(R.layout.tab_layout);

	    
	    this.setDelegate(new SliderBarActivityDelegateImpl());
	    
	    this.setDefaultShade(RadioStateDrawable.SHADE_GRAY , RadioStateDrawable.SHADE_YELLOW);
	    
	    //TabHost tabHost = getTabHost();  // The activity TabHost
	    //TabHost.TabSpec spec;  // Resusable TabSpec for each tab
	      // Reusable Intent for each tab

	    // Create an Intent to launch an Activity for the tab (to be reused)
	   
	    
	    //讀取分類項目
	    //建立tabitem
	    
	    List<TabItem> tabs=new LinkedList<TabItem>();
	   	
	    tabs.add(new TabItem("聯絡人", R.drawable.tab_contacts, ListContactActivity.class));
	    tabs.add(new TabItem("搜尋", R.drawable.tab_search, ListContactActivity.class));
	    tabs.add(new TabItem("群組", R.drawable.tab_search, ListContactGroupActivity.class));
	    tabs.add(new TabItem("通話記錄", R.drawable.call_log, ListContactActivity.class));
	    tabs.add(new TabItem("我的最愛", R.drawable.fave_contacts, ListContactActivity.class));
	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    
	    
	    
	    
	  
	    for(TabItem tab:tabs){
	    	Intent intent;
	    	intent = new Intent().setClass(this, tab.cls);
	    	//intent.putExtra("Cat", c.getString(0));
	    	
	    	
	    	
	    	
	    	//spec = tabHost.newTabSpec(tab.name).setIndicator(tab.name,getResources().getDrawable(tab.drawableId)).setContent(intent);
		    //tabHost.addTab(spec);
	    	this.addTab(tab.name, tab.drawableId, intent);
	    	
	    	
		  
	    }
	 
	    
	   
	    this.commit();
	   
	  
	  
	    
	    
	}

	
	 private class SliderBarActivityDelegateImpl extends SliderBarActivityDelegate
	    {
	    	/*
	    	 * Optional callback method
	    	 * called when users tap on the tab bar button
	    	 */
	    	protected void onTabChanged(int tabIndex) 
	    	{
	    		//Ln.d("onTabChanged",""+tabIndex);
	    	}
	    }

	
	
}
