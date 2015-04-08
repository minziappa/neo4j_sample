package io.sample.main;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class TestReadMain {

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
		GraphDatabaseService db= dbFactory.newEmbeddedDatabase("var/graphDb/test");
		
//		try (Transaction tx = db.beginTx()) {
//
//			Node javaNode = db.createNode(Tutorials.JAVA);
//			Node scalaNode = db.createNode(Tutorials.SCALA);
//
//			javaNode.setProperty("TutorialID", "JAVA001");
//			javaNode.setProperty("Title", "Learn Java");
//			javaNode.setProperty("NoOfChapters", "25");
//			javaNode.setProperty("Status", "Completed");	
//				
//			scalaNode.setProperty("TutorialID", "SCALA001");
//			scalaNode.setProperty("Title", "Learn Scala");
//			scalaNode.setProperty("NoOfChapters", "20");
//			scalaNode.setProperty("Status", "Completed");
//
//			Relationship relationship = javaNode.createRelationshipTo(scalaNode, TutorialRelationships.JVM_LANGIAGES);
//			relationship.setProperty("Id","1234");
//			relationship.setProperty("OOPS","YES");
//			relationship.setProperty("FP","YES");
//
//			tx.success();
//		}
		System.out.println("Done successfully");
		
	}

}
