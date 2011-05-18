package idv.hic.android.lazycontacts;

import idv.hic.android.lazycontacts.adapter.SimpleContactAdapter;
import idv.hic.android.lazycontacts.adapter.SimpleContactGroupAdapter;
import idv.hic.android.lazycontacts.model.Contact;
import idv.hic.android.lazycontacts.model.ContactGroup;
import idv.hic.android.lazycontacts.service.ContactGroupService;
import idv.hic.android.lazycontacts.service.ContactService;
import idv.hic.android.lazycontacts.service.IndexService;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListContactGroupActivity extends ListActivity implements
		OnScrollListener {

	final int ITEM_NUM = 10;
	int PAGE_NUM = 1;
	int TOTAL_NUM = 0;
	int PROCESSED_ITEM=0;
	boolean isQuerying;

	List<ContactGroup> item = new ArrayList<ContactGroup>();

	
	ListView mListView;

	

	
	ContactGroupService contactGroupService;

	
	IndexService indexService;

	BaseAdapter adapter;

	View loadingLayout;

	
	
	private void initActivity(){
		
		mListView=(ListView)this.findViewById(android.R.id.list);
		
		
		contactGroupService=new ContactGroupService(this);

		 indexService=new IndexService(this);
		
	}
	
	
	Handler loadHeadler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//try{
			adapter.notifyDataSetChanged();
			
			super.handleMessage(msg);
			//}catch(Exception ex){
				
				
			//}
		};

	};
	Thread thread;
	Thread imgThread;

	private Runnable loadRunning = new Runnable() {
		public void run() {
			// if(isQuerying){
			ListContactGroupActivity.this.loadData();
			// }
			isQuerying = false;
		}
	};
	
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.lazycontacts_list_contact);

		this.initActivity();
		
		this.mListView.setOnScrollListener(this);

		LayoutInflater inflater = LayoutInflater.from(this);

		this.loadingLayout = (inflater.inflate(R.layout.loading, null, false));

		this.mListView.setAdapter(adapter);

		
		
		adapter = new SimpleContactGroupAdapter(this, item);

		
		this.setListAdapter(adapter);
		
		this.TOTAL_NUM = contactGroupService.getAllCount();

		this.loadData();

		// this.indexService.getAll();

	}

	private void loadData() {
		// CALUATE OFFSET

		int offset = (this.PAGE_NUM - 1) * this.ITEM_NUM;

		List<ContactGroup> tmpItem = contactGroupService.getAll(this.ITEM_NUM,
				offset);

		item.addAll(tmpItem);

		//
		loadHeadler.sendMessage(new Message());

		//
	}

	// 設定如果到頁尾~要再自動載入後面

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub

		if (firstVisibleItem + visibleItemCount >= totalItemCount
				- (this.ITEM_NUM)) {

			if ((totalItemCount < this.TOTAL_NUM) && !isQuerying) {
				this.mListView.addFooterView(this.loadingLayout, null, false);
				this.PAGE_NUM++;
				this.isQuerying = true;
				// this.loadHeadler.post(this.loadRunning);

				thread = new Thread(loadRunning);
				thread.start();
			}
		} else {
			try {
				this.mListView.removeFooterView(loadingLayout);
			} catch (Exception ex) {

			}
		}

	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub

	}
	
	
	
	
	

}
