package com.jsonengine.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsonengine.exception.JEException;
import com.jsonengine.base.JSONEngine;
import com.jsonengine.base.JsonLoader;
import com.jsonengine.base.RuleTreeBuilder;
import com.jsonengine.base.RuleTreeNode;

public class TestStringParseRuleHandler {

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testExecute() throws JEException {

		JSONObject testSrc = JsonLoader.INSTANCE.loadAsJSONObject( "handler/stringParse/testData.json" );

		assertNotNull( testSrc );

		RuleTreeNode ruleSet = RuleTreeBuilder.INSTANCE.build( "handler/stringParse/testRule.json" );

		assertNotNull( ruleSet );

		JSONEngine engine = new JSONEngine( ruleSet );

		JSONObject result = engine.processing( testSrc );

		assertNotNull( result );

		Object resultObject = ( (JSONObject) result.get( "test" ) );

		assertTrue( resultObject instanceof JSONObject );

		JSONObject resultJson = (JSONObject) resultObject;

		Object address = resultJson.get( "address" );

		assertNotNull( address );

		assertEquals( address, "615 S Rengstorff Ave" );

		Object city = resultJson.get( "city" );

		assertNotNull( city );

		assertEquals( city, "Mountain View" );

		Object state = resultJson.get( "state" );

		assertNotNull( state );

		assertEquals( state, "CA" );

		Object zipcode = resultJson.get( "zipcode" );

		assertNotNull( zipcode );

		assertEquals( zipcode, "94040" );

		Object country = resultJson.get( "country" );

		assertNotNull( country );

		assertEquals( country, "United States" );
	}

}
