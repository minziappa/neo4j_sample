package io.sample.service.impl;

import java.io.File;
import java.util.Map;
import java.util.Map.Entry;

import io.sample.bean.para.Neo4jLocalPara;
import io.sample.service.LocalNeo4jService;

import org.apache.commons.configuration.Configuration;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Result;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;
import org.neo4j.io.fs.FileUtils;
import org.neo4j.tooling.GlobalGraphOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class LocalNeo4jServiceImpl implements LocalNeo4jService {

	private Logger logger = LoggerFactory.getLogger(LocalNeo4jServiceImpl.class);
	private static Index<Node> nodeIndex;
	private static String GRAPH_DB_PATH = "var/graphDb/local";

	public String greeting;

    // START SNIPPET: vars
    GraphDatabaseService graphDb;
    Node firstNode;
    Node secondNode;
    Relationship relationship;
    // END SNIPPET: vars

	@Autowired
    private Configuration configuration;

    // START SNIPPET: createRelTypes
    private static enum RelTypes implements RelationshipType
    {
    	KNOWS
    }


	@Override
	public void createDb() throws Exception {

        // START SNIPPET: startDb
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( GRAPH_DB_PATH );
        registerShutdownHook( graphDb );
        // END SNIPPET: startDb

        // START SNIPPET: transaction
        try ( Transaction tx = graphDb.beginTx() ) {
            // Database operations go here
            // END SNIPPET: transaction
            // START SNIPPET: addData
            firstNode = graphDb.createNode();
            firstNode.setProperty( "message", "Hello, " );
            secondNode = graphDb.createNode();
            secondNode.setProperty( "message", "World!" );

            relationship = firstNode.createRelationshipTo( secondNode, RelTypes.KNOWS );
            relationship.setProperty( "message", "brave Neo4j " );
            // END SNIPPET: addData

            // START SNIPPET: readData
            System.out.print( firstNode.getProperty( "message" ) );
            System.out.print( relationship.getProperty( "message" ) );
            System.out.print( secondNode.getProperty( "message" ) );
            // END SNIPPET: readData

            greeting = ( (String) firstNode.getProperty( "message" ) )
                       + ( (String) relationship.getProperty( "message" ) )
                       + ( (String) secondNode.getProperty( "message" ) );

            // START SNIPPET: transaction
            tx.success();
        }

	}

	@Override
	public void deleteDb() throws Exception {
        FileUtils.deleteRecursively( new File( GRAPH_DB_PATH ) );
	}

	public void setGraph(Neo4jLocalPara neo4jLocalPara) throws Exception {

		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( GRAPH_DB_PATH );

		registerShutdownHook(graphDb);

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
		logger.info(" get - 1");
    	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( GRAPH_DB_PATH );
		registerShutdownHook(graphDb);

		logger.info(" get - 2");

    	nodeIndex = graphDb.index().forNodes( "nodes" );
    	logger.info(" get - 3");
        // Find a user through the search index
        String indexValue = "World!";
        Node foundUser = nodeIndex.get( "message_key", indexValue ).getSingle();

        logger.info( "index value is " + indexValue);
        logger.info( "value is " +foundUser.getProperty( "message_key" ) );
        logger.info(" get - 4");	

        return "";
	}

	public String getGraph2(Neo4jLocalPara neo4jLocalPara) throws Exception {
		logger.info(" get - 1");
    	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( GRAPH_DB_PATH );
		registerShutdownHook(graphDb);

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
        return "";
	}

	public void deleteGraph(Neo4jLocalPara neo4jLocalPara) throws Exception {
//    	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( "var/graphDb" );
//		registerShutdownHook(graphDb);

		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( GRAPH_DB_PATH );

		try ( Transaction tx = graphDb.beginTx() ) {

//            // Delete the persons and remove them from the index
//            for ( Node user : nodeIndex.query( "message_key", "*" ) ) {
//                nodeIndex.remove(user, "message_key", user.getProperty( "message_key" ) );
//                user.delete();
//            }

            tx.success();
        } finally {
        	;
        }

	}

	public void cleanUp(final Index<Node> nodeIndex) {

		graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( GRAPH_DB_PATH );

		for (Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
			for (Relationship rel : node.getRelationships()) {
				rel.delete();
			}
			nodeIndex.remove(node);
			node.delete();
		}
 
	}

	public void removeData() {
	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( GRAPH_DB_PATH );

        try ( Transaction tx = graphDb.beginTx() ){
            // START SNIPPET: removingData
            // let's remove the data
            firstNode.getSingleRelationship( RelTypes.KNOWS, Direction.OUTGOING ).delete();
            firstNode.delete();
            secondNode.delete();
            // END SNIPPET: removingData

            tx.success();
        }
    }

    // START SNIPPET: shutdownHook
    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        // Registers a shutdown hook for the Neo4j instance so that it
        // shuts down nicely when the VM exits (even if you "Ctrl-C" the
        // running application).
        Runtime.getRuntime().addShutdownHook( new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }
    // END SNIPPET: shutdownHook

	public void shutdown() throws Exception {
		System.out.println();
        System.out.println( "Shutting down database ..." );
		if(graphDb != null) {
			graphDb.shutdown();		
		}
	}

}
