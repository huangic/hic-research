package idv.hic.util;

import com.google.ads.AdRequest;
import com.google.ads.AdView;

public class AdmobUtils {
	public static void SetAdView(AdView ads){
		AdRequest request = new AdRequest();
		   request.setTesting(false);     
		   ads.loadAd(request);
		
	}
}
