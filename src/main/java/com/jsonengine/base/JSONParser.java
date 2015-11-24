package com.jsonengine.base;

import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class JSONParser {

	private static final String		ARRAY_REGEX		= "^(.+)(\\[.+\\])$";

	private static final Pattern	ARRAY_PATTERN	= Pattern.compile( ARRAY_REGEX );

	private JSONObject				data;

	public static Object extract( String key, JSONObject data ) {

		if ( key == null || data == null ) {
			return null;
		}

		return parseWithKeys( parseKey( key ), data );
	}

	public JSONParser( JSONObject data ) {
		if ( data == null ) {
			throw new InvalidParameterException( "JSONParser expect to have a concreted resource to parse." );
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
			if ( !key.matches( ARRAY_REGEX ) ) {
				Object value = ( (JSONObject) data ).get( key );

				return parseWithKeys( keyList, value );
			} else {
				ArrayToken token = ArrayToken.createToken( key );
				Object value = ( (JSONObject) data ).get( token.fieldName );

				if ( value == null || !( value instanceof JSONArray ) ) {
					return value;
				} else {
					return parseArrayWithKeys( (JSONArray) value, token, keyList );
				}
			}

		} else if ( data instanceof JSONArray ) {
			JSONArray jArray = (JSONArray) data;

			if ( jArray == null || jArray.isEmpty() ) {
				return jArray;
			}

			List<Object> resultList = new ArrayList<Object>();
			jArray.forEach( json -> {
				Object furtherResult = parseWithKeys( keyList, json );
				if ( furtherResult != null ) {
					resultList.add( furtherResult );
				}
			} );

			return resultList.isEmpty();
		} else {
			return data;
		}
	}

	@SuppressWarnings( { "rawtypes", "unchecked" } )
	private static Object parseArrayWithKeys( JSONArray data, ArrayToken token, List<String> keyList ) {

		if ( token.key == null ) {
			Object result = data.get( token.offset );
			return result == null ? null : parseWithKeys( keyList, result );

		} else {

			Optional result = data.stream().filter( d -> {
				if ( d instanceof JSONObject ) {
					Object value = ( (JSONObject) d ).get( token.key );
					return value != null && value.equals( token.filterValue );
				} else {
					return d.equals( token.filterValue );
				}
			} ).findFirst();

			return result.isPresent() ? parseWithKeys( keyList, result.get() ) : null;
		}

	}

	protected static class ArrayToken {

		String	fieldName;

		String	key;

		String	filterValue;

		int		offset;

		private ArrayToken( String fieldName, String key, String filterValue, int offset ) {
			this.fieldName = fieldName;
			this.key = key;
			this.filterValue = filterValue;
			this.offset = offset;
		}

		// tokenStr is expected to be a valid string
		static ArrayToken createToken( String tokenStr ) {

			Matcher matcher = ARRAY_PATTERN.matcher( tokenStr );
			if ( !matcher.find() ) {
				// TODO add error handle later
			}

			String fieldName = matcher.group( 1 );
			String data = matcher.group( 2 );
			data = data.substring( 1, data.length() - 1 );

			if ( data.contains( "=" ) ) {
				String[] keyValue = data.split( "=" );

				if ( keyValue.length < 2 ) {
					// TODO add error handle later
				}

				return new ArrayToken( fieldName, keyValue[0].trim(), keyValue[1].trim(), -1 );
			} else {
				try {
					int offset = Integer.parseInt( data );
					return new ArrayToken( fieldName, null, null, offset );
				}
				catch ( NumberFormatException ne ) {
					// TODO add error handle later;
				}
			}

			return null;
		}

	}

	public static void main( String[] args ) {

		Pattern pattern = Pattern.compile( ARRAY_REGEX );

		String testStr = "testStr[3]";

		Matcher matcher = pattern.matcher( testStr );

		System.out.println( "The matcher is: " + matcher.find() );

		String group1 = matcher.group( 1 );

		System.out.println( "The group1 is: " + matcher.group( 1 ) );
		System.out.println( "The group2 is: " + matcher.group( 2 ) );
	}

}
