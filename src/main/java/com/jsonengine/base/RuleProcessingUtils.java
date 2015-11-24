package com.jsonengine.base;

public class RuleProcessingUtils {

	public static boolean isAbsoluteValue( String field ) {

		return field != null && field.length() >= 2 && '"' == field.charAt( 0 )
				&& '"' == field.charAt( field.length() - 1 );
	}

	public static String getAbsoluteValue( String field ) {

		return isAbsoluteValue( field ) ? field.substring( 1, field.length() - 1 ) : null;
	}

}
