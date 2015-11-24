package com.jsonengine.base;

import java.util.HashMap;
import java.util.Map;

import com.jsonengine.api.Rule;

public class RuleTreeNode {

	private final String					absolutePath;

	private final String					currentPath;

	private final Map<String, RuleTreeNode>	children;

	private Rule							rule;

	public RuleTreeNode( String parentPath, String currentPath ) {
		this( parentPath, currentPath, null );
	}

	public RuleTreeNode( String parentPath, String currentPath, Rule rule ) {
		this.absolutePath = parentPath;

		this.currentPath = currentPath;

		this.setRule( rule );

		children = new HashMap<String, RuleTreeNode>();
	}

	public String getCurrentPath() {

		return currentPath;
	}

	public void addChild( String key, RuleTreeNode child ) {

		children.put( key, child );
	}

	protected Map<String, RuleTreeNode> getChildren() {

		return children;
	}

	public Rule getRule() {

		return rule;
	}

	public RuleTreeNode getChild( String key ) {

		return key == null ? null : children.get( key );
	}

	public void setRule( Rule rule ) {

		this.rule = rule;
	}

	public String getAbsolutePath() {

		return absolutePath;
	}

}
