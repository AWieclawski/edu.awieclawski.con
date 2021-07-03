package edu.awieclawski.con;

import java.net.ConnectException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnChecker {
	private final static Logger LOGGER = Logger.getLogger(ConnChecker.class.getName());

	public boolean getSocket(String host, int port) {
		boolean result = true;

		try {
			Socket clientSocket = new Socket(host, port);

			LOGGER.log(Level.WARNING,
					"Socket connected=" + clientSocket.isConnected() + "|host=" + host + ",port=" + port);

			clientSocket.close();
		} catch (ConnectException e) {
			result = false;

			LOGGER.log(Level.SEVERE, "ConnectException " + host + port, e);

		} catch (Exception e) {
			result = false;

			LOGGER.log(Level.SEVERE, "Exception  " + host + port, e);

		}
		return result;
	}

}
