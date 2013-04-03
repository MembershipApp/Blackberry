//#preprocess
package com.infostretch.isosbb.location;

import javax.microedition.location.LocationException;
import javax.microedition.location.LocationProvider;

import net.rim.device.api.gps.BlackBerryCriteria;
import net.rim.device.api.gps.BlackBerryLocation;
import net.rim.device.api.gps.GPSInfo;
import net.rim.device.api.gps.BlackBerryLocationProvider;
import net.rim.device.api.system.ControlledAccessException;

import com.infostretch.isosbb.push.LoggerText;
//#ifndef ISOS_SDK6
/*
//#endif
 import net.rim.device.api.gps.LocationInfo;
//#ifndef ISOS_SDK6
 * 
 */
//#endif
/**
 * Fetches approximate location using Cellsite mode and Assisted mode for OS 5.0
 * Fetches approximate location using Geolocaiton mode for OS 6.0 and above.
 * Location data is logged using LoggerText
 * @author Amit Gupta
 *
 */
public class ISOSLocationFetcher extends Thread {
	
	
//	private int GPS_INTERVAL = 15 * 60 * 1000; //15 minutes
	private int GPS_INTERVAL = 30 * 1000; //30 sec
	private int GPS_TIMEOUT = 20 * 1000; // 20 sec
	// Reference to the BlackBerryLocationProvider
	private BlackBerryLocationProvider _bbProvider;
//	private LocationProvider _provider;
	// Determines the mode of the LocationProvider
	private BlackBerryCriteria _bbCriteria;
//	private Criteria _criteria;
	// Location object that holds the current fix
	private BlackBerryLocation _bbLocation;
	private double latitude, longitude;
	private LocationOperations locOperation;
	public static final String GPS_ERROR_MESSAGE = "GPS ERROR:";
	
	/**
	 * 
	 */
	public ISOSLocationFetcher(LocationOperations operation) {
		locOperation = operation;	
	start();
	}


	public void run() {

		while(true){
			//LoggerText.addLog2("--GPS Fetch START--");

			try {
				if(_bbCriteria == null && _bbProvider == null){
					
					 //#ifndef ISOS_SDK5
					/*
					//#endif
					 if (GPSInfo.isGPSModeAvailable(GPSInfo.GPS_MODE_CELLSITE)) {
					    _bbCriteria = new BlackBerryCriteria(); 
						_bbCriteria.setMode(GPSInfo.GPS_MODE_CELLSITE); 
						LoggerText.addLog2(" Mode GPS_MODE_CELLSITE");
					} else {
						if (GPSInfo.isGPSModeAvailable(GPSInfo.GPS_MODE_ASSIST)) {
						     _bbCriteria = new BlackBerryCriteria();
							 _bbCriteria.setMode(GPSInfo.GPS_MODE_ASSIST); 
							LoggerText.addLog2("Mode GPS_MODE_ASSIST");
						}
					}
					//#ifndef ISOS_SDK5
					 * 
					 */
					//#endif		 
					// Preprocessor for 6.0
					 //#ifndef ISOS_SDK6
						/*
						//#endif
						_bbCriteria = new BlackBerryCriteria(LocationInfo.GEOLOCATION_MODE);
						LoggerText.addLog2("GEO_LOCATION_MODE");
						//#ifndef ISOS_SDK6
						 * 
						 */
						//#endif
					
					//.addLog2("-Before BB provider-");
				_bbProvider = (BlackBerryLocationProvider) LocationProvider.getInstance(_bbCriteria);
				}
				//LoggerText.addLog2("-After BB provider-");

				if (_bbProvider != null) {
						_bbLocation = (BlackBerryLocation) _bbProvider.getLocation(GPS_TIMEOUT);
					if (_bbLocation != null) {
						if (_bbLocation.isValid()) {
							//LoggerText.addLog2("Valid Location ...");
							latitude = _bbLocation.getQualifiedCoordinates().getLatitude();
							longitude = _bbLocation.getQualifiedCoordinates().getLongitude();
							LoggerText.addLog2("Lat "+latitude +" Lon "+longitude);
                           locOperation.getLocationListener().locationUpdated(_bbProvider, _bbLocation);
                           //LoggerText.addLog2("-After Listener Update-");
						} else {
							LoggerText.addLog2("invalid fix...");
						}
					} else {
						LoggerText.addLog2("Location is null");
					}
				} else {
					LoggerText.addLog2("Provider unavailable ");
				}

			} catch (Exception e) {
				handleGPSException(e, "");
			}
			
			resetProvider();
			//LoggerText.addLog2("--GPS Fetch END--");
			try{
				Thread.sleep(GPS_INTERVAL);
			}catch (Exception e) {
			}
		}
	}
	
	private void resetProvider() {
		//LoggerText.addLog2("-RESET PROVIDER-");
		if (_bbProvider != null) {
			_bbProvider.reset();
			_bbProvider = null;
			_bbCriteria = null;
			//_criteria = null;
		}
	}
	
	private void handleGPSException(Exception e, String msg) {
        if(msg == null)
        	msg = "";
        
        boolean shownativeError = true;
        String nativeError = ""+(shownativeError ? e.getMessage() : "");
		
		if (e instanceof UnsupportedOperationException) {
			LoggerText.addLog2(GPS_ERROR_MESSAGE+" UnsupportedOperationException:"+msg +nativeError);
		} else if (e instanceof ControlledAccessException) {
			LoggerText.addLog2(GPS_ERROR_MESSAGE+" ControlledAccessException:"+msg+nativeError);
		}else if (e instanceof SecurityException) {
			LoggerText.addLog2(GPS_ERROR_MESSAGE+" SecurityException:"+msg+nativeError);
		}else if (e instanceof IllegalArgumentException) {
			LoggerText.addLog2(GPS_ERROR_MESSAGE+""+msg+nativeError);
		}else if(e instanceof LocationException){
			LoggerText.addLog2(GPS_ERROR_MESSAGE+" "+" LocationException:"+msg+nativeError);
		}else if(e instanceof InterruptedException){
			LoggerText.addLog2(GPS_ERROR_MESSAGE+" InterruptedException:"+msg+nativeError);
		}
		else {
			LoggerText.addLog2(GPS_ERROR_MESSAGE+" msg:"+msg+ nativeError);
		}

	}

}
