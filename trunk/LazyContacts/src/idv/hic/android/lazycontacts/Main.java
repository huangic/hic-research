package idv.hic.android.lazycontacts;

import com.google.inject.Injector;

import roboguice.activity.RoboActivity;
import roboguice.inject.InjectResource;
import roboguice.inject.InjectView;
import roboguice.util.Ln;
import android.os.Bundle;
import android.widget.TextView;

public class Main extends RoboActivity {
    /** Called when the activity is first created. */
    
	@InjectView(R.id.tv_hello)
	TextView mTextView;
	
	
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        mTextView.setText("Inject Success!!");
        Ln.d("Success");
        
    }
	
	
	
}