package idv.hic.android.gojuon.service;

import idv.hic.android.gojuon.Letter;
import idv.hic.android.gojuon.PrefValue;
import idv.hic.android.gojuon.dao.SQLite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;



/**
 * @author hic
 *	passed preferences parameter to generate quiz letter list
 *
 *
 */
public class QuizService {
	
	Context mContext=null;
	
	SQLite dbHelper=null;
	
	public QuizService(Context context){
		
		this.mContext=context;
		this.dbHelper=new SQLite(mContext);
	}
	
	private static String LOGTAG=QuizService.class.toString();
	
	public List<Letter> getQuizLetter(){
		//取得參數
		
		
		
		List<Integer> Ids=new ArrayList<Integer>();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mContext);

		PrefValue prefValue=new PrefValue(prefs);
		
		//依照參數送回亂數字母

		List<String> vocals=new ArrayList<String>();
		//取平假名
		if(prefValue.isHiragana()){
			if(prefValue.isHira_vocal_1())
				vocals.add("vocal_1");
			if(prefValue.isHira_vocal_2())
				vocals.add("vocal_2");
			if(prefValue.isHira_vocal_3())
				vocals.add("vocal_3");
			
			Cursor c=dbHelper.getQuizLetter("type_1", vocals);
			int Type1Num=c.getCount();
			c.moveToFirst();
			for(int i=0;i<Type1Num;i++){
				Ids.add(c.getInt(0));
				c.moveToNext();
			}
			c.close();
		}
		
		//get cursor
		
		
		
		
		
		//取片假名
		vocals.clear();
		if(prefValue.isKatakana()){
			if(prefValue.isKata_vocal_1())
				vocals.add("vocal_1");
			if(prefValue.isKata_vocal_2())
				vocals.add("vocal_2");
			if(prefValue.isKata_vocal_3())
				vocals.add("vocal_3");
		
			Cursor c=dbHelper.getQuizLetter("type_2", vocals);
			int Type2Num=c.getCount();
			c.moveToFirst();
			for(int i=0;i<Type2Num;i++){
				Ids.add(c.getInt(0));
				c.moveToNext();
			}
			c.close();
		}
		
		
		
		//取出LETTER
		
		
		//將來要改為加權
		Cursor letter_cursor=dbHelper.getQuizLetter(Ids, prefValue.getQuiznum());
		
		int rowNum = letter_cursor.getCount();
		letter_cursor.moveToFirst();

		List<Letter> itemList = new LinkedList<Letter>();

		for (int i = 0; i < rowNum; i++) {

			Letter currentItem = new Letter(letter_cursor.getInt(0), letter_cursor.getString(1),
					letter_cursor.getInt(2), letter_cursor.getInt(3), letter_cursor.getInt(4), letter_cursor.getInt(5));

			itemList.add(currentItem);
			letter_cursor.moveToNext();
		}
		
		letter_cursor.close();
		
	
		this.setPhonics(itemList);
		
		
		//確認一下 數量是否正確 如果小於測驗數 那就亂數拿
		int quizNum=prefValue.getQuiznum();
		int itemSize=itemList.size();
		
		if(itemSize< prefValue.getQuiznum()){
				
			List<Letter> newItemList=new LinkedList<Letter>();
			
			Random rnd=new Random();
			
			for(int i =0;i<quizNum;i++){
				int index=rnd.nextInt(itemSize-1);
				try{
				newItemList.add((Letter)itemList.get(index).clone());
				}catch(CloneNotSupportedException ex){
					Log.d(LOGTAG, ex.getMessage());
					
				}
			}
			
			
			
			
			return newItemList;
		}
		
		
		
		return itemList;
		
	}

	public void CheckQuizLetter(Letter letter,String phonic){
		//比對這讀音的ID是否為 LETTER 的字
		
		
		phonic=phonic.toLowerCase();
		
		//取出對應的IDS
		
		
		List<Integer> Ids=dbHelper.getPhonicLetterIds(phonic);
		
		boolean correct=false;
		if(Ids.indexOf(letter.getId())!=-1){
			
			correct=true;
		
		
		}
		letter.setCorrect(correct);
		
		letter.setCurrent(false);
		letter.setUsed(true);
		
		dbHelper.updateLetterCorrentRate(letter.getId(),correct);
		
		
	}
	
	public void setPhonics(List<Letter> letters){
		//read all letter and set its phonic(s)
		for(Letter l :letters){
		List<String> phonics= this.dbHelper.getPhonics(l.getId());
			if(phonics.size()>0){
			StringBuffer sb=new StringBuffer();
			for(String s:phonics){
			sb.append("[").append(s).append("]").append("\n");
				}
			l.setPhonics(sb.substring(0, sb.length()-1).toString());
			}
			
		}
		
	}
	
	public void close(){
		this.dbHelper.close();
		
	}
	
	public List<Letter> getExpLetter(String vocal_cat,String mType){
		 Cursor c=this.dbHelper.getLetterByVocalAndType(vocal_cat, mType);
	        
	        int rowNum=c.getCount();
	        c.moveToFirst();
	        
	        List<Letter> itemList=new LinkedList<Letter>();
	        
	        Letter perviousItem=null;
	        
	        for(int i=0;i<rowNum;i++){
	        	
	        	Letter currentItem=new Letter(c.getInt(0), c.getString(1), c.getInt(2), c.getInt(3), c.getInt(4), c.getInt(5));
	        	
	        	//取目前筆跟上壹筆決定插入幾筆mock字
	        	if(i>0){
	              	int minus=currentItem.getOrder()-perviousItem.getOrder();
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
	        
	        
	        this.setPhonics(itemList);
	        
		return itemList;
	}
}
