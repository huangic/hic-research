package idv.hic.android.gojuon.adapter;

import idv.hic.android.gojuon.Letter;
import idv.hic.android.gojuon.R;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QuizLetterAdapter extends BaseAdapter {

	private List<Letter> items;

	private LayoutInflater mInflater;

	public QuizLetterAdapter(Context context, List<Letter> list) {

		mInflater = LayoutInflater.from(context);
		this.items = list;

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
		// return arg0;
		return this.items.get(arg0).getId();

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;

		// TODO Auto-generated method stub
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.letter_item, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.letter_item_name);
			holder.rate = (TextView) convertView
					.findViewById(R.id.letter_item_rate);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();

		}

		Letter l = this.items.get(position);

		// if(!l.getName().equals("")){
		holder.name.setText(l.getName());

		// }else{
		// holder.name.setText("");

		// }
		//init
		convertView.setBackgroundColor(Color.BLACK);
		holder.name.setTextColor(Color.WHITE);
		
		if (!l.isUsed()) {
			
			if (l.isCurrent()) {
				convertView.setBackgroundColor(Color.LTGRAY);
			}
		} else {

			
			if (l.isCorrent()) {
				holder.name.setTextColor(Color.GREEN);
			}else{	
			 holder.name.setTextColor(Color.RED);

			}

		}

		return convertView;
	}

	private class ViewHolder {
		TextView name;
		TextView rate;

	}

}
