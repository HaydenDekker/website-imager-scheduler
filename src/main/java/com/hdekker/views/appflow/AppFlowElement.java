package com.hdekker.views.appflow;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import com.hdekker.domain.AppFlow;
import com.hdekker.domain.WebsiteDisplayConfiguration;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;

public class AppFlowElement extends VerticalLayout{
	
	final String name;
	Grid<WebsiteDisplayConfiguration> wdcGrid = new Grid<WebsiteDisplayConfiguration>();
	
	TextField newWebsiteName = new TextField();
	NumberField displayDuration = new NumberField("display duration");
	Grid<LocalDateTime> updateTimes = new Grid<LocalDateTime>();
	
	Button addWebsiteConfiguration = new Button("add");
	Button updateTimeAdder = new Button("add update");
	
	public AppFlowElement(AppFlow appFlow, Consumer<AppFlow> siteUpdateConsumer, Runnable deleteConsumer) {
		
		name = appFlow.getName();
		HorizontalLayout overview = new HorizontalLayout();
		overview.setWidthFull();
		add(overview);
		overview.add(new NativeLabel(name));
		Button deleteButton = new Button("delete");
		deleteButton.addClickListener(e->deleteConsumer.run());
		
		overview.add(deleteButton);
		overview.setJustifyContentMode(JustifyContentMode.BETWEEN);
		
		newWebsiteName.setPlaceholder("website name");
		addWebsiteConfiguration.setIcon(new Icon(VaadinIcon.PLUS_CIRCLE));
		
		HorizontalLayout header = new HorizontalLayout();
		HorizontalLayout addNewWebsite = new HorizontalLayout();
		header.setAlignItems(Alignment.BASELINE);
		NativeLabel websitesL = new NativeLabel("Websites");
		header.add(websitesL, addNewWebsite);
		header.setJustifyContentMode(JustifyContentMode.BETWEEN);
		addNewWebsite.setAlignItems(Alignment.BASELINE);
		addNewWebsite.add(new NativeLabel("Add a new Website:"));
		addNewWebsite.add(newWebsiteName);
		addNewWebsite.add(addWebsiteConfiguration);
		
		wdcGrid.addColumn(new ComponentRenderer<VerticalLayout, WebsiteDisplayConfiguration>(VerticalLayout::new, 
				(a,b) ->{
					a.add(new WebsiteDisplayElement(b, update->{
						
						List<WebsiteDisplayConfiguration> wdc = new ArrayList<>(appFlow.getSiteOrder());
						int index = wdc.indexOf(b);
						wdc.remove(b);
						wdc.add(index, update);
						
						AppFlow newAppFlow = new AppFlow(appFlow.getId(), appFlow.getName(), wdc);
						siteUpdateConsumer.accept(newAppFlow);
						
					},
					()->{
						
						List<WebsiteDisplayConfiguration> wdc = new ArrayList<>(appFlow.getSiteOrder());
						wdc.remove(b);
	
						AppFlow newAppFlow = new AppFlow(appFlow.getId(), appFlow.getName(), wdc);
						siteUpdateConsumer.accept(newAppFlow);
						
					}));
				}))
			.setHeader(header);
		
		add(wdcGrid);
		
		wdcGrid.setItems(appFlow.getSiteOrder());
			
		addWebsiteConfiguration.addClickListener(b->{
		
			List<WebsiteDisplayConfiguration> wdcs = new ArrayList<>(appFlow.getSiteOrder());
			wdcs.add(new WebsiteDisplayConfiguration(newWebsiteName.getValue(), 0, List.of()));
			AppFlow newAppFlow = new AppFlow(appFlow.getId(), appFlow.getName(), wdcs);
			siteUpdateConsumer.accept(newAppFlow);
			
		});
		
		
	}
	
}
