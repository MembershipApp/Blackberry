/**
 * AUTO_COPYRIGHT_SUB_TAG
 */
package com.infostretch.isosbb.push;

/**
 * Simplified logger to ease the debugging process.
 */
public class LoggerText {
	private static long lastTime;
	public static StringBuffer txt = new StringBuffer();
	public static void addLog(String logtxt){
//		txt.append(logtxt).append('\n');
	}
	
	public static void addLog1(String logtxt){
		txt.append(logtxt).append('\n');
	}
	public static void addLog2(String logtxt){
		txt.append("#-"+logtxt+"-#").append('\n');
	}
	
	/**
	 * Logs the text and the Time taken in milliseconds since the last log added.
	 * @param logtxt
	 */
	public static void addLog3(String logtxt){
		long currentTime = System.currentTimeMillis();
		if(lastTime == 0){
			lastTime = currentTime;
		}
		txt.append("#- Time :"+(currentTime - lastTime)+" "+logtxt+"-#").append('\n');
		lastTime = currentTime;
	}
	public static void removeLog(){
		txt.delete(0, txt.length());
		txt = null;
		txt = new StringBuffer();
	}
 }

