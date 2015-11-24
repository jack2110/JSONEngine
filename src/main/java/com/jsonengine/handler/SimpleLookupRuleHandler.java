package com.jsonengine.handler;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.json.simple.JSONArray;

import com.jsonengine.exception.JEErrorCodeEnum;
import com.jsonengine.api.Instantiatable;
import com.jsonengine.base.ProcessContext;
import com.jsonengine.exception.JERuleHandlerInitException;
import com.jsonengine.schema.SimpleLookupRule;

public class SimpleLookupRuleHandler extends AbstractExtractRuleHandler<SimpleLookupRule>
		implements Instantiatable<SimpleLookupRule> {

	private Map<String, Set<String>>	lookupTable	= new HashMap<String, Set<String>>();

	private String						delimiter;

	private String						separator;

	private boolean						isSingleOutput;

	@Override
	public void initialize( SimpleLookupRule rule ) {

		if ( rule == null || rule.getLookupFile() == null ) {
			throw new JERuleHandlerInitException( JEErrorCodeEnum.INVALID_PARAMETERS,
					String.format( "%s requires lookup file in Rule", this.getClass().getSimpleName() ) );
		}

		delimiter = rule.getDelimiter() != null ? rule.getDelimiter() : "=";
		separator = rule.getSeparator() != null ? rule.getSeparator() : ",";

		isSingleOutput = rule.getIsSingle() != null ? rule.getIsSingle() : false;

		URL resource = this.getClass().getClassLoader().getResource( rule.getLookupFile() );
		if ( resource == null ) {
			throw new JERuleHandlerInitException( JEErrorCodeEnum.INVALID_PARAMETERS,
					String.format( "Cannot find lookup file of %s for %s initialization", rule.getLookupFile(),
							this.getClass().getSimpleName() ) );
		}

		try {
			Path path = Paths.get( resource.toURI() );
			@SuppressWarnings( "resource" )
			Stream<String> stream = Files.lines( path );

			stream.forEach( str -> addlookUp( str ) );

		}
		catch ( IOException | URISyntaxException e ) {
			throw new JERuleHandlerInitException( JEErrorCodeEnum.FILE_READ_FAILURE,
					String.format( "Cannot load lookup file of %s for %s initialization", rule.getLookupFile(),
							this.getClass().getSimpleName() ),
					e );
		}
	}

	private void addlookUp( String str ) {

		if ( str == null || str.isEmpty() ) {
			return;
		}

		String[] data = str.split( delimiter );
		if ( data == null || data.length != 2 ) {
			return; // ignore bad data
		}

		String key = data[0].trim();
		String values = data[1].trim();

		Set<String> valueSet = lookupTable.get( key );
		if ( valueSet == null ) {
			valueSet = new HashSet<String>();
			lookupTable.put( key, valueSet );
		}

		String[] valueArr = values.split( separator );
		if ( valueArr == null ) {
			return;
		}

		for ( String value : valueArr ) {
			valueSet.add( value.trim() );
		}

	}

	@Override
	protected String[] getSourceField( SimpleLookupRule rule ) {

		String sourceField = rule.getSourceField();

		return sourceField == null ? null : new String[] { sourceField };
	}

	@SuppressWarnings( "unchecked" )
	@Override
	protected Object execute( SimpleLookupRule rule, Object[] parameters, ProcessContext context ) {

		Object src = parameters[0];
		final Set<String> resultSet = new HashSet<String>();
		if ( src instanceof JSONArray ) {
			( (JSONArray) src ).forEach( key -> {
				Set<String> data = lookupTable.get( key.toString() );
				if ( data != null && !data.isEmpty() ) {
					resultSet.addAll( data );
				}
			} );

		} else {
			Set<String> data = lookupTable.get( src.toString() );
			if ( data != null && !data.isEmpty() ) {
				resultSet.addAll( data );
			}
		}

		return convertResult( resultSet );
	}

	@SuppressWarnings( "unchecked" )
	private Object convertResult( Set<String> resultSet ) {

		if ( resultSet == null || resultSet.isEmpty() ) {
			return null;
		}

		if ( this.isSingleOutput ) {
			resultSet.iterator().next();
		}

		JSONArray arrayResult = new JSONArray();
		resultSet.forEach( data -> arrayResult.add( data ) );

		return arrayResult;
	}

	@Override
	protected boolean validateParameters( Object[] parameters ) {

		return parameters != null && parameters.length > 0;
	}

}
