package com.infostretch.isosbb.models;
/**
 * Request data model Class
 * 
 * @author Amit Gupta
 *
 */
public class Request {

	private String memberId;
	private boolean rememberMe;
	private String nativeFunction;
	private String jsonCallBack;

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String getJsonCallBack() {
		return jsonCallBack;
	}

	public void setJsonCallBack(String jsonCallBack) {
		this.jsonCallBack = jsonCallBack;
	}

	public String getNativeFunction() {
		return nativeFunction;
	}

	public void setNativeFunction(String nativeFunction) {
		this.nativeFunction = nativeFunction;
	}

}
