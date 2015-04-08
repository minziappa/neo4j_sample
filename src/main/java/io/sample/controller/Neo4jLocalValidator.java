package io.sample.controller;

import io.sample.bean.para.Neo4jLocalPara;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class Neo4jLocalValidator implements Validator {

	public boolean supports(Class clazz) {
		if(Neo4jLocalPara.class.equals(clazz)) {
			return true;
		} else {
			return true;
		}

	}

	public void validate(Object object, Errors errors) {

		if (object instanceof Neo4jLocalPara) {

			Neo4jLocalPara neo4jLocalPara = (Neo4jLocalPara) object;

	        if(neo4jLocalPara != null) {
	        	// There is nothing to do.
	        }

		}

	}

}