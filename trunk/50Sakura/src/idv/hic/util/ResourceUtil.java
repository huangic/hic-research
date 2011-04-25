package idv.hic.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.util.Log;

public class ResourceUtil {
	
	
	public static int getResourceId(Context context,String folder,String resName){
		Log.d(ProjectUtil.LOGTAG, "PackageName:"+context.getPackageName());
		Log.d(ProjectUtil.LOGTAG, "folder:"+folder);
		Log.d(ProjectUtil.LOGTAG, "ResourceName:"+resName);
		
			
	//得到该图片的id(name 是该图片的名字，"drawable" 是该图片存放的目录，appInfo.packageName是应用程序的包)
	int resID = context.getResources().getIdentifier(resName, folder, context.getPackageName());
	return resID;
	}
	
	public static int getResurceStringId(Context context,String resName){
		return  getResourceId(context, "string", resName);
		
	}
	
	public static String getResurceString(Context context,String resName){
		
		return  context.getString(getResourceId(context, "string", resName));
		
	}
	
}
