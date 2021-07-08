package edu.awieclawski.con.con;

import java.net.HttpURLConnection;
import java.net.ProtocolException;

public class ConnectionConfig {

	private int timeOut;
	private String contentType;
	private String reqMethod;
	private boolean followRedirect;

	public boolean isFollRedirect() {
		return followRedirect;
	}

	public void setFollRedirect(boolean follRedirect) {
		this.followRedirect = follRedirect;
	}

	public int getTimeOut() {
		return timeOut;
	}

	public void setTimeOut(int timeOut) {
		this.timeOut = timeOut;
	}

	public String getReqMethod() {
		return reqMethod;
	}

	public void setReqMethod(String reqMethod) {
		this.reqMethod = reqMethod;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	/**
	 * 
	 * @param con
	 * @return
	 * @throws ProtocolException
	 */
	public HttpURLConnection setConnConfig(HttpURLConnection con, int timeOut, String contentType, String reqMethod,
			boolean followRedirect) throws ProtocolException {

		con.setRequestMethod(reqMethod);
		con.setRequestProperty("Content-Type", contentType);
		con.setConnectTimeout(timeOut);
		con.setReadTimeout(timeOut);
		// enable or disable automatically following redirects
		con.setInstanceFollowRedirects(followRedirect);

		return con;
	}

}
