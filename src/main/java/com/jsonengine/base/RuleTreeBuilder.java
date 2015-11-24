package com.jsonengine.base;

import org.json.simple.JSONObject;

import com.jsonengine.exception.JEException;
import com.jsonengine.api.Rule;
import com.jsonengine.schema.ExtractRule;
import com.jsonengine.schema.ExtractRuleSet;
import com.jsonengine.schema.MappingRule;

public enum RuleTreeBuilder {

	INSTANCE;

	public RuleTreeNode build( String ruleFilename ) throws JEException {

		if ( ruleFilename == null ) {
			throw new IllegalArgumentException( "Failed to build rule tree: ruleFilename is null." );
		}

		try {
			JSONObject json = JsonLoader.INSTANCE.loadAsJSONObject( ruleFilename );
			System.out.println( json.toJSONString() );

			ExtractRuleSet ruleSet = JsonLoader.INSTANCE.loadAsJavaObject( ruleFilename, ExtractRuleSet.class );
			return build( ruleSet );
		}
		catch ( Exception e ) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;

	}

	public RuleTreeNode build( ExtractRuleSet ruleSet ) {

		if ( ruleSet == null || ruleSet.getRules() == null || ruleSet.getRules().isEmpty() ) {
			throw new IllegalArgumentException(
					"Failed to build rule tree: passing rules object either is null or contains no extract rule" );
		}

		RuleTreeNode root = new RuleTreeNode( "", "" );

		ruleSet.getRules().forEach( extractRule -> insertKey( root, extractRule ) );

		return root;

	}

	protected void insertKey( RuleTreeNode root, ExtractRule extractRule ) {

		String targetField = extractRule.getTargetField();

		String executeRuleType = extractRule.getExecuteRuleType();

		if ( targetField == null || executeRuleType == null ) {
			throw new IllegalArgumentException(
					"Failed to build rule tree: extract rule must contains target field and executeRuleType" );
		}

		Rule eRule = Rule.createRule( executeRuleType, extractRule.getExecuteRule() );

		String[] fields = targetField.split( "\\." );

		insertRule( root, eRule, fields, 0 );

	}

	private void insertRule( RuleTreeNode root, Rule eRule, String[] fields, int offset ) {

		// finished;
		if ( offset == fields.length ) {
			root.setRule( eRule );
			return;
		}

		String currentPath = fields[offset];

		RuleTreeNode child = root.getChild( currentPath );
		if ( child == null ) {
			String absolutePath = buildAbsolutePath( root.getAbsolutePath(), currentPath );
			child = new RuleTreeNode( absolutePath, currentPath );
			root.addChild( currentPath, child );
		}

		insertRule( child, eRule, fields, offset + 1 );

	}

	private String buildAbsolutePath( String absolutePath, String currentPath ) {

		StringBuilder sb = new StringBuilder();
		if ( absolutePath != null && !absolutePath.isEmpty() ) {
			sb.append( absolutePath ).append( "." );
		}

		if ( currentPath != null && !currentPath.isEmpty() ) {
			sb.append( currentPath ).append( "." );
		}

		return sb.toString();
	}

	public static void main( String[] args ) throws Exception {

		System.out.println( "Mapping is assignable " + MappingRule.class.isAssignableFrom( Rule.class ) );
		System.out.println( "Mapping is assignable " + Rule.class.isAssignableFrom( MappingRule.class ) );

		JSONObject rulesJSON = JsonLoader.INSTANCE.loadAsJSONObject( "samples/extractRulesSample.json" );
		System.out.println( "ruleJson: " + rulesJSON.toJSONString() );

		ExtractRuleSet tmpJava1 = JsonLoader.INSTANCE.loadAsJavaObject( "samples/extractRulesSample.json",
				ExtractRuleSet.class );

		ExtractRuleSet rulesJava = JsonLoader.INSTANCE.convertToJava( rulesJSON, ExtractRuleSet.class );

		RuleTreeNode rulesTree = RuleTreeBuilder.INSTANCE.build( rulesJava );

		if ( rulesTree != null ) {
			System.out.println( "get rules tree" );
		} else {
			System.out.println( "cannot get rules tree" );
		}

	}

}
