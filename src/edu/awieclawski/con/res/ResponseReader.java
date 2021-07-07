package edu.awieclawski.con.res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;

public class ResponseReader {

	private String responseBody;

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public String getResponseBody() {
		return responseBody;
	}

	/**
	 * 
	 * @param connection
	 * @return
	 * @throws IOException
	 */
	public ResponseReader getResponseReaderFromConnection(HttpURLConnection connection) throws IOException {
		BufferedReader in = null;
		Reader streamReader = null;

		streamReader = new InputStreamReader(connection.getInputStream());
		in = new BufferedReader(streamReader);
		String inputLine = "";
		StringBuffer content = new StringBuffer();

		while ((inputLine = in.readLine()) != null) {
			content.append(inputLine);
		}

		in.close();
		connection.disconnect();

		// set responseBody field
		String strContent = content.toString();
		setResponseBody(strContent);

		return this;
	}

}
