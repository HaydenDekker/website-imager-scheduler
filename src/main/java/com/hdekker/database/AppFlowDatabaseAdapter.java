package com.hdekker.database;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hdekker.RuntimeProfiles;
import com.hdekker.appflow.AppFlowDeleter;
import com.hdekker.appflow.AppFlowLister;
import com.hdekker.appflow.AppFlowPersitance;
import com.hdekker.appflow.AppFlowSupplier;
import com.hdekker.domain.AppFlow;

@Service
@Profile(RuntimeProfiles.POSTGRESS)
public class AppFlowDatabaseAdapter implements AppFlowSupplier, AppFlowPersitance, AppFlowLister, AppFlowDeleter {
	
	@Autowired
	AppFlowRepository appFlowRepository;

	@Override
	public AppFlow save(AppFlow flow) {
		
		return appFlowRepository.save(flow);
	}

	@Override
	public AppFlow getFlow(Integer id) {
		
		Exception e = new Exception("Could not find appFlow with Id" + id);
		
		try {
			return appFlowRepository.findById(id)
					.orElseThrow(()->{
						return e;
					});
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
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
