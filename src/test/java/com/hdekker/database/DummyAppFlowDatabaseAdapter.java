package com.hdekker.database;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery.FetchableFluentQuery;

import com.hdekker.TestProfiles;
import com.hdekker.appflow.AppFlowDeleter;
import com.hdekker.appflow.AppFlowLister;
import com.hdekker.appflow.AppFlowPersitance;
import com.hdekker.appflow.AppFlowSupplier;
import com.hdekker.domain.AppFlow;

@Configuration
@Profile(TestProfiles.NO_DB_CONFIGURATION)
@Primary
public class DummyAppFlowDatabaseAdapter implements AppFlowSupplier, AppFlowPersitance, AppFlowLister, AppFlowDeleter {

	@Override
	public AppFlow delete(AppFlow toDelete) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<AppFlow> listAll() {
		return List.of();
	}

	@Override
	public AppFlow save(AppFlow flow) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public AppFlow getFlow(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
}
