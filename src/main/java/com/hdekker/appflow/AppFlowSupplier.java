package com.hdekker.appflow;

import com.hdekker.domain.AppFlow;

public interface AppFlowSupplier {

	AppFlow getFlow(Integer id);
	
}
