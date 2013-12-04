package com.pivotal.cf.broker.model;

import java.util.Map;

import org.codehaus.jackson.annotate.JsonAutoDetect;
import org.codehaus.jackson.annotate.JsonAutoDetect.Visibility;
import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * The response sent to the cloud controller when a bind
 * request is successful.
 * 
 * @author sgreenberg@gopivotal.com
 */
@JsonAutoDetect(getterVisibility = Visibility.NONE)
public class ServiceInstanceBindingResponse {

	ServiceInstanceBinding binding;
	
	public ServiceInstanceBindingResponse() {}
	
	public ServiceInstanceBindingResponse(ServiceInstanceBinding binding) {
		this.binding = binding;
	}

	@NotEmpty
	@JsonSerialize
	@JsonProperty("credentials")
	public Map<String, Object> getCredentials() {
		return binding.getCredentials();
	}

	@JsonSerialize
	@JsonProperty("syslog_drain_url")
	public String getSyslogDrainUrl() {
		return binding.getSyslogDrainUrl();
	}
	
}
