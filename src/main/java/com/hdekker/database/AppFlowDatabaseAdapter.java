package com.hdekker.database;

import org.springframework.stereotype.Service;

import com.hdekker.appflow.AppFlowPersitance;
import com.hdekker.appflow.AppFlowSupplier;
import com.hdekker.domain.AppFlow;

@Service
public class AppFlowDatabaseAdapter implements AppFlowSupplier, AppFlowPersitance {

	@Override
	public AppFlow save(AppFlow flow) {
		
		return null;
	}

	@Override
	public AppFlow getFlow(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
