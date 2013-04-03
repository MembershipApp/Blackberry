package com.infostretch.isosbb.ui;

import java.util.Timer;
import java.util.TimerTask;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.system.Display;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.container.FullScreen;
import net.rim.device.api.ui.container.MainScreen;

public class SplashScreen extends MainScreen{

	private SplashScreen instance;
	private Bitmap bmp;
	MainScreen next;
	public SplashScreen(MainScreen next) {
		instance = this;
		this.next = next;
		bmp = new Bitmap(Display.getWidth(), Display.getHeight());
		Bitmap src = Bitmap.getBitmapResource("splash.png");
		src.scaleInto(bmp, Bitmap.FILTER_BOX);
		add(new BitmapField(bmp));
		Timer t = new Timer();
		t.schedule(new SplashTimer(), 10 * 1000);
//		this.addMenuItem(logMenuItem);
	}
	
	private class SplashTimer extends TimerTask {

		public void run() {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					UiApplication.getUiApplication().popScreen(instance);
					UiApplication.getUiApplication().pushScreen(next);
				}
			});
		}
		
	}
}
