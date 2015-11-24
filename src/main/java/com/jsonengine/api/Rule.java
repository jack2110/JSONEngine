package com.jsonengine.api;

import org.json.simple.JSONObject;

import com.jsonengine.base.JsonLoader;
import com.jsonengine.schema.ExecuteRule;

public interface Rule {

	String RULE_PACKAGE = "com.jsonengine.schema";

	String getIdentifier();

	String getHandler();

	static Rule createRule( String ruleType, ExecuteRule rawRule ) {

		try {
			String ruleStr = JSONObject.toJSONString( rawRule.getAdditionalProperties() );
			JSONObject json = JsonLoader.INSTANCE.convertToJSON( ruleStr );

			String className = RULE_PACKAGE + "." + ruleType;
			Class<?> ruleClass = null;

			try {
				ruleClass = Class.forName( className );
			}
			catch ( ClassNotFoundException cnfe ) {
				ruleClass = Class.forName( ruleType );
			}

			if ( Rule.class.isAssignableFrom( ruleClass ) ) {
				return (Rule) ( JsonLoader.INSTANCE.convertToJava( json, ruleClass ) );
			}

		}
		catch ( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

}
