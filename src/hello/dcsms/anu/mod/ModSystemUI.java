package hello.dcsms.anu.mod;

import hello.dcsms.anu.Utils.PrefHelper;
import hello.dcsms.anu.Utils.XLog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.TextView;
import de.robv.android.xposed.XSharedPreferences;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LayoutInflated;
import de.robv.android.xposed.callbacks.XC_LayoutInflated.LayoutInflatedParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class ModSystemUI {
	private static Context mContext;
	private static String SUI = "com.android.systemui";
	private static String STATUSBAR = "super_status_bar";// "status_bar_simple";
	static LinearLayout NOTIFICON;
	static TextView SPEED;
	static LinearLayout STATUSICON;
	static LinearLayout SIGNALICON;
	static ImageView BATTICON;
	static TextView CLOCK;
	static LinearLayout ROOT;
	static RelativeLayout ML;

	private static int getViewById(LayoutInflatedParam l, String id) {
		return l.res.getIdentifier(id, "id", SUI);
	}

	private static BroadcastReceiver receiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context c, Intent i) {
			String act = i.getAction();
			if (act.equals("hello.dcsms.ANUIN")) {
				AnuinStatusbar();
			}
			XLog.log("ON Receive Intent : " + i.getAction());

		}
	};

	private static void AnuinStatusbar() {
		int mstyle = 0;
		int wrap_content = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
		try {
			mstyle = Integer.parseInt(PrefHelper
					.getStringVal("anu_layout_style"));
		} catch (Exception e) {
			XLog.log(e.getMessage());
		}
		switch (mstyle) {
		case 0:			
			CLOCK.setLayoutParams(RLParam.KANAN(wrap_content));
			BATTICON.setLayoutParams(RLParam.LEFT_OF(CLOCK.getId()));	
			SIGNALICON.setLayoutParams(RLParam.LEFT_OF(BATTICON.getId()));
			STATUSICON.setLayoutParams(RLParam.LEFT_OF(SIGNALICON.getId()));
			SPEED.setLayoutParams(RLParam.LEFT_OF(STATUSICON.getId()));
			NOTIFICON.setLayoutParams(RLParam.KIRI(wrap_content));			
			break;

		case 1:
			SIGNALICON.setLayoutParams(RLParam.KIRI(wrap_content));
			BATTICON.setLayoutParams(RLParam.KANAN(wrap_content));
			CLOCK.setLayoutParams(RLParam.TENGAH(wrap_content));
			STATUSICON.setLayoutParams(RLParam.LEFT_OF(BATTICON.getId()));
			NOTIFICON.setLayoutParams(RLParam.RIGHT_OF(SIGNALICON.getId()));
			SPEED.setLayoutParams(RLParam.LEFT_OF(STATUSICON.getId()));
			break;
		case 2:
			BATTICON.setLayoutParams(RLParam.KIRI(wrap_content));
			SIGNALICON.setLayoutParams(RLParam.KANAN(wrap_content));			
			CLOCK.setLayoutParams(RLParam.TENGAH(wrap_content));
			STATUSICON.setLayoutParams(RLParam.LEFT_OF(SIGNALICON.getId()));
			SPEED.setLayoutParams(RLParam.LEFT_OF(STATUSICON.getId()));
			NOTIFICON.setLayoutParams(RLParam.LEFT_OF(SPEED.getId()));
		
			break;
		case 3:
			CLOCK.setLayoutParams(RLParam.KIRI(wrap_content));
			BATTICON.setLayoutParams(RLParam.RIGHT_OF(CLOCK.getId()));
			SIGNALICON.setLayoutParams(RLParam.RIGHT_OF(BATTICON.getId()));
			STATUSICON.setLayoutParams(RLParam.RIGHT_OF(SIGNALICON.getId()));			
			SPEED.setLayoutParams(RLParam.RIGHT_OF(STATUSICON.getId()));
			NOTIFICON.setLayoutParams(RLParam.RIGHT_OF(SPEED.getId()));
			break;
		case 4:
			CLOCK.setLayoutParams(RLParam.KIRI(wrap_content));
			BATTICON.setLayoutParams(RLParam.KANAN(wrap_content));	
			SIGNALICON.setLayoutParams(RLParam.LEFT_OF(BATTICON.getId()));
			STATUSICON.setLayoutParams(RLParam.LEFT_OF(SIGNALICON.getId()));
			SPEED.setLayoutParams(RLParam.LEFT_OF(STATUSICON.getId()));
			NOTIFICON.setLayoutParams(RLParam.LEFT_OF(SPEED.getId()));	
			break;
		}


	}

	public static void handleInitPackageResources(
			InitPackageResourcesParam resparam) {
		resparam.res.hookLayout(SUI, "layout", STATUSBAR,
				new XC_LayoutInflated() {

					@Override
					public void handleLayoutInflated(LayoutInflatedParam liparam)
							throws Throwable {

						mContext = liparam.view.getContext();
						ROOT = (LinearLayout) liparam.view
								.findViewById(getViewById(liparam, "icons"));
						ML = new RelativeLayout(mContext);

						/*
						 * LinearLayout --carrier --LinearLayout
						 * ----statusbariconview ----iconmerge
						 */
						NOTIFICON = (LinearLayout) ROOT.getChildAt(0);
						/*
						 * NetworkSpeedMeter
						 */
						SPEED = (TextView) ROOT.getChildAt(1);
						// SDtatusicon
						STATUSICON = (LinearLayout) ROOT.getChildAt(2);
						// Sinyal
						SIGNALICON = (LinearLayout) ROOT.getChildAt(3);
						// Battery
						BATTICON = (ImageView) ROOT.getChildAt(4);
						// Clock
						CLOCK = (TextView) ROOT.getChildAt(5);

						ROOT.removeView(NOTIFICON);
						ROOT.removeView(SPEED);
						ROOT.removeView(STATUSICON);
						ROOT.removeView(SIGNALICON);
						ROOT.removeView(BATTICON);
						ROOT.removeView(CLOCK);
						ML.addView(NOTIFICON);
						ML.addView(SPEED);
						ML.addView(STATUSICON);
						ML.addView(SIGNALICON);
						ML.addView(BATTICON);
						ML.addView(CLOCK);
						ROOT.addView(ML);
						AnuinStatusbar();
						IntentFilter filter = new IntentFilter();
						filter.addAction("hello.dcsms.ANUIN");
						filter.addAction(Intent.ACTION_TIME_TICK);
						filter.addAction(Intent.ACTION_BOOT_COMPLETED);
						filter.addAction(Intent.ACTION_TIME_CHANGED);
						filter.addAction(Intent.ACTION_BATTERY_CHANGED);
						filter.addAction(Intent.ACTION_CONFIGURATION_CHANGED);
						mContext.registerReceiver(receiver, filter);
						// ImageView baterai = (ImageView) liparam.view
						// .findViewById(getViewById(liparam, "battery"));
						// LinearLayout statusIcons = (LinearLayout)
						// liparam.view
						// .findViewById(getViewById(liparam,
						// "statusIcons"));

					}
				});
	}

	public static void handleLoadPackage(LoadPackageParam lpparam) {

	}

}
