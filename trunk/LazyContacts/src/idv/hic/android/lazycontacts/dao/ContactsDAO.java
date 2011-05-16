package idv.hic.android.lazycontacts.dao;

import idv.hic.android.dao.GenericPagingDAO;
import idv.hic.android.lazycontacts.model.Contact;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Contacts.Photo;
import android.provider.ContactsContract.Data;

public class ContactsDAO extends GenericPagingDAO<Contact> {

	
	Context mContext;

	String[] dbfields = { Data._ID, Data.DISPLAY_NAME, Data.HAS_PHONE_NUMBER ,"sort_key" };

	
	public ContactsDAO(Context context){
		this.mContext=context;
		
	}
	
	
	@Override
	public Cursor getCursor(int limit, int offset) {
		// TODO Auto-generated method stub
		String OrderBy = Data.DISPLAY_NAME + " COLLATE LOCALIZED ASC ";
		if (limit != 0) {
			OrderBy = String.format(OrderBy + "limit %s,%s", offset, limit);
		}

		Cursor c = mContext.getContentResolver().query(Contacts.CONTENT_URI,
				dbfields, Data.IN_VISIBLE_GROUP + "='1'", null, OrderBy);

		return c;
	}

	@Override
	public Contact setMapping(Cursor c) {

		Contact contact = new Contact();
		contact.setId(c.getString(c.getColumnIndex(Data._ID)));
		contact.setName(c.getString(c.getColumnIndex(Data.DISPLAY_NAME)));
		contact.setSortKey(c.getString(c.getColumnIndex("sort_key")));
		
		// 設定PHONE

		if (c.getInt(c.getColumnIndex(Data.HAS_PHONE_NUMBER)) > 0) {
			List<String> phone = new ArrayList<String>();

			Cursor phone_cursor = this.getPhoneCursor(Long.parseLong(contact
					.getId()));

			while (phone_cursor.moveToNext()) {
				phone.add(phone_cursor.getString(phone_cursor
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
			}
			
			phone_cursor.close();
			
			
			contact.setPhone(phone);

		}

		// contact.setRawId(c.getString(c.getColumnIndex(Data.RAW_CONTACT_ID)));
		// contact.setContactType(c.getString(c.getColumnIndex(Data.)));
		return contact;
	}

	private Cursor getPhoneCursor(long id) {
		Cursor c = mContext.getContentResolver().query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
				ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=?",
				new String[] { String.valueOf(id) }, null);

		return c;
	}

	
	private Cursor getRawCursor(long id){
		Cursor c = mContext.getContentResolver().query(
				ContactsContract.RawContacts.CONTENT_URI, null,
				ContactsContract.RawContacts.CONTACT_ID + "=?",
				new String[] { String.valueOf(id) }, null);

		return c;
		
	}
}
