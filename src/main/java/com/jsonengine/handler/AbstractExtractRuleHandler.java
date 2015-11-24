package com.jsonengine.handler;

import com.jsonengine.api.ExtractRuleHandler;
import com.jsonengine.api.Rule;
import com.jsonengine.base.ProcessContext;

public abstract class AbstractExtractRuleHandler<ERule extends Rule> implements ExtractRuleHandler<ERule> {

	public AbstractExtractRuleHandler() {}

	@Override
	public Object extract( ERule rule, ProcessContext context ) {

		Object[] parameters = getParameters( rule, context );
		if ( parameters == null || parameters.length == 0 ) {
			return null;
		}

		if ( rule == null || !validateParameters( parameters ) ) {
			throw new IllegalArgumentException(
					"Failed to handle a rule: extract rule is null or parameters are illegal" );
		}

		return execute( rule, parameters, context );
	}

	protected Object[] getParameters( ERule rule, ProcessContext context ) {

		String[] sourceFields = getSourceField( rule );

		if ( sourceFields == null ) {
			return null;
		}

		Object[] results = new Object[sourceFields.length];

		for ( int i = 0; i < sourceFields.length; i++ ) {
			results[i] = context.getSourceValue( sourceFields[i] );
		}

		return results;
	}

	protected abstract String[] getSourceField( ERule rule );

	protected abstract Object execute( ERule rule, Object[] parameters, ProcessContext context );

	protected abstract boolean validateParameters( Object[] parameters );
}
