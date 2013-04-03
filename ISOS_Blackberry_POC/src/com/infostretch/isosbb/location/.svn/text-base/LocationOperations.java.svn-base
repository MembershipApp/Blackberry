//#preprocess
package com.infostretch.isosbb.location;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;

import net.rim.device.api.gps.BlackBerryCriteria;
import net.rim.device.api.gps.BlackBerryLocationProvider;
import net.rim.device.api.gps.GPSInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.util.MathUtilities;

import com.infostretch.isosbb.models.ISOSLocationInfo;
import com.infostretch.isosbb.models.Request;
import com.infostretch.isosbb.push.LoggerText;
import com.infostretch.isosbb.ui.BrowserFieldScreen;
import com.infostretch.isosbb.util.ConnectionUtil;
import com.infostretch.isosbb.util.CountryCode;
import com.infostretch.org.json.me.JSONArray;
import com.infostretch.org.json.me.JSONException;
import com.infostretch.org.json.me.JSONObject;

/***
 * 
 * Performs following operations using the location data.
 *  a. Does check-in if the user moves a distance >= 300 meters
 *  b. Does check-in every 15 minutes 
 * @author Amit Gupta
 *
 */
public class LocationOperations {

	private static final long DELAY = 15 * 60 * 1000;
	private static LocationOperations instance = null;
	private BlackBerryLocationProvider _locationProvider;
	private ISOSLocationInfo locationInfo;
	private String cachedUserName;
	private Timer autoCheckinTimer;
	private String country;
	private double totalDistance = 0;
	private int updateCnt = 0;
	private BlackBerryCriteria myCriteria;
	private boolean isAlternateProvider = false;
	private LocationListenerImpl locaitonListener;

	private LocationOperations() {

		ISOSLocationFetcher locaitonFetcher = new ISOSLocationFetcher(this);
		
		// Try to start the GPS thread that listens for updates.
//		if (startLocationUpdate()) {
			// If successful, start the thread that communicates with the
			// server.
			// startServerConnectionThread();
			locationInfo = new ISOSLocationInfo();
			locaitonListener = new LocationListenerImpl();
			locationInfo.setLatitude(0.0);
			locationInfo.setLongitude(0.0);
			autoCheckinTimer = new Timer();
			autoCheckinTimer.schedule(new LocationAutoCheckinTask(), DELAY,DELAY);
			String country = CountryCode.getCountryName();
			setCountry(country != null ? country: "");
//		}
	}

	public static LocationOperations getInstance() {
		if (instance == null) {
			instance = new LocationOperations();
		}
		return instance;
	}
	
	LocationListenerImpl getLocationListener(){
		return locaitonListener;
	}

	private boolean startLocationUpdate() {
		LoggerText.addLog("Inside the start Location update");

		try {
			myCriteria = new BlackBerryCriteria(GPSInfo.GPS_MODE_AUTONOMOUS);
//			 //#ifndef ISOS_PD
//			/*
//			//#endif
//			 myCriteria.enableGeolocationWithGPS();
//			//#ifndef ISOS_PD
//			 * 
//			 */
//			//#endif
			 
			_locationProvider = (BlackBerryLocationProvider) LocationProvider
					.getInstance(myCriteria);
			_locationProvider.setLocationListener(new LocationListenerImpl(),
					15, 15, 1);
			return true;
		} catch (UnsupportedOperationException uoex) {
			return false;
		}
		// Only a single listener can be associated with a provider, and
		// unsetting it
		// involves the same call but with null, therefore, no need to cache
		// the listener
		// instance request an update every second.

		catch (LocationException le) {
			System.err
					.println("Failed to instantiate the LocationProvider object, exiting...");
			System.err.println(le);
			LoggerText
					.addLog(new Date().toString()
							+ ": "
							+ " Failed to instantiate the LocationProvider object, exiting...");
			return false;
		}
	}

	public ISOSLocationInfo getLastLocation() {
		return locationInfo;
	}
    
	/***
	 * Performs check-in operation with the current location
	 * @param userName BrowserField Screen
	 * @param screen
	 * @param request
	 * @param isAutoCheckin
	 */
	public void performCheckinOperation(String userName,
			final BrowserFieldScreen screen, final Request request,
			final boolean isAutoCheckin) {
		final JSONObject json = new JSONObject();
		cachedUserName = userName;
		try {
			json.putOpt("UserName", cachedUserName);
			json.putOpt("Latitude", "" + locationInfo.getLatitude());
			json.putOpt("Longitude", "" + locationInfo.getLongitude());
			json.putOpt("DeviceType", "BB");
			json.putOpt("DeviceToken",Integer.toHexString(DeviceInfo.getDeviceId()));
			json.putOpt("timestamp", ""+System.currentTimeMillis());
		} catch (JSONException e) {
			System.out.println("Exception while creating JSOn: " + e);
		}
		if (!isAutoCheckin) {
			// WaitingScreen.showPopUP();
		}
		new Thread() {
			public void run() {
				System.out.println("Request: " + json.toString());
//				String response = ConnectionUtil.callService(
//						"http://202.131.96.148/isos/services/checkin/",
//						json.toString());
				String response = ConnectionUtil.callService(
						"http://202.131.96.148/isostesting/services/checkin/",json.toString());
				System.out.println(response);
				if (!isAutoCheckin) {
					// WaitingScreen.removePopUP();
					System.out.println("Callback: " + request.getJsonCallBack());
					try{
					screen.getBrowserField().executeScript(request.getJsonCallBack() + "(" + response + ")");
					}catch (Exception e) {
						LoggerText.addLog2("Exception executing Scritp:"+response);
					}
				}
				updateCountry(true);
			}
		}.start();
	}

	public void stopAutoChekin() {
		autoCheckinTimer.cancel();
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		LoggerText.addLog2(" COUNTRY:"+country);
		this.country = country;
	}

	/**
	 * Updates the country details on the UI.
	 * @param isForcefully
	 */
	private void updateCountry(boolean isForcefully) {
		BrowserFieldScreen screen = BrowserFieldScreen.getCopy();
		Boolean response = new Boolean(false);
		try{
			response = (Boolean) screen.getBrowserField().executeScript("isOnDashboard()");
			}catch (Exception e) {
				LoggerText.addLog2("Exception executing Scritp:isOnDashboard()");
			}
		if(screen != null && updateCnt == 0 && response.booleanValue()) {
			JSONObject obj = getCountryName();
			String country = obj.optString("country");
			if(country.length() > 0) {
				updateCnt++;
				JSONObject json = new JSONObject();
				try {
					json.put("country", country);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try{
					screen.getBrowserField().executeScript("setCountry(" + json + ")");
					}catch (Exception e) {
						LoggerText.addLog2("Exception executing Scritp:"+"setCountry(" + json + ")");
					}
			}
		} else if(isForcefully) {
			JSONObject obj = getCountryName();
			String country = obj.optString("country");
			if(country.length() > 0) {
				updateCnt++;
				JSONObject json = new JSONObject();
				try {
					json.put("country", country);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				try{
					screen.getBrowserField().executeScript("setCountry(" + json + ")");
					}catch (Exception e) {
						LoggerText.addLog2("Exception executing Scritp:"+"setCountry(" + json + ")");
					}
			}
		}
	}

	/**
	 * Fetches country name either using the mcc data or using the Google Revers geo coding API.
	 * @return Json Object containing country details
	 */
	private JSONObject getCountryName() {
		double lat = LocationOperations.getInstance().getLastLocation()
				.getLatitude();
		double lng = LocationOperations.getInstance().getLastLocation()
				.getLongitude();
		JSONObject retVal = null;
		String value = "";
		String country = CountryCode.getCountryName();
		if (country == null) {
			String response = ConnectionUtil.callService("http://maps.googleapis.com/maps/api/geocode/json?latlng="+ lat + "," + lng + "&sensor=false");
			try {
				JSONObject json = new JSONObject(response);
				JSONArray array = json.getJSONArray("results");
				if (array.length() > 0) {
					value = (String) array.getJSONObject(0).get("formatted_address");
					value = value.substring(value.lastIndexOf(',') + 1).trim();
				}
			} catch (Exception e) {
			}
		} else {
			value = country;
		}

		try {
			retVal = new JSONObject();
			retVal.put("lat", String.valueOf(lat));
			retVal.put("lng", String.valueOf(lng));
			retVal.put("country", value);
			LocationOperations.getInstance().setCountry(value);
		} catch (Exception e) {
			System.out.println("JSON Exception: " + e);
			LoggerText.addLog2(new Date().toString() + ": "
					+ " LocationOperations::Exception: " + e);
		}
		return retVal;
	}

	 class LocationListenerImpl implements LocationListener {
		// Members
		// ----------------------------------------------------------------------------------------------
		private int captureCount;
		private int sendCount;

		// Methods
		// ----------------------------------------------------------------------------------------------
		/**
		 * @see javax.microedition.location.LocationListener#locationUpdated(LocationProvider,Location)
		 */
		public void locationUpdated(LocationProvider provider, Location location) {
			LoggerText.addLog(new Date().toString() + ": "+ " Location Updated");
			if (location.isValid()) {
				LoggerText.addLog(new Date().toString() + ": "+ " Location Valid");
				double longitude = location.getQualifiedCoordinates().getLongitude();
				LoggerText.addLog(new Date().toString() + ": " + " Lon: "+ longitude);
				double latitude = location.getQualifiedCoordinates().getLatitude();
				LoggerText.addLog(new Date().toString() + ": " + " Lat: "+ latitude);
				float altitude = location.getQualifiedCoordinates().getAltitude();
				LoggerText.addLog(new Date().toString() + ": "+ " Location Method: " + location.getLocationMethod());
				LoggerText.addLog(new Date().toString()+ ": "+ " Location Extra info: "+ location.getExtraInfo("application/X-jsr179-location-nmea"));
				totalDistance += distFrom(locationInfo.getLatitude(),locationInfo.getLongitude(), latitude, longitude);
				if (totalDistance >= 300) {
					totalDistance = 0;
					performCheckinOperation(cachedUserName, null, null, true);
				}
				locationInfo.setLatitude(latitude);
				locationInfo.setLongitude(longitude);
				locationInfo.setAltitude(altitude);
				new Thread(){
					public void run() {
						updateCountry(false);
					};
				}.start();
			} else {
				LoggerText.addLog("Location Invalid");
				LoggerText.addLog("Location Course: " + location.getCourse());
				LoggerText.addLog("Location Method: "+ location.getLocationMethod());
				LoggerText.addLog("Location Speed: " + location.getSpeed());
				LoggerText.addLog("Location Extra info: "+ location.getExtraInfo("application/X-jsr179-location-nmea"));
				// If is cell site gps fetch criteria, then do not reset
//				if (!isAlternateProvider) {
//					resetProvider();
//					setAlternateProvider();
//					isAlternateProvider = true;
//				}
			}
		}

		public void providerStateChanged(LocationProvider provider, int newState) {
			// Not implemented.
		}
	}

	/**
	 * Calculates distance between two geographical points.
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return distance
	 */
	public static double distFrom(double lat1, double lng1, double lat2,
			double lng2) {
		double earthRadius = 3958.75;
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.cos(Math.toRadians(lat1))
				* Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2)
				* Math.sin(dLng / 2);
		double c = 2 * MathUtilities.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = earthRadius * c;

		int meterConversion = 1609;

		return (dist * meterConversion);
	}

	/**
	 * Set the new alternate location provider mode (GEOLOCATION mode).
	 */
	private void setAlternateProvider() {
		// DebugScreen.Log("Set alternate provider");
		try {
			myCriteria = new BlackBerryCriteria(GPSInfo.GPS_MODE_CELLSITE);
			_locationProvider = (BlackBerryLocationProvider) LocationProvider
					.getInstance(myCriteria);
			if (_locationProvider != null) {
				_locationProvider.setLocationListener(
						new LocationListenerImpl(), 20, 15, 1);
			}
		} catch (Exception e) {
			LoggerText
					.addLog("Failed to instantiate the LocationProvider object inside the setAltrenateProvide, exiting...");

		}

	}

	private class LocationAutoCheckinTask extends TimerTask {

		public void run() {
			performCheckinOperation(cachedUserName, null, null, true);
		}

	}

	private void resetProvider() {
		LoggerText.addLog("Resetting LocationProvider");
		if (_locationProvider != null) {
			_locationProvider.setLocationListener(null, 0, 0, 0);
			_locationProvider = null;
			myCriteria = null;
		}
	}

}
