package io.sample.service;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.index.Index;

import io.sample.bean.para.Neo4jLocalPara;


public interface LocalNeo4jService {

	public void deleteDb() throws Exception;

	public boolean setGraph(Neo4jLocalPara neo4jLocalPara) throws Exception;
	public String getGraph(Neo4jLocalPara neo4jLocalPara) throws Exception;
	public String getGraph2(Neo4jLocalPara neo4jLocalPara) throws Exception;

	public boolean deleteGraph(Neo4jLocalPara neo4jLocalPara) throws Exception;
	public boolean deleteGraph2(Neo4jLocalPara neo4jLocalPara) throws Exception;

	public boolean cleanUp(final Index<Node> nodeIndex) throws Exception;
}
