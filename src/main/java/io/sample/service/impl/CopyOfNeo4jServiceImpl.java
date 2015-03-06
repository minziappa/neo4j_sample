//package io.sample.neo4j.service.impl;
//
//import java.net.ConnectException;
//import java.net.NoRouteToHostException;
//import java.net.SocketTimeoutException;
//import java.net.URI;
//import java.net.URISyntaxException;
//import java.net.UnknownHostException;
//
//import javax.ws.rs.core.MediaType;
//
//import io.sample.neo4j.bean.page.PagingBean;
//import io.sample.neo4j.bean.page.PagingInfoBean;
//import io.sample.neo4j.bean.page.PagingPrevNextBean;
//import io.sample.neo4j.common.Relationship;
//import io.sample.neo4j.common.TraversalDescription;
//import io.sample.neo4j.service.Neo4jService;
//
//import org.apache.commons.configuration.Configuration;
//import org.apache.commons.httpclient.ConnectTimeoutException;
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.methods.GetMethod;
//
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import org.springframework.stereotype.Service;
//
//import com.sun.jersey.api.client.Client;
//import com.sun.jersey.api.client.ClientResponse;
//import com.sun.jersey.api.client.WebResource;
//
//@Service
//public class CopyOfNeo4jServiceImpl implements Neo4jService{
//
//	private Logger logger = LoggerFactory.getLogger(CopyOfNeo4jServiceImpl.class);
//
//	@Autowired
//    private Configuration configuration;
//
//	public void requestUrl(String strUrl) throws Exception {
//
//		GetMethod method = null;
//
//        // 送信
//        try {
//
//	    	// HttpClient生成
//	        HttpClient client = new HttpClient();
//
//	        // socket Time out設定
//	        client.getHttpConnectionManager().getParams().setSoTimeout(3000);
//	        // connect Time out設定
//	        client.getHttpConnectionManager().getParams().setConnectionTimeout(3000);
//	        // URLを設定
//	        method = new GetMethod(strUrl);
//	        // Head設定
//	        method.setRequestHeader("Content-Type", "text/xml;charset=utf-8");
//
//        	int statusCode = client.executeMethod(method);
//        	System.out.println("resultCode="+statusCode);
//        	if(statusCode >= 400 && statusCode < 500){
//        		System.out.println("400 resultCode over="+statusCode);
//        	} else if (statusCode >= 500) {
//        		System.out.println("500 resultCode over="+statusCode);
//        	} else {
//
//        		logger.info(String.format("GET on [%s], status code [%d]", strUrl, method.getStatusCode()));
//
//            	//InputStream inStream = method.getResponseBodyAsStream();
//
//        	}
//
//        } catch (ConnectException we) {
//        	System.out.println("ConnectException:");
//        } catch (SocketTimeoutException we) {
//        	System.out.println("SocketTimeoutException");
//        } catch (ConnectTimeoutException we) {
//        	System.out.println("SocketTimeoutException");
//        } catch (UnknownHostException we) {
//        	System.out.println("UnknownHostException");
//        } catch (NoRouteToHostException we) {
//        	System.out.println("NoRouteToHostException");
//        } catch (NullPointerException we) {
//        	System.out.println("NullPointerException");
//        } catch (Exception e) {
//        	System.out.println("Exception");
//        } finally {
//			if (method != null) {
//				method.releaseConnection();
//			}
//        }
//
//	}
//
//	public void creatingANode(String nodeEntryPointUri) throws Exception {
//
//		WebResource resource = Client.create().resource(nodeEntryPointUri); // http://localhost:7474/db/data/node
//		// /{} -> *Empty Node*/
//		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity("{}").post(ClientResponse.class);
//
//		// <tt>POST</tt> {} to the node entry point URI
//		String strFormat = String.format("POST to [%s], status code [%d], location header [%s]", 
//				nodeEntryPointUri, response.getStatus(), response.getLocation().toString());
//
//		logger.info(strFormat);
//
//	}
//
//	public void addingProperties(String addingPropertiesUri) throws Exception {
//
//		URI firstNode = createNode();
//		addProperty(firstNode, "name", "Joe Strummer");
//		URI secondNode = createNode();
//		addProperty(secondNode, "band", "The Clash");
//
//		String propertyUri = addingPropertiesUri.toString() + "/properties/" + "band";
//
//		WebResource resource = Client.create().resource(propertyUri); // http://localhost:7474/db/data/node/{node_id}/properties/{property_name}
//		ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(toJsonStringLiteral("band")).put(ClientResponse.class);
//
//		System.out.println(String.format("PUT to [%s], status code [%d]", propertyUri, response.getStatus()));
//
//	}
//
//	public void createSimpleGraph(String addingPropertiesUri) throws Exception {
//		// START SNIPPET: nodesAndProps
//		URI firstNode = createNode();
//		addProperty(firstNode, "name", "Joe Strummer");
//		URI secondNode = createNode();
//		addProperty(secondNode, "band", "The Clash");
//		// END SNIPPET: nodesAndProps
//		// START SNIPPET: addRel
//		URI relationshipUri = addRelationship(firstNode, secondNode, "singer", "{ \"from\" : \"1976\", \"until\" : \"1986\" }");
//		// END SNIPPET: addRel       
//
//		// START SNIPPET: addMetaToRel
//		addMetadataToProperty(relationshipUri, "stars", "5");
//		// END SNIPPET: addMetaToRel
//
//		// START SNIPPET: queryForSingers
//		findSingersInBands(firstNode);
//		// END SNIPPET: queryForSingers
//	}
//
//	// START SNIPPET: insideAddMetaToProp
//	private static void addMetadataToProperty(URI relationshipUri, String name, String value) throws URISyntaxException {
//	    URI propertyUri = new URI(relationshipUri.toString() + "/properties");
//	    WebResource resource = Client.create().resource(propertyUri);
//	   
//	    String entity = toJsonNameValuePairCollection(name, value);
//	    ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(entity).put(ClientResponse.class);
//	   
//	    System.out.println(String.format("PUT [%s] to [%s], status code [%d]", entity, propertyUri, response.getStatus()));
//	}
//
//	private static String toJsonNameValuePairCollection(String name, String value) {
//	    return String.format("{ \"%s\" : \"%s\" }", name, value);
//	}
//
//    private static void findSingersInBands(URI startNode) throws URISyntaxException {
//        // START SNIPPET: traversalDesc
//        // TraversalDescription turns into JSON to send to the Server
//        TraversalDescription t = new TraversalDescription();
//        t.setOrder(TraversalDescription.DEPTH_FIRST);
//        t.setUniqueness(TraversalDescription.NODE);
//        t.setMaxDepth(10);
//        t.setReturnFilter(TraversalDescription.ALL);
//        t.setRelationships(new Relationship("singer", Relationship.OUT));
//        // END SNIPPET: traversalDesc
//       
//        // START SNIPPET: traverse
//        URI traverserUri = new URI(startNode.toString() + "/traverse/node");
//        WebResource resource = Client.create().resource(traverserUri);
//        String jsonTraverserPayload = t.toJson();
//        ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(jsonTraverserPayload).post(ClientResponse.class);
//       
//        System.out.println(String.format("POST [%s] to [%s], status code [%d], returned data: " + System.getProperty("line.separator") + "%s", jsonTraverserPayload, traverserUri, response.getStatus(), response.getEntity(String.class)));
//        // END SNIPPET: traverse
//    }
//
//	// END SNIPPET: insideAddMetaToProp
//	private static URI addRelationship(URI startNode, URI endNode, String relationshipType, String jsonAttributes) throws Exception {
//
//	    URI fromUri = new URI(endNode.toString() + "/relationships");
//	    String relationshipJson = generateJsonRelationship(endNode, relationshipType, jsonAttributes);
//
//	    WebResource resource = Client.create().resource(fromUri);
//	    ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity(relationshipJson).post(ClientResponse.class); // POST JSON to the relationships URI
//	      
//	    System.out.println(String.format("POST to [%s], status code [%d], location header [%s]", fromUri, response.getStatus(), response.getLocation().toString()));
//	      
//	    return response.getLocation();
//
//	}
//
//	private static String generateJsonRelationship(URI endNode, String relationshipType, String ... jsonAttributes) {
//		    StringBuilder sb = new StringBuilder();
//		    sb.append("{ \"to\" : \"");
//		    sb.append(endNode.toString());
//		    sb.append("\", ");
//
//		    sb.append("\"type\" : \"");
//		    sb.append(relationshipType);
//		    if(jsonAttributes == null || jsonAttributes.length < 1) {
//		        sb.append("\"");
//		    } else {
//		        sb.append("\", \"data\" : ");
//		        for(int i = 0; i < jsonAttributes.length; i++) {
//		            sb.append(jsonAttributes[i]);
//		            if(i < jsonAttributes.length -1) { // Miss off the final comma
//		                sb.append(", ");
//		            }
//		        }
//		    }
//
//		    sb.append(" }");
//		    return sb.toString();
//	}
//
//	private static void addProperty(URI nodeUri, String propertyName, String propertyValue) {
//	    // START SNIPPET: addProp
//	    String propertyUri = nodeUri.toString() + "/properties/" + propertyName;
//	   
//	    WebResource resource = Client.create().resource(propertyUri); // http://localhost:7474/db/data/node/{nodties/{property_name}
//	    ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity("\"" + propertyValue + "\"").put(ClientResponse.class);
//	   
//	    System.out.println(String.format("PUT to [%s], status code [%d]", propertyUri, response.getStatus()));
//	    // END SNIPPET: addProp
//	}
//
//	private static URI createNode() {
//	    // START SNIPPET: createNode
//	    final String nodeEntryPointUri = "http://localhost:7474/db/manage/" + "node"; // http://localhost:7474/db/manage/node
//
//	    WebResource resource = Client.create().resource(nodeEntryPointUri); // http://localhost:7474/db/data/node
//	    // POST {} to the node entry point URI
//	    ClientResponse response = resource.accept(MediaType.APPLICATION_JSON).type(MediaType.APPLICATION_JSON).entity("{}").post(ClientResponse.class); 
//
//	    System.out.println(String.format("POST to [%s], status code [%d], location header [%s]", nodeEntryPointUri, response.getStatus(),response.getLocation().toString()));
//	    return response.getLocation();
//	    // END SNIPPET: createNode
//	}
//
//	private static String toJsonStringLiteral(String str) {
//	       return "\"" + str + "\"";
//	}
//
//	/*
//	 * Set paging information and a result information
//	 */
//	public void linkPaging(PagingBean pagingBean, int intNowPagePara) throws Exception {
//
//		int intNowPage = 1;
//		int intMaxPage = 1;
//		int intLimitPage = configuration.getInt("peta.page.limit");
//		int intPerPage = configuration.getInt("peta.page.per");
//		int intTotalHits = 0;
//		int intStartCount = 0;
//		int intEndCount = 0;
//
//		// Total count
//		intTotalHits = pagingBean.getAllCount();
//
//		// If the total count is over, it set 1000 count.
//		if (intTotalHits > 1000) {
//			intTotalHits = 1000;
//		}
//
//		// Now page
//		intNowPage = intNowPagePara;
//
//		// If the NowPage the is below, it set 1. 
//		if (intNowPage < 1) {
//			intNowPage = 1;
//		}
//
//		// Set the MaxPage
//		if (intTotalHits < intPerPage) {
//			intMaxPage = 1;
//		} else {
//			intMaxPage = (int) (intTotalHits / intPerPage);
//
//			// Plus the page left
//			if ((intTotalHits % intPerPage) > 0) {
//				intMaxPage++;
//			}
//		}
//
//		// They request it to set the NowPage
//		if (intNowPage > intMaxPage) {
//			intNowPage = intMaxPage;
//		}
//
//		// Set the limit max page
//		if (intMaxPage < intLimitPage) {
//			intLimitPage = intMaxPage;
//		}
//
//		// If there is the Nextpage, it is setting
//		if (intNowPage < intMaxPage) {
//			PagingPrevNextBean pagingNext = new PagingPrevNextBean();
//			pagingNext.setNowPage(String.valueOf(intNowPage+1));
//
//			pagingBean.setNextPage(pagingNext);
//		}
//
//		// If there is the PrevPage, it is setting
//		if (intNowPage > 1) {
//			PagingPrevNextBean pagingPrev = new PagingPrevNextBean();
//			pagingPrev.setNowPage(String.valueOf(intNowPage-1));
//
//			pagingBean.setPrevPage(pagingPrev);
//		}
//
//		// To the row page number
//		int intAveragePage = intLimitPage / 2;
//		int intPageNumber = 0;
//		intPageNumber = intNowPage - intAveragePage - 1;
//
//		// To find the end page
//		int intEndPage = intMaxPage - intNowPage;
//
//		// To set the first page number
//		if (intEndPage <= intAveragePage) {
//			intPageNumber = intPageNumber - (intAveragePage - intEndPage);
//		}
//
//		// Although it get a minus number, it's 0
//		if (intPageNumber < 0) {
//			intPageNumber = 0;
//		}
//
//		// to set the link parametar in the 7 number
//		for (int i=0;intLimitPage > i; i++ ) {
//			PagingInfoBean pagingInfoBean = new PagingInfoBean();
//
//			intPageNumber++;
//
//			pagingInfoBean.setPageNumber(String.valueOf(intPageNumber));
//			pagingBean.addPagingInfoList(pagingInfoBean);
//		}
//
//		// to set the UTF-8 for the paging
//		// pagingBean.setQuery(Utility.encodingUtf("strQuery"));
//		// to press the button munber that is paging number.
//		pagingBean.setNowPage(String.valueOf(intNowPage));
//		// to set the count for represent
//		intStartCount = (intNowPage-1)*intPerPage+1;
//		intEndCount = (intNowPage)*intPerPage;
//		pagingBean.setStartCount(String.valueOf(intStartCount));
//		pagingBean.setEndCount(String.valueOf(intEndCount));
//	}
//
//}