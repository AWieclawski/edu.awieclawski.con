package edu.awieclawski.con.res;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;

public class FullResponseBuilder {

	private ResponseReader responseReader;
	private String fullResponse;

	public String getFullResponse() {
		return fullResponse;
	}

	public void setFullResponse(String fullResponse) {
		this.fullResponse = fullResponse;
	}

	public ResponseReader getResponseReader() {
		return responseReader;
	}

	public void setResponseReader(ResponseReader responseReader) {
		this.responseReader = responseReader;
	}

	/**
	 * 
	 * @param con
	 * @return
	 * @throws IOException
	 */
	public FullResponseBuilder getFullResponseBuilderFromConnection(HttpURLConnection con) throws IOException {
		StringBuilder fullResponseBuilder = new StringBuilder();

		// read status and message
		fullResponseBuilder.append(con.getResponseCode()).append(" ").append(con.getResponseMessage()).append("\n");

		// read headers
		con.getHeaderFields().entrySet().stream().filter(entry -> entry.getKey() != null).forEach(entry -> {
			fullResponseBuilder.append(entry.getKey()).append(": ");
			List<?> headerValues = entry.getValue();
			Iterator<?> it = headerValues.iterator();
			if (it.hasNext()) {
				fullResponseBuilder.append(it.next());
				while (it.hasNext()) {
					fullResponseBuilder.append(", ").append(it.next());
				}
			}
			fullResponseBuilder.append("\n");
		});

		// read response content and initialize ResponseReader
		responseReader = new ResponseReader().getResponseReaderFromConnection(con);
		// set ResponseReader field
		setResponseReader(responseReader);
		// set fullResponse field
		fullResponseBuilder.append(responseReader.getResponseBody());
		setFullResponse(fullResponseBuilder.toString());

		return this;
	}
}
