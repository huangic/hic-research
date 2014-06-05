package idv.hic.android.dao;

import java.util.LinkedList;
import java.util.List;

import android.database.Cursor;

/**分頁用泛型DAO物件
 * @author Hic
 *
 * @param <T>
 */
public abstract class GenericPagingDAO<T> {
	
	
	
		
	
	public abstract Cursor getCursor(int limit,int offset);
	
	public Cursor getCursor(){
		
		return getCursor(0,0);
	}
	
	public int getAllCount(){
		Cursor c = getCursor();
		int total = c.getCount();
		c.close();
		return total;
		
	}
	
	public List<T> fetchAll(){
		
		return fetch(0,0);
		
	}
	
	public List<T> fetch(int limit,int offset){
		List<T> list = new LinkedList<T>();

		Cursor c = getCursor(limit, offset);

		while (c.moveToNext()) {
			T t = setMapping(c);
			
			list.add(t);

		}
		c.close();

		return list;
		
	}
	
	
	public abstract T setMapping(Cursor c);
	
}
