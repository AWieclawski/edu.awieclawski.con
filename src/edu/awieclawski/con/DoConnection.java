package edu.awieclawski.con;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.awieclawski.con.ctrl.ConnectionController;
import edu.awieclawski.con.exc.DataMissingException;
import edu.awieclawski.con.utils.ConnChecker;
import edu.awieclawski.con.utils.ParameterStringBuilder;

/**
 * Simple JAVA HTTP connection
 * 
 * @author AWieclawski
 *
 */
public class DoConnection {
	private final static Logger LOGGER = Logger.getLogger(DoConnection.class.getName());

	public static void main(String[] args) {

		// connection parameters
		int timeOut = 5000; // 5 seconds
		String contentType = "application/json";
		String reqMethod = "GET";
		boolean followRedirect = false;

		// http://api.nbp.pl/api/exchangerates/tables/a/2021-07-05?format=json

		String protocol = "http";
		String host = "api.nbp.pl";
		int port = 80;
		String path = "api/exchangerates/tables/a/2021-07-05";
		String response = "";

		if (new ConnChecker().getSocket(host, port)) {

			String fullUrl = protocol + "://" + host + ":" + port + "/" + path;
			Map<String, String> parameters = getParametersMap();

			try {
				fullUrl = addParametersToUrlPath(parameters, fullUrl);
			} catch (UnsupportedEncodingException e) {

				if (parameters != null)
					LOGGER.log(Level.SEVERE,
							"UnsupportedEncodingException " + fullUrl + ", parameters map=" + parameters.toString(), e);
			}

			ConnectionController connController = new ConnectionController();
			try {
				response = connController.establishConnection(fullUrl, timeOut, contentType, reqMethod, followRedirect);
			} catch (DataMissingException e) {
				
				LOGGER.log(Level.SEVERE, "DataMissingException url=" + fullUrl + "|contentType=" + contentType
						+ "|reqMethod=" + reqMethod + "|followRedirect=" + followRedirect, e);
				

			}
			LOGGER.log(Level.WARNING, " * Full response *\n" + connController.getFullResponse());
			System.out.println(" * Response Body *\n" + response);
		}

	}

	private static String addParametersToUrlPath(Map<String, String> parameters, String urlPath)
			throws UnsupportedEncodingException {
		if (parameters.size() > 0 && urlPath != null) {
			ParameterStringBuilder parameterStringBuilder = new ParameterStringBuilder();
			urlPath = urlPath + "?" + parameterStringBuilder.getParamsString(parameters);
		}
		return urlPath;
	}

	// parameters repository map
	private static Map<String, String> getParametersMap() {
		// map of request parameters
		Map<String, String> parameters = new HashMap<>();
		String par = "format";
		String value = "json";
		parameters.put(par, value);
		return parameters;
	}

}
