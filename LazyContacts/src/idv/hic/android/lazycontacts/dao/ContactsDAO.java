package idv.hic.android.lazycontacts.dao;

import com.google.inject.Inject;

import roboguice.util.Ln;
import idv.hic.android.dao.GenericPagingDAO;
import idv.hic.android.lazycontacts.model.Contact;
import android.content.Context;
import android.database.Cursor;
import android.provider.Contacts.People;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;

public class ContactsDAO extends GenericPagingDAO<Contact> {

	
	@Inject
	Context mContext;
	
	String[] dbfields = { Data._ID, Data.DISPLAY_NAME };

	@Override
	public Cursor getCursor(int limit, int offset) {
		// TODO Auto-generated method stub
		String OrderBy = Data.DISPLAY_NAME + " COLLATE LOCALIZED ASC ";
		if (limit != 0) {
			OrderBy = String.format(OrderBy + "limit %s,%s", offset,limit);
		}

	
		Cursor c = mContext.getContentResolver().query(Contacts.CONTENT_URI, dbfields,
				Data.IN_VISIBLE_GROUP+"='1'", null, OrderBy);

		return c;
	}

	@Override
	public Contact setMapping(Cursor c) {
		
		Contact contact = new Contact();
		contact.setId(c.getString(c.getColumnIndex(Data._ID)));
		contact.setName(c.getString(c.getColumnIndex(Data.DISPLAY_NAME)));
		//contact.setRawId(c.getString(c.getColumnIndex(Data.RAW_CONTACT_ID)));
		//contact.setContactType(c.getString(c.getColumnIndex(Data.)));
		return contact;
	}

}
