package edu.awieclawski.con.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class ParameterStringBuilder {

	public String getParamsString(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuilder result = new StringBuilder();
		String charEncoding = "UTF-8";

		for (Map.Entry<String, String> entry : params.entrySet()) {
			result.append(URLEncoder.encode(entry.getKey(), charEncoding));
			result.append("=");
			result.append(URLEncoder.encode(entry.getValue(), charEncoding));
			result.append("&");
		}

		String resultString = result.toString();
		// removes last "&" String
		resultString = resultString.length() > 0 ? resultString.substring(0, resultString.length() - 1) : resultString;

		return resultString;
	}

}
