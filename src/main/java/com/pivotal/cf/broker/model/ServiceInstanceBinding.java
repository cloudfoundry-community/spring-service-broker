package com.pivotal.cf.broker.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A binding to a service instance
 * 
 * @author sgreenberg@gopivotal.com
 *
 */
public class ServiceInstanceBinding {

	private String id;
	private String serviceInstanceId;
	private Map<String,String> credentials = new HashMap<String,String>();
	private String syslogDrainUrl;
	
	public ServiceInstanceBinding() {}
	
	public ServiceInstanceBinding(String id, 
			String serviceInstanceId, 
			Map<String,String> credentials,
			String syslogDrainUrl) {
		this.id = id;
		this.serviceInstanceId = serviceInstanceId;
		setCredentials(credentials);
		this.syslogDrainUrl = syslogDrainUrl;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServiceInstanceId() {
		return serviceInstanceId;
	}

	public void setServiceInstanceId(String serviceInstanceId) {
		this.serviceInstanceId = serviceInstanceId;
	}

	public Map<String, String> getCredentials() {
		return credentials;
	}

	public void setCredentials(Map<String, String> credentials) {
		if (credentials == null) {
			credentials = new HashMap<String,String>();
		} else {
			this.credentials = credentials;
		}
	}

	public String getSyslogDrainUrl() {
		return syslogDrainUrl;
	}

	public void setSyslogDrainUrl(String syslogDrainUrl) {
		this.syslogDrainUrl = syslogDrainUrl;
	}
	
}
