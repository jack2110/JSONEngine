package com.jsonengine.handler;

import java.util.List;

import com.jsonengine.base.ProcessContext;
import com.jsonengine.schema.MergeRule;

/**
 * MergeRuleHandler expects String for its parameters. It will use toString() to get value of parameters. Therefore, it
 * might result in unexpected result if calling method uses another type of Object for parameter.
 * 
 * Delimiter is expected to be a single character. If delimiter is null, " " is used as default delimiter
 * 
 * @author jian
 *
 */
public class MergeRuleHandler extends AbstractExtractRuleHandler<MergeRule> {

	@Override
	protected Object execute( MergeRule rule, Object[] parameters, ProcessContext context ) {

		String delimiter = rule.getDelimiter() == null ? " " : rule.getDelimiter();

		StringBuilder builder = new StringBuilder( "" );

		for ( Object parameter : parameters ) {
			String str = parameter == null ? "" : parameter.toString();
			builder.append( str ).append( delimiter );
		}

		return builder.substring( 0, builder.length() - delimiter.length() );
	}

	@Override
	protected String[] getSourceField( MergeRule rule ) {

		List<String> sourceFields = rule.getSourceField();

		if ( sourceFields == null ) {
			return null;
		} else {
			String[] result = new String[sourceFields.size()];
			return sourceFields.toArray( result );
		}
	}

	@Override
	protected boolean validateParameters( Object[] parameters ) {

		return parameters != null && parameters.length > 0;
	}

}
