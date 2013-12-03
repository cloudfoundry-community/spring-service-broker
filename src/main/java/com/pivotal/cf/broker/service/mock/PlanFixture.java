package com.pivotal.cf.broker.service.mock;

import java.util.ArrayList;
import java.util.List;

import com.pivotal.cf.broker.model.Plan;

public class PlanFixture {

	public static List<Plan> getAllPlans() {
		List<Plan> plans = new ArrayList<Plan>();
		plans.add(getPlanOne());
		plans.add(getPlanTwo());
		return plans;
	}
		
	public static Plan getPlanOne() {
		return new Plan("plan-one-id", "plan1", "Description for Plan One");
	}
	
	public static Plan getPlanTwo() {
		return new Plan("plan-two-id", "plan2", "Description for Plan Two");
	}
	
}
