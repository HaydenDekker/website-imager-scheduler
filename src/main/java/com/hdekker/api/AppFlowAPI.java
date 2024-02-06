package com.hdekker.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hdekker.UseCase;
import com.hdekker.appflow.AppFlowLister;
import com.hdekker.appflow.AppFlowPersitance;
import com.hdekker.appflow.AppFlowSupplier;
import com.hdekker.appflow.AppFlowDeleter;
import com.hdekker.domain.AppFlow;
import com.hdekker.flowschedules.FlowSchedulerPort;
import com.vaadin.flow.data.provider.DataProvider;

@RestController
@RequestMapping()
@UseCase(name = "Create an AppFlow")
public class AppFlowAPI {
	
	Logger log = LoggerFactory.getLogger(AppFlowAPI.class);
	
	@Autowired
	AppFlowLister appFlowLister;
	
	@Autowired
	AppFlowSupplier appFlowSupplier;
	
	@Autowired
	AppFlowPersitance appFlowPersitance;
	
	@Autowired
	ObjectMapper om;
	
	@Autowired
	FlowSchedulerPort scheduler;

	@PostMapping(path = Endpoints.APPFLOW_CREATE_NAME)
	public AppFlow create(
			@PathVariable("name") String name) {
		log.info("Create - " + name);
		return appFlowPersitance.save(new AppFlow(null, name, new ArrayList<>()));
	}

	@GetMapping(value = Endpoints.APPFLOW_LIST)
	public List<AppFlow> listAll() {
		return appFlowLister.listAll();
	}

	@PutMapping(value = Endpoints.APPFLOW_UPDATE)
	public AppFlow update(
			@RequestBody AppFlow update) {
		
		try {
			log.info(om.writeValueAsString(update));
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		AppFlow flow = appFlowPersitance.save(update);
		scheduler.schedule(flow);
		
		return flow;
	}
	
	@Autowired
	AppFlowDeleter appFlowDeleter;

	public AppFlow delete(AppFlow b) {
		return appFlowDeleter.delete(b);
		
	}

	
}
