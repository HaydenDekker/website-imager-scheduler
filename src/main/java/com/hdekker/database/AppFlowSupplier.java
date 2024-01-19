package com.hdekker.database;

import com.hdekker.appflow.AppFlow;

public interface AppFlowSupplier {

	AppFlow getFlow(Integer id);
	
}
