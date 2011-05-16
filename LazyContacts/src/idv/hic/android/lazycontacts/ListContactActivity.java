package idv.hic.android.lazycontacts;

import greendroid.app.GDListActivity;
import idv.hic.android.lazycontacts.adapter.SimpleContactAdapter;
import idv.hic.android.lazycontacts.model.Contact;
import idv.hic.android.lazycontacts.service.ContactService;
import idv.hic.android.lazycontacts.service.IndexService;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.ContentUris;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class ListContactActivity extends ListActivity implements
		OnScrollListener {

	final int ITEM_NUM = 20;
	int PAGE_NUM = 1;
	int TOTAL_NUM = 0;
	int PROCESSED_ITEM=0;
	boolean isQuerying;

	List<Contact> item = new ArrayList<Contact>();

	
	ListView mListView;

	TextView mTextView;

	
	ContactService contactService;

	
	IndexService indexService;

	BaseAdapter adapter;

	View loadingLayout;

	
	
	private void initActivity(){
		
		mListView=(ListView)this.findViewById(android.R.id.list);
		
		mTextView=(TextView)this.findViewById(R.id.contact_tv);

		 contactService=new ContactService(this);

		 indexService=new IndexService(this);
		
	}
	
	
	Handler loadHeadler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			//try{
			adapter.notifyDataSetChanged();
			ListContactActivity.this.mTextView
					.setText(ListContactActivity.this.item.size() + "");
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
			ListContactActivity.this.loadContacts();
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

		
		
		adapter = new SimpleContactAdapter(this, item);

		
		this.setListAdapter(adapter);
		
		this.TOTAL_NUM = contactService.getAllContactsCount();

		this.loadContacts();

		// this.indexService.getAll();

	}

	private void loadContacts() {
		// CALUATE OFFSET

		int offset = (this.PAGE_NUM - 1) * this.ITEM_NUM;

		List<Contact> tmpItem = contactService.getAllContacts(this.ITEM_NUM,
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
