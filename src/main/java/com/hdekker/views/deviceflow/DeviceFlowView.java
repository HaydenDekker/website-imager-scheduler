package com.hdekker.views.deviceflow;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.ftp.dsl.Ftp;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hdekker.api.DeviceAPI;
import com.hdekker.api.DeviceFlowAPI;
import com.hdekker.device.DeviceLister;
import com.hdekker.domain.Device;
import com.hdekker.domain.DeviceFlow;
import com.hdekker.views.MainLayout;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.Route;

import reactor.core.Disposable;

@Route(value = "/deviceflows", layout = MainLayout.class)
public class DeviceFlowView extends VerticalLayout implements AfterNavigationObserver{
	
	Logger log = LoggerFactory.getLogger(DeviceFlowView.class);
	
	ComboBox<Device> devices = new ComboBox<>("Devices.");
	
	@Autowired
	DeviceAPI deviceAPI;
	
	VerticalLayout deviceFlowHolder = new VerticalLayout();
	
	@Autowired
	DeviceFlowAPI deviceFlowAPI;
	
	Disposable terminate = ()->{};
	
	public DeviceFlowView() {
		
		add(devices);
		devices.setItemLabelGenerator(d->d.getName());
		devices.addValueChangeListener(d->{
			terminate.dispose();
			terminate = deviceFlowAPI.subscribe(d.getValue())
				.subscribe(deviceFlow->{
					
					ObjectMapper om = new ObjectMapper();
					om.registerModule(new JavaTimeModule());
					
					deviceFlowHolder.getUI()
						.ifPresentOrElse(ui->
							ui.access(()->{
							String ds = "";
							try {
								ds = om.writeValueAsString(deviceFlow);
							} catch (JsonProcessingException e) {
								e.printStackTrace();
							}
							deviceFlowHolder.removeAll();
							deviceFlowHolder.add(new Text(ds));
							deviceFlowHolder.getUI()
								.get()
								.push();
							getPhotos(deviceFlow);
							Notification.show("Received DeviceFlow");
							
						}), ()->{
							terminate.dispose();
						});
				});
		});
		
		add(deviceFlowHolder);
	}
	
	public DefaultFtpSessionFactory sessionFactory() {
	    DefaultFtpSessionFactory sessionFactory = new DefaultFtpSessionFactory();
	    sessionFactory.setHost("localhost");
	    sessionFactory.setPort(21);
	    sessionFactory.setUsername("hayden");
	    sessionFactory.setPassword("hayden");
	    return sessionFactory;
	}
	
	@Autowired
	IntegrationFlowContext ctx;
	
	private void getPhotos(DeviceFlow deviceFlow) {
		
		FtpRemoteFileTemplate template = new FtpRemoteFileTemplate(sessionFactory());
		IntegrationFlowBuilder flow = IntegrationFlow.from(Ftp.inboundStreamingAdapter(template)
				.remoteDirectory("/"))
		.handle((p)->{
			log.info("Received file " + p.getClass().toGenericString());
		});
		
		ctx.registration(flow.get())
			.register()
			.start();
		
	}

	@Override
	public void afterNavigation(AfterNavigationEvent event) {
		
		devices.setItems(deviceAPI.listAllDevices());
		
	}

}
