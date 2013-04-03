package com.infostretch.isosbb.push;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import javax.microedition.io.Connection;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.io.ServerSocketConnection;

import net.rim.device.api.io.IOUtilities;
import net.rim.device.api.io.http.HttpServerConnection;
import net.rim.device.api.io.http.MDSPushInputStream;
import net.rim.device.api.io.http.PushInputStream;
import net.rim.device.api.system.CoverageInfo;
import net.rim.device.api.system.DeviceInfo;

/**
 * Registers and receives bis push messages.<br/>
 * Based on PushLib43.java found in (standard installtion) <br/>
 * C:\Program Files\BPSS\pushsdk\client-sample-app\pushdemo\com\rim\samples\device\push\lib<br/>
 * Please note that the registration to your content provider is not included.<br/>
 * You have to enter your port and app id, and change the connection suffix<br/>
 * Unregister-code is left if needed, but not implemented.<br/>
 * It is suggested to replace the sysouts with a real logger service.
 * 
 * @author simon_hain of supportforums.blackberry.com
 */
public class PushAgent {

	private static final String REGISTER_SUCCESSFUL = "rc=200";
	private static final String DEREGISTER_SUCCESSFUL = REGISTER_SUCCESSFUL;
	private static final String USER_ALREADY_SUBSCRIBED = "rc=10003";
	private static final String ALREADY_UNSUSCRIBED_BY_USER = "rc=10004";
	private static final String ALREADY_UNSUSCRIBED_BY_PROVIDER = "rc=10005";

	private static final String PUSH_PORT = "32651";
	private static final String BPAS_URL = "http://cp3131.pushapi.eval.blackberry.com";
	private static final String APP_ID = "3131-ac535r06e61i2414aa9501c93160ak9s256";
	private static final String CONNECTION_SUFFIX = ";deviceside=false;ConnectionType=mds-public";

	private MessageReadingThread messageReadingThread;

	/**
	 * Instantiates a new push agent.
	 * 
	 */
	public PushAgent() {
		
		
		if (DeviceInfo.isSimulator()) {
			System.out.println("Error: Registration can only be done for real devices");
			return;
		}
		messageReadingThread = new MessageReadingThread();
		messageReadingThread.start();
		registerBpas();
	}

	/**
	 * Thread that processes incoming connections through {@link PushMessageReader}.
	 */
	private static class MessageReadingThread extends Thread {

		private boolean running;
		private ServerSocketConnection socket;
		private HttpServerConnection conn;
		private InputStream inputStream;
		private PushInputStream pushInputStream;

		/**
		 * Instantiates a new message reading thread.
		 */
		public MessageReadingThread() {
			this.running = true;
		}

		/**
		 * {@inheritDoc}
		 * 
		 * @see java.lang.Thread#run()
		 */
		public void run() {
			String url = "http://:" + PUSH_PORT + CONNECTION_SUFFIX;
			System.out.println("Starting to listen for push messages through '" + url + "'");
			LoggerText.addLog(new Date().toString() + ": " + " Starting to listen for push messages through '" + url + "'");

			try {
				socket = (ServerSocketConnection) Connector.open(url);
			} catch (IOException ex) {
				// can't open the port, probably taken by another application
				onListenError(ex);
			}

			while (running) {
				try {
					Object o = socket.acceptAndOpen();
					conn = (HttpServerConnection) o;
					inputStream = conn.openInputStream();
					pushInputStream = new MDSPushInputStream(conn, inputStream);
					PushMessageReader.process(pushInputStream, conn);
				} catch (Exception e) {
					if (running) {
						System.out.println("Failed to read push message, caused by " + e.getMessage());
						LoggerText.addLog(new Date().toString() + ": " + " Failed to read push message, caused by " + e.getMessage());
						running = false;
					}
				} finally {
					close(conn, pushInputStream, null);
				}
			}

			System.out.println("Stopped listening for push messages");
			LoggerText.addLog(new Date().toString() + ": " + " Stopped listening for push messages");
		}

		/**
		 * Stop running.
		 */
		public void stopRunning() {
			running = false;
			close(socket, null, null);
		}

		/**
		 * On listen error.
		 * 
		 * @param ex
		 *            the ex
		 */
		private void onListenError(final Exception ex) {
			System.out.println("Failed to open port, caused by " + ex);
			LoggerText.addLog(new Date().toString() + ": " + " Failed to open port, caused by " + ex);
		}
	}

	/**
	 * Safely closes connection and streams.
	 * 
	 * @param conn
	 *            the conn
	 * @param is
	 *            the is
	 * @param os
	 *            the os
	 */
	public static void close(Connection conn, InputStream is, OutputStream os) {
		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
			}
		}
		if (is != null) {
			try {
				is.close();
			} catch (IOException e) {
			}
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (IOException e) {
			}
		}
	}

	/**
	 * Form a register request.
	 * 
	 * @param bpasUrl
	 *            the bpas url
	 * @param appId
	 *            the app id
	 * @param token
	 *            the token
	 * @return the the built request
	 */
	private String formRegisterRequest(String bpasUrl, String appId, String token) {
		StringBuffer sb = new StringBuffer(bpasUrl);
		sb.append("/mss/PD_subReg?");
		sb.append("serviceid=").append(appId);
		sb.append("&osversion=").append(DeviceInfo.getSoftwareVersion());
		sb.append("&model=").append(DeviceInfo.getDeviceName());
		if (token != null && token.length() > 0) {
			sb.append("&").append(token);
		}
		System.out.println("Register Req: " + sb.toString());
		LoggerText.addLog(new Date().toString() + ": " + " Register Req: " + sb.toString());
		return sb.toString();
	}

	/**
	 * Form an unregister request.
	 * 
	 * @param bpasUrl
	 *            the bpas url
	 * @param appId
	 *            the app id
	 * @param token
	 *            the token
	 * @return the built request
	 */
	private String formUnRegisterRequest(String bpasUrl, String appId, String token) {
		StringBuffer sb = new StringBuffer(bpasUrl);
		sb.append("/mss/PD_subDereg?");
		sb.append("serviceid=").append(appId);
		if (token != null && token.length() > 0) {
			sb.append("&").append(token);
		}
		return sb.toString();
	}

	/**
	 * Register to the BPAS.
	 */
	private void registerBpas() {
		final String registerUrl = formRegisterRequest(BPAS_URL, APP_ID, null) + CONNECTION_SUFFIX;
		/**
		 * As the connection suffix is fixed I just use a Thread to call the connection code
		 * 
		 **/
		new Thread() {
			public void run() {
				try {
					HttpConnection httpConnection = (HttpConnection) Connector.open(registerUrl);
					LoggerText.addLog(new Date().toString() + ": " + " Register URL: " + registerUrl);
					InputStream is = httpConnection.openInputStream();
					String response = new String(IOUtilities.streamToBytes(is));
					LoggerText.addLog(new Date().toString() + ": " + " registerBpas response: " + response);
					close(httpConnection, is, null);
					String nextUrl = formRegisterRequest(BPAS_URL, APP_ID, response) + CONNECTION_SUFFIX;
					LoggerText.addLog(new Date().toString() + ": " + " NextURL: " + nextUrl);
					HttpConnection nextHttpConnection = (HttpConnection) Connector.open(nextUrl);
					InputStream nextInputStream = nextHttpConnection.openInputStream();
					response = new String(IOUtilities.streamToBytes(nextInputStream));
					LoggerText.addLog(new Date().toString() + ": " + " Next URL response: " + response);
					close(nextHttpConnection, is, null);
					if (REGISTER_SUCCESSFUL.equals(response) || USER_ALREADY_SUBSCRIBED.equals(response)) {
						System.out.println("Registered successfully for BIS push");
						LoggerText.addLog(new Date().toString() + ": " + " Registered successfully for BIS push");
					} else {
						System.out.println("BPAS rejected registration");
						LoggerText.addLog(new Date().toString() + ": " + " BPAS rejected registration");
					}
				} catch (IOException e) {
					System.out.println("IOException on register() " + e + " " + e.getMessage());
					LoggerText.addLog(new Date().toString() + ": " + " IOException on register() " + e + " " + e.getMessage());
				}
			}
		}.start();
	}
	
	public void closePort() {
		messageReadingThread.stopRunning();
	}
}
