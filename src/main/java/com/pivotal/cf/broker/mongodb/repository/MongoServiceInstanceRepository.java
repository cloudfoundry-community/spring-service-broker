package com.pivotal.cf.broker.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pivotal.cf.broker.model.ServiceInstance;

/**
 * Repository for ServiceInstance objects
 * 
 * @author sgreenberg@gopivotal.com
 *
 */
@Repository
public interface MongoServiceInstanceRepository extends MongoRepository<ServiceInstance, String> {

}