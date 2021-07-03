package edu.awieclawski.res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpCookie;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.awieclawski.con.ConnConfig;
import edu.awieclawski.con.Connection;

public class ResponseReader {
	private final static Logger LOGGER = Logger.getLogger(ResponseReader.class.getName());

	/**
	 * 
	 * @param con
	 * @return
	 */
	public String getResponse(HttpURLConnection con) {
		Reader streamReader = null;
		try {
			int status = con.getResponseCode();

			if (new ConnConfig().isFollRedirect())
				con = doRedirectConnection(con, status);

			if (status > 299) { // get error
				streamReader = new InputStreamReader(con.getErrorStream());

				LOGGER.log(Level.SEVERE, "get Error: " + streamReader.toString() + ",Status=" + status);

			} else {
				streamReader = new InputStreamReader(con.getInputStream());
				cookiesHandler(con);
			}

		} catch (IOException e) {

			LOGGER.log(Level.SEVERE, "IOException \n" + con, e);

		}

		// get Body of Response
		BufferedReader in = null;
		in = new BufferedReader(streamReader);
		String inputLine = "";
		StringBuffer content = new StringBuffer();
		try {
			while ((inputLine = in.readLine()) != null) {
				content.append(inputLine);
			}
		} catch (IOException e) {

			LOGGER.log(Level.SEVERE, "IOException \n" + inputLine, e);

		}
		try {
			in.close();
		} catch (IOException e) {

			LOGGER.log(Level.SEVERE, "IOException \n" + in, e);

		}

		con.disconnect();
		return content.toString();
	}

	/**
	 * if request returns a status code 301 or 302, indicating a redirect, it
	 * retrieves the Location header and create a new request to the new URL
	 * 
	 * @param con
	 * @param status
	 * @return
	 */
	private HttpURLConnection doRedirectConnection(HttpURLConnection con, int status) {

		if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
			String location = con.getHeaderField("Location");

			con = new Connection().getConnection(location);
			con = new ConnConfig().setConnConfig(con);

		}
		return con;
	}

	/**
	 * adds the cookies to the cookie store
	 * 
	 * @param con
	 */
	private void cookiesHandler(HttpURLConnection con) {
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
		String cookiesHeader = con.getHeaderField("Set-Cookie");
		List<HttpCookie> cookies = HttpCookie.parse(cookiesHeader);
		cookies.forEach(cookie -> cookieManager.getCookieStore().add(null, cookie));

	}

}
