package io.sample.service.impl;

import io.sample.bean.para.Neo4jLocalPara;
import io.sample.service.LocalNeo4jService;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.configuration.Configuration;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.io.fs.FileUtils;
import org.neo4j.tooling.GlobalGraphOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocalNeo4jServiceImpl extends LocalNeo4jAbstract implements LocalNeo4jService {

	private Logger logger = LoggerFactory.getLogger(LocalNeo4jServiceImpl.class);

	@Autowired
    private Configuration configuration;

    public LocalNeo4jServiceImpl() {
    	super();
    }

	public boolean setGraph(Neo4jLocalPara neo4jLocalPara) throws Exception {

		try ( Transaction tx = graphDb.beginTx() ) {

			nodeIndex = graphDb.index().forNodes( "nodes" );

			Node firstNode = graphDb.createNode();
			firstNode.setProperty( neo4jLocalPara.getFirstNodeKey(), neo4jLocalPara.getFirstNodeValue() );
	        nodeIndex.add( firstNode, neo4jLocalPara.getFirstNodeKey(), neo4jLocalPara.getFirstNodeValue() );

			Node secondNode = graphDb.createNode();
			secondNode.setProperty( neo4jLocalPara.getSecondNodeKey(), neo4jLocalPara.getSecondNodeValue() );
	        nodeIndex.add( secondNode, neo4jLocalPara.getSecondNodeValue(), neo4jLocalPara.getSecondNodeValue() );

			Relationship relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
			relationship.setProperty( neo4jLocalPara.getRelationshopKey(), neo4jLocalPara.getRelationshopValue() );

			logger.info("success - create node");
		    // Database operations go here
		    tx.success();
        } finally {
            ;
        }
		
		return true;
	}

	public String getGraph(Neo4jLocalPara neo4jLocalPara) throws Exception {

		String value = null;
		
		try ( Transaction tx = graphDb.beginTx() ){
			nodeIndex = graphDb.index().forNodes( "nodes" );
	        // Find a user through the search index
	        IndexHits<Node> indexHits = nodeIndex.get( neo4jLocalPara.getFirstNodeKey(), neo4jLocalPara.getFirstNodeValue() );
	        while(indexHits.hasNext()) {
	        	Node nodeHit = indexHits.next();

	        	value = (String)nodeHit.getProperty(neo4jLocalPara.getFirstNodeKey());

	        	logger.info((String)nodeHit.getProperty(neo4jLocalPara.getFirstNodeKey()));
	        }

	        logger.info( "index value is " + neo4jLocalPara.getFirstNodeValue());
		}

        return value;
	}

	public String getGraph2(Neo4jLocalPara neo4jLocalPara) throws Exception {

		String rows = "";
		try ( Transaction ignored = graphDb.beginTx(); 
				Result result = graphDb.execute( "match (n {message_key: 'Hello, '}) return n, n.message_key" ) ) {
			    while ( result.hasNext() )
			    {
			        Map<String,Object> row = result.next();
			        for ( Entry<String,Object> column : row.entrySet() )
			        {
			            rows += column.getKey() + ": " + column.getValue() + "; ";
			        }
			        rows += "\n";
			    }
			}
		logger.info(">>>" + rows);
        return rows;
	}

	public boolean deleteGraph(Neo4jLocalPara neo4jLocalPara) throws Exception {

		try ( Transaction tx = graphDb.beginTx() ) {

            // Delete the persons and remove them from the index
            for ( Node user : nodeIndex.query( neo4jLocalPara.getFirstNodeKey(), "*" ) ) {
                nodeIndex.remove(user, neo4jLocalPara.getFirstNodeKey(), user.getProperty( neo4jLocalPara.getFirstNodeKey() ) );
                user.delete();
            }

            tx.success();
        } finally {
        	;
        }

		return true;
	}

	public boolean deleteGraph2(Neo4jLocalPara neo4jLocalPara) throws Exception {

        try ( Transaction tx = graphDb.beginTx() ){

        	nodeIndex = graphDb.index().forNodes( "nodes" );
        	IndexHits<Node> indexHits = nodeIndex.get(neo4jLocalPara.getFirstNodeKey(), neo4jLocalPara.getFirstNodeValue());

        	if (indexHits.getSingle() == null) {
        		return false;
        	}

            // let's remove the data
            // firstNode.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
            // firstNode.delete();
            // secondNode.delete();

            tx.success();
        }
        
        return true;
    }

	public boolean cleanUp(final Index<Node> nodeIndex) throws Exception {

		for (Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
			for (Relationship rel : node.getRelationships()) {
				rel.delete();
			}
			nodeIndex.remove(node);
			node.delete();
		}

		return true;
	}

	@Override
	public void deleteDb() throws Exception {
        FileUtils.deleteRecursively( new File( GRAPH_DB_PATH ) );
	}

}
