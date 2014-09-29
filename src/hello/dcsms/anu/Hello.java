package hello.dcsms.anu;

import android.app.FragmentTransaction;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class Hello extends FragmentActivity {


	private Setting s;

	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}

	@Override
	protected void onCreate(Bundle arg0) {
		setTheme(0x060d003a);
		super.onCreate(arg0);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.rgb(255, 0, 48)));
		setContentView(R.layout.hello);

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		if (s == null)
			s = new Setting();
		ft.replace(R.id.shithead, s);
		ft.commit();
	}

	
}
