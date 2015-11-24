package com.jsonengine.base;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

public class ProcessContext {

	public final static String			TMP_PREFIX		= "_TMP.";

	public final static String			RESULT_PREFIX	= "_RESULT.";

	private final JSONParser			parser;

	private final Map<String, Object>	resourceMap;

	private final Map<String, Object>	resultMap;

	private final Map<String, Object>	cacheMap;

	protected ProcessContext( JSONObject resource ) {
		if ( resource == null ) {
			throw new IllegalArgumentException( "ProcessorContext requires a resource input." );
		}

		parser = new JSONParser( resource );

		resourceMap = new HashMap<String, Object>();

		resultMap = new HashMap<String, Object>();

		cacheMap = new HashMap<String, Object>();
	}

	public Object getFromResource( String key ) {

		return key == null ? null : resourceMap.get( key );
	}

	public Object addToResource( String key, Object value ) {

		return key == null ? null : resourceMap.put( key, value );
	}

	public Object getFromCache( String key ) {

		return key == null ? null : cacheMap.get( key );
	}

	public Object addToCache( String key, Object value ) {

		return key == null ? null : cacheMap.put( key, value );
	}

	public Object getFromResult( String key ) {

		return key == null ? null : resultMap.get( key );
	}

	public Object addToResult( String key, Object value ) {

		return key == null ? null : resultMap.put( key, value );
	}

	public Object getDirctSourceValue( String sourceField ) {

		return sourceField == null ? null : parser.getValue( sourceField );
	}

	public Object getSourceValue( String sourceField ) {

		if ( sourceField == null ) {
			return null;
		}

		int len = sourceField.length();

		if ( len >= 2 && sourceField.charAt( 0 ) == '\'' || sourceField.charAt( len - 1 ) == '\'' ) {
			return sourceField.substring( 1, len - 1 );
		}

		if ( sourceField.startsWith( TMP_PREFIX ) ) {
			return this.getFromCache( sourceField.substring( TMP_PREFIX.length() ) );
		}

		if ( sourceField.startsWith( RESULT_PREFIX ) ) {
			return this.getFromResult( sourceField.substring( RESULT_PREFIX.length() ) );
		}

		Object result = this.resourceMap.get( sourceField );
		if ( result == null ) {
			result = this.getDirctSourceValue( sourceField );
			if ( result != null ) {
				this.resourceMap.put( sourceField, result );
			}
		}

		return result;
	}

}
