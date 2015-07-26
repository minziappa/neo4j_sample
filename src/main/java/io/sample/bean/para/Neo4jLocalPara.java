package io.sample.bean.para;

import io.sample.bean.CommonBean;


public class Neo4jLocalPara extends CommonBean {

	private String firstNodeKey = null;

	private String firstNodeValue = null;

	private String secondNodeKey = null;

	private String secondNodeValue = null;

	public String getFirstNodeKey() {
		return firstNodeKey;
	}

	public void setFirstNodeKey(String firstNodeKey) {
		this.firstNodeKey = firstNodeKey;
	}

	public String getFirstNodeValue() {
		return firstNodeValue;
	}

	public void setFirstNodeValue(String firstNodeValue) {
		this.firstNodeValue = firstNodeValue;
	}

	public String getSecondNodeKey() {
		return secondNodeKey;
	}

	public void setSecondNodeKey(String secondNodeKey) {
		this.secondNodeKey = secondNodeKey;
	}

	public String getSecondNodeValue() {
		return secondNodeValue;
	}

	public void setSecondNodeValue(String secondNodeValue) {
		this.secondNodeValue = secondNodeValue;
	}


}
