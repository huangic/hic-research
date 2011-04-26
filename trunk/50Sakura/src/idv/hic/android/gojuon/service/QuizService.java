package idv.hic.android.gojuon.service;

import idv.hic.android.gojuon.Letter;
import idv.hic.android.gojuon.PrefValue;
import idv.hic.android.gojuon.dao.SQLite;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;



/**
 * @author hic
 *	passed preferences parameter to generate quiz letter list
 *
 *
 */
public class QuizService {
	
	
	
	public List<Letter> getQuizLetter(Context context){
		//取得參數
		SQLite DbHelper=new SQLite(context);
		
		
		List<Integer> Ids=new ArrayList<Integer>();
		
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);

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
			
			Cursor c=DbHelper.getQuizLetter("type_1", vocals);
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
		
			Cursor c=DbHelper.getQuizLetter("type_2", vocals);
			int Type2Num=c.getCount();
			c.moveToFirst();
			for(int i=0;i<Type2Num;i++){
				Ids.add(c.getInt(0));
				c.moveToNext();
			}
			c.close();
		}
		
		
		
		//取出LETTER
		
		
		Cursor letter_cursor=DbHelper.getQuizLetter(Ids, prefValue.getQuiznum());
		
		int rowNum = letter_cursor.getCount();
		letter_cursor.moveToFirst();

		List<Letter> itemList = new LinkedList<Letter>();

		for (int i = 0; i < rowNum; i++) {

			Letter currentItem = new Letter(letter_cursor.getInt(0), letter_cursor.getString(1),
					letter_cursor.getInt(2), letter_cursor.getInt(3), letter_cursor.getInt(4), letter_cursor.getInt(5));

			itemList.add(currentItem);
			letter_cursor.moveToNext();
		}
		
		
		
		
		
		DbHelper.close();
		
		
		return itemList;
		
	}
}
