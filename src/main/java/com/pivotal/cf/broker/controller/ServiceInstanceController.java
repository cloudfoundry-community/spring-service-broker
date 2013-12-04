package com.pivotal.cf.broker.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pivotal.cf.broker.model.CreateServiceInstanceRequest;
import com.pivotal.cf.broker.model.CreateServiceInstanceResponse;
import com.pivotal.cf.broker.model.ServiceDefinition;
import com.pivotal.cf.broker.model.ServiceInstance;
import com.pivotal.cf.broker.service.CatalogService;
import com.pivotal.cf.broker.service.ServiceInstanceExistsException;
import com.pivotal.cf.broker.service.ServiceInstanceService;

/**
 * See: http://docs.cloudfoundry.com/docs/running/architecture/services/writing-service.html
 * 
 * @author sgreenberg@gopivotal.com
 */
@Controller
public class ServiceInstanceController {

	public static final String BASE_PATH = "/v2/service_instances";
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceInstanceController.class);
	
	private ServiceInstanceService service;
	private CatalogService catalogService;
	
	@Autowired
 	public ServiceInstanceController(ServiceInstanceService service, CatalogService catalogService) {
 		this.service = service;
 		this.catalogService = catalogService;
 	}
	
	@RequestMapping(value = BASE_PATH, method = RequestMethod.GET)
	public @ResponseBody List<ServiceInstance> getServiceInstances() {
		logger.debug("GET: " + BASE_PATH + ", getServiceInstances()");
		return service.getAllServiceInstances();
	}
		
	@RequestMapping(value = BASE_PATH + "/{instanceId}", method = RequestMethod.PUT)
	public ResponseEntity<CreateServiceInstanceResponse> createServiceInstance(
			@PathVariable("instanceId") String serviceInstanceId, 
			@Valid @RequestBody CreateServiceInstanceRequest request) {
		logger.debug("PUT: " + BASE_PATH + "/{instanceId}" 
				+ ", createServiceInstance(), serviceInstanceId = " + serviceInstanceId);
		ServiceDefinition svc = catalogService.getServiceDefinition(request.getServiceDefinitionId());
		if (svc == null) {
			return new ResponseEntity<CreateServiceInstanceResponse>(HttpStatus.UNPROCESSABLE_ENTITY);
		}
		ServiceInstance instance;
		try {
			instance = service.createServiceInstance(
					svc, 
					serviceInstanceId, 
					request.getPlanId(),
					request.getOrganizationGuid(), 
					request.getSpaceGuid());
		} catch (ServiceInstanceExistsException e) {
			return new ResponseEntity<CreateServiceInstanceResponse>(HttpStatus.CONFLICT);
		}
		logger.debug("ServiceInstance Created: " + instance.getId());
        return new ResponseEntity<CreateServiceInstanceResponse>(
        		new CreateServiceInstanceResponse(instance), 
        		HttpStatus.CREATED);
	}
	
	@RequestMapping(value = BASE_PATH + "/{instanceId}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteServiceInstance(
			@PathVariable("instanceId") String instanceId, 
			@RequestParam("service_id") String serviceId,
			@RequestParam("plan_id") String planId) {
		logger.debug( "DELETE: " + BASE_PATH + "/{instanceId}" 
				+ ", deleteServiceInstanceBinding(), serviceInstanceId = " + instanceId 
				+ ", serviceId = " + serviceId
				+ ", planId = " + planId);
		ServiceInstance instance = service.deleteServiceInstance(instanceId);
		if (instance == null) {
			return new ResponseEntity<String>("{}", HttpStatus.NOT_FOUND);
		}
		logger.debug("ServiceInstance Deleted: " + instance.getId());
        return new ResponseEntity<String>("{}", HttpStatus.OK);
	}

	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	@ResponseBody
	public ResponseEntity<String> handleException(HttpMessageNotReadableException ex, HttpServletResponse response)
	{
	    return new ResponseEntity<String>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	public ResponseEntity<String> handleException(MethodArgumentNotValidException ex, HttpServletResponse response)
	{
	    BindingResult result = ex.getBindingResult();
	    String message = "Missing required fields:";
	    for (FieldError error: result.getFieldErrors()) {
	    	message += " " + error.getField();
	    }
		return new ResponseEntity<String>(message, HttpStatus.UNPROCESSABLE_ENTITY);
	}
	
}
