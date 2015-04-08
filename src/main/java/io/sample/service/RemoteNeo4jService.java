package io.sample.service;

import io.sample.bean.para.Neo4jRemotePara;


public interface RemoteNeo4jService {
	public String createSimpleGraph(Neo4jRemotePara neo4jLocalPara) throws Exception;
}
