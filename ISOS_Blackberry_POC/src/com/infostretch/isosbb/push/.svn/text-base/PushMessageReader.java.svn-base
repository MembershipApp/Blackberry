package com.infostretch.isosbb.push;

import java.io.ByteArrayInputStream;
import java.util.Date;

import javax.microedition.io.Connection;

import net.rim.device.api.io.Base64InputStream;
import net.rim.device.api.io.http.HttpServerConnection;
import net.rim.device.api.io.http.PushInputStream;
import net.rim.device.api.ui.Manager;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.UiEngine;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.util.Arrays;

import com.infostretch.isosbb.models.Alerts;
import com.infostretch.isosbb.util.Util;

/**
 * Source (on standard installation): C:\Program Files\BPSS\pushsdk\client-sample-app\pushdemo\com\rim\samples\device\push <br/>
 * Reads incoming push messages and extracts texts and images.
 */
public final class PushMessageReader {

	// HTTP header property that carries unique push message ID
	private static final String MESSAGE_ID_HEADER = "Push-Message-ID";
	// content type constant for text messages
	private static final String MESSAGE_TYPE_TEXT = "text";
	// content type constant for image messages
	private static final String MESSAGE_TYPE_IMAGE = "image";

	private static final int MESSAGE_ID_HISTORY_LENGTH = 10;
	private static String[] messageIdHistory = new String[MESSAGE_ID_HISTORY_LENGTH];
	private static byte historyIndex;

	private static byte[] buffer = new byte[15 * 1024];
	private static byte[] imageBuffer = new byte[10 * 1024];

	/**
	 * Utility classes should have a private constructor.
	 */
	private PushMessageReader() {
	}

	/**
	 * Reads the incoming push message from the given streams in the current thread and notifies controller to display the information.
	 * 
	 * @param pis
	 *            the pis
	 * @param conn
	 *            the conn
	 */
	public static void process(PushInputStream pis, Connection conn) {
		System.out.println("Reading incoming push message ...");
		LoggerText.addLog3(new Date().toString() + ": " + " Reading incoming push message ...");

		try {

			HttpServerConnection httpConn;
			if (conn instanceof HttpServerConnection) {
				httpConn = (HttpServerConnection) conn;
			} else {
				throw new IllegalArgumentException("Can not process non-http pushes, expected HttpServerConnection but have "
						+ conn.getClass().getName());
			}

			String msgId = httpConn.getHeaderField(MESSAGE_ID_HEADER);
			String msgType = httpConn.getType();
			String encoding = httpConn.getEncoding();

			System.out.println("Message props: ID=" + msgId + ", Type=" + msgType + ", Encoding=" + encoding);
			LoggerText.addLog3(new Date().toString() + ": " + " Message props: ID=" + msgId + ", Type=" + msgType + ", Encoding=" + encoding);

			boolean accept = true;
			if (!alreadyReceived(msgId)) {
				byte[] binaryData;

				if (msgId == null) {
					msgId = String.valueOf(System.currentTimeMillis());
				}

				if (msgType == null) {
					System.out.println("Message content type is NULL");
					LoggerText.addLog3(new Date().toString() + ": " + " Message content type is NULL");
					accept = false;
				} else if (msgType.indexOf(MESSAGE_TYPE_TEXT) >= 0) {
					// a string
					int size = pis.read(buffer);
					binaryData = new byte[size];
					System.arraycopy(buffer, 0, binaryData, 0, size);			
					final String str = new String(binaryData);
					LoggerText.addLog3(new Date().toString() + ": " + " Message Received: " + str);
					String MessageString = "";
					if(str.indexOf("$$")!=-1){
						MessageString =  str.substring(0, str.indexOf("$$"));
						String TimestampString = Util.getDateFromTimeStamp(str.substring(str.indexOf("$")+2, str.length()));
						Alerts.getInstance().setTimeString(TimestampString);
						Alerts.getInstance().addAlertsData(MessageString);
					}
					
					final String alertMsg = MessageString == "" ? str : MessageString;
					UiApplication.getUiApplication().invokeLater(new Runnable() {
						
						public void run() {
							Dialog screen = new Dialog(Dialog.D_OK,alertMsg,Dialog.OK,null,Manager.VERTICAL_SCROLL);
							Ui.getUiEngine().pushGlobalScreen(screen, 1, UiEngine.GLOBAL_QUEUE);
//							Dialog.inform("Pushed Msg: " + str);
						}
					});
					// TODO report message
				} else if (msgType.indexOf(MESSAGE_TYPE_IMAGE) >= 0) {
					// an image in binary or Base64 encoding
					int size = pis.read(buffer);
					if (encoding != null && encoding.equalsIgnoreCase("base64")) {
						// image is in Base64 encoding, decode it
						Base64InputStream bis = new Base64InputStream(new ByteArrayInputStream(buffer, 0, size));
						size = bis.read(imageBuffer);
					}
					binaryData = new byte[size];
					System.arraycopy(buffer, 0, binaryData, 0, size);					
					// TODO report message
				} else {
					System.out.println("Unknown message type " + msgType);
					LoggerText.addLog(new Date().toString() + ": " + " Unknown message type " + msgType);
					accept = false;
				}
			} else {
				System.out.println("Received duplicate message with ID " + msgId);
				LoggerText.addLog(new Date().toString() + ": " + " Received duplicate message with ID " + msgId);
			}
			pis.accept();
		} catch (Exception e) {
			System.out.println("Failed to process push message: " + e);
			LoggerText.addLog(new Date().toString() + ": " + " Failed to process push message: " + e);
		} finally {
			PushAgent.close(conn, pis, null);
		}
	}

	/**
	 * Check whether the message with this ID has been already received.
	 * 
	 * @param id
	 *            the id
	 * @return true, if successful
	 */
	private static boolean alreadyReceived(String id) {
		if (id == null) {
			return false;
		}

		if (Arrays.contains(messageIdHistory, id)) {
			return true;
		}

		// new ID, append to the history (oldest element will be eliminated)
		messageIdHistory[historyIndex++] = id;
		if (historyIndex >= MESSAGE_ID_HISTORY_LENGTH) {
			historyIndex = 0;
		}
		return false;
	}

}