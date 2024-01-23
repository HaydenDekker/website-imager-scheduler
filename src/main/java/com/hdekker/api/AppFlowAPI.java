package com.hdekker.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hdekker.UseCase;
import com.hdekker.domain.AppFlow;

@RestController
@RequestMapping()
@UseCase(name = "Create an AppFlow")
public class AppFlowAPI {
	
	Logger log = LoggerFactory.getLogger(AppFlowAPI.class);

	@PostMapping(path = Endpoints.APPFLOW_CREATE_NAME)
	public AppFlow create(
			@PathVariable("name") String name) {
		log.info("Create - " + name);
		return new AppFlow(name, List.of());
	}

	
}
