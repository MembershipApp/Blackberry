package com.infostretch.isosbb.ui;

import com.infostretch.isosbb.push.LoggerText;
import com.infostretch.isosbb.util.Utilities;

import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.MainScreen;
/***
 * 
 * Screen to display application logs.
 * @author Amit Gupta
 *
 */
public class LogScreen extends MainScreen {
	
	private RichTextField logField;

	public LogScreen() {
		deleteAll();
		logField = new RichTextField(LoggerText.txt.toString());
		add(logField);
	}
	
	MenuItem clearMenuItem = new MenuItem("Clear", 9, 9) {
		
		public void run() {
			LoggerText.removeLog();
			logField.setText("");
		}
	};
	
	MenuItem emailMenuItem = new MenuItem("Email Logs", 9, 9) {
		
		public void run() {
			Utilities.sendEmail(logField.getText().getBytes());
		}
	};
	
	protected void makeMenu(Menu menu, int instance) {
		menu.add(clearMenuItem);
		menu.add(emailMenuItem);
	}
}
