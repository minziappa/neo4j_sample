package io.sample.main;

import org.neo4j.cypher.ExecutionEngine;
import org.neo4j.cypher.ExecutionResult;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Label;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class TestCypherMain {

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

		String rows = "";

		
//		try ( Transaction ignored = graphDb.beginTx();
//			      Result result = graphDb.execute( "match (n {name: 'my node'}) return n, n.name" ) )
//			{
//			    while ( result.hasNext() )
//			    {
//			        Map<String,Object> row = result.next();
//			        for ( Entry<String,Object> column : row.entrySet() )
//			        {
//			            rows += column.getKey() + ": " + column.getValue() + "; ";
//			        }
//			        rows += "\n";
//			    }
//			}
//		
//		myNode.setProperty( "name", "my node" );
//		
//		javaNode.setProperty("TutorialID", "JAVA001");
//		javaNode.setProperty("Title", "Learn Java");
//		javaNode.setProperty("NoOfChapters", "25");
//		javaNode.setProperty("Status", "Completed");	
//		
	      ExecutionEngine execEngine = new ExecutionEngine(graphDb, null);
	      ExecutionResult execResult = execEngine.execute("MATCH (java:JAVA) RETURN java");
	      String results = execResult.dumpToString();
	      System.out.println(results);
		System.out.println("Done successfully");
		
	}

}
