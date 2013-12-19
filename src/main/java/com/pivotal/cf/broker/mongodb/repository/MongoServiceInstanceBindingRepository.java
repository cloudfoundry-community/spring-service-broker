package com.pivotal.cf.broker.mongodb.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pivotal.cf.broker.model.ServiceInstanceBinding;

/**
 * Repository for ServiceInstanceBinding objects
 * 
 * @author sgreenberg@gopivotal.com
 *
 */
@Repository
public interface MongoServiceInstanceBindingRepository extends MongoRepository<ServiceInstanceBinding, String> {

}
