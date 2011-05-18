package idv.hic.android.lazycontacts.adapter;


import idv.hic.android.lazycontacts.R;
import idv.hic.android.lazycontacts.model.ContactGroup;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SimpleContactGroupAdapter extends BaseAdapter  {

	private List<ContactGroup> items;
	
	private LayoutInflater mInflater;
	
	private Context mContext;
	
	public SimpleContactGroupAdapter(Context context,List<ContactGroup> list){
		
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
		return this.items.get(arg0).getId();
		
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		
		// TODO Auto-generated method stub
		if(convertView==null){
			convertView=mInflater.inflate(R.layout.lazycontacts_contact_group_item,null);
			holder=new ViewHolder();
			holder.name=(TextView)convertView.findViewById(R.id.contact_item_name);
					
			
			
			convertView.setTag(holder);			
		}else{
			holder=(ViewHolder)convertView.getTag();
			
		}
		
		ContactGroup c=this.items.get(position);
			
		holder.name.setText(c.getTitle()+"("+c.getSummaryHasPhoneCount()+")");
			
		
		//convertView.setClickable(false);
		return convertView;
	}
	
	
	static class ViewHolder{
		ImageView icon;
		
		
		TextView name;
		
		
	}


	
	


	
	

}
