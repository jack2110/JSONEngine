package com.jsonengine.handler;

import com.jsonengine.base.ProcessContext;
import com.jsonengine.schema.SplitRule;

/**
 * This rule expects to return an array as splitting result. Its delimiter is regex string
 * 
 * The import parameters expect to be Strings. the handler uses toString() to get value of parameter. So, it could
 * result in unexpected result if parameter is not a string.
 * 
 * @author jian
 *
 */
public class SplitRuleHandler extends AbstractExtractRuleHandler<SplitRule> {

	private static final String RULE_PREFIX = "split.";

	@Override
	protected Object execute( SplitRule rule, Object[] parameters, ProcessContext context ) {

		String delimiter = rule.getDelimiter() == null ? " " : rule.getDelimiter();

		return parameters[0].toString().split( delimiter );
	}

	@Override
	protected boolean validateParameters( Object[] parameters ) {

		return parameters != null && parameters.length > 0;
	}

	@Override
	protected String[] getSourceField( SplitRule rule ) {

		String sourceField = rule.getSourceField();

		return sourceField == null ? null : new String[] { sourceField };
	}

}
