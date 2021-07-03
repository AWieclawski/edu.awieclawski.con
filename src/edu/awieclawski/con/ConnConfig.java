package edu.awieclawski.con;

import java.net.HttpURLConnection;
//import java.util.logging.Level;
//import java.util.logging.Logger;

public class ConnConfig {
//	private final static Logger LOGGER = Logger.getLogger(ConnConfig.class.getName());

	private String typeName = "Content-Type";
	private int timeOut = 5000; // 5 seconds
	private boolean followRedirect = false;

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

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	/**
	 * 
	 * @param con
	 * @return
	 */
	public HttpURLConnection setConnConfig(HttpURLConnection con) {

		String contentType = "";

		// TODO get Content Type before establich connection
		// String contentType = con.getHeaderField(typeName);

		con.setRequestProperty(typeName, "application/json");
		con.setConnectTimeout(timeOut);
		con.setReadTimeout(timeOut);
		// enable or disable automatically following redirects
		con.setInstanceFollowRedirects(followRedirect);

		con.setRequestProperty(typeName, contentType);

		return con;
	}
}
