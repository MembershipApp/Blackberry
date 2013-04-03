package com.infostretch.isosbb.util;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.TimeZone;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import com.infostretch.isosbb.push.LoggerText;

import net.rim.device.api.i18n.SimpleDateFormat;
import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.WLANInfo;

public class Util {	
	/**
	 * Retrives the connection string to pre apended to the Http connnection URL
	 * 
	 * @return String
	 */
	public static String getConnectionString() {

		// This code is based on the connection code developed by Mike Nelson of
		// AccelGolf.
		// http://blog.accelgolf.com/2009/05/22/blackberry-cross-carrier-and-cross-network-http-connection
		String connectionString = null;

		// Simulator behavior is controlled by the USE_MDS_IN_SIMULATOR
		// variable.
		if (DeviceInfo.isSimulator()) {

			connectionString = ";deviceSide=true";
		}

		// Wifi is the preferred transmission method
		else if (WLANInfo.getWLANState() == WLANInfo.WLAN_STATE_CONNECTED) {
			// System.out.println("Device is connected via Wifi.");
			connectionString = ";interface=wifi";
		} else if(CoverageInfo.getCoverageStatus() == CoverageInfo.COVERAGE_BIS_B) {
			String carrierUid = getCarrierBIBSUid();
			connectionString = ";deviceside=true;connectionUID="
					+ carrierUid + ";ConnectionType=mds-public";
		}

		// Is the carrier network the only way to connect?
		else if ((CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_DIRECT) == CoverageInfo.COVERAGE_DIRECT) {
			// System.out.println("Carrier coverage.---->>" +
			// CoverageInfo.getCoverageStatus());

				String wapString = getAvailableConnectionsString();
				if (wapString == null) {
					connectionString = ";deviceside=true";
				} else {
					connectionString = wapString;
				}
		}

		// Check for an MDS connection instead (BlackBerry Enterprise Server)
		else if ((CoverageInfo.getCoverageStatus() & CoverageInfo.COVERAGE_MDS) == CoverageInfo.COVERAGE_MDS) {
			// System.out.println("MDS coverage found");
			connectionString = ";deviceside=false";
		}

		// If there is no connection available abort to avoid bugging the user
		// unnecssarily.
		else if (CoverageInfo.getCoverageStatus() == CoverageInfo.COVERAGE_NONE) {
			// System.out.println("There is no available connection.");
		}

		// In theory, all bases are covered so this shouldn't be reachable.
		else {
			// System.out.println("no other options found, assuming device.");
			connectionString = ";deviceside=true";
		}

		return connectionString;
	}

	/**
	 * Looks through the phone's service book for a carrier provided BIBS
	 * network
	 * 
	 * @return The uid used to connect to that network.
	 */
	private static String getCarrierBIBSUid() {

		ServiceRecord[] records = ServiceBook.getSB().getRecords();
		int currentRecord;

		for (currentRecord = 0; currentRecord < records.length; currentRecord++) {
			// DebugScreen.Log("Util.getCarrierBIBSUid() for ippp--------->>" +
			// records[currentRecord].getCid().toLowerCase());
			if (records[currentRecord].getCid().toLowerCase().equals("ippp")) {
				// DebugScreen.Log("Util.getCarrierBIBSUid() for bibs..........'''''"
				// +
				// records[currentRecord].getName().toLowerCase().indexOf("bibs")
				// );
				if (records[currentRecord].getName().toLowerCase()
						.indexOf("bibs") >= 0) {
					return records[currentRecord].getUid();
				}
			}

		}

		return null;
	}

	/**
	 * Prepares the connection Strings with the avialable transports on the
	 * device.
	 * 
	 * @return Vector containing the connection strings
	 */
	public static String getAvailableConnectionsString() {
		String conns = null;
		ServiceBook sb = ServiceBook.getSB();
		ServiceRecord[] records = sb.getRecords();

		String cid;
		String uid;
		for (int i = 0; i < records.length; i++) {
			ServiceRecord myRecord = records[i];
			// System.out.println("record name:"+myRecord.getName()+"  cid:"+myRecord.getCid().toLowerCase()+" "+myRecord.getUid().toLowerCase());
			if (myRecord.isValid() && !myRecord.isDisabled()) {
				cid = myRecord.getCid().toLowerCase();
				uid = myRecord.getUid().toLowerCase();

				// Wap2.0
				if (cid.indexOf("wptcp") != -1 && uid.indexOf("wifi") == -1
						&& uid.indexOf("mms") == -1) {
					conns = ";deviceside=true" + ";ConnectionUID="
							+ myRecord.getUid();
					if (myRecord.getUid().equalsIgnoreCase("GTCP BIBS")) {
						return conns;
					}
				}

			}
		}

		return null;
	}
	
// Below is the method to make the connection
public String makeconnetion(String finalURL) // makes the connection and returns the
	// response that is received.
	{
		HttpConnection connection = null;
		InputStream inputStream = null;
		String response = "";
		try {
			// throw new Exception();
			System.out.println("FinalURL: " + finalURL);
			connection = (HttpConnection) Connector.open(finalURL, Connector.READ_WRITE);
			connection.setRequestProperty("Content-Type", "application/json");
			inputStream = connection.openInputStream();
			byte[] data = IOUtilities.streamToBytes(inputStream);
			response = new String(data);
		} catch (Exception e) {
			LoggerText.addLog(new Date().toString() + ": " + " Exception while making GET connection: " + e);
			e.printStackTrace();
		}
		finally{
			try{
				inputStream.close();
				inputStream = null;
			}catch(Exception ex){
			}
			try{
				connection.close();
				connection = null;
			}
			catch(Exception ex){
			}
		}
		return response;
	}

public String makeconnetion(String finalURL, String postData) // makes the connection and returns the
// response that is received.
{
	HttpConnection connection = null;
	InputStream inputStream = null;
	OutputStream os = null;
	String response = "";
	try {
		LoggerText.addLog(new Date().toString() + ": " + " FinalURL: " + finalURL);
		System.out.println("Postdata: " + postData);
		connection = (HttpConnection) Connector.open(finalURL, Connector.READ_WRITE);
		connection.setRequestMethod(HttpConnection.POST);
		connection.setRequestProperty("Content-Type", "application/json");
		os = connection.openOutputStream();
		os.write(postData.getBytes());
		inputStream = connection.openInputStream();
		byte[] data = IOUtilities.streamToBytes(inputStream);
		response = new String(data);
		LoggerText.addLog(new Date().toString() + ": " + " Response: " + response);
	} catch (Exception e) {
		LoggerText.addLog(new Date().toString() + ": " + " Exception while making connection: " + e);
		e.printStackTrace();
	}
	finally{
		try{
			inputStream.close();
			inputStream = null;
			os.close();
			os = null;
		}catch(Exception ex){
		}
		try{
			connection.close();
			connection = null;
		}
		catch(Exception ex){
		}
	}
	return response;
}

public static String getDateFromTimeStamp(String timeStamp) {
	try {
		Date date = new Date(Long.parseLong(timeStamp));
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String Timestampdate = formatter.format(date);
		return Timestampdate;
	} catch (Exception e) {
		return null;
	}
}


}
	
	
	