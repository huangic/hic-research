package idv.hic.android.lazycontacts.dao;

import java.sql.SQLException;

import roboguice.util.Ln;

import idv.hic.android.lazycontacts.model.SearchIndex;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

import com.google.inject.Inject;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

public class DBHelper extends OrmLiteSqliteOpenHelper {

	
	
	
	private static final String DATABASE_NAME="lazycontacts.db";
	private static final int DATABASE_VERSION=1;
	
	
	
	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
		
	}

	@Override
	public void onCreate(SQLiteDatabase arg0, ConnectionSource arg1) {
		// TODO Auto-generated method stub
	
		
		Ln.d("Database Create!!");
		try {
			TableUtils.createTable(arg1, SearchIndex.class);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Ln.d("Database Create error");
		}
		

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, ConnectionSource arg1, int arg2,
			int arg3) {
		// TODO Auto-generated method stub

		try {
			TableUtils.dropTable(arg1, SearchIndex.class, true);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			Ln.d("Database Drop error");
		}
		
		this.onCreate(arg0, arg1);
		
		
	}
	
	@Override
	public void close() {
		// TODO Auto-generated method stub
		super.close();
	}

}
