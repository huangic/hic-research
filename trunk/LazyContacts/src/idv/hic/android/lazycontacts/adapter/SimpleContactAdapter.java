package idv.hic.android.lazycontacts.adapter;


import idv.hic.android.lazycontacts.R;
import idv.hic.android.lazycontacts.model.Contact;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class SimpleContactAdapter extends BaseAdapter {

	private List<Contact> items;
	
	private LayoutInflater mInflater;
	
	
	public SimpleContactAdapter(Context context,List<Contact> list){
		
		mInflater=LayoutInflater.from(context);
		this.items=list;
		
		
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
			convertView=mInflater.inflate(R.layout.contact_item,null);
			holder=new ViewHolder();
			holder.name=(TextView)convertView.findViewById(R.id.contact_item_name);
			convertView.setTag(holder);			
		}else{
			holder=(ViewHolder)convertView.getTag();
			
		}
		
		Contact c=this.items.get(position);
			
		holder.name.setText(c.getId()+","+c.getName()+","+c.getRawId());
		
		
		return convertView;
	}
	
	
	private class ViewHolder{
		TextView name;
		//TextView phonic;
		//TextView rate;
		
	}

}
