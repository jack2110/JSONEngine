package com.jsonengine.api;

import com.jsonengine.base.ProcessContext;

public interface ExtractRuleHandler<ERule extends Rule> {

	String	RULE_PACKAGE		= "com.jsonengine.handler";

	String	RULE_CACHE_PREFIX	= "_RULE.";

	Object extract( ERule rule, ProcessContext context );

	@SuppressWarnings( "unchecked" )
	public static ExtractRuleHandler generateHandler( String type ) {

		if ( type == null ) {
			throw new IllegalArgumentException(
					"Cannot generate extract rule handler. The handle type or rule is null." );
		}

		String fullClassName = type.contains( "." ) ? type : RULE_PACKAGE + "." + type;

		try {

			Class handleClass = Class.forName( fullClassName );

			if ( ExtractRuleHandler.class.isAssignableFrom( handleClass ) ) {
				return (ExtractRuleHandler) handleClass.newInstance();
			}

		}
		catch ( Exception e ) {
			// TODO will handle it later
		}

		return null;
	}
}
