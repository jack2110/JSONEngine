package com.jsonengine.handler;

import com.jsonengine.base.ProcessContext;
import com.jsonengine.schema.MappingRule;

public class MappingRuleHandler extends AbstractExtractRuleHandler<MappingRule> {

	@Override
	protected Object execute( MappingRule rule, Object[] parameters, ProcessContext context ) {

		return parameters[0];
	}

	@Override
	protected boolean validateParameters( Object[] parameters ) {

		return parameters != null && parameters.length > 0;
	}

	@Override
	protected String[] getSourceField( MappingRule rule ) {

		String sourceField = rule.getSourceField();

		return sourceField == null ? null : new String[] { sourceField };
	}

}
