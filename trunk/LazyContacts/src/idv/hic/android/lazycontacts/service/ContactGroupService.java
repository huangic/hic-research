package idv.hic.android.lazycontacts.service;

import idv.hic.android.lazycontacts.dao.ContactsGroupDAO;
import idv.hic.android.lazycontacts.model.ContactGroup;

import java.util.List;

import android.content.Context;

public class ContactGroupService {
	
	Context mContext;
	ContactsGroupDAO dao;
	
	public ContactGroupService(Context context){
		this.dao=new ContactsGroupDAO(context);
		this.mContext=context;
	}
	
	
	public int getAllCount() {
		return dao.getAllCount();

	}

	public List<ContactGroup> getAll(int limit, int offset) {
		
		return dao.fetch(limit, offset);

	}

	public List<ContactGroup> getAll() {
		
		return getAll(0,0);
	}
}
