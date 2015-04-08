package io.sample.main;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.neo4j.graphdb.DynamicLabel;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ResourceIterator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;
import org.neo4j.graphdb.schema.IndexDefinition;
import org.neo4j.graphdb.schema.Schema;

public class TestFindMain {

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
		
		Label label = DynamicLabel.label( "User" );
		int idToFind = 45;
		String nameToFind = "user" + idToFind + "@neo4j.org";
		try (Transaction tx = graphDb.beginTx()) {
		    try ( ResourceIterator<Node> users =
		            graphDb.findNodes( label, "username", nameToFind ) )
		    {
		        ArrayList<Node> userNodes = new ArrayList<>();
		        while ( users.hasNext() )
		        {
		            userNodes.add( users.next() );
		        }

		        for ( Node node : userNodes ) {
		            System.out.println( "The username of user " + idToFind + " is " + node.getProperty( "username" ) );
		        }
		    }
		System.out.println("Done successfully");
		
		}
	}
}
