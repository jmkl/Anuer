package hello.dcsms.anu.Utils;

import android.util.Log;

public class XLog {
	private static boolean DEBUG = true;
public static void log(String msg){
	if(DEBUG)Log.e("ANUER", msg);
}
}
