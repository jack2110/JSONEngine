package com.jsonengine.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jsonengine.base.ProcessContext;
import com.jsonengine.schema.StringParseRule;

public class StringParseRuleHandler extends AbstractExtractRuleHandler<StringParseRule> {

	@Override
	protected String[] getSourceField( StringParseRule rule ) {

		String sourceField = rule.getSourceField();

		return sourceField == null ? null : new String[] { sourceField };
	}

	@Override
	protected Object execute( StringParseRule rule, Object[] parameters, ProcessContext context ) {

		String cacheKey = RULE_CACHE_PREFIX + rule.getIdentifier() + "." + rule.getSourceField();

		List<String> data = null;

		Object cacheResult = context.getFromCache( cacheKey );

		if ( cacheResult == null || !( cacheResult instanceof List ) ) {
			data = parse( rule, parameters[0].toString() );
			if ( data != null && !data.isEmpty() ) {
				context.addToCache( cacheKey, data );
			}
		} else {
			data = (List<String>) cacheResult;
		}

		int groupNo = rule.getGroupNo();

		return ( groupNo >= 0 && groupNo < data.size() ) ? data.get( groupNo ) : null;
	}

	private List<String> parse( StringParseRule rule, String src ) {

		Pattern pattern = Pattern.compile( rule.getPattern() );

		Matcher matcher = pattern.matcher( src );
		List<String> result = new ArrayList<String>();
		if ( !matcher.find() ) {
			return result;
		}

		for ( int i = 1; i <= matcher.groupCount(); i++ ) {
			result.add( matcher.group( i ).trim() );
		}

		return result;
	}

	@Override
	protected boolean validateParameters( Object[] parameters ) {

		return parameters != null && parameters.length > 0;
	}

	public static void main( String[] args ) {

		String data = "615 S Rengstorff Ave, Mountain View, CA 94040, United States";

		String patternStr = "^(.+)(,)(.+)(,)(.+)(\\s)(.+)(,)(.+)$";

		Pattern pattern = Pattern.compile( patternStr );

		Matcher matcher = pattern.matcher( data );

		if ( matcher.find() ) {
			System.out.println( "String matches with patter, and have group of " + matcher.groupCount() );
			for ( int i = 0; i <= matcher.groupCount(); i++ ) {
				System.out.println( String.format( "The group %d value is %s", i, matcher.group( i ) ) );
			}

		} else {
			System.out.println( "Could not find match" );
		}

	}

}
