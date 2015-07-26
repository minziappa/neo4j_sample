package io.sample.service.impl;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.index.Index;

public abstract class LocalNeo4jAbstract {

	public Index<Node> nodeIndex;
	public String GRAPH_DB_PATH = "var/graphDb/local";
	public Relationship relationship;

	public GraphDatabaseService graphDb;

    public static enum RelTypes implements RelationshipType
    {
    	KNOWS
    }

    public LocalNeo4jAbstract() {
    	graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( GRAPH_DB_PATH );
    	registerShutdownHook( graphDb );
    }

    public static void registerShutdownHook( final GraphDatabaseService graphDb )
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

}