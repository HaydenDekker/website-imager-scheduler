package com.hdekker.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.appflow.AppFlowDeleter;
import com.hdekker.appflow.AppFlowLister;
import com.hdekker.appflow.AppFlowPersitance;
import com.hdekker.appflow.AppFlowSupplier;
import com.hdekker.domain.AppFlow;

@Service
public class AppFlowDatabaseAdapter implements AppFlowSupplier, AppFlowPersitance, AppFlowLister, AppFlowDeleter {
	
	@Autowired
	AppFlowRepository appFlowRepository;

	@Override
	public AppFlow save(AppFlow flow) {
		
		return appFlowRepository.save(flow);
	}

	@Override
	public AppFlow getFlow(Integer id) {
		
		return appFlowRepository.findById(id).get();
	}

	@Override
	public List<AppFlow> listAll() {
		
		return appFlowRepository.findAll();
	}

	@Override
	public AppFlow delete(AppFlow toDelete) {
		
		appFlowRepository.delete(toDelete);
		return toDelete;
	}

}
