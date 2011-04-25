package idv.hic.android.gojuon;

import idv.hic.android.gojuon.dao.SQLite;

import java.util.LinkedList;
import java.util.List;

import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

public class  QuizFragment extends Fragment {
	
	GridView mGridView;
	
	String mType="type_1";// Letter type {type_1,type_2}
	SQLite dbHelper=new SQLite(this.getActivity());
	 @Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 mGridView=(GridView)this.getActivity().findViewById(R.id.quiz_gridview);
		
		this.initGridView();
	}
	 
	 @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                             Bundle savedInstanceState) {
	        // Inflate the layout for this fragment
	        return inflater.inflate(R.layout.quiz_frag, container, false);
	    }
	
	 
	 
		private void initGridView(){
			//抓看看有沒有extra
		       try{
		        String vocal_cat="vocal_1";
		        
		        
		        
		        Cursor c=this.dbHelper.getLetterByVocalAndType(vocal_cat, mType);
		        
		        int rowNum=c.getCount();
		        c.moveToFirst();
		        
		        List<Letter> itemList=new LinkedList<Letter>();
		        
		        Letter perviousItem=null;
		        
		        for(int i=0;i<rowNum;i++){
		        	
		        	Letter currentItem=new Letter(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5));
		        	
		        	//取目前筆跟上壹筆決定插入幾筆mock字
		        	if(i>0){
		              	int minus=currentItem.order-perviousItem.order;
		        	    if(minus>1){
		        			//Add Mock Letter(null)
		        			for(int j=1;j<minus;j++){
		        				itemList.add(Letter.getMock());
		        			}
		        		}
		        	}
		        	perviousItem=currentItem;
		        	itemList.add(currentItem);
		        	c.moveToNext();
		        }
		        c.close();
		        
		        this.mGridView.setAdapter(new LetterAdapter(this.getActivity(), itemList));
		        
		        
		        
		       }catch(Exception ex){
		    	   
		    	 Log.d("Extra", ex.getMessage());
		    	   
		       }
			
		}




}
