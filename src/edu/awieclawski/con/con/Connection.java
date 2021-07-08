package edu.awieclawski.con.con;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Connection {

	public HttpURLConnection getConnection(String urlPath) throws MalformedURLException, IOException {
		URL url = null;
		url = new URL(urlPath);

		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		return connection;
	}

}
