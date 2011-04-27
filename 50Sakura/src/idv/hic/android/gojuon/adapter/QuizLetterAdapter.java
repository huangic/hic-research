package idv.hic.android.gojuon.adapter;

import idv.hic.android.gojuon.Letter;
import idv.hic.android.gojuon.R;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.opengl.Visibility;
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
			convertView = mInflater.inflate(R.layout.letter_quizitem, null);
			holder = new ViewHolder();
			holder.name = (TextView) convertView
					.findViewById(R.id.letter_item_name);
			holder.phonic=(TextView) convertView
			.findViewById(R.id.letter_item_phonic);
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
		convertView.setBackgroundColor(Color.GRAY);
		holder.name.setTextColor(Color.WHITE);
		holder.phonic.setText("");
		if (!l.isUsed()) {
			
			if (l.isCurrent()) {
				convertView.setBackgroundColor(Color.LTGRAY);
				
			}
		} else {

			
			if (l.isCorrect()) {
				holder.name.setTextColor(Color.GREEN);
				 holder.phonic.setTextColor(Color.GREEN);
				holder.phonic.setText(l.getPhonics());
			}else{	
			 holder.name.setTextColor(Color.RED);
			 holder.phonic.setTextColor(Color.RED);
			 holder.phonic.setText(l.getPhonics());
			
			}

		}

		return convertView;
	}

	private class ViewHolder {
		TextView name;
		TextView phonic;

	}

}
