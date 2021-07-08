package edu.awieclawski.con.ctr;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;

import edu.awieclawski.con.con.Connection;
import edu.awieclawski.con.con.ConnectionConfig;
import edu.awieclawski.con.dao.I_FullResponse;
import edu.awieclawski.con.res.FullResponseBuilder;
import edu.awieclawski.con.exc.DataMissingException;

public class ConnectionController {

	private String response;
	private String fullResponse;

	/**
	 * 
	 * @param fullUrl
	 * @param timeOut
	 * @param contentType
	 * @param reqMethod
	 * @param followRedirect
	 * @return
	 * @throws DataMissingException
	 */
	public String establishConnection(String fullUrl, int timeOut, String contentType, String reqMethod,
			boolean followRedirect) throws DataMissingException {
		final int responseCode;
		HttpURLConnection connection = null;

		Connection newConn = new Connection();
		ConnectionConfig newConf = new ConnectionConfig();

		try {
			connection = newConn.getConnection(fullUrl);
			connection = newConf.setConnConfig(connection, timeOut, contentType, reqMethod, followRedirect);
			responseCode = connection.getResponseCode();
			if (newConf.isFollRedirect())
				connection = doRedirectConnection(connection, responseCode);

		} catch (MalformedURLException e) {
			throw new DataMissingException(DataMissingException.DataMissingExceptionType.INVALID_URL_ERROR, fullUrl,
					"url string cannot be parsed ");
		} catch (ProtocolException e) {
			throw new DataMissingException(DataMissingException.DataMissingExceptionType.PROTOCOL_ERROR, fullUrl,
					"error in the underlying protocol during connect to the host");
		} catch (IOException e) {
			throw new DataMissingException(DataMissingException.DataMissingExceptionType.NETWORK_ERROR, fullUrl,
					"failed to connect to host");
		}

		switch (responseCode) {
		case 404:
			throw new DataMissingException(DataMissingException.DataMissingExceptionType.INCORRECT_PARMETERS_ERROR,
					fullUrl, "404 error");
		case 400:
			throw new DataMissingException(DataMissingException.DataMissingExceptionType.INVALID_REQUEST_ERROR, fullUrl,
					"400 error");
		case 200: {
			try {
				setAllResponseFields(connection);
				return response;
			} catch (IOException e) {
				throw new DataMissingException(DataMissingException.DataMissingExceptionType.RESPONSE_READER_ERROR,
						fullUrl, "200 error. Response reader fault");
			}
		}
		default:
			return null;
		}
	}

	/**
	 * 
	 * @param connection
	 * @throws IOException
	 */
	private void setAllResponseFields(HttpURLConnection connection) throws IOException {
		I_FullResponse fullResponseBuilder = new FullResponseBuilder().getFullResponseBuilderFromConnection(connection);
		response = fullResponseBuilder.getResponseBody();
		fullResponse = fullResponseBuilder.getResponseHeader() + response;
	}

	/**
	 * if request returns a status code 301 or 302, indicating a redirect, it
	 * retrieves the Location header and create a new request to the new URL
	 * 
	 * @param con
	 * @param status
	 * @return
	 * @throws IOException
	 * @throws MalformedURLException
	 */
	private HttpURLConnection doRedirectConnection(HttpURLConnection con, int status)
			throws MalformedURLException, IOException {

		if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
			String location = con.getHeaderField("Location");

			con = new Connection().getConnection(location);

			con = new ConnectionConfig().setConnConfig(con, con.getConnectTimeout(), con.getContentType(),
					con.getRequestMethod(), con.getInstanceFollowRedirects());

		}
		return con;
	}

	public String getFullResponse() {
		return fullResponse;
	}

	public String getResponse() {
		return response;
	}

}
