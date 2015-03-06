package io.sample.service;

import io.sample.bean.model.Neo4jModel;
import io.sample.bean.para.Neo4jLocalPara;


public interface LocalNeo4jService {

	public void setGraph(Neo4jLocalPara neo4jLocalPara) throws Exception;
	public String getGraph(Neo4jLocalPara neo4jLocalPara) throws Exception;
	public void deleteGraph(Neo4jLocalPara neo4jLocalPara) throws Exception;

//	public Neo4jModel setGetGraph(Neo4jLocalPara neo4jLocalPara) throws Exception;

}
