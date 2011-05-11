package idv.hic.android.lazycontacts.service;

import idv.hic.android.lazycontacts.model.Contact;

import java.util.LinkedList;
import java.util.List;

import com.google.inject.Inject;
import com.google.inject.Provider;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Data;



public class ContactService {
	@Inject 
	Context mContext;
		
	public List<Contact> getAllContacts(){
		//c=ContactsContract.
		//mContext.getContentResolver()
		
		List<Contact> list=new LinkedList<Contact>();
		
		Cursor c = mContext.getContentResolver().query(Data.CONTENT_URI,
	             null,null,null,null);
		
		
		while(c.moveToNext()){
			Contact contact=new Contact();
			contact.setId(c.getString(c.getColumnIndex(Data._ID)));
			contact.setName(c.getString(c.getColumnIndex(Data.DISPLAY_NAME)));
			contact.setRawId(c.getString(c.getColumnIndex(Data.RAW_CONTACT_ID)));
			
			list.add(contact);
			
		}
		c.close();
		
		return list;
	}
}
