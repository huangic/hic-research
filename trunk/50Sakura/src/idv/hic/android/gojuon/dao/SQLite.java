package idv.hic.android.gojuon.dao;

import idv.hic.android.gojuon.R;
import idv.hic.util.ProjectUtil;
import idv.hic.util.StringUtils;

import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;

public class SQLite extends OrmLiteSqliteOpenHelper  {
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
	public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource ) {
		 //建立基本資料庫

		//DAO TEST
	
		
		
		String Sql;
		//建立字元檔
		//letter{pk,letter.order}
		Sql="CREATE TABLE letter(id INTEGER PRIMARY KEY,letter,letter_order,err_count INTEGER DEFAULT 0 NOT NULL,crr_count INTEGER DEFAULT 0 NOT NULL,total_count INTEGER DEFAULT 0 NOT NULL);";
		
		
		db.execSQL(Sql);
		
		//建立字元讀音
	   //phonics {pk,letter(fk),phonic}
		
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
		
		this.initDB(db);
		
		
		//歷史紀錄與熟練
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource,int oldVersion, int newVersion) {
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
	
	
	private void initDB(SQLiteDatabase db){
		
		
		
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
	        	  Log.d("Extra",execute);
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

	
}
