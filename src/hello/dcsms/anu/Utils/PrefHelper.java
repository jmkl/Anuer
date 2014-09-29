package hello.dcsms.anu.Utils;

import de.robv.android.xposed.XSharedPreferences;

public class PrefHelper {
	private static String MODUL_NAMA_PAKET = "hello.dcsms.anu";

	public static XSharedPreferences get() {
		XSharedPreferences pref = new XSharedPreferences(MODUL_NAMA_PAKET);
		pref.makeWorldReadable();
		return pref;
	}
	public static String getStringVal(String key){
		XSharedPreferences pref = new XSharedPreferences(MODUL_NAMA_PAKET);
		pref.makeWorldReadable();
		return pref.getString(key, "0");
	}
}
