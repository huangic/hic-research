package idv.hic.android.lazycontacts.service;

import idv.hic.android.lazycontacts.dao.DBHelper;
import idv.hic.android.lazycontacts.model.SearchIndex;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;


public class IndexService {

	
	






	Context mContext;
	
	
	public IndexService(Context mContext) {
		this.mContext = mContext;
	}
	
	
	
	public List<SearchIndex> getAll(){
		Dao<SearchIndex, Long> indexDao;
		List<SearchIndex> result=null;
		
		
		
		
		try {
			indexDao=new DBHelper(mContext).getDao(SearchIndex.class);
			
			result=indexDao.queryForAll();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return result;
		
	}
	
}
