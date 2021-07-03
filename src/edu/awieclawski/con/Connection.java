package edu.awieclawski.con;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class Connection {
	private final static Logger LOGGER = Logger.getLogger(Connection.class.getName());

	public HttpURLConnection getConnection(String urlPath) {
		URL url = null;
		try {
			url = new URL(urlPath);
		} catch (MalformedURLException e) {

			LOGGER.log(Level.SEVERE, "MalformedURLException \n" + urlPath, e);

		}
		HttpURLConnection connection = null;
		try {
			connection = (HttpURLConnection) url.openConnection();
		} catch (IOException e) {

			LOGGER.log(Level.SEVERE, "IOException \n" + urlPath, e);

		}
		try {
			connection.setRequestMethod("GET");
		} catch (ProtocolException e) {

			LOGGER.log(Level.SEVERE, "ProtocolException \n" + urlPath, e);

		}
		return connection;
	}

}
