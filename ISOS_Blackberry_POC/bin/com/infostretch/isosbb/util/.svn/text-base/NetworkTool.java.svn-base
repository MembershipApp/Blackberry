package com.infostretch.isosbb.util;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Vector;

import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;

import net.rim.device.api.servicebook.ServiceBook;
import net.rim.device.api.servicebook.ServiceRecord;
import net.rim.device.api.synchronization.ConverterUtilities;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.system.RadioInfo;
import net.rim.device.api.util.DataBuffer;

import com.infostretch.isosbb.push.LoggerText;


/**
 * Class to identify and set the connection String for the p2p application.
 * The applicaiton checks for available network transport from the known transports( Wifi, MDS, Direct TCP, WAP, BIS) 
 * and sets the connection string with it.
 * <p>
 * <ol>
 * <li>BIS
 * <li>Direct TCP
 * <li>WAP
 * <li>WAP2
 * <li>Unite
 * <li>WiFi
 * <li>MDS
 * </ol>
 * we are following three step process for network searching.
 * <ol>
 * <li>search for available transports.
 * <li>creating URL + connection string for every available transports.
 * <li>try to connect to Internet with every URL+connection string.if connection
 * is successful, we stop searching and store the connection string for next
 * time.
 * </ol>
 * 
 * Sample Code:
 * <pre>
 * <code>
 *  1. Instantiate the Network Thread 
 *  2. Check if the Connection has been established.
 *  
 *  	if (NetworkTool.getConnectionString().length() == 0) {
 *		new Thread(new NetworkTool()).start();
 *  		}
 *		// if network search is not completed , wait for that.
 *		while (NetworkTool.status != NetworkTool.STATUS_DONE)
 *		;
 *      // add your network related code for performing Http requset here 
 *      // as the Connection String has been set  
 *   </code>
 * </pre>
 * 
 * 
 */



public class NetworkTool extends Thread {
    /** Status constant for the network operation. */
	/** STATUS_DONE indicates network search completed. */
	public static final int STATUS_DONE = 1;

	/** Stores the status value of the network operation. */

	public static int status = 0;

    /** Default url used to set up a netowrk connection. */ 

	private final static String baseUrl = "http://www.google.com";// Constants.URL_CONNECTION+"images.img?imgId=-1&versionId=1";
    /** Constant denoting a network transport type. */
	/** these variables used for different network transports. */
	private static final int CONFIG_TYPE_BES = 1;
	private static final int CONFIG_TYPE_WAP  = 0;

	//public static String msg;
    /** boolean array to store the coverage information. */
	private boolean[] transports = new boolean[7];
    
	/** Constants denoting network transports available in Blackberry. */
	private final int coverageBIS = 0;
	private final int coverageTCP = 1;
	private final int coverageWAP = 2;
	private final int coverageWAP2 = 3;
	private final int coverageUnite = 4;
	private final int coverageWiFi = 5;
	private final int coverageMDS = 6;
	
	/* Countries MCC codes. */
//	private static final int Afghanistan = 412;
//	private static final int Albania = 276;
//	private static final int Algeria = 603;
//	private static final int Andorra = 213;
//	private static final int Angola = 631;
//	private static final int Antigua_and_Barbuda = 344;
//	private static final int Australia = 505;
//	private static final int Austria = 232;
//	private static final int Belgium = 206;
//	private static final int Bolivia = 736;
//	private static final int Brazil = 724;
//	private static final int Cameroon = 624;
//	private static final int Cananda = 302;
//	private static final int Chile = 730;
//	private static final int China = 460;
//	private static final int Denmark = 238;
//	private static final int Finland = 244;
//	private static final int France = 208;
//	private static final int Germany = 262;
//	private static final int Greece = 202;
//	private static final int Hungary = 216;
//	private static final int APN_HongKong = 454;
//	private static final int Iceland = 274;
//	private static final int India = 404;
//	private static final int Ireland = 272;
//	private static final int Italy = 222;
//	private static final int Japan = 440;
//	private static final int Malta = 278;
//	private static final int Mauritius = 617;
//	private static final int APN_Malaysia = 502;
//	private static final int APN_Mexico = 334;
//	private static final int Netherlands = 204;
//	private static final int APN_NewZealand = 530;
//	private static final int Norway = 242;
//	private static final int Poland = 260;
//	private static final int Portugal = 268;
//	private static final int PuertoRico = 330;
//	private static final int Romania = 226;
//	private static final int Russia = 250;
//	private static final int Singapore = 525;
//	private static final int SouthAfrica = 655;
//	private static final int Spain = 214;
//	private static final int Sweden = 240;
//	private static final int Switzerland = 228;
//	private static final int APN_Turkey = 286;
//	private static final int UnitedKingdom = 234;
//	private static final int UnitedStatesofAmerica = 310;
//	private static final int APN_Vietnam = 452;
	
	/** boolean True if connection successful. */
	private static boolean connected = false;
	/** This value is used to store connection string for http request. */
	private static String connectionString = "";// ";deviceside=true;apn=airtelwap.es;tunnelauthusername=wap@wap;tunnelauthpassword=wap125";
	
	/** 
	 * Set the Connection String with the method parameter.
	 * @param s String
	 */
	private static void setConnectionString(String s) {
		//System.out.println("NetworkTool.getConnectionString(0)"+s);
		connectionString = s;
	}
    /**
     * Get the Connection String. 
     * @return String Connection String
     */
	public static String getConnectionString() {
		//System.out.println("NetworkTool.getConnectionString(1)"+connectionString);
   		return connectionString;
	}
	/**
	 * Sets the transports array with the available transport on the device. 
	 */
//	private void setAvailableTransports() {
//		if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_CARRIER)) {
//			transports[coverageBIS] = true;
//		}
//		if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_CARRIER)) {
//			transports[coverageTCP] = true;
//			transports[coverageWAP] = true;
//			transports[coverageWAP2] = true;
//		}
//		if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_MDS)) {
//			transports[coverageMDS] = true;
//			transports[coverageUnite] = true;
//		}
//		if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_CARRIER,
//				RadioInfo.WAF_WLAN, false)) {
//			transports[coverageWiFi] = true;
//		}
//	}
	private void setAvailableTransports() {
		if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_BIS_B)) {  //COVERAGE_BIS_B //COVERAGE_DIRECT
			transports[coverageBIS] = true;
		}
		if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_DIRECT)) {
			transports[coverageTCP] = true;
			transports[coverageWAP] = true;
			transports[coverageWAP2] = true;
		}
		if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_MDS)) {
			transports[coverageMDS] = true;
			transports[coverageUnite] = true;
		}
		if (CoverageInfo.isCoverageSufficient(CoverageInfo.COVERAGE_DIRECT,
				RadioInfo.WAF_WLAN, false)) {
			transports[coverageWiFi] = true;
		}
	}
	
    /**
     * Prepares the connection Strings with the avialable transports on the device. 
     * @return Vector containing the connection strings 
     */
	private Vector getAvailableConnections() {
		Vector conns = new Vector();
		ServiceBook sb = ServiceBook.getSB();
		ServiceRecord[] records = sb.getRecords();
		//System.out.println("NetworkTool.getAvailableConnections()==================>>>");
		String cid;
		String uid;
		for (int i = 0; i < records.length; i++) {
			ServiceRecord myRecord = records[i];
			// System.out.println("record name:"+myRecord.getName()+"  cid:"+myRecord.getCid().toLowerCase()+" "+myRecord.getUid().toLowerCase());
			if (myRecord.isValid() && !myRecord.isDisabled()) {
				cid = myRecord.getCid().toLowerCase();
				uid = myRecord.getUid().toLowerCase();
				
				
				if (cid.indexOf("ippp") != -1 && myRecord.getName().indexOf("BIBS") != -1 && transports[coverageBIS]) {
					conns.addElement(";deviceside=false;ConnectionUID=" + myRecord.getUid()
							+ ";ConnectionType=mds-public");
				}
				// BES
				if (cid.indexOf("ippp") != -1 && uid.indexOf("gpmds") == -1 && transports[coverageMDS]) {
				////	";deviceside=false"
					conns.addElement(";deviceside=false");
				}
				//BIS
				if (cid.indexOf("ippp") != -1 && uid.indexOf("gpmds") != -1 && transports[coverageBIS]) {
					conns.addElement(";deviceside=false");
				}
				//WiFi
				if (cid.indexOf("wptcp") != -1 && uid.indexOf("wifi") != -1 && transports[coverageWiFi]) {
					conns.addElement(";interface=wifi");
				}
				// Wap1.0 and Wap2.0
				//System.out.println("Current network : " + RadioInfo.getCurrentNetworkName());
				//BurrpDebugScreen.Log("Current network: " + RadioInfo.getCurrentNetworkName());
				if(! (RadioInfo.getCurrentNetworkName().startsWith("vodafone") || RadioInfo.getCurrentNetworkName().startsWith("Vodafone"))){
				if (getDataInt(myRecord, 12)==CONFIG_TYPE_WAP && cid.equalsIgnoreCase("wap") && transports[coverageWAP]) {
					conns.addElement(";deviceside=true");
				}
				
				if (cid.indexOf("wptcp") != -1 && uid.indexOf("wifi") == -1 && uid.indexOf("mms") == -1 && transports[coverageWAP2]) {
					conns.addElement(";deviceside=true" + ";ConnectionUID="+ myRecord.getUid());
				}
				}
				//Unite
				if (getDataInt(myRecord, 12) == CONFIG_TYPE_BES && myRecord.getName().equals("Unite") && transports[coverageUnite]) {
					conns.addElement(";deviceside=false" + ";ConnectionUID="+ myRecord.getUid());
				}
			}
		}
		if (transports[coverageTCP]) {
			try {
				conns.addElement(";deviceside=true");
				//conns.addElement(getAPN());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return conns;
	}
	/**
	 * Fetches the APN string from the mcc and mnc data retrived from RadioInfo.
	 * @return String APN 
	 * @throws Exception 
	 */
//	private static String getAPN() throws Exception {
//		int num = RadioInfo.getNumberOfNetworks();
//		for (int i = 0; i < num; i++) {
//			int mcc = RadioInfo.getMCC(i);
//			int mnc = RadioInfo.getMNC(i);
//			return getAPNFromCode(mcc, mnc);
//		}
//		return null;
//	}
	
	/**
	 * This method is used to fetch the APN String from mcc and mnc.
	 * @param mcc
	 * @param mnc
	 * @return returns APN
	 * @throws Exception
	 */
//	private static String getAPNFromCode(int mcc, int mnc) throws Exception {
//
//		String url = ";deviceside=true";
//		try {
//			mcc = Integer.parseInt(Integer.toHexString(mcc));
//			mnc = Integer.parseInt(Integer.toHexString(mnc));
//		} catch (Exception e) {
//			throw e;
//		}
//
//		String networkName = RadioInfo.getCurrentNetworkName();
//		if (networkName != null) {
//			networkName = networkName.toLowerCase();
//		} else {
//			throw new Exception("No network name found");
//		}
//
//		switch (mcc) {
//		case Spain:
//			url = ";deviceside=true;apn=airtelwap.es;tunnelauthusername=wap@wap;tunnelauthpassword=wap125";
//			break;
//		case UnitedKingdom:
//			if (mnc == 15) { // vodaphone
//				url = ";deviceside=true;apn=wap.vodafone.uk;tunnelauthusername=user@vodafone.net;tunnelauthpassword=user";
//				// url =
//				// ";deviceside=true;apn=internet;tunnelauthusername=web;tunnelauthpassword=web";
//			} else if (mnc == 10 || mnc == 11 || mnc == 2) { // O2
//				url = ";deviceside=true;apn=wap.o2.co.uk;tunnelauthusername=o2wap;tunnelauthpassword=password";
//			} else if (mnc == 30) {// t mobile
//				url = ";deviceside=true;apn=general.t-mobile.uk";
//				// url = ";deviceside=true;apn=blackberry.net;";
//				// url =
//				// ";deviceside=true;apn=general.t-mobile.uk;tunnelauthusername=user;tunnelauthpassword=mms";
//				// url =
//				// ";deviceside=true;apn=general.t-mobile.uk;tunnelauthusername=Username;tunnelauthpassword=one2one";
//			}
//			break;
//		case Germany:
//			if (mnc == 1) { // t mobile
//				url = ";deviceside=true;apn=wap.t-d1.de";
//				// url =
//				// ";deviceside=true;apn=internet.t-d1.de;tunnelauthusername=td1;tunnelauthpassword=gprs";
//			} else if (mnc == 7 || mnc == 8 || mnc == 11) { // O2
//				url = ";deviceside=true;apn=wap.viaginterkorn.de";
//			} else if (mnc == 77) {// E-Plus
//				url = ";deviceside=true;apn=wap.eplus.de";
//			}
//			break;
//		case Italy:
//			if (mnc == 1) { // TIM
//				url = ";deviceside=true;apn=wap.tim.it";
//				// url = ";deviceside=true;apn=ibox.tim.it;";
//			} else if (mnc == 7 || mnc == 8 || mnc == 11) { // O2
//				url = ";deviceside=true;apn=wap.viaginterkorn.de";
//			} else if (mnc == 77) {// E-Plus
//				url = ";deviceside=true;apn=wap.eplus.de";
//			} else if (mnc == 10) {// Vodafone
//				url = ";deviceside=true;apn=web.omnitel.it";
//			}
//			break;
//		case UnitedStatesofAmerica:
//			if (mnc == 38 || mnc == 90 || mnc == 150 || mnc == 410
//					|| mnc == 560 || mnc == 680 || mnc == 980 || mnc == 990) { // AT&T
//				url = ";deviceside=true;apn=proxy";
//			} else if (mnc == 640 || mnc == 651) { // Bell Mobility
//				// url = url + ";deviceside=true;apn=wap.viaginterkorn.de;";
//			} else if (mnc == 420) {// Cincinnati Bell
//				url = ";deviceside=true;apn=wap.gocbw.com;tunnelauthusername=cbw";
//			} else if(mnc == 490){
//				url = ";deviceside=true;apn=mobileinternet";
//			} else if(mnc==41 || mnc==15 || mnc==410 || mnc==310){
//				url = ";deviceside=true;apn=wap.cingular";
//			} else if (mnc == 770){
//				url = ";deviceside=true;apn=i2.iwireless.com";
//			} else if(mnc == 160 || mnc == 200 || mnc == 210 || mnc ==220
//					 || mnc == 230 || mnc == 240 || mnc == 250 || mnc == 270
//					 || mnc == 310 || mnc == 490 || mnc == 580 || mnc == 660
//					 || mnc == 800 || mnc == 260){
//				url = ";deviceside=true;apn=epc.tmobile.com";
//			}
//			// else if(){ //roggers
//			// url =
//			// ";deviceside=true;apn=wap.gocbw.com;tunnelauthusername=cbw;";
//			// }
//			else if (mnc == 26 || mnc == 260 || mnc == 330) { // T-mobille
//				// url = ";deviceside=true;apn=internet2.voicestream.com;";
//			}
//			break;
//		case India:
//			if (networkName.startsWith("airtel")
//					|| (mnc == 2 || mnc == 3 || mnc == 6 || mnc == 10
//							|| mnc == 31 || mnc == 40 || mnc == 45 || mnc == 49
//							|| mnc == 51 || mnc == 52 || mnc == 70 || (mnc >= 94 && mnc <= 98))) {
//				url = ";deviceside=true;apn=airtelgprs.com";
//			}
//			break;
//		case Austria:
//			if (mnc == 3 || mnc == 7) {// t mobile
//				url = ";deviceside=true;apn=gprswap";
//			}
//			// else if(){ //Optus
//			// url = ";deviceside=true;apn=internet;";
//			// url = ";deviceside=true;apn=wap.optus.net.au;";
//			// }
//			// else if(){ //Telstra
//			// url = ";deviceside=true;apn=telstra.internet;";
//			// }
//			break;
//		case APN_Turkey:
//			if (mnc == 3) {// AVEA
//				url = ";deviceside=true;apn=internet";
//			}
//			break;
//		case APN_HongKong:
//			if (mnc == 0 || mnc == 2 || mnc == 18) {// CSL 1010
//				url = ";deviceside=true;apn=hkcsl";
//			}
//			break;
//		case Netherlands:
//			if (mnc == 3 || mnc == 8 || mnc == 12) {// KPN Mobile
//				url = ";deviceside=true;apn=internet;tunnelauthusername=KPN;tunnelauthpassword=gprs";
//			}
//			break;
//		case Chile:
//			if (mnc == 10) {// Telefonica Movil
//				url = ";deviceside=true;apn=gprswap";
//			}
//			break;
//		case APN_Mexico:
//			if (mnc == 2) {// Telcel
//				url = ";deviceside=true;apn=internet.itelcel.com;tunnelauthusername=webgprs;tunnelauthpassword=webgprs2002";
//			}
//			break;
//		case Cananda:
//			if (mnc == 720) {// Rogers
//				url = ";deviceside=true;apn=internet.com";
//			}
//			break;
//		case France:
//			if (mnc == 0 || mnc == 1 || mnc == 2) {// Orange
//				url = ";deviceside=true;apn=orange.fr;tunnelauthusername=orange;tunnelauthpassword=orange";
//			}
//			break;
//		case Singapore:
//			if (mnc == 3) {// MobileOne
//				url = ";deviceside=true;apn=mobilenet";
//			}
//			break;
//		case APN_Malaysia:
//			if (mnc == 12 || mnc == 17) {// Maxis
//				url = ";deviceside=true;apn=internet.gprs.maxis";
//			}
//			break;
//		case Brazil:
//			if (mnc == 2 || mnc == 3 || mnc == 4 || mnc == 8) {// TIM
//				url = ";deviceside=true;apn=tim.br;tunnelauthusername=TIM;tunnelauthpassword=tim";
//			}
//			break;
//		case APN_Vietnam:
//			if (mnc == 4) {// Viettel
//				url = ";deviceside=true;apn=v-internet";
//			}
//			break;
//		case Ireland:
//			// if(mnc ){// Maxis
//			// url =
//			// ";deviceside=true;apn=wap.dol.ie;tunnelauthusername=gprs;tunnelauthpassword=gprs";
//			// }
//			break;
//		case APN_NewZealand:
//			if (mnc == 1) {// Vodafone
//				url = ";deviceside=true;apn=blackberry.vodafone.nl;tunnelauthusername=vodafone;tunnelauthpassword=vodafone";
//			}
//			break;
//
//		default:
//			break;
//		}
//		return url;
//	}
	
    /**
     * Tries to connect with the URL passed in the method parameter.
     * @param url String for connection
     * @return boolean True if successfully connected
     */
	private boolean checkConnectivity(String url) {
		//System.out.println("NetworkTool.checkConnectivity() URL:"+url);
		HttpConnection hconn = null;
		try {
			hconn = (HttpConnection) Connector.open(url);
			InputStream iStream = hconn.openInputStream();
		} catch (Exception e) {
			//System.out.println("NetworkTool.checkConnectivity()"+e.getMessage());
			return false;
		} finally {
			if (hconn != null) {
				try {
					hconn.close();
				} catch (IOException e) {
				}
			}
		}
		return true;
	}

	/**
	 * Gets the config type of a ServiceRecord. Passing 12 as type returns the
	 * configType.
	 * @param record A ServiceRecord
	 * @param type dataType
	 * @return configType
	 */
	private int getDataInt(ServiceRecord record, int type) {
		DataBuffer buffer = null;
		//buffer = getDataBuffer(record, type);
		byte[] data = record.getApplicationData();
		if (data != null) {
			buffer = new DataBuffer(data, 0, data.length, true);
			try {
				buffer.readByte();
			} catch (EOFException e1) {
				buffer = null;
			}
			if (!ConverterUtilities.findType(buffer, type)) {
				buffer = null;
			}
		}
		if (buffer != null) {
			try {
				return ConverterUtilities.readInt(buffer);
			} catch (EOFException e) {
				return -1;
			}
		}
		return -1;
	}

//	/**
//	 * Utility Method for getDataInt()
//	 */
//	private DataBuffer getDataBuffer(ServiceRecord record, int type) {
//		byte[] data = record.getApplicationData();
//		if (data != null) {
//			DataBuffer buffer = new DataBuffer(data, 0, data.length, true);
//			try {
//				buffer.readByte();
//			} catch (EOFException e1) {
//				return null;
//			}
//			if (ConverterUtilities.findType(buffer, type)) {
//				return buffer;
//			}
//		}
//		return null;
//	}
	
    /**
     * The Connection string is set in this method.
     * The method first checks whether the app is running on Simulator, 
     * If yes , it directly sets the connection string to the apn of the targetted location(Spain in the code below).
     * If No, the method uses the default apn and tries to connect, if connection is not found,
     * it fetches the available transports and tries to connect to them one by one. The method sets the connection 
     * string and returs on the first connection found.
     * @return void
     */
    public void run() {
		status = STATUS_DONE + 1;
		//System.out.println("NetworkTool.run(0)");
		LoggerText.addLog(new Date().toString() + ": " + " NetworkTool.run()");
		if (DeviceInfo.isSimulator()) {
			String s = "";
			setConnectionString(s);
			status = STATUS_DONE;
			connected = true;
			return; // 1 of 3 RETURN
		}
//		 Removing wifi connection for security reasons
		String s = ";interface=wifi";
		LoggerText.addLog(new Date().toString() + ": " + " NetworkTool.run() str->"+s);
		if (checkConnectivity(baseUrl + s)) {
			setConnectionString(s);
			status = STATUS_DONE;
			connected = true;
			//System.out.println("NetworkTool.run(1) ");
			LoggerText.addLog(new Date().toString() + ": " + " NetworkTool.run() Conn Done"+s);
			return; // 2 of 3 RETURN
		}
		setAvailableTransports();
		Vector connections = getAvailableConnections();
		int length = connections.size();
		LoggerText.addLog(new Date().toString() + ": " + " NetworkTool.run() Conn len:"+length);
		for (int i = 0; i < length; i++) {
			LoggerText.addLog(new Date().toString() + ": " + " Connection string----> " + i +":"+connections.elementAt(i));
			//System.out.println("Connection string----> " + i +":"+connections.elementAt(i));
			if (checkConnectivity(baseUrl + connections.elementAt(i))) {
				setConnectionString((String) connections.elementAt(i));
				status = STATUS_DONE;
				connected = true;
				//System.out.println("NetworkTool.run(3)"+connections.elementAt(i));
				LoggerText.addLog(new Date().toString() + ": " + "  Connection Done----> " + i +":"+connections.elementAt(i));
				return; // 3 of 3 RETURN
			}
		}
		status = STATUS_DONE;
    }

}