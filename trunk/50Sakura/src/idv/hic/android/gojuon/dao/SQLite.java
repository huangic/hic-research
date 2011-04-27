package idv.hic.android.gojuon.dao;

import idv.hic.android.gojuon.R;
import idv.hic.android.gojuon.R.raw;
import idv.hic.util.ProjectUtil;
import idv.hic.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class SQLite extends SQLiteOpenHelper  {
	private static final String LOGTAG=ProjectUtil.LOGTAG;
	
	private static final String DATABASE_NAME = "gojuon.db";	//資料庫名稱
	private static final int DATABASE_VERSION = 1;	//資料庫版本
 
	//private SQLiteDatabase db;
 
	private Context mContext;
	
	
	
	public SQLite(Context context) {	//建構子
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		this.mContext=context;
		
		//db = this.getWritableDatabase();
	   
	}
	
		
	

	@Override
	public void onCreate(SQLiteDatabase db ) {
		 //建立基本資料庫

		//DAO TEST
	
		
		
		String Sql;
		//建立字元檔
		//letter{pk,letter.order}
		Sql="CREATE TABLE letter(id INTEGER PRIMARY KEY,letter,letter_order,err_count INTEGER DEFAULT 0 NOT NULL,crr_count INTEGER DEFAULT 0 NOT NULL,total_count INTEGER DEFAULT 0 NOT NULL);";
		
		
		db.execSQL(Sql);
		
		//建立字元讀音
	   //phonics {pk,letter_id(fk),phonic}
		
        Sql="CREATE TABLE phonics(letter_id,phonic);";
		db.execSQL(Sql);
		
		
		
		//分類表
		//cat {pk cat_code,cat_name}
		Sql="CREATE TABLE cat(cat_code,cat_name,cat_group,cat_order);";
		db.execSQL(Sql);
		//建立
		
		
				
		
		//字母分類
		//letter_cat{pk,cat(fk)letter(fk)}
		
		Sql="CREATE TABLE letter_cat(cat_code,letter_id);";
		db.execSQL(Sql);
		
		this.initDBFromSql(db);
		
		
		//歷史紀錄與熟練
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db,int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
		

	}
	
	
	public Cursor getAllVocalCat() {
	    SQLiteDatabase db=getReadableDatabase();
		return db.rawQuery("SELECT * FROM cat where cat_group='vocal'", null);
	}
	
	
	public Cursor getLetterByVocalAndType(String vocal,String type){
		SQLiteDatabase db=getReadableDatabase();
		String sql="select a.* from letter a,letter_cat b " + 
					"where a.id=b.letter_id and b.cat_code=? " +
					"intersect " +
					"select a.* from letter a,letter_cat b " +
					"where a.id=b.letter_id and b.cat_code=? order by a.letter_order";
		return db.rawQuery(sql, new String[]{vocal,type});
	} 
	
	
	private void initDBFromXml(SQLiteDatabase db){
		
		
		
		//讀取xml初始話資料庫
		 Resources res=mContext.getResources();
		   XmlResourceParser xrp=res.getXml(R.xml.initdb);
	
		
		try{
		xrp.next();
		
	   boolean inTitle=false;
	   while (xrp.getEventType() != XmlResourceParser.END_DOCUMENT) { 
		     if (xrp.getEventType() == XmlResourceParser.START_TAG) { 
		          String s = xrp.getName(); 
		          if (s.equals("execute")) { 
		               // Get the resource id; this will be retrieved as a 
		               // resolved hex value. 
		        	  inTitle=true;
		        	         	  
		          }
		     }else if(xrp.getEventType()==XmlResourceParser.TEXT&&inTitle){
	        	  String execute=xrp.getText();
	        	  Log.d("init_db",execute);
	        	  db.execSQL(execute);		
	        	  inTitle=false;
	          }
		xrp.next();
	   }	
	   
	  
		}catch(Exception ex){
			
			Log.e("Extra", ex.getMessage());
			
		}finally{
			xrp.close();
			
		}
		
	}

	
	private void initDBFromSql(SQLiteDatabase db){
		InputStream inputStream = mContext.getResources().openRawResource(R.raw.gojuon);

        InputStreamReader inputreader = new InputStreamReader(inputStream);
        BufferedReader buffreader = new BufferedReader(inputreader);
         String line;
        
         try {
           while (( line = buffreader.readLine()) != null) {
        	   Log.d("init_db",line);
	           try{
        	   db.execSQL(line);
	           }catch(Exception ex){
	        	   Log.d("init_db_error", ex.getMessage());
	        	   
	           }
             }
       } catch (IOException e) {
           //return null;
       }
        
	}

	public Cursor getQuizLetter(String type,List<String> vocals){
		SQLiteDatabase db=getReadableDatabase();
		String sql="select a.* from letter a,letter_cat b " +
					"where b.letter_id=a.id and b.cat_code=? " +
					"and id in ( " +
					"select id from letter c,letter_cat d " +
					"where c.id=d.letter_id " +
					"and (cat_code in ("+StringUtils.ListToComma(vocals)+" ) ) )";
		return db.rawQuery(sql, new String[]{type});
		
	}
	
	public Cursor getQuizLetter(List Ids,int Num){
		SQLiteDatabase db=getReadableDatabase();
		String sql="select * from letter " + 
					"where id in ("+StringUtils.ListToComma(Ids)+") Order by RANDOM() limit " +Num;
					
		return db.rawQuery(sql, null);
		
	}
	
	public List<Integer> getPhonicLetterIds(String phonic){
		SQLiteDatabase db=getReadableDatabase();
		String sql="select letter_id from phonics where phonic =?";
		
		List<Integer> list=new ArrayList<Integer>();
		Cursor c=db.rawQuery(sql, new String[]{phonic});
		
		while(c.moveToNext()){
			list.add(c.getInt(0));
		}
		
		c.close();
		return list ;
	}
	
	public void updateLetterCorrentRate(int letter_id,boolean correct){
		SQLiteDatabase db=getWritableDatabase();
		int err=0;
		int crr=0;
		
		if(correct){
			crr=1;		
		}else{
			err=1;
			
		}
		
		String sql="update letter set " +
				"'err_count'='err_count'+"+err+"," +
				"'crr_count'='crrcount'+"+crr+"," +
				"'total_count'='total_count'+1 where id="+letter_id;
		db.execSQL(sql);
		 Log.d("init_db",sql);
		//db.update(table, values, whereClause, whereArgs)
		db.close();
	}
	
}
