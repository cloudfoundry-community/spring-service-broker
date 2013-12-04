package com.pivotal.cf.broker.service.mock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pivotal.cf.broker.exception.ServiceInstanceBindingExistsException;
import com.pivotal.cf.broker.exception.ServiceInstanceExistsException;
import com.pivotal.cf.broker.model.Catalog;
import com.pivotal.cf.broker.model.ServiceDefinition;
import com.pivotal.cf.broker.model.ServiceInstance;
import com.pivotal.cf.broker.model.ServiceInstanceBinding;
import com.pivotal.cf.broker.service.CatalogService;
import com.pivotal.cf.broker.service.ServiceInstanceBindingService;
import com.pivotal.cf.broker.service.ServiceInstanceService;

@org.springframework.stereotype.Service
public class MockBroker 
		implements CatalogService, ServiceInstanceService, ServiceInstanceBindingService {

	private static final Logger logger = LoggerFactory.getLogger(MockBroker.class);
	
	private Map<String, ServiceInstanceBinding> bindings = 
			new HashMap<String, ServiceInstanceBinding>();
	
	private Map<String, ServiceInstance> instances = 
			new HashMap<String, ServiceInstance>();
	
	@Override
	public ServiceInstanceBinding createServiceInstanceBinding(
			String bindingId, ServiceInstance serviceInstance, String serviceId, String planId)
			throws ServiceInstanceBindingExistsException {
		logger.info("createServiceInstanceBinding: serviceInstanceId = " + serviceInstance.getId()
				+ ", serviceId = " + serviceId 
				+ ", planId = " + planId);
		ServiceInstanceBinding binding = new ServiceInstanceBinding(bindingId, serviceInstance.getId(), null, null);
		bindings.put(bindingId,  binding);
		return binding;
	}

	@Override
	public ServiceInstanceBinding getServiceInstanceBinding(String id) {
		logger.info("getServiceInstanceBinding: id = " + id);
		return bindings.get(id);
	}

	@Override
	public ServiceInstanceBinding deleteServiceInstanceBinding(String id) {
		logger.info("deleteServiceInstanceBinding: id = " + id);
		return bindings.remove(id);
	}

	@Override
	public List<ServiceInstance> getAllServiceInstances() {
		logger.info("getAllServiceInstances");
		return new ArrayList<ServiceInstance>(instances.values());
	}

	@Override
	public ServiceInstance createServiceInstance(ServiceDefinition service,
			String serviceInstanceId, String planId,
			String organizationGuid, String spaceGuid)
			throws ServiceInstanceExistsException {
		logger.info("createServiceInstance: serviceId = " + service.getId()
			+ ", serviceInstanceId = " + serviceInstanceId
			+ ", organizationGuid = " + organizationGuid
			+ ", spaceGuid = " + spaceGuid);
		ServiceInstance instance = new ServiceInstance(serviceInstanceId, service.getId(),
				planId, organizationGuid, spaceGuid, null);
		instances.put(serviceInstanceId, instance);
		return instance;
	}

	@Override
	public ServiceInstance getServiceInstance(String id) {
		logger.info("getServiceInstance: id = " + id);
		return instances.get(id); 
	}

	@Override
	public ServiceInstance deleteServiceInstance(String id) {
		logger.info("deleteServiceInstance: id = " + id);
		return instances.remove(id);
	}

	@Override
	public Catalog getCatalog() {
		logger.info("getCatalog");
		return CatalogFixture.getCatalog();
	}

	@Override
	public ServiceDefinition getServiceDefinition(String serviceId) {
		logger.info("getService: serviceId = " + serviceId);
		return ServiceFixture.getService();
	}
	
}
