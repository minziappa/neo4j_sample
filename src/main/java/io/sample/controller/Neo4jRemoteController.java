package io.sample.controller;

import io.sample.bean.model.Neo4jModel;
import io.sample.bean.para.Neo4jRemotePara;
import io.sample.service.RemoteNeo4jService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * the <code>InviteController</code> class represents action controller.
 * 
 * @author  kim joon
 * @version 1.21, 10/12/14
 * @since   JDK1.7
 */
@Controller
@RequestMapping("/neo4j")
public class Neo4jRemoteController extends AbstractBaseController {

	private Logger logger = LoggerFactory.getLogger(Neo4jRemoteController.class);

	/* The petaService is used for business Logic */
	@Autowired
    private RemoteNeo4jService remoteNeo4jService;

	@RequestMapping(value = {"creatingANode.neo"})
	public String creatingANode(ModelMap model) throws Exception {

		// neo4jService.creatingANode(configuration.getString("neo4j.server") + "db/data/node");

		//model.addAttribute("model", petaNeo4jModel);

		return "neo4j/ok";
	}

	@RequestMapping(value = {"addingProperties.neo"})
	public String addingProperties(ModelMap model) throws Exception {

		// neo4jService.addingProperties(strUrl2);

		//model.addAttribute("model", petaNeo4jModel);

		return "neo4j/ok";
	}

	@RequestMapping(value = {"addingRelations.neo"})
	public String addingRelations(ModelMap model) throws Exception {

		// neo4jService.addingProperties(strUrl2);

		return "neo4j/ok";
	}

	@RequestMapping(value = {"addPropertiesToARelation.neo"})
	public String addPropertiesToARelation(ModelMap model) throws Exception {

		return "neo4j/ok";
	}

	@RequestMapping(value = {"resultRemote.neo"})
	public String queryingGraphs(@ModelAttribute("neo4jRemotePara")Neo4jRemotePara neo4jRemotePara, 
			BindingResult bindingResult, ModelMap model) throws Exception {

		Neo4jModel neo4jModel = new Neo4jModel();

		String strReturn = remoteNeo4jService.createSimpleGraph(neo4jRemotePara);
		model.addAttribute("errorMessage", strReturn);
		model.addAttribute("model", neo4jModel);

		return "neo4j/resultRemote";
	}

}