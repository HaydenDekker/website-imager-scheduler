package com.hdekker.views.appflow;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

import org.springframework.boot.autoconfigure.web.format.DateTimeFormatters;

import com.hdekker.domain.WebsiteDisplayConfiguration;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.NativeLabel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.data.renderer.ComponentRenderer;

public class WebsiteDisplayElement extends VerticalLayout {

	public WebsiteDisplayElement(WebsiteDisplayConfiguration config, 
			Consumer<WebsiteDisplayConfiguration> updateConsumer, 
			Runnable deleter) {
	
		HorizontalLayout overview = new HorizontalLayout();
		overview.setWidthFull();
		HorizontalLayout nameDuration = new HorizontalLayout();
		nameDuration.getStyle().set("flex-wrap", "wrap");
		NativeLabel websiteName = new NativeLabel(config.getWebsite());
		websiteName.setWidth("200px");
		nameDuration.add(websiteName);
		NumberField durationField = new NumberField("display duration");
		durationField.setValue(config.getDisplayDuration().doubleValue());
		nameDuration.add(durationField);
		
		durationField.addValueChangeListener(c->{
			
			config.setDisplayDuration(c.getValue().intValue());
			updateConsumer.accept(config);
			
		});
		
		overview.add(nameDuration);
		overview.setAlignItems(Alignment.BASELINE);
		overview.setJustifyContentMode(JustifyContentMode.BETWEEN);
		
		Button delete = new Button("delete");
		overview.add(delete);
		delete.addClickListener(d->{
			deleter.run();
		});
		
		add(overview);
		nameDuration.setAlignItems(Alignment.BASELINE);
		
		Grid<OffsetTime> updateTimes = new Grid<OffsetTime>();
		
		add(updateTimes);
		
		HorizontalLayout updateTimesLayout = new HorizontalLayout();
		updateTimesLayout.setAlignItems(Alignment.BASELINE);
		updateTimesLayout.setJustifyContentMode(JustifyContentMode.BETWEEN);
		updateTimesLayout.add(new NativeLabel("Update Times"));
		TimePicker dtp = new TimePicker();
		Button addDt = new Button("add");
		HorizontalLayout updateTimesLayoutSub = new HorizontalLayout();
		updateTimesLayoutSub.add(dtp, addDt);
		updateTimesLayout.add(updateTimesLayoutSub);
		updateTimesLayoutSub.setAlignItems(Alignment.BASELINE);
		
		updateTimes.addColumn(new ComponentRenderer<HorizontalLayout, OffsetTime>(
				HorizontalLayout::new, 
				(a,dt)->{
					String time = dt.format(DateTimeFormatter.ISO_OFFSET_TIME);
					a.add(time);
					Button timeDelete = new Button("delete");
					a.add(timeDelete);
					timeDelete.addClickListener(b->{
						config.getWebsiteUpdateTime().remove(dt);
						updateConsumer.accept(config);
					});
					a.setAlignItems(Alignment.BASELINE);
					a.setJustifyContentMode(JustifyContentMode.BETWEEN);
				}))
			.setHeader(updateTimesLayout);
		
		addDt.addClickListener(b->{
			config.getWebsiteUpdateTime().add(dtp.getValue()
					.atOffset(ZoneOffset.systemDefault().getRules().getOffset(Instant.now())));
			updateConsumer.accept(config);
		});
		
		updateTimes.setItems(config.getWebsiteUpdateTime());
		
	}
	
}
