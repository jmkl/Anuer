package hello.dcsms.anu;

import java.io.File;

import hello.dcsms.anu.Utils.XLog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Setting extends PreferenceFragment implements
		OnSharedPreferenceChangeListener {
	private int mSettFrag = -1;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			mSettFrag = savedInstanceState.getInt("mSettingFrag");
		}
		addPreferencesFromResource(R.xml.settings);
		SharedPreferences pref = getActivity().getSharedPreferences(
				"hello.dcsms.anu_preferences", Context.MODE_WORLD_READABLE);

		pref.registerOnSharedPreferenceChangeListener(this);
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putInt("mSettingFrag", mSettFrag);
	}

	private void enablebutton(boolean enable) {

		if (enable) {
			File f = new File(
					"/data/data/hello.dcsms.anu/shared_prefs/hello.dcsms.anu_preferences.xml");
			f.setReadable(true, false);

			Intent i = new Intent();
			i.setAction("hello.dcsms.ANUIN");
			getActivity().sendBroadcast(i);
		}

	}

	@Override
	public void onSharedPreferenceChanged(
			SharedPreferences paramSharedPreferences, String paramString) {
		Handler h = new Handler();
		enablebutton(false);
		h.postDelayed(new Runnable() {

			@Override
			public void run() {
				enablebutton(true);

			}
		}, 1000);
		XLog.log("PREFERENCECHANGE");

	}
}