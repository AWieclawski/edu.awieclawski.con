package edu.awieclawski.ctrl;

import edu.awieclawski.con.ConnChecker;

/**
 * Simple JAVA HTTP connection
 * 
 * @author AWieclawski
 *
 */
public class DoConnection {

	public static void main(String[] args) {

		// http://api.nbp.pl/api/exchangerates/tables/a/2021-07-02?format=json

		String protocol = "http";
		String host = "api.nbp.pl";
		int port = 80;
		String path = "api/exchangerates/tables/a/2021-07-02";
		String response = "";

		if (new ConnChecker().getSocket(host, port)) {

			String fullUrl = protocol + "://" + host + ":" + port + "/" + path;

			response = new ConnConttoller().establishConnection(fullUrl);

			System.out.println(response);
		}

	}

}
