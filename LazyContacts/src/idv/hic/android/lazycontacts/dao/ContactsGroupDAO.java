package idv.hic.android.lazycontacts.dao;

import idv.hic.android.dao.GenericPagingDAO;
import idv.hic.android.lazycontacts.model.Contact;
import idv.hic.android.lazycontacts.model.ContactGroup;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Groups;;

public class ContactsGroupDAO extends GenericPagingDAO<ContactGroup> {

	
	Context mContext;

	String[] dbfields = { Data._ID, Groups.TITLE,Groups.SUMMARY_COUNT, Groups.SUMMARY_WITH_PHONES };

	
	public ContactsGroupDAO(Context context){
		this.mContext=context;
		
	}
	
	
	@Override
	public Cursor getCursor(int limit, int offset) {
		// TODO Auto-generated method stub
		String OrderBy = Groups.TITLE + " ASC ";
		if (limit != 0) {
			OrderBy = String.format(OrderBy + "limit %s,%s", offset, limit);
		}

		Cursor c = mContext.getContentResolver().query(Groups.CONTENT_SUMMARY_URI,
				dbfields, Groups.DELETED + "='0' and "+Groups.GROUP_VISIBLE+"='0'" , null, OrderBy);

		return c;
	}

	@Override
	public ContactGroup setMapping(Cursor c) {
		ContactGroup cg=new ContactGroup();
		
		cg.setId(c.getInt(0));
		cg.setTitle(c.getString(1));
		cg.setSummaryCount(c.getInt(2));
		cg.setSummaryHasPhoneCount(c.getInt(3));
		
		
		return cg;
	}

	
}
