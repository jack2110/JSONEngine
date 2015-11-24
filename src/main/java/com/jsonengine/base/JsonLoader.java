package com.jsonengine.base;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.jsonengine.exception.JEParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

public enum JsonLoader {

	INSTANCE;

	private JSONParser		parser;

	private ObjectMapper	mapper;

	JsonLoader() {
		parser = new JSONParser();

		mapper = new ObjectMapper();
	}

	public JSONObject convertFromOtherJSON( Map src ) {

		if ( src == null ) {
			return null;
		}

		try {
			return (JSONObject) ( parser.parse( JSONObject.toJSONString( src ) ) );
		}
		catch ( ParseException e ) {
			throw new JEParseException( String.format( "JsonLader failed to convert from one json to another one." ),
					e );
		}

	}

	public JSONObject convertObjectToJSON( Object data ) {

		if ( data == null ) {
			return null;
		}

		String jsonStr;
		try {
			jsonStr = mapper.writeValueAsString( data );
			return convertToJSON( jsonStr );
		}
		catch ( Exception e ) {
			throw new JEParseException( String.format( "JsonLader failed to convert an object of %s to Json.",
					data.getClass().getSimpleName() ), e );
		}

	}

	public String convertObjectToJSONString( Object data ) {

		if ( data == null ) {
			return null;
		}

		try {
			return mapper.writeValueAsString( data );

		}
		catch ( Exception e ) {
			throw new JEParseException( String.format( "JsonLader failed to convert an object of %s to Json.",
					data.getClass().getSimpleName() ), e );
		}
	}

	public JSONObject convertToJSON( String data ) {

		if ( data == null ) {
			return null;
		}

		try {
			return (JSONObject) parser.parse( data );
		}
		catch ( ParseException e ) {
			throw new JEParseException( String.format( "JsonLader failed to convert a string of %s to Json.", data ),
					e );
		}
	}

	public JSONObject loadAsJSONObject( String fileName ) {

		if ( fileName == null ) {
			return null;
		}

		try {
			Reader reader = readStream( fileName );
			Object result = parser.parse( reader );

			return (JSONObject) result;
		}
		catch ( IOException | ParseException e ) {
			throw new JEParseException(
					String.format( "JsonLader failed to load a Json object from file of %s to Json.", fileName ), e );
		}

	}

	private Reader readStream( String fileName ) {

		InputStream in = this.getClass().getClassLoader().getResourceAsStream( fileName );

		if ( in == null ) {
			return null;
		}

		return new InputStreamReader( in );
	}

	public <T> T loadAsJavaObject( String fileName, Class<T> type ) throws Exception {

		Reader reader = readStream( fileName );

		return mapper.readValue( reader, type );

	}

	public <T> T convertToJava( JSONObject data, Class<T> classType ) {

		if ( data == null || classType == null ) {
			return null;
		}

		try {
			return mapper.readValue( data.toJSONString(), classType );
		}
		catch ( IOException e ) {
			throw new JEParseException( String.format( "JsonLader failed to convert a Json object to java of %s.",
					classType.getSimpleName() ), e );
		}

	}

}