package edu.awieclawski.con.dao;

import java.io.IOException;
import java.net.HttpURLConnection;

import edu.awieclawski.con.res.ResponseReader;

/**
 * 
 * @author AWieclawski
 *
 */
public interface I_Response {

	StringBuffer CONTENT = new StringBuffer();

	public String getResponseBody();

	public ResponseReader getResponseReaderFromConnection(HttpURLConnection connection) throws IOException;

}
