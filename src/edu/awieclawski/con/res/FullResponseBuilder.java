package edu.awieclawski.con.res;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.Iterator;
import java.util.List;

import edu.awieclawski.con.dao.I_FullResponse;

public class FullResponseBuilder implements I_FullResponse {

	private ResponseReader responseReader;
	private String responseHeader;

	@Override
	public String getResponseHeader() {
		return responseHeader;
	}

	@Override
	public ResponseReader getResponseReader() {
		return responseReader;
	}

	@Override
	public void setResponseReader(ResponseReader responseReader) {
		this.responseReader = responseReader;
	}

	@Override
	public FullResponseBuilder getFullResponseBuilderFromConnection(HttpURLConnection con) throws IOException {

		// read status and message
		FULL_CONTENT.append(con.getResponseCode()).append(SPACE).append(con.getResponseMessage()).append(NEXT_LINE);

		// read headers
		con.getHeaderFields().entrySet().stream().filter(entry -> entry.getKey() != null).forEach(entry -> {
			FULL_CONTENT.append(entry.getKey()).append(COLON);
			List<?> headerValues = entry.getValue();
			Iterator<?> it = headerValues.iterator();
			if (it.hasNext()) {
				FULL_CONTENT.append(it.next());
				while (it.hasNext()) {
					FULL_CONTENT.append(COMMA).append(it.next());
				}
			}
			FULL_CONTENT.append(NEXT_LINE);
		});

		responseHeader = FULL_CONTENT.toString();
		responseReader = getResponseReaderFromConnection(con);

		return this;
	}

	@Override
	public String getResponseBody() {
		return getResponseReader().getResponseBody();
	}

	@Override
	public ResponseReader getResponseReaderFromConnection(HttpURLConnection connection) throws IOException {
		return new ResponseReader().getResponseReaderFromConnection(connection);
	}
}
