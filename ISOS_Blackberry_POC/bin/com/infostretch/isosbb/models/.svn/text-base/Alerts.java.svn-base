package com.infostretch.isosbb.models;

import java.util.Vector;

/**
 * Stores and retrieves Push Message alerts.
 * @author Amit Gupta
 */
public class Alerts {
	
	private static Alerts alerts;
	private Vector alertsData;
	private String TimeString;
	
	public String getTimeString() {
		return TimeString;
	}

	public void setTimeString(String timeString) {
		TimeString = timeString;
	}

	private Alerts() {
		alertsData = new Vector();
	}
	
	public static Alerts getInstance() {
		if(alerts == null) {
			alerts = new Alerts();
//			alerts.addAlertsData("Test2");
		}
		return alerts;
	}

	public Vector getAlertsData() {
		//System.out.println("AlertData ******* "+alertsData);
		//LoggerText.addLog3("AlertDate ******* "+alertsData);
		return alertsData;
	}

	public void addAlertsData(String MessageString) {
		this.alertsData.addElement(MessageString + "$$" + getTimeString());
	}
	

}
