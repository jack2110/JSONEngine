package com.jsonengine.handler;

import java.util.List;

import org.json.simple.JSONArray;

import com.jsonengine.base.ProcessContext;
import com.jsonengine.schema.FilterRule;

public class FilterRuleHandler extends AbstractExtractRuleHandler<FilterRule> {

	@SuppressWarnings( "unchecked" )
	@Override
	protected Object execute( FilterRule rule, Object[] parameters, ProcessContext context ) {

		List<String> supportItems = rule.getSupportItems();
		Object origSrc = parameters[0];

		if ( supportItems == null || supportItems.isEmpty() ) {
			return origSrc; // there is no filter
		}

		if ( origSrc instanceof JSONArray ) {
			final JSONArray results = new JSONArray();
			( (JSONArray) origSrc ).forEach( key -> {
				if ( supportItems.contains( key ) ) {
					results.add( key );
				}
			} );

			return results;

		} else {
			if ( supportItems.contains( origSrc ) ) {
				return origSrc;
			} else {
				return null;
			}
		}
	}

	@Override
	protected boolean validateParameters( Object[] parameters ) {

		return parameters != null && parameters.length > 0;
	}

	@Override
	protected String[] getSourceField( FilterRule rule ) {

		String sourceField = rule.getSourceField();

		return sourceField == null ? null : new String[] { sourceField };
	}

}
