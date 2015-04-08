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

public class TestMain {

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

			Node javaNode = graphDb.createNode(Tutorials.JAVA);
			Node scalaNode = graphDb.createNode(Tutorials.SCALA);

			javaNode.setProperty("TutorialID", "JAVA001");
			javaNode.setProperty("Title", "Learn Java");
			javaNode.setProperty("NoOfChapters", "25");
			javaNode.setProperty("Status", "Completed");	
				
			scalaNode.setProperty("TutorialID", "SCALA001");
			scalaNode.setProperty("Title", "Learn Scala");
			scalaNode.setProperty("NoOfChapters", "20");
			scalaNode.setProperty("Status", "Completed");

			Relationship relationship = javaNode.createRelationshipTo(scalaNode, TutorialRelationships.JVM_LANGIAGES);
			relationship.setProperty("Id","1234");
			relationship.setProperty("OOPS","YES");
			relationship.setProperty("FP","YES");

			tx.success();
		}
		System.out.println("Done successfully");
		
	}

}
