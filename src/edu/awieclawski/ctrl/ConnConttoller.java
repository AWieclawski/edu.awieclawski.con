package edu.awieclawski.ctrl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import edu.awieclawski.con.ConnConfig;
import edu.awieclawski.con.Connection;
import edu.awieclawski.con.utils.ParameterStringBuilder;
import edu.awieclawski.res.FullResponseBuilder;

public class ConnConttoller {
	private final static Logger LOGGER = Logger.getLogger(ConnConttoller.class.getName());

	public String establishConnection(String fullUrl) {
		String response = "";
		HttpURLConnection con;

		Connection newConn = new Connection();
		ConnConfig newConf = new ConnConfig();

		// map of request parameters
		Map<String, String> parameters = new HashMap<>();
		String par = "format";
		String value = "json";
		parameters.put(par, value);

		if (parameters.size() > 0) {
			try {
				new ParameterStringBuilder();
				fullUrl = fullUrl + "?" + ParameterStringBuilder.getParamsString(parameters);
			} catch (UnsupportedEncodingException e) {

				LOGGER.log(Level.SEVERE, "UnsupportedEncodingException " + fullUrl, e);

			}
		}

//		LOGGER.log(Level.WARNING, "fullUrl=" + fullUrl);

		con = newConn.getConnection(fullUrl);
		con = newConf.setConnConfig(con);

		try {
			new FullResponseBuilder();
			response = FullResponseBuilder.getFullResponse(con);
		} catch (IOException e) {

			LOGGER.log(Level.SEVERE, "IOException " + fullUrl, e);

		}
		return response;
	}

}
