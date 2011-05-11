package idv.hic.android.lazycontacts;

import idv.hic.android.lazycontacts.adapter.SimpleContactAdapter;
import idv.hic.android.lazycontacts.service.ContactService;

import com.google.inject.Inject;

import roboguice.activity.RoboListActivity;
import roboguice.inject.InjectView;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

public class ListContactActivity extends RoboListActivity {

	 @InjectView(android.R.id.list)
	 ListView mListView;
	 @InjectView(R.id.contact_tv)
	 TextView mTv;
	 
	 @Inject
	 ContactService contactService;
	 
	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.list_contact);
		
		//contactService=new ContactService();
		
		this.loadContacts();
	}
	
	private void loadContacts(){
		//列出所有聯絡人
		
		this.setListAdapter(new SimpleContactAdapter(this,contactService.getAllContacts()));
		
		
	}
	
	
}
