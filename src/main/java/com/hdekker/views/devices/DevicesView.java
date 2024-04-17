package com.hdekker.views.devices;

import org.springframework.beans.factory.annotation.Autowired;

import com.hdekker.api.DeviceAPI;
import com.hdekker.domain.Device;
import com.hdekker.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;

@PageTitle("Devices")
@Route(value = "/home", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class DevicesView extends VerticalLayout implements AfterNavigationObserver {

    Grid<Device> devices;
    
    @Autowired
	DeviceAPI deviceAPI;
    
    Button add = new Button("add");
    TextField name = new TextField("name");

    public DevicesView() {
        
    	devices = new Grid<>();
    	devices.addColumn(Device::getName)
    		.setHeader("Name");
    	devices.addColumn(new ComponentRenderer<Button, Device>(Button::new, 
    			(a,b)->{
    				a.setIcon(new Icon(VaadinIcon.CLOSE_CIRCLE));
    				a.addClickListener(c->{
    					deviceAPI.delete(b);
    					refreshItems();
    				});
    			}))
    		.setHeader("Delete.");
    	add(devices);
    	
    	HorizontalLayout formLayout = new HorizontalLayout(add, name);
    	formLayout.setAlignItems(Alignment.BASELINE);
    	add(formLayout);
    	add.addClickListener(b->{
    		deviceAPI.createDevice(name.getValue());
    		refreshItems();
    	});
    	
    }
    private void refreshItems() {
    	devices.setItems(deviceAPI.listAllDevices());
    }

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		refreshItems();
	}

}
