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

public class TestFilterRuleHandler {

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testValidateParameters() {

		assertTrue( true );
	}

	@Test
	public void testFilterRuleHandler() throws JEException {

		JSONObject testSrc = JsonLoader.INSTANCE.loadAsJSONObject( "handler/FilterHandler/testData.json" );

		Object testObject = ( (JSONObject) testSrc.get( "src" ) ).get( "item1" );

		assertTrue( testObject instanceof JSONArray );

		JSONArray testArray = (JSONArray) testObject;

		assertEquals( testArray.size(), 6 );

		assertTrue( testArray.contains( "badArtists" ) );

		RuleTreeNode ruleSet = RuleTreeBuilder.INSTANCE.build( "handler/FilterHandler/testRule.json" );

		JSONEngine engine = new JSONEngine( ruleSet );

		JSONObject result = engine.processing( testSrc );

		assertNotNull( result );

		Object resultObject = ( (JSONObject) result.get( "test" ) ).get( "field1" );

		assertTrue( resultObject instanceof JSONArray );

		JSONArray resultArray = (JSONArray) resultObject;

		assertEquals( resultArray.size(), 3 );

		assertTrue( !resultArray.contains( "badArtists" ) );
	}

	@Test
	public void testGetParametersFilterRule() {

		assertTrue( true );
	}

	@Test
	public void testExecuteFilterRuleObjectArray() {

		assertTrue( true );

	}

}
