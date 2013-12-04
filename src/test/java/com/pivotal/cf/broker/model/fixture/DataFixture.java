package com.pivotal.cf.broker.model.fixture;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;


public class DataFixture {

	public static String getOrgOneGuid() {
		return "org-guid-one";
	}
	
	public static String getSpaceOneGuid() {
		return "space-guid-one";
	}
	
	public static String toJson(Object object) throws JsonGenerationException, JsonMappingException, IOException {
		 ObjectMapper mapper = new ObjectMapper();
		 return mapper.writeValueAsString(object);
	}
	
}
