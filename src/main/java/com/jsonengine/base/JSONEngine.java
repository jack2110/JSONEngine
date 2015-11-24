package com.jsonengine.base;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;

import com.jsonengine.api.ExtractRuleHandler;
import com.jsonengine.api.Instantiatable;
import com.jsonengine.api.Rule;

public class JSONEngine {

	public final static String							TMP_PREFIX		= "_TMP.";

	public final static String							RESULT_PREFIX	= "_RESULT.";

	private final RuleTreeNode							rulesTree;

	private final Map<String, ExtractRuleHandler<?>>	ruleHandlers;

	public JSONEngine( RuleTreeNode rulesTree ) {
		if ( rulesTree == null ) {
			throw new IllegalArgumentException( "JSONEngine requires rulesConfig inputs." );
		}

		this.rulesTree = rulesTree;
		ruleHandlers = new HashMap<String, ExtractRuleHandler<?>>();

		initRules( this.rulesTree );
	}

	private void initRules( RuleTreeNode rulesTree ) {

		if ( rulesTree == null || rulesTree.getChildren().isEmpty() ) {
			return;
		}

		rulesTree.getChildren().values().forEach( rule -> {
			if ( rule.getRule() != null ) {
				insertRule( rule.getRule() );
			} else {
				initRules( rule );
			}
		} );
	}

	private void insertRule( Rule rule ) {

		if ( ruleHandlers.containsKey( rule.getIdentifier() ) ) {
			return;
		}

		ExtractRuleHandler handler = ExtractRuleHandler.generateHandler( rule.getHandler() );

		if ( handler instanceof Instantiatable ) {
			( (Instantiatable) handler ).initialize( rule );
		}

		ruleHandlers.put( rule.getIdentifier(), handler );

	}

	public JSONObject processing( JSONObject resource ) {

		if ( resource == null ) {
			return null;
		}

		ProcessContext context = new ProcessContext( resource );

		return internalProcessing( this.rulesTree, context );
	}

	private JSONObject internalProcessing( RuleTreeNode root, ProcessContext context ) {

		JSONObject result = new JSONObject();

		Map<String, RuleTreeNode> children = root.getChildren();

		if ( children != null && !children.isEmpty() ) {
			children.forEach( ( path, child ) -> {
				Object targetChild = null;
				if ( child.getRule() != null ) {
					targetChild = buildWithRule( child.getRule(), context );
				} else {
					targetChild = internalProcessing( child, context );
				}

				if ( targetChild != null ) {
					result.put( path, targetChild );

					context.addToResult( child.getAbsolutePath(), targetChild );
				}
			} );
		}

		return result;
	}

	private Object buildWithRule( Rule rule, ProcessContext context ) {

		ExtractRuleHandler<Rule> handler = (ExtractRuleHandler<Rule>) ruleHandlers.get( rule.getIdentifier() );

		return handler.extract( rule, context );

	}
}
