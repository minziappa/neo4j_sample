package io.sample.bean.model;

import java.util.List;

import io.sample.bean.CommonBean;


public class Neo4jModel {

	private String value;
	private List<CommonBean> nodeList;

	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public List<CommonBean> getNodeList() {
		return nodeList;
	}

	public void setNodeList(List<CommonBean> nodeList) {
		this.nodeList = nodeList;
	}

}