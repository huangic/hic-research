package idv.hic.android.lazycontacts.service;

import idv.hic.android.lazycontacts.dao.ContactsDAO;
import idv.hic.android.lazycontacts.model.Contact;

import java.util.List;

import android.content.Context;

public class ContactService {
	
	Context mContext;
	ContactsDAO dao;
	
	public ContactService(Context context){
		this.dao=new ContactsDAO(context);
		this.mContext=context;
	}
	
	
	public int getAllContactsCount() {
		return dao.getAllCount();

	}

	public List<Contact> getAllContacts(int limit, int offset) {
		
		return dao.fetch(limit, offset);

	}

	public List<Contact> getAllContacts() {
		
		return getAllContacts(0,0);
	}
}
