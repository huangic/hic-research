package idv.hic.android.lazycontacts;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends Activity {
    /** Called when the activity is first created. */
    
	
	TextView mTextView;
	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lazycontacts_main);
        
        this.mTextView=(TextView)findViewById(R.id.tv_hello);
        
        
        mTextView.setText("Inject Success!!");
        //Ln.d("Success");
        
    }
	
	
	
}