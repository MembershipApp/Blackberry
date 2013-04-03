package com.infostretch.isosbb.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.io.http.HttpHeaders;
import net.rim.device.api.io.http.HttpProtocolConstants;
import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;

import com.infostretch.isosbb.push.LoggerText;

/**
 * Http helper class to perform GET and POST requests.
 * @author Amit Gupta
 *
 */
public class ConnectionUtil {
	
	private static boolean serviceRecordsLoaded;
	private static ServiceRecord srWAP2[];
    
	/**
	 *  Performs a HTTP POST request
	 * @param url The URL address
	 * @param postData
	 * @return  String Http Response
	 */
	public static String callService(String url, String postData) {
		loadServiceBooks(false);
		HttpConnection connection = null;
		InputStream inputStream = null;
		String response = "";
		LoggerText.addLog2("N/w Operation:"+" URL:"+url+" postData:"+postData);
		HttpHeaders  headers = new  HttpHeaders();
		headers.addProperty("Content-Length", String.valueOf(postData.length()));
		headers.addProperty("Content-Type", "application/json");
		headers.addProperty("charset", "UTF-8");
		HttpConnectionFactory factory = new HttpConnectionFactory();
		connection = factory.getHttpConnection(url, headers, postData.getBytes());
		try {
//			connection = (HttpConnection) Connector.open(url + ";deviceside=true;ConnectionUID="+ srWAP2[0].getUid(), Connector.READ_WRITE);
//			connection.setRequestMethod(HttpConnection.POST);
//			connection.setRequestProperty(HttpProtocolConstants.HEADER_CONTENT_TYPE, "application/json");
//
//			os = connection.openOutputStream();
//			os.write(postData.getBytes());
			inputStream = connection.openInputStream();
			byte[] data = IOUtilities.streamToBytes(inputStream);
			 for ( int j1 = 0; j1 < 100; j1++ ) {
                 String field = connection.getHeaderField(j1);
                 String key = connection.getHeaderFieldKey(j1);

                 if ( key == null) {
                     break;
                 }
                 LoggerText.addLog(new Date().toString() + key+" "+field);
                 System.out.println(key+" "+field);
                
             }
			LoggerText.addLog2(new Date().toString() + ": " + " HTTP Status: " + connection.getResponseCode());
			response = new String(data);
		} catch (Exception e) {
			LoggerText.addLog2(new Date().toString() + ": " + " Exception in ConnectioUtil: " + e);
			e.printStackTrace();
		} finally {
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
    
	/**
	 * Performs a Http GET request
	 * @param url address
	 * @return String response
	 */
	public static String callService(String url) {
//		Util u = new Util();
//		String response = u.makeconnetion(url + Util.getConnectionString());
//		return response;
		HttpConnection connection = null;
		InputStream inputStream = null;
		String response = "";
		LoggerText.addLog2("N/w Operation2:"+" URL:"+url);
		HttpConnectionFactory factory = new HttpConnectionFactory();
		connection = factory.getHttpConnection(url);
		try {
			inputStream = connection.openInputStream();
			byte[] data = IOUtilities.streamToBytes(inputStream);
			response = new String(data);
			LoggerText.addLog2("N/w Operation2:"+" Response:"+response);
		} catch (Exception e) {
			LoggerText.addLog2(new Date().toString() + ": " + " Exception in ConnectioUtil2: " + e);
			e.printStackTrace();
		} finally {
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
	
	private static synchronized void loadServiceBooks(boolean reload) {
		if (serviceRecordsLoaded && !reload) {
			return;
		}
		ServiceBook sb = ServiceBook.getSB();
		ServiceRecord[] records = sb.getRecords();
//		Vector mdsVec = new Vector();
//		Vector bisVec = new Vector();
		Vector wap2Vec = new Vector();
//		Vector wifiVec = new Vector();

		if (!serviceRecordsLoaded) {
			for (int i = 0; i < records.length; i++) {
				ServiceRecord myRecord = records[i];
				String cid, uid;

				if (myRecord.isValid() && !myRecord.isDisabled()) {
					cid = myRecord.getCid().toLowerCase();
					uid = myRecord.getUid().toLowerCase();
					if ((cid.indexOf("wptcp") != -1) && (uid.indexOf("wap2") != -1) && (uid.indexOf("wifi") == -1) && (uid.indexOf("mms") == -1)) {
						wap2Vec.addElement(myRecord);
					}
				}
			}

			srWAP2 = new ServiceRecord[wap2Vec.size()];
			wap2Vec.copyInto(srWAP2);
			wap2Vec.removeAllElements();
			wap2Vec = null;

			serviceRecordsLoaded = true;
		}
	}
}
