package com.infostretch.isosbb.models;

/***
 * 
 * Model to store and retrieve location data
 * 
 * @author Amit Gupta
 *
 */
public class ISOSLocationInfo {

	private double latitude;
	private double longitude;
	private float altitude;

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public float getAltitude() {
		return altitude;
	}

	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}

}
