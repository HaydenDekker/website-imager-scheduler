package com.hdekker.views.assignement;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.hdekker.api.AppFlowAPI;
import com.hdekker.api.DeviceAPI;
import com.hdekker.api.DeviceAppFlowAssignmentAPI;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceAppflowAssignment;
import com.hdekker.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;

@Route(value =  "/assignment", layout = MainLayout.class)
public class DeviceAppFlowAssignmentView extends VerticalLayout implements AfterNavigationObserver{

	@Autowired
	AppFlowAPI appFlowAPI;
	
	@Autowired
	DeviceAPI deviceAPI;
	
	@Autowired
	DeviceAppFlowAssignmentAPI deviceAppFlowAssignmentAPI;
	
	Map<Integer, Device> deviceById = new HashMap<>();
	Map<Integer, AppFlow> appFlowById = new HashMap<>();
	
	Grid<DeviceAppflowAssignment> appFlows = new Grid<>();
	
	ComboBox<Device> deviceList = new ComboBox<>("devices");
	ComboBox<AppFlow> appFlowList = new ComboBox<>("appflows");
	Button set = new Button("set");
	
	public DeviceAppFlowAssignmentView() {
		
		HorizontalLayout manage = new HorizontalLayout();
		manage.add(deviceList, appFlowList, set);
		manage.setAlignItems(Alignment.BASELINE);
		
		deviceList.setItemLabelGenerator(d->d.getName());
		appFlowList.setItemLabelGenerator(a->a.getName());
		
		appFlows.addColumn(d->{
			return deviceById.get(d.getDeviceId())
					.getName();
		})
		.setHeader("Device");
		
		
		appFlows.addColumn(d->{
			return Optional.ofNullable(appFlowById.get(d.getAppFlowId()))
					.map(a->a.getName())
					.orElse("Error");
		})
		.setHeader("AppFlow");
		
		appFlows.addColumn(new ComponentRenderer<Button, DeviceAppflowAssignment>(Button::new, 
				(b, d)->{
					b.setIcon(new Icon(VaadinIcon.DEL));
					b.addClickListener(del->{
						deviceAppFlowAssignmentAPI.delete(d);
						refresh();
					});
				})
		)
		.setHeader("delete");
		
		add(manage);
		add(appFlows);
		
		set.addClickListener(b->{
			deviceAppFlowAssignmentAPI.create(new DeviceAppflowAssignment(
					deviceList.getValue().getId(), 
					appFlowList.getValue().getId())
			);
			refresh();
		});
		
		
	}
	
	private void refresh() {
		
		deviceById = deviceAPI.listAllDevices()
				.stream()
				.collect(Collectors.toMap(d->d.getId(), d->d));
			
		appFlowById = appFlowAPI.listAll()
			.stream()
			.collect(Collectors.toMap(a->a.getId(), a->a));
		
		deviceList.setItems(deviceById.values());
		appFlowList.setItems(appFlowById.values());
		
		appFlows.setItems(deviceAppFlowAssignmentAPI.list());
			
	}
	
	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		refresh();
	}
	
	

}
