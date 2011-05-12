package idv.hic.android.lazycontacts.service;

import idv.hic.android.lazycontacts.dao.ContactsDAO;
import idv.hic.android.lazycontacts.model.Contact;

import java.util.LinkedList;
import java.util.List;

import roboguice.util.Ln;

import com.google.inject.Inject;
import com.google.inject.Provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;

public class ContactService {
	@Inject
	ContactsDAO dao;
	
	
	
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
