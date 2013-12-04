package com.pivotal.cf.broker.exception;

public class ServiceDefinitionDoesNotExistException extends Exception {
	
	private static final long serialVersionUID = -62090827040416788L;
	private String serviceDefinitionId;
	
	public ServiceDefinitionDoesNotExistException(String serviceDefinitionId) {
		this.serviceDefinitionId = serviceDefinitionId;
	}
	
	public String getMessage() {
		return "ServiceDefinition does not exist: id = " + serviceDefinitionId;
	}
	
}