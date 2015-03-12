package io.sample.service.impl;

import io.sample.bean.para.Neo4jLocalPara;
import io.sample.service.LocalNeo4jService;

import org.apache.commons.configuration.Configuration;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.tooling.GlobalGraphOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class LocalNeo4jServiceImpl implements LocalNeo4jService {

	private Logger logger = LoggerFactory.getLogger(LocalNeo4jServiceImpl.class);
	private static GraphDatabaseService graphDb;
	private static Index<Node> nodeIndex;

	@Autowired
    private Configuration configuration;

    // START SNIPPET: createRelTypes
    private static enum RelTypes implements RelationshipType
    {
    	KNOWS
    }

    public void LocalNeo4jServiceImpl() {
    	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "var/graphDb" );
    	// registerShutdownHook(graphDb);
    }

	public void setGraph(Neo4jLocalPara neo4jLocalPara) throws Exception {

		GraphDatabaseService graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "var/graphDb" );
		try ( Transaction tx = graphDb.beginTx() ) {

			nodeIndex = graphDb.index().forNodes( "nodes" );
			
			Node firstNode = graphDb.createNode();
			firstNode.setProperty( "message_key", "Hello, " );
	        nodeIndex.add( firstNode, "message_key", "Hello, " );

			Node secondNode = graphDb.createNode();
			secondNode.setProperty( "message_key", "World!" );
	        nodeIndex.add( secondNode, "message_key", "World!" );

			Relationship relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
			relationship.setProperty( "message_key", "brave Neo4j " );

			logger.info("success - create node");
		    // Database operations go here
		    tx.success();
        } finally {
            ;
        }

	}

	public String getGraph(Neo4jLocalPara neo4jLocalPara) throws Exception {

        try {
        	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "var/graphDb" );
        	
            // Find a user through the search index
            String indexValue = "World!";
            Node foundUser = nodeIndex.get( "message_key", indexValue ).getSingle();

            logger.info( "index value is " + indexValue);
            logger.info( "value is " +foundUser.getProperty( "message_key" ) );

        	logger.info("success - get node");
        } finally {
        	;
        }
        return "";
	}

	public void deleteGraph(Neo4jLocalPara neo4jLocalPara) throws Exception {

		try ( Transaction tx = graphDb.beginTx() ) {

            // Delete the persons and remove them from the index
            for ( Node user : nodeIndex.query( "message_key", "*" ) ) {
                nodeIndex.remove(user, "message_key", user.getProperty( "message_key" ) );
                user.delete();
            }

            tx.success();
        } finally {
        	;
        }

	}

	public static void cleanUp(final Index<Node> nodeIndex) {

		for (Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
			for (Relationship rel : node.getRelationships()) {
				rel.delete();
			}
			nodeIndex.remove(node);
			node.delete();
		}
 
	}

	private static void registerShutdownHook( final GraphDatabaseService graphDb )
	{
	    // Registers a shutdown hook for the Neo4j instance so that it
	    // shuts down nicely when the VM exits (even if you "Ctrl-C" the
	    // running application).
	    Runtime.getRuntime().addShutdownHook( new Thread()
	    {
	        @Override
	        public void run()
	        {
	            graphDb.shutdown();
	        }
	    } );
	}

	public void shutdown() throws Exception {
		if(graphDb != null) {
			graphDb.shutdown();		
		}
	}

}