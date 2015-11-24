package com.jsonengine.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jsonengine.exception.JEException;
import com.jsonengine.base.JSONEngine;
import com.jsonengine.base.JsonLoader;
import com.jsonengine.base.RuleTreeBuilder;
import com.jsonengine.base.RuleTreeNode;

public class TestMergeRuleHandler {

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testExecute() throws JEException {

		JSONObject testSrc = JsonLoader.INSTANCE.loadAsJSONObject( "handler/merge/testData.json" );

		Object testObject = ( (JSONObject) testSrc.get( "geocode" ) );

		assertNotNull( testObject );

		RuleTreeNode ruleSet = RuleTreeBuilder.INSTANCE.build( "handler/merge/testRule.json" );

		assertNotNull( ruleSet );

		JSONEngine engine = new JSONEngine( ruleSet );

		JSONObject result = engine.processing( testSrc );

		assertNotNull( result );

		Object resultObject = ( (JSONObject) result.get( "test" ) ).get( "location" );

		assertNotNull( result );

		assertEquals( resultObject, "12345,67890" );
	}

}
