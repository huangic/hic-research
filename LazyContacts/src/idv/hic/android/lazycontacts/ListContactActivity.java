package idv.hic.android.lazycontacts;

import idv.hic.android.lazycontacts.adapter.SimpleContactAdapter;
import idv.hic.android.lazycontacts.model.Contact;
import idv.hic.android.lazycontacts.service.ContactService;

import java.util.ArrayList;
import java.util.List;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.inject.Inject;


public class ListContactActivity extends RoboListActivity  implements OnScrollListener {

	 final int ITEM_NUM=10;
	 int PAGE_NUM=1;
	 int TOTAL_NUM=0;
	 boolean isQuerying;
	 
	 List<Contact> item=new ArrayList<Contact>();
	
	 @InjectView(android.R.id.list)
	 ListView mListView;
	 @InjectView(R.id.contact_tv)
	 TextView mTextView;
	 
	 @Inject
	 ContactService contactService;
	 
	 
	 View loadingLayout;
	 
	 Handler loadHeadler=new Handler();
	 
	 
	 private Runnable loadRunning=new Runnable(){
		public void run(){
			//if(isQuerying){
			ListContactActivity.this.loadContacts();
			//}
			isQuerying=false;
		}
	 };
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.list_contact);
		
		
		this.mListView.setOnScrollListener(this);
		
		LayoutInflater inflater=LayoutInflater.from(this);
		
		this.loadingLayout=(inflater.inflate(R.layout.loading, null,false));
		
		
		this.setListAdapter(new SimpleContactAdapter(this,item));
		
		this.TOTAL_NUM=contactService.getAllContactsCount();
		
		this.loadContacts();
		
		
		
		
		
		
	}
	
	
	
	
	private void loadContacts(){
	// CALUATE OFFSET
			
			
			int offset=(this.PAGE_NUM-1)*this.ITEM_NUM;
		
			
			List<Contact> tmpItem=contactService.getAllContacts(this.ITEM_NUM,offset);
		 	
			item.addAll(tmpItem);
			
			
			
			BaseAdapter a=(BaseAdapter)this.getListAdapter();
			
			a.notifyDataSetChanged();
			
			this.mTextView.setText(this.item.size()+"");
	}
	

	
	//設定如果到頁尾~要再自動載入後面
	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		
		 if (firstVisibleItem + visibleItemCount >= totalItemCount-(this.ITEM_NUM/2)) {
		      			 
			 if((totalItemCount<this.TOTAL_NUM)&&!isQuerying){
				 this.mListView.addFooterView(this.loadingLayout,null,false);
				 this.PAGE_NUM++;
				 this.isQuerying=true;
				 this.loadHeadler.post(this.loadRunning);
			 }
		    }else{
		    	try{
		    	this.mListView.removeFooterView(loadingLayout);
		    	}catch(Exception ex){
		    		
		    		
		    	}
		    }
		
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}

	
	
	
}
