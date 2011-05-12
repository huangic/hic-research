package idv.hic.android.util;

import java.util.Collection;

public class StringUtils {
	
	/**
	 * @param list
	 * @return comma String
	 */
	public static String ListToComma(Collection<?> list){
		StringBuffer sb=new StringBuffer();
		
		for(Object o:list){
				sb.append(",'").append(o.toString()).append("'");
		}
	return sb.substring(1);
		
	}
	
	
}
