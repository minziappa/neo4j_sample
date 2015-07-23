package io.sample.service;

import io.sample.bean.para.Neo4jLocalPara;


public interface LocalNeo4jService {

	
	public void createDb() throws Exception;
	public void deleteDb() throws Exception;

	public void setGraph(Neo4jLocalPara neo4jLocalPara) throws Exception;
	public String getGraph(Neo4jLocalPara neo4jLocalPara) throws Exception;
	public String getGraph2(Neo4jLocalPara neo4jLocalPara) throws Exception;
	public void deleteGraph(Neo4jLocalPara neo4jLocalPara) throws Exception;

	public void shutdown() throws Exception;

//	public Neo4jModel setGetGraph(Neo4jLocalPara neo4jLocalPara) throws Exception;

}
