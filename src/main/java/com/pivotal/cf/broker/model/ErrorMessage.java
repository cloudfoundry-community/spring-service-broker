package com.pivotal.cf.broker.model;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * Used to send errors back to the cloud controller.
 * 
 * @author sgreenberg@gopivotal.com
 *
 */
public class ErrorMessage {

	@JsonProperty("message")
	private String message;

	public ErrorMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
