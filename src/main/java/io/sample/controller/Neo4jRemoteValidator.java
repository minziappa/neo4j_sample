package io.sample.controller;

import io.sample.bean.para.Neo4jRemotePara;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class Neo4jRemoteValidator implements Validator {

	public boolean supports(Class clazz) {
		if(Neo4jRemotePara.class.equals(clazz)) {
			return true;
		} else {
			return true;
		}

	}

	public void validate(Object object, Errors errors) {

		if (object instanceof Neo4jRemotePara) {

			Neo4jRemotePara showToPetaPara = (Neo4jRemotePara) object;

	        if(showToPetaPara != null) {
	        	// There is nothing to do.
	        }

		}

	}

}