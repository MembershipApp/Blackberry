package com.infostretch.isosbb.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import com.infostretch.isosbb.models.ISOSLocationInfo;
import com.infostretch.isosbb.push.LoggerText;
import com.infostretch.isosbb.ui.LogScreen;
import com.infostretch.org.json.me.JSONArray;
import com.infostretch.org.json.me.JSONObject;

import net.rim.device.api.system.CDMAInfo;
import net.rim.device.api.system.GPRSInfo;
import net.rim.device.api.system.IDENInfo;
import net.rim.device.api.system.RadioInfo;


/**
 * Retrieves Latitude and longitude information using the cell id information using
 * Goolge's API
 * @author Amit Gupta
 *
 */
public class LatLonFinder{


	public static String url = "http://www.google.com/loc/json";
	public static String User_Agent = "Mozilla/4.0 (compatible; MSIE 5.5; Windows NT)";

	String nwType;
	String _mcc;
	String _mnc;
	String _lac;
	String _cellID;
	
	int responseCode;
	
	public LatLonFinder() {
		getNetworkInfo();
	}


	public byte[] getpostData(){
		JSONObject jObj = new JSONObject();
		JSONArray jArray = new JSONArray();
		JSONObject j1 = new JSONObject();

		try {
			jObj.put("version", "1.0");
			jObj.put("host", "maps.google.com");
			jObj.put("radio_type", nwType);

			j1.put("cell_id", _cellID);   // BID for CDMA
			j1.put("location_area_code", _lac);   // NID for CDMA
			j1.put("mobile_country_code", _mcc);
			j1.put("mobile_network_code", _mnc);   // SID
			
			jArray.put(j1);

			jObj.put("cell_towers", jArray);
			System.out.println("Test.main()"+jObj.toString());	
		}catch (Exception e) {

		}
		LoggerText.addLog2("-Finder- getpostData:"+jObj.toString());
		return jObj.toString().getBytes();
	}


	public void getNetworkInfo(){
		int mcc;
		int mnc;
		int lac;
		int cellID;

		System.out.println("Network type : " + RadioInfo.getNetworkType());
		System.out.println("Network name : " + RadioInfo.getCurrentNetworkName());
		
		switch (RadioInfo.getNetworkType()) {

		case RadioInfo.NETWORK_CDMA:
			try{
				nwType = "cdma";
				mnc =	CDMAInfo.getCellInfo().getSID();
				_mnc = Integer.toString(mnc);
				cellID = CDMAInfo.getCellInfo().getBID();
				_cellID = Integer.toString(cellID);
				lac = 	CDMAInfo.getCellInfo().getNID();
				_lac = Integer.toString(lac);
				_mcc = GetMCC();
			}catch (final Exception e) {
				e.printStackTrace();
			}
			break;
			
		case RadioInfo.NETWORK_GPRS:
		case RadioInfo.NETWORK_UMTS:
			try{
				nwType = "gsm";	
				cellID = GPRSInfo.getCellInfo().getCellId();
				_cellID = Integer.toString(cellID);
				lac = GPRSInfo.getCellInfo().getLAC();
				_lac = Integer.toString(lac);
				mcc = RadioInfo.getMCC(RadioInfo.getCurrentNetworkIndex());
				mnc = RadioInfo.getMNC(RadioInfo.getCurrentNetworkIndex());
				_mcc = Integer.toHexString(mcc);
				_mnc = Integer.toHexString(mnc);
				
				
			}catch (final Exception e) {
				e.printStackTrace();
			}
			
			break;
			
		case RadioInfo.NETWORK_IDEN:
			
			try{
				nwType = "iden";	
				cellID = IDENInfo.getCellInfo().getCellId();
				_cellID = Integer.toString(cellID);
				lac = GPRSInfo.getCellInfo().getLAC();
				_lac = Integer.toString(lac);
				mcc = RadioInfo.getMCC(RadioInfo.getCurrentNetworkIndex());
				mnc = RadioInfo.getMNC(RadioInfo.getCurrentNetworkIndex());
				_mcc = Integer.toHexString(mcc);
				_mnc = Integer.toHexString(mnc);
				
			}catch (final Exception e) {
				e.printStackTrace();
			}
			break;
			
		default:
			_cellID = Integer.toString(0);
			_lac = Integer.toString(0);
			_mcc = Integer.toString(0);
			_mnc = Integer.toString(0);
			break;
		}
	}

	public String GetMCC() {
		String data = "";
		if(RadioInfo.getNetworkType() == RadioInfo.NETWORK_CDMA)
		{
			byte[] imsi = CDMAInfo.getIMSI();           

			if( imsi == null || imsi.length < 3 ) {                   
			}
			else{               
				// the first three bytes of the IMSI string represents the country code
				int mcc = 0;
				for( int i = 0; i < 3; ++i ) {
					mcc *= 10;
					mcc += imsi[i];
				}
				data = data + mcc;
			}
		}
		return data;
	}



	public void fetchLatLong(ISOSLocationInfo locationInfo){

		HttpConnection c = null;
		InputStream is = null;
		ByteArrayOutputStream bos = null;
		byte[] data = null;
		byte[] requestData = getpostData();
		LoggerText.addLog2("-Finder- URL"+url);
		try {
			HttpConnectionFactory factory = new HttpConnectionFactory();
			c = factory.getHttpConnection(url);
//			c = (HttpConnection)Connector.open(url+NetworkTool.getConnectionString(), 
//					Connector.READ_WRITE, true);
			c.setRequestProperty("User-Agent", User_Agent);
			c.setRequestMethod(HttpConnection.POST);
			LoggerText.addLog2("-Finder- 1");
			//c.setRequestProperty("Content-Length", "" + requestData.length);
			OutputStream os = c.openOutputStream();
			os.write(requestData, 0, requestData.length);
			os.close();
			final int rc = c.getResponseCode(); 

			is = c.openInputStream();
			responseCode = rc;
			bos = new ByteArrayOutputStream();
			byte[] buf = new byte[256];
			while (true) {
				int rd = is.read(buf, 0, 256);
				if (rd == -1)
					break;
				bos.write(buf, 0, rd);
			}
			bos.flush();
			buf = bos.toByteArray();
			final String responseDump = new String(buf);
			LoggerText.addLog2("-Finder-Response:"+responseDump);
			//System.out.println("HttpTransport.call()   RESPONSE-->"+responseDump);
			
			JSONObject jsonObject = new JSONObject(responseDump);
			JSONObject innerJSONObject = new JSONObject();
			innerJSONObject = jsonObject.getJSONObject("location");
			
			double latitude = Double.parseDouble(innerJSONObject.getString("latitude"));
			double longitude = Double.parseDouble(innerJSONObject.getString("longitude"));
			
//			Main.gps.setLatitude(latitude);
//			Main.gps.setLongitude(longitude);
//			locationInfo.setLatitude(latitude);
//			locationInfo.setLatitude(longitude);
			
			LoggerText.addLog2("-Finder- lat"+latitude+ " long:"+longitude);
			LoggerText.addLog("Lat lon secondTry1");

		}catch (final Exception e) {
			if(e instanceof IOException)
//			secondConnTry();
//			UiApplication.getUiApplication().invokeLater(new Runnable() {
//				public void run() {
//					callback.retrieveData("Exception fetching data"+e.getMessage()+"Response code:"+responseCode+ "  ***Input Data cellID:"+_cellID+"  lac:"+_lac+" mcc:"+_mcc+"  mnc:"+_mnc);
//				}
//			});	
			e.printStackTrace();
		}finally{
			try {
				c.close();
				is.close();
				bos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
//	private void secondConnTry(){
//		if(!HttpTransport.secondTry){
//			NetworkTool  nT = new NetworkTool();
//			Thread t = new Thread(nT);
//			t.start();
//			try {
//				LoggerText.addLog("before latLon join");
//				t.join();
//				LoggerText.addLog("after latLon join");
//				HttpTransport.secondTry = true;
//				fetchLatLong();
//			} catch (InterruptedException e1) {
//				LoggerText.addLog(" fetch Lat lon Exception:"+e1.getMessage());
//			}
//
//		}else {
//			LoggerText.addLog("Lat lon secondTry2");
//			HttpTransport.secondTry = false;
//			//throw new CuTechException("Connection Failed", "Connection failed! Please check your APN settings" );
//		}
//	}

//	public void run() {
//		fetchLatLong();
//	}


}

