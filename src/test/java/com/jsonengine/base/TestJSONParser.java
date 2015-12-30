package com.jsonengine.base;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.json.simple.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestJSONParser {

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testJSONArrayExtract() {

		JSONObject testSrc = JsonLoader.INSTANCE.loadAsJSONObject( "base/JSONParserArraySample1.json" );

		// Test get single result
		Object result = JSONParser.extract( "event.schedule.dates[1].start", testSrc );

		assertEquals( result, "2015-12-23" );

		System.out.println( "The result is: " + result );

		result = JSONParser.extract( "event.schedule.dates.start", testSrc );

		assertTrue( result instanceof List );

		List resultList = (List) result;

		assertEquals( resultList.size(), 5 );

		assertEquals( resultList.get( 1 ), "2015-12-23" );

	}

	@Test
	public void testJSONObjectExtractWithArrayPattern() {

		JSONObject testSrc = JsonLoader.INSTANCE.loadAsJSONObject( "base/JSONParserArraySample2.json" );

		// Test get single result
		Object result = JSONParser.extract( "event.schedule.dates[0].start", testSrc );

		System.out.println( "The result is: " + result );

		assertEquals( result, "2015-12-19" );

	}

}
