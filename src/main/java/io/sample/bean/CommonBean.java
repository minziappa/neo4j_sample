package io.sample.bean;

public class CommonBean {

	private int nodeId;

	private String nodeKey = null;

	private String nodeValue = null;

	private String relationshopKey = null;

	private String relationshopValue = null;

	public int getNodeId() {
		return nodeId;
	}

	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}

	public String getNodeKey() {
		return nodeKey;
	}

	public void setNodeKey(String nodeKey) {
		this.nodeKey = nodeKey;
	}

	public String getNodeValue() {
		return nodeValue;
	}

	public void setNodeValue(String nodeValue) {
		this.nodeValue = nodeValue;
	}

	public String getRelationshopKey() {
		return relationshopKey;
	}

	public void setRelationshopKey(String relationshopKey) {
		this.relationshopKey = relationshopKey;
	}

	public String getRelationshopValue() {
		return relationshopValue;
	}

	public void setRelationshopValue(String relationshopValue) {
		this.relationshopValue = relationshopValue;
	}

	
}