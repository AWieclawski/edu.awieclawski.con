package edu.awieclawski.con.dao;

import java.io.IOException;
import java.net.HttpURLConnection;

import edu.awieclawski.con.res.FullResponseBuilder;
import edu.awieclawski.con.res.ResponseReader;

public interface I_FullResponse extends I_Response {

	StringBuffer FULL_CONTENT = new StringBuffer();
	final String SPACE = " ";
	final String NEXT_LINE = "\n";
	final String COLON = ": ";
	final String COMMA = ", ";

	public String getResponseHeader();

	public ResponseReader getResponseReader();

	public void setResponseReader(ResponseReader responseReader);

	public FullResponseBuilder getFullResponseBuilderFromConnection(HttpURLConnection con) throws IOException;

}
