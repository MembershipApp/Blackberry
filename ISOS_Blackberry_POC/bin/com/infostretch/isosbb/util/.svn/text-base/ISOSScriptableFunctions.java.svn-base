package com.infostretch.isosbb.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.microedition.io.Connector;
import javax.microedition.io.file.FileConnection;

import net.rim.blackberry.api.browser.Browser;
import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.script.ScriptableFunction;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.Dialog;

import com.infostretch.isosbb.db.LocalDB;
import com.infostretch.isosbb.location.LocationOperations;
import com.infostretch.isosbb.models.Alerts;
import com.infostretch.isosbb.models.Request;
import com.infostretch.isosbb.models.Username;
import com.infostretch.isosbb.push.LoggerText;
import com.infostretch.isosbb.ui.BrowserFieldScreen;
import com.infostretch.isosbb.ui.WebScreen;
import com.infostretch.org.json.me.JSONArray;
import com.infostretch.org.json.me.JSONException;
import com.infostretch.org.json.me.JSONObject;

public class ISOSScriptableFunctions extends ScriptableFunction {

	public static final byte FUNCTION_CHANGE_COLOR = 0;
	public static final byte FUNCTION_DO_NATIVE = 1;

	private static final String VIDEO_FILE_PATH = "file:///store/home/user/sample.3gp";
	public static final String FUNCTION_NAME_DO_NATIVE = "bb.doNative";
	
	private static final String JSON_KEY_MEMBER_ID = "membershipid";
	private static final String JSON_KEY_REMEMBER_ME = "rememberme";
	private static final String JSON_KEY_NATIVE_FUNCTION = "native_function";
	private static final String JSON_KEY_CALLBACK = "jsonCallBack";
	
	private static final String METHOD_LOGIN = "login";
//	private static final String METHOD_RENDER_DASHBOARD = "renderDashboard";
	private static final String METHOD_CHECKIN = "checkin";
	private static final String METHOD_PLAY_VIDEO = "playVideo";
	private static final String METHOD_SHOW_ALERTS = "showAlerts";
	private static final String METHOD_GET_LOCATION = "getLocation";
	private static final String METHOD_GET_COUNTRY = "getCountry";
	private static final String METHOD_GET_CACHED_USERNAME = "getCachedUsername";
	private static final String METHOD_GET_EXTERNAL_LINK = "getExternalLink";

	private byte functionType;
	private BrowserFieldScreen screen;
	private Request request;
	private String userName;

	public ISOSScriptableFunctions(byte functionType, BrowserFieldScreen screen) {
		this.functionType = functionType;
		this.screen = screen;
	}

	public Object invoke(Object thiz, Object[] args) throws Exception {
		System.out.println("ISOSScriptableFunctions.invoked(0) :" + args[0]);
		request = parseJSON(args[0].toString());
		
		switch (functionType) {
		case FUNCTION_DO_NATIVE:
			if(request != null) {
				String method = request.getNativeFunction();
				if(method.equalsIgnoreCase(METHOD_LOGIN)) {
					if(request.isRememberMe()) {
						cacheUsername(request.getMemberId());
					} else {
						new LocalDB().clearDB();
					}
//					WaitingScreen.showPopUP();
					new Thread() {
						public void run() {
							JSONObject json = new JSONObject();
							try {
								json.putOpt("UserName", request.getMemberId());
								userName = request.getMemberId();
								json.putOpt("Password", "123");
								json.putOpt("DeviceType", "BB");
								json.putOpt("DeviceToken", Integer.toHexString(DeviceInfo.getDeviceId()));
							} catch (JSONException e) {
								System.out.println("Exception while creating JSOn: " + e);
							}
							
							if(CoverageInfo.isOutOfCoverage()) {
								UiApplication.getUiApplication().invokeLater(new  Runnable() {

									public  void  run() {
										Dialog.alert("Internet connectivity unavailable. Please try again later.");
									}
								});
							}
							else {
//								String response = ConnectionUtil.callService("http://202.131.96.148/isos/services/authenticate/", json.toString());
								String response = ConnectionUtil.callService("http://202.131.96.148/isostesting/services/authenticate/", json.toString());
								System.out.println(response);
								//							WaitingScreen.removePopUP();
								synchronized (UiApplication.getEventLock()) {
									screen.getBrowserField().getManager().setVerticalScroll(0);
								}
								screen.getBrowserField().executeScript(request.getJsonCallBack() + "(" + response + ")");
							}
						};
					}.start();
				} /*else if(method.equalsIgnoreCase(METHOD_RENDER_DASHBOARD)) {
					BrowserFieldRequest browserRequest = new BrowserFieldRequest("local:///dashboard.html");
					screen.getBrowserField().requestContent(browserRequest);
				} */else if(method.equalsIgnoreCase(METHOD_CHECKIN)) {
					LocationOperations.getInstance().performCheckinOperation(userName, screen, request, false);
				} else if(method.equalsIgnoreCase(METHOD_PLAY_VIDEO)) {
					
					try {
					     FileConnection fconn = (FileConnection)Connector.open(VIDEO_FILE_PATH);
					     // If no exception is thrown, then the URI is valid, but the file may or may not exist.
					     if (!fconn.exists()) {
					         fconn.create();  // create the file if it doesn't exist
					         OutputStream out = fconn.openOutputStream();
					         InputStream in = getClass().getResourceAsStream("/sample.3gp");
					         byte[] data = IOUtilities.streamToBytes(in);
					         out.write(data);
					         in.close();
					         out.close();
					     }

					     fconn.close();
					 }
					 catch (Exception ioe) {
						 LoggerText.addLog2("Exception playing video:"+ioe.getMessage());
					 }
					try {
						Browser.getDefaultSession().displayPage("file:///store/home/user/sample.3gp");
					} catch (Exception e) {
						 LoggerText.addLog2("Exception playing video:"+e.getMessage());
					}
				} else if(method.equalsIgnoreCase(METHOD_SHOW_ALERTS)) {
					JSONArray alerts =  new JSONArray(Alerts.getInstance().getAlertsData());
					screen.getBrowserField().executeScript(request.getJsonCallBack() + "(" + alerts + ")");
				} else if(method.equalsIgnoreCase(METHOD_GET_LOCATION)) {
					new Thread() {
						public void run() {
							JSONObject value = getCountryName();
							try {
								if(value != null) {
									System.out.println("JSON: " + value.toString());
									screen.getBrowserField().executeScript(request.getJsonCallBack() + "(" + value + ")");
								}
							} catch (Exception e) {
								System.out.println("Error in executing script: " + e);
							}
						};
					}.start();
				} else if(method.equalsIgnoreCase(METHOD_GET_COUNTRY)) {
					JSONObject obj = new JSONObject();
					obj.put("country", LocationOperations.getInstance().getCountry());
					screen.getBrowserField().executeScript(request.getJsonCallBack() + "(" + obj + ")");
				} else if(method.equalsIgnoreCase(METHOD_GET_CACHED_USERNAME)) {
					JSONObject obj = new JSONObject();
					String user = new LocalDB().getUsername();
					obj.put("username", user);
					screen.getBrowserField().executeScript(request.getJsonCallBack() + "(" + obj + ")");
				} else if(method.equalsIgnoreCase(METHOD_GET_EXTERNAL_LINK)) {
					synchronized (UiApplication.getEventLock()) {
						UiApplication.getUiApplication().pushScreen(new WebScreen());
					}
//					String url = "http://mobile.usablenet.com/mt/www.internationalsos.com/MasterPortal/default.aspx?templatemembnum=11BWEB000048&membnum=14AYCA000033&membershiptype=comp&content=landing&countryid=111";
//					screen.setExternal(true);
//					screen.getBrowserField().requestContent(url);
				}
			}
			
			break;

		default:
			break;
		}
		return Boolean.FALSE;
	}
	
	private JSONObject getCountryName() {
		double lat = LocationOperations.getInstance().getLastLocation().getLatitude();
		double lng = LocationOperations.getInstance().getLastLocation().getLongitude();
//		if(lat == 0.0 && lng == 0.0){
//			ISOSLocationInfo locationInfo = new ISOSLocationInfo();
//			LatLonFinder finder = new LatLonFinder();
//			finder.fetchLatLong(locationInfo);
//		}
		
		
		JSONObject retVal = null;
		String value = "N/A";
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
			LoggerText.addLog2("getCountryName Exception: " + e);
			e.printStackTrace();
		}
		return retVal;
	}
	
	private Request parseJSON(String jsonStr) {
		try {
			JSONObject json = new JSONObject(jsonStr);
			Request request = new Request();
			try {
				JSONObject obj = (JSONObject) json.get("arguments");
				request.setMemberId(obj.optString(JSON_KEY_MEMBER_ID));
				request.setRememberMe(obj.optBoolean(JSON_KEY_REMEMBER_ME));
			} catch (JSONException e) {
				System.out.println("Error parsing JSON: " + e);
			}
			request.setNativeFunction(json.optString(JSON_KEY_NATIVE_FUNCTION));
			request.setJsonCallBack(json.optString(JSON_KEY_CALLBACK));
			return request;
		} catch (JSONException e) {
			System.out.println("Error parsing JSON: " + e);
		}
		return null;
	}
	
	private void cacheUsername(String username) {
		Username user = new Username();
		user.setUsername(username);
		LocalDB db = new LocalDB();
		db.setUsername(user);
	}
}
