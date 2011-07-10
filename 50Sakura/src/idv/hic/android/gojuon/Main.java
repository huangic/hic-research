package idv.hic.android.gojuon;

import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;

public class Main extends BaseActivity {
    /** Called when the activity is first created. */
    
	WebView mWebView;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
        setContentView(R.layout.main);
       
        mWebView=(WebView)findViewById(R.id.readme);
        
        
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);


        
        mWebView.loadUrl("file:///android_asset/html/"+getString(R.string.readme));
        
    }
    
	
	    
}