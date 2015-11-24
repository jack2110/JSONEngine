package com.jsonengine.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsonengine.exception.JEException;
import com.jsonengine.base.JSONEngine;
import com.jsonengine.base.JsonLoader;
import com.jsonengine.base.RuleTreeBuilder;
import com.jsonengine.base.RuleTreeNode;

public class TestSimpleLookupRuleHandler {

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testExecute() throws JEException {

		JSONObject testSrc = JsonLoader.INSTANCE.loadAsJSONObject( "handler/simpleLookup/testData.json" );

		Object testObject = ( (JSONObject) testSrc.get( "src" ) ).get( "item1" );

		assertTrue( testObject instanceof JSONArray );

		JSONArray testArray = (JSONArray) testObject;

		assertEquals( testArray.size(), 4 );

		assertTrue( testArray.contains( "sushi" ) );

		RuleTreeNode ruleSet = RuleTreeBuilder.INSTANCE.build( "handler/simpleLookup/testRule.json" );

		JSONEngine engine = new JSONEngine( ruleSet );

		JSONObject result = engine.processing( testSrc );

		assertNotNull( result );

		Object resultObject = ( (JSONObject) result.get( "test" ) ).get( "field1" );

		assertTrue( resultObject instanceof JSONArray );

		JSONArray resultArray = (JSONArray) resultObject;

		assertEquals( resultArray.size(), 7 );

		assertTrue( resultArray.contains( "Japanese Restaurant" ) );
	}

}
