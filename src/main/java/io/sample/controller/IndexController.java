package io.sample.controller;

import io.sample.service.LocalNeo4jService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/***
 * This is Index
 * 
 * @author  Joon Kim
 * @version 0.1, 14/07/17
 * @see     io.sample.controller.IndexController#index()
 * @since   JDK1.7
 */
@Controller
public class IndexController extends AbstractBaseController {

	final Logger logger = LoggerFactory.getLogger(IndexController.class);
	@Autowired
    private LocalNeo4jService localNeo4jService;

	@RequestMapping(value = {"/", "", "index.neo"})
	public String index(ModelMap model) throws Exception {
		return "index";
	}

	@RequestMapping(value = {"deleteDb.neo"})
	public String deleteDb(ModelMap model) throws Exception {

		localNeo4jService.deleteDb();

		return "deleteDb";
	}

}