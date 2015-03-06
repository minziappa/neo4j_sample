package io.sample.service.impl;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Iterator;

import javax.ws.rs.core.MediaType;

import io.sample.bean.para.Neo4jRemotePara;
import io.sample.common.Relationship;
import io.sample.common.TraversalDescription;
import io.sample.service.RemoteNeo4jService;

import org.apache.commons.configuration.Configuration;

import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.ReturnableEvaluator;
import org.neo4j.graphdb.StopEvaluator;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.Traverser;
import org.neo4j.graphdb.Traverser.Order;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

@Service
public class RemoteNeo4jServiceImpl implements RemoteNeo4jService {

    public enum MyRelationshipTypes implements RelationshipType {
        KNOWS
    }

	private Logger logger = LoggerFactory.getLogger(RemoteNeo4jServiceImpl.class);

	@Autowired
    private Configuration configuration;

	public String createSimpleGraph(Neo4jRemotePara neo4jLocalPara) throws Exception {

		// START SNIPPET: nodesAndProps
		URI firstNode = createNode();
		addProperty(firstNode, "name", "kim");
		addProperty(firstNode, "age", "20");
		URI secondNode = createNode();
		addProperty(secondNode, "name", "kara");
		addProperty(secondNode, "age", "19");
		// END SNIPPET: nodesAndProps

		// START SNIPPET: add Relationship
		URI relationshipUri = addRelationship(firstNode, secondNode, "KNOWS", "{ \"from\" : \"20110215\", \"until\" : \"20110222\" }");
		// END SNIPPET: add Relationship

		// START SNIPPET: addMetaToRelationship
		addMetadataToProperty(relationshipUri, "stars", "5");
		// END SNIPPET: addMetaToRelationship

		// START SNIPPET: queryForSingers
		return findSingersInBands(firstNode);
		// END SNIPPET: queryForSingers

	}

	private void printFriends( Node person )
	{
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

	// START SNIPPET: insideAddMetaToProp
	private void addMetadataToProperty(URI relationshipUri, String name, String value) throws URISyntaxException {
	    URI propertyUri = new URI(relationshipUri.toString() + "/properties");
	    WebResource resource = Client.create().resource(propertyUri);

	    String entity = toJsonNameValuePairCollection(name, value);
	    ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(entity).put(ClientResponse.class);

	    String strFormat = String.format("PUT [%s] to [%s], status code [%d]", entity, propertyUri, response.getStatus());

	    logger.info("addMetadataToProperty = " + strFormat);
	}

	private static String toJsonNameValuePairCollection(String name, String value) {
	    return String.format("{ \"%s\" : \"%s\" }", name, value);
	}

    private String findSingersInBands(URI startNode) throws URISyntaxException {
        // START SNIPPET: traversalDesc
        // TraversalDescription turns into JSON to send to the Server
        TraversalDescription t = new TraversalDescription();
        t.setOrder(TraversalDescription.DEPTH_FIRST);
        t.setUniqueness(TraversalDescription.NODE);
        t.setMaxDepth(10);
        t.setReturnFilter(TraversalDescription.ALL);
        t.setRelationships(new Relationship("singer", Relationship.OUT));
        // END SNIPPET: traversalDesc

        // START SNIPPET: traverse
        URI traverserUri = new URI(startNode.toString() + "/traverse/node");
        WebResource resource = Client.create().resource(traverserUri);
        String jsonTraverserPayload = t.toJson();
        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(jsonTraverserPayload).post(ClientResponse.class);

        String strFormat = String.format("POST [%s] to [%s], status code [%d], returned data: " + System.getProperty("line.separator") + "%s", 
        		jsonTraverserPayload, traverserUri, response.getStatus(), response.getEntity(String.class));

        logger.info("findSingersInBands = " + strFormat);
        // END SNIPPET: traverse
        return strFormat;
    }

	// END SNIPPET: insideAddMetaToProp
	private URI addRelationship(URI startNode, URI endNode, String relationshipType, String jsonAttributes) throws Exception {

		URI fromUri = new URI(startNode.toString() + "/relationships");
	    String relationshipJson = generateJsonRelationship(endNode, relationshipType, jsonAttributes);

	    WebResource resource = Client.create().resource(fromUri);
	    ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(relationshipJson).post(ClientResponse.class); // POST JSON to the relationships URI

	    logger.info("addRelationship 0 = " + fromUri);
	    logger.info("addRelationship 1 = " + response.getStatus());
	    logger.info("addRelationship 2 = " + response.getLocation().toString());

	    String strFormat = String.format("POST to [%s], status code [%d], location header [%s]", fromUri, response.getStatus(), response.getLocation().toString());

	    logger.info("addRelationship = " + strFormat);

	    return response.getLocation();

	}

	private String generateJsonRelationship(URI endNode, String relationshipType, String ... jsonAttributes) {

			StringBuilder sb = new StringBuilder();
		    sb.append("{ \"to\" : \"");
		    sb.append(endNode.toString());
		    sb.append("\", ");

		    sb.append("\"type\" : \"");
		    sb.append(relationshipType);
		    if(jsonAttributes == null || jsonAttributes.length < 1) {
		        sb.append("\"");
		    } else {
		        sb.append("\", \"data\" : ");
		        for(int i = 0; i < jsonAttributes.length; i++) {
		            sb.append(jsonAttributes[i]);
		            if(i < jsonAttributes.length -1) { // Miss off the final comma
		                sb.append(", ");
		            }
		        }
		    }

		    sb.append(" }");
		    logger.info("generateJsonRelationship = " + sb.toString());

		    return sb.toString();
	}

	private void addProperty(URI nodeUri, String propertyName, String propertyValue) {

	    // START SNIPPET: addProp
	    String propertyUri = nodeUri.toString() + "/properties/" + propertyName;

	    WebResource resource = Client.create().resource(propertyUri); // http://localhost:7474/db/data/node/{nodties/{property_name}
	    ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity("\"" + propertyValue + "\"").put(ClientResponse.class);

	    String strFormat = String.format("PUT to [%s], status code [%d]", propertyUri, response.getStatus());

	    logger.info("addRelationship = " + strFormat);

	    // END SNIPPET: addProp
	}

	private URI createNode() {

	    // START SNIPPET: createNode
	    final String nodeEntryPointUri = "http://172.0.0.1:7474/db/data/" + "node"; // http://localhost:7474/db/data/node

	    WebResource resource = Client.create().resource(nodeEntryPointUri); // http://localhost:7474/db/data/node
	    // POST {} to the node entry point URI
	    ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity("{}").post(ClientResponse.class); 
	    logger.info("createNode 0 = " + nodeEntryPointUri);
	    logger.info("createNode 1 = " + response.getStatus());
	    logger.info("createNode 2 = " + response.getLocation().toString());

	    String strFormat = String.format("POST to [%s], status code [%d], location header [%s]", nodeEntryPointUri, response.getStatus(), response.getLocation().toString());
	    logger.info("createNode = " + strFormat);

	    return response.getLocation();
	    // END SNIPPET: createNode
	}

	private static String toJsonStringLiteral(String str) {
	       return "\"" + str + "\"";
	}

}