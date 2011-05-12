package idv.hic.android.gojuon;

import idv.hic.android.util.AdmobUtils;
import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.ads.AdView;

public class AdmobPreference extends Preference {

	public AdmobPreference(Context context) {
		super(context, null);
	}

	public AdmobPreference(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	@Override
	protected View onCreateView(ViewGroup parent) {
		// override here to return the admob ad instead of a regular preference
		// display
		LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view=inflater.inflate(R.layout.admob_preference, null);
			AdView ads=(AdView) view.findViewById(R.id.ad);
			
			AdmobUtils.SetAdView(ads);
			
		return view;
		
		
	}

}
