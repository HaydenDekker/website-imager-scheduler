package com.hdekker.views.appflow;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.hdekker.api.AppFlowAPI;
import com.hdekker.domain.AppFlow;
import com.hdekker.views.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;

@Route(value = "/appflow", layout = MainLayout.class)
public class AppFlowView extends VerticalLayout implements AfterNavigationObserver{
	
	Logger log = LoggerFactory.getLogger(AppFlowView.class);
	
	Grid<AppFlow> appFlows;
    
    @Autowired
	AppFlowAPI appFlowAPI;
    
    Button add = new Button("add");
    TextField name = new TextField();

    public AppFlowView() {
    	
    	setHeightFull();
    	
    	HorizontalLayout header = new HorizontalLayout();
    	header.setAlignItems(Alignment.BASELINE);
    	NativeLabel appFlowL = new NativeLabel("AppFlows");
    	NativeLabel addFlow = new NativeLabel("Add a new AppFlow:");
    	
    	HorizontalLayout addNewL = new HorizontalLayout();
    	addNewL.setAlignItems(Alignment.BASELINE);
    	addNewL.add(addFlow, name, add);
    	header.add(appFlowL, addNewL);
    	header.setJustifyContentMode(JustifyContentMode.BETWEEN);
        
    	appFlows = new Grid<>();
    	appFlows.setHeightFull();
    	appFlows.addColumn(new ComponentRenderer<VerticalLayout, AppFlow>(
    			VerticalLayout::new, (a,b) -> {
    				a.add(new AppFlowElement(b, 
    						(update)->{
    							appFlowAPI.update(update);
    							refreshItems();
    						},
    						()->{
    							appFlowAPI.delete(b);
    							refreshItems();
    						})
    			);
    			}))
    		.setHeader(header);
    	add(appFlows);
    	
    	add.addClickListener(b->{
    		appFlowAPI.create(name.getValue());
    		refreshItems();
    	});
    	
    	name.setPlaceholder("flow name");
    	
    }
    
    public void refreshItems() {
    	List<AppFlow> appFlowsList = appFlowAPI.listAll();
    	//appFlowsList.forEach(app->log.info(app.getName()));
    	appFlows.setItems(appFlowsList);
    }

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		refreshItems();
	}

}
