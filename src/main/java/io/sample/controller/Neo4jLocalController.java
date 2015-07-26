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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/local")
public class Neo4jLocalController extends AbstractBaseController {

	private Logger logger = LoggerFactory.getLogger(Neo4jLocalController.class);

	@Autowired
    private LocalNeo4jService localNeo4jService;

	@RequestMapping(value = {"/", "", "index.neo"})
	public String index(ModelMap model) throws Exception {

		return "local/index";
	}

	@RequestMapping(value = {"setData.neo"})
	public String setData(@RequestParam("type") String type, ModelMap model) throws Exception {

		Neo4jModel petaNeo4jModel = new Neo4jModel();

		model.addAttribute("model", petaNeo4jModel);

		return "local/setData";
	}

	@RequestMapping(value = {"setDataComplete.neo"})
	public String setDataComplete(@ModelAttribute("Neo4jLocalPara")Neo4jLocalPara neo4jLocalPara, 
			@RequestParam("type") String type, BindingResult bindingResult, ModelMap model) throws Exception {

		Neo4jModel neo4jModel = new Neo4jModel();

		localNeo4jService.setGraph(neo4jLocalPara);

		model.addAttribute("model", neo4jModel);

		return "local/setDataComplete";
	}

	@RequestMapping(value = {"getData.neo"})
	public String getData(@RequestParam("type") String type, ModelMap model) throws Exception {

		Neo4jModel petaNeo4jModel = new Neo4jModel();

		model.addAttribute("model", petaNeo4jModel);

		return "local/getData";
	}

	@RequestMapping(value = {"getDataComplete.neo"})
	public String getDataComplete(@ModelAttribute("Neo4jLocalPara")Neo4jLocalPara neo4jLocalPara, 
			@RequestParam("type") String type, BindingResult bindingResult, ModelMap model) throws Exception {

		Neo4jModel neo4jModel = new Neo4jModel();
		String strReturn = localNeo4jService.getGraph(neo4jLocalPara);
		neo4jModel.setValue(strReturn);

		model.addAttribute("model", neo4jModel);

		return "local/getDataComplete";
	}

	@RequestMapping(value = {"deleteData.neo"})
	public String deleteData(@RequestParam("type") String type, ModelMap model) throws Exception {

		Neo4jModel neo4jModel = new Neo4jModel();

		model.addAttribute("model", neo4jModel);

		return "local/deleteData";
	}

	@RequestMapping(value = {"deleteDataComplete.neo"})
	public String deleteDataComplete(@ModelAttribute("Neo4jLocalPara")Neo4jLocalPara neo4jLocalPara, 
			@RequestParam("type") String type, BindingResult bindingResult, ModelMap model) throws Exception {

		Neo4jModel neo4jModel = new Neo4jModel();
		localNeo4jService.deleteGraph(neo4jLocalPara);

		model.addAttribute("model", neo4jModel);

		return "local/deleteDataComplete";
	}

}