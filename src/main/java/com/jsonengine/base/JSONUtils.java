package com.jsonengine.base;

import org.json.simple.JSONArray;

public class JSONUtils {

	private static final String DEFAULT_DELIMITER = ",";

	public static String JSONArrayToString( JSONArray array ) {

		return JSONArrayToString( array, null );
	}

	@SuppressWarnings( "unchecked" )
	public static String JSONArrayToString( JSONArray array, String delimiter ) {

		StringBuilder sb = new StringBuilder();

		final String separator = delimiter == null ? DEFAULT_DELIMITER : delimiter;

		if ( array == null || array.isEmpty() ) {
			return sb.toString();
		}

		array.forEach( json -> sb.append( json.toString() ).append( separator ) );

		return sb.substring( 0, sb.length() - separator.length() );
	}
}
