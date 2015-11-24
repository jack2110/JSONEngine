package com.jsonengine.base;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONObjectParser {

	private static final String	ARRAY_PATTERN	= "^(.+)(\\[.+\\])$";

	private JSONObject			data;

	public static Object extract( String key, JSONObject data ) {

		if ( key == null || data == null ) {
			return null;
		}

		return parseWithKeys( parseKey( key ), data );
	}

	public JSONObjectParser( JSONObject data ) {
		if ( data == null ) {
			throw new InvalidParameterException( "JSONObjectParser expect to have a concreted resource to parse." );
		}

		this.data = data;
	}

	public Object getValue( String key ) {

		if ( key == null || key.isEmpty() ) {
			throw new InvalidParameterException( "JSONObjectParser.getValue() calling key is null or empty" );
		}

		return parseWithKeys( parseKey( key ), data );
	}

	private static List<String> parseKey( String key ) {

		String[] keyArray = key.split( "\\." );

		return new ArrayList<String>( Arrays.asList( keyArray ) );
	}

	@SuppressWarnings( "unchecked" )
	protected static Object parseWithKeys( List<String> keyList, Object data ) {

		if ( keyList.isEmpty() || data == null ) {
			return data;
		}

		if ( data instanceof JSONObject ) {
			String key = keyList.remove( 0 );
			Object value = ( (JSONObject) data ).get( key );

			return parseWithKeys( keyList, value );
		} else if ( data instanceof JSONArray ) {
			JSONArray jArray = (JSONArray) data;

			List<Object> resultList = new ArrayList<Object>();
			jArray.forEach( json -> {
				Object furtherResult = parseWithKeys( keyList, json );
				if ( furtherResult != null ) {
					resultList.add( furtherResult );
				}
			} );

			return resultList.isEmpty() ? null : resultList;
		} else {
			return data;
		}
	}

	public static void main( String[] args ) throws Exception {

		String s = "one.two.three.four";

		String[] split = s.split( "\\." );
		for ( String element : split ) {
			System.out.println( element );
		}

		JSONObject data = JsonLoader.INSTANCE.loadAsJSONObject( "googleResult.json" );
		JSONObjectParser parser = new JSONObjectParser( data );

		Object value = parser.getValue( "geometry.location.lat" );
		System.out.println( "Retrived value is: " + value );
	}

}
