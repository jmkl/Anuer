package hello.dcsms.anu.mod;

import hello.dcsms.anu.Utils.XLog;
import android.content.res.XModuleResources;
import android.content.res.XResources;
import de.robv.android.xposed.IXposedHookInitPackageResources;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.IXposedHookZygoteInit;
import de.robv.android.xposed.callbacks.XC_InitPackageResources.InitPackageResourcesParam;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class Modul implements IXposedHookZygoteInit, IXposedHookLoadPackage,
		IXposedHookInitPackageResources {
	private String MOD_PATH = null;
	private String MODUL_NAMA_PAKET = "hello.dcsms.anu";
	private String SYSTEMUI = "com.android.systemui";
	private String LAUNCHER = "com.miui.home";

	@Override
	public void initZygote(StartupParam startupParam) throws Throwable {
		MOD_PATH = startupParam.modulePath;
	}

	@Override
	public void handleInitPackageResources(InitPackageResourcesParam resparam)
			throws Throwable {
		XModuleResources modres = XModuleResources.createInstance(MOD_PATH,
				resparam.res);
		if (resparam.packageName.equals(SYSTEMUI))
			try {
				ModSystemUI.handleInitPackageResources(resparam);
			} catch (Exception e) {
				XLog.log(e.getMessage());
			}
		else if (resparam.packageName.equals(LAUNCHER))
			try {
				ModLauncher.handleInitPackageResources(resparam);
			} catch (Exception e) {
				XLog.log(e.getMessage());
			}

	}

	@Override
	public void handleLoadPackage(LoadPackageParam lpparam) throws Throwable {
		String paket = lpparam.packageName;
		if (paket.equals(SYSTEMUI))
			try {
				ModSystemUI.handleLoadPackage(lpparam);
			} catch (Exception e) {
				XLog.log(e.getMessage());
			}
		else if (paket.equals(LAUNCHER))
			ModLauncher.handleLoadPackage(lpparam);
	}

}
