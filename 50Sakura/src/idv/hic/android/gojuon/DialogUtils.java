package idv.hic.android.gojuon;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DialogUtils {
	
	public static Dialog getLetterDialog(Context context,Letter l){
		
		
		
		
		
		final Dialog dialog = new Dialog(context);  
        dialog.setContentView(R.layout.letter_dialog);  
        dialog.setTitle("50音字母");  
        dialog.setCancelable(true);  
        TextView textView01 = (TextView) dialog.findViewById(R.id.letter_item_name);
        TextView textView02 = (TextView) dialog.findViewById(R.id.dialog_phonic);
        textView01.setText(l.getName());  
        textView02.setText(l.getPhonics());
       
        
        
        
        Button btnCancel = (Button) dialog.findViewById(R.id.dialog_cancel);  
        
        btnCancel.setOnClickListener(new Button.OnClickListener() {  
            public void onClick(View view) {  
                dialog.cancel();  
            }  
        });  
		return dialog;
	}
}
