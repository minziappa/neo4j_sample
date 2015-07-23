package io.sample.controller;

import io.sample.bean.model.Neo4jModel;
import io.sample.bean.para.Neo4jLocalPara;
import io.sample.service.LocalNeo4jService;

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
 * Show target user's list.
 * 
 * @author  kim woong-joon
 * @version 1.21, 10/12/14
 * @since   JDK1.6
 */
@Controller
@RequestMapping("/local")
public class Neo4jLocalController extends AbstractBaseController {

	private Logger logger = LoggerFactory.getLogger(Neo4jLocalController.class);

	@Autowired
    private LocalNeo4jService localNeo4jService;

	@RequestMapping(value = {"/", "", "index.neo"})
	public String remote(ModelMap model) throws Exception {

		return "local/index";
	}

	@RequestMapping(value = {"setLocal.neo"})
	public String setLocal(ModelMap model) throws Exception {

		Neo4jModel petaNeo4jModel = new Neo4jModel();

		model.addAttribute("model", petaNeo4jModel);

		return "local/setLocal";
	}

	@RequestMapping(value = {"setGraphLocal.neo"})
	public String setGraphLocal(@ModelAttribute("Neo4jLocalPara")Neo4jLocalPara neo4jLocalPara, 
			BindingResult bindingResult, ModelMap model) throws Exception {

		Neo4jModel neo4jModel = new Neo4jModel();

		localNeo4jService.setGraph(neo4jLocalPara);

		model.addAttribute("model", neo4jModel);

		return "local/setGraphLocal";
	}

	@RequestMapping(value = {"getLocal.neo"})
	public String getLocal(ModelMap model) throws Exception {

		Neo4jModel petaNeo4jModel = new Neo4jModel();

		model.addAttribute("model", petaNeo4jModel);

		return "local/getLocal";
	}

	@RequestMapping(value = {"getGraphLocal.neo"})
	public String getGraphLocal(@ModelAttribute("Neo4jLocalPara")Neo4jLocalPara neo4jLocalPara, 
			BindingResult bindingResult, ModelMap model) throws Exception {

		Neo4jModel neo4jModel = new Neo4jModel();
		String strReturn = localNeo4jService.getGraph2(neo4jLocalPara);
		neo4jModel.setValue(strReturn);

		model.addAttribute("model", neo4jModel);

		return "local/getGraphLocal";
	}

	@RequestMapping(value = {"deleteLocal.neo"})
	public String deleteLocal(@ModelAttribute("Neo4jLocalPara")Neo4jLocalPara neo4jLocalPara, 
			BindingResult bindingResult, ModelMap model) throws Exception {

		Neo4jModel neo4jModel = new Neo4jModel();

		model.addAttribute("model", neo4jModel);

		return "local/deleteLocal";
	}

	@RequestMapping(value = {"deleteGraphLocal.neo"})
	public String deleteGraphLocal(@ModelAttribute("Neo4jLocalPara")Neo4jLocalPara neo4jLocalPara, 
			BindingResult bindingResult, ModelMap model) throws Exception {

		Neo4jModel neo4jModel = new Neo4jModel();
		localNeo4jService.deleteGraph(neo4jLocalPara);

		model.addAttribute("model", neo4jModel);

		return "local/deleteGraphLocal";
	}

}