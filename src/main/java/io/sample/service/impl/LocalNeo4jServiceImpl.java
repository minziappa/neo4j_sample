package io.sample.service.impl;

import io.sample.bean.para.Neo4jLocalPara;
import io.sample.service.LocalNeo4jService;

import org.apache.commons.configuration.Configuration;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;
import org.neo4j.graphdb.index.Index;
import org.neo4j.graphdb.index.IndexHits;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.tooling.GlobalGraphOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

@Service
public class LocalNeo4jServiceImpl implements LocalNeo4jService {

	private Logger logger = LoggerFactory.getLogger(LocalNeo4jServiceImpl.class);
    private static Node usersReferenceNode;
    private static final String USERNAME_KEY = "username";

	@Autowired
    private Configuration configuration;

    // START SNIPPET: createRelTypes
    private static enum RelTypes implements RelationshipType
    {
        USERS_REFERENCE,
        USER
    }
    // END SNIPPET: createRelTypes

    public enum MyRelationshipTypes implements RelationshipType {
        KNOWS
    }

	public void setGraph(Neo4jLocalPara neo4jLocalPara) throws Exception {

		GraphDatabaseService graphDb = new EmbeddedGraphDatabase("var/base");
		Index<Node> nodeIndex = graphDb.index().forNodes( "nodes" );

        Transaction tx = graphDb.beginTx();

        try {
        	usersReferenceNode = graphDb.createNode();
        	graphDb.getReferenceNode().createRelationshipTo(usersReferenceNode, RelTypes.USERS_REFERENCE);
        	Iterable<Relationship> relationship = usersReferenceNode.getRelationships(RelTypes.USER, Direction.OUTGOING);

        	/*
        	for(int i=0; i < 1; i++) {
                Node node = graphDb.createNode();
                node.setProperty(USERNAME_KEY, neo4jLocalPara.getNodeValue());
                nodeIndex.add(node, USERNAME_KEY, neo4jLocalPara.getNodeValue());
                usersReferenceNode.createRelationshipTo( node, RelTypes.USER );
                logger.info(i + "abcd >>> " + RelTypes.USER);
                logger.info(" >>>>> key=" + neo4jLocalPara.getNodeKey() + ", value=" + node.getProperty(USERNAME_KEY));

                if(usersReferenceNode == null) {
                	logger.info("2 userReference is null in the SetGraph.");
                }
            	if (relationship == null) {
            		logger.info("2 relationship  is null.");
            	}
        	}
        	*/
        	
            for ( int id = 0; id < 100; id++ )
            {
                Node userNode = createAndIndexUser(graphDb, nodeIndex, idToUserName( id ) );
                usersReferenceNode.createRelationshipTo( userNode,
                    RelTypes.USER );
            }

            tx.success();

        } finally {
            tx.finish();
            graphDb.shutdown();
        }

	}

	public String getGraph(Neo4jLocalPara neo4jLocalPara) throws Exception {

		GraphDatabaseService graphDb = new EmbeddedGraphDatabase("var/base");
		Index<Node> nodeIndex = graphDb.index().forNodes( "nodes" );

		String returnValue = null;

        try {
        	logger.info("abcd >>> " + neo4jLocalPara.getNodeValue());
        	
            // Find a user through the search index
            // Node foundNode = nodeIndex.get( USERNAME_KEY, neo4jLocalPara.getNodeValue()).getSingle();
            // returnValue = (String)foundNode.getProperty( USERNAME_KEY );
            // logger.info( "The username of user " + neo4jLocalPara.getNodeValue() + " is " + foundNode.getProperty( USERNAME_KEY ) );

        	/*
            IndexHits<Node> nodeList = nodeIndex.get(USERNAME_KEY, neo4jLocalPara.getNodeValue());
            try {
                for (Node node:nodeList) {
                    returnValue = (String)node.getProperty( USERNAME_KEY );
                    logger.info( "The username of user " + neo4jLocalPara.getNodeValue() + " is " + node.getProperty( USERNAME_KEY ) );
                }
            } finally {
            	nodeList.close();
            }
            */

            // START SNIPPET: findUser
            int idToFind = 45;
            Node foundUser = nodeIndex.get( USERNAME_KEY, idToUserName( idToFind ) ).getSingle();
            System.out.println( "The username of user " + idToFind + " is " + foundUser.getProperty( USERNAME_KEY ) );
            // END SNIPPET: findUser

            
        } finally {
            graphDb.shutdown();
        }

        return returnValue;
	}

	public void deleteGraph(Neo4jLocalPara neo4jLocalPara) throws Exception {

		GraphDatabaseService graphDb = new EmbeddedGraphDatabase("var/base");
		Index<Node> nodeIndex = graphDb.index().forNodes( "nodes" );

        Transaction tx = graphDb.beginTx();
        try {
            // Reset the UserReferenceNode....
            Node usersReferenceNode = graphDb.getReferenceNode();

            
            /*
        	// Delete the persons and remove them from the index
            for ( Relationship relationship : usersReferenceNode.getRelationships(RelTypes.USER, Direction.OUTGOING ) )
            {
            	logger.info("1 userReference is null in the SetGraph.");
                Node user = relationship.getEndNode();
                nodeIndex.remove(user, USERNAME_KEY, user.getProperty( USERNAME_KEY ));
                user.delete();
                relationship.delete();
            }
            usersReferenceNode.getSingleRelationship( RelTypes.USERS_REFERENCE, Direction.INCOMING ).delete();
            // Relationship relationShip = usersReferenceNode.getSingleRelationship(RelTypes.USERS_REFERENCE, Direction.INCOMING);
            usersReferenceNode.delete();
			*/

            // Delete the persons and remove them from the index
            for ( Relationship relationship : usersReferenceNode.getRelationships(RelTypes.USER, Direction.OUTGOING ) ) {
                Node user = relationship.getEndNode();
                nodeIndex.remove(  user, USERNAME_KEY, user.getProperty( USERNAME_KEY ) );
                user.delete();
                relationship.delete();
            }
            usersReferenceNode.getSingleRelationship( RelTypes.USERS_REFERENCE, Direction.INCOMING ).delete();
            usersReferenceNode.delete();

            tx.success();

        } finally {
        	tx.finish();
        	graphDb.shutdown();
        }

	}

	public static void cleanUp(final Index<Node> nodeIndex) {

		GraphDatabaseService graphDb = new EmbeddedGraphDatabase("var/base");

		for (Node node : GlobalGraphOperations.at(graphDb).getAllNodes()) {
			for (Relationship rel : node.getRelationships()) {
				rel.delete();
			}
			nodeIndex.remove(node);
			node.delete();
		}
 
	}

    // START SNIPPET: helperMethods
    private static String idToUserName( final int id )
    {
        return "user" + id + "@neo4j.org";
    }
    private static Node createAndIndexUser(GraphDatabaseService graphDb, Index<Node> nodeIndex, final String username )
    {
        Node node = graphDb.createNode();
        node.setProperty( USERNAME_KEY, username );
        nodeIndex.add( node, USERNAME_KEY, username );
        return node;
    }
    // END SNIPPET: helperMethods

	private void printFriends( Node person ) {

	    Traverser traverser = person.traverse(
	        Order.BREADTH_FIRST,
	        StopEvaluator.END_OF_GRAPH,
	        ReturnableEvaluator.ALL_BUT_START_NODE,
	        MyRelationshipTypes.KNOWS,
	        Direction.OUTGOING );
	    for ( Node friend : traverser )
	    {
	    	logger.info("get >>>> " + (String)friend.getProperty( "message" ) );
	    }
	}

//    private static void registerShutdownHook()
//    {
//        // Registers a shutdown hook for the Neo4j and index service instances
//        // so that it shuts down nicely when the VM exits (even if you
//        // "Ctrl-C" the running example before it's completed)
//        Runtime.getRuntime().addShutdownHook( new Thread()
//        {
//            @Override
//            public void run()
//            {
//            	graphDb.shutdown();
//            }
//        } );
//    }

}