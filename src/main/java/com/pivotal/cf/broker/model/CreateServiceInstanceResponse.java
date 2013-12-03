package com.pivotal.cf.broker.model;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * The response from the broker sent back to the cloud controller 
 * on a successful service instance creation request
 * 
 * @author sgreenberg@gopivotal.com
 */
@JsonAutoDetect(getterVisibility = Visibility.NONE)
public class CreateServiceInstanceResponse {

	private ServiceInstance instance;
	
	public CreateServiceInstanceResponse() {}
	
	public CreateServiceInstanceResponse(ServiceInstance instance) {
		this.instance = instance;
	}

	@JsonSerialize
	@JsonProperty("dashboard_url")
	public String getDashboardUrl() {
		return instance.getDashboardUrl();
	}
	
}
