package io.sample.main;

import java.util.concurrent.TimeUnit;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;

public class TestCreateMain {

	public enum Tutorials implements Label {
		JAVA,SCALA,SQL,NEO4J;
	}
	public enum TutorialRelationships implements RelationshipType {
		JVM_LANGIAGES,NON_JVM_LANGIAGES;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		GraphDatabaseService graphDb= dbFactory.newEmbeddedDatabase("var/graphDb/test");
		
		try (Transaction tx = graphDb.beginTx()) {

		    Label label = DynamicLabel.label( "User" );
		    // Create some users
		    for ( int id = 0; id < 100; id++ )
		    {
		        Node userNode = graphDb.createNode( label );
		        userNode.setProperty( "username", "user" + id + "@neo4j.org" );
		    }

		    System.out.println( "Users created" );
			tx.success();
		}
		System.out.println("Done successfully");
		
	}

}
