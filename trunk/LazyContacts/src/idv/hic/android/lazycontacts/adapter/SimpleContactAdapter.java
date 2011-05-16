package idv.hic.android.lazycontacts.adapter;


import greendroid.widget.AsyncImageView;
import idv.hic.android.lazycontacts.R;
import idv.hic.android.lazycontacts.model.Contact;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract.Contacts;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleContactAdapter extends BaseAdapter  {

	private List<Contact> items;
	
	private LayoutInflater mInflater;
	
	private Context mContext;
	
	public SimpleContactAdapter(Context context,List<Contact> list){
		
		mInflater=LayoutInflater.from(context);
		this.items=list;
		
		this.mContext=context;
		
	}
	
	
	@Override
	public int getCount() {
		
		return this.items.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		
	
		return this.items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		//return arg0;
		return Long.parseLong(this.items.get(arg0).getId());
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.lazycontacts_contact_item,null);
			holder=new ViewHolder();
			holder.name=(TextView)convertView.findViewById(R.id.contact_item_name);
			holder.icon=(ImageView)convertView.findViewById(R.id.contact_icon);
			
			
			
			
			
			holder.tel=(TextView)convertView.findViewById(R.id.contact_item_tel);
			
			
			convertView.setTag(holder);			
		}else{
			holder=(ViewHolder)convertView.getTag();
			
		}
		
		Contact c=this.items.get(position);
			
		holder.name.setText(c.getName());
		holder.tel.setText("");
		
		if(c.getPhone()!=null){
			
			holder.icon.setImageBitmap(c.getPhoto());
		}else{
			holder.icon.setImageResource(R.drawable.android_icon);
			
		}
		
		//Bitmap photo=this.openPhoto(Long.parseLong(c.getId()));
		//if(photo!=null){
		//holder.icon.setImageBitmap(photo);
		//}
		//
		
		List<String> phone=c.getPhone();
		if(phone!=null&&phone.size()>0){
			holder.tel.setText(phone.get(0));
		}
		
		//convertView.setClickable(false);
		return convertView;
	}
	
	
	static class ViewHolder{
		ImageView icon;
		
		
		TextView name;
		TextView tel;
		//TextView phonic;
		//TextView rate;
		
	}


	
	


	
	

}
