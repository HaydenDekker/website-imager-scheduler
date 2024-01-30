package com.hdekker.views.deviceflow;

import com.hdekker.views.MainLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;

@Route(value = "/deviceflows", layout = MainLayout.class)
public class DeviceFlowView extends VerticalLayout implements AfterNavigationObserver{

	
	
	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		
	}

}
