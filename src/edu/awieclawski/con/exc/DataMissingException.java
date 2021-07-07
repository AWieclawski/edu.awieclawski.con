package edu.awieclawski.con.exc;

/**
 * An exception used for signaling situations where some data may not be
 * available, has different types.
 */
public class DataMissingException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7590301480491623450L;
	private DataMissingException.DataMissingExceptionType dataMissingExceptionType;
	private String url;
	private String message;

	@Override
	public String toString() {
		return "\nDataMissingException{" + "dataMissingExceptionType=" + dataMissingExceptionType + "\n---> url='" + url
				+ '\'' + ", message='" + message + '\'' + '}';
	}

	public DataMissingException(DataMissingExceptionType dataMissingExceptionType, String url, String message) {

		this.dataMissingExceptionType = dataMissingExceptionType;
		this.url = url;
		this.message = message;
	}

	// inner Enum
	public enum DataMissingExceptionType {
		NETWORK_ERROR, INCORRECT_PARMETERS_ERROR, INVALID_REQUEST_ERROR, INVALID_URL_ERROR, PROTOCOL_ERROR,
		RESPONSE_READER_ERROR

	}
}
