package edu.awieclawski.con.res;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;

import edu.awieclawski.con.dao.I_Response;

public class ResponseReader implements I_Response {

	private String responseBody;

	@Override
	public String getResponseBody() {
		return responseBody;
	}

	@Override
	public ResponseReader getResponseReaderFromConnection(HttpURLConnection connection) throws IOException {
		BufferedReader in = null;
		Reader streamReader = null;
		String inputLine = null;
//		StringBuffer content = new StringBuffer();

		streamReader = new InputStreamReader(connection.getInputStream());
		in = new BufferedReader(streamReader);

		while ((inputLine = in.readLine()) != null) {
			CONTENT.append(inputLine);
		}

		in.close();
		connection.disconnect();

		responseBody = CONTENT.toString();

		return this;
	}

}
