package com.hdekker.ftp;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.IntegrationMessageHeaderAccessor;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlowBuilder;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.dsl.context.IntegrationFlowContext.IntegrationFlowRegistration;
import org.springframework.integration.file.remote.gateway.AbstractRemoteFileOutboundGateway;
import org.springframework.integration.file.remote.gateway.AbstractRemoteFileOutboundGateway.Command;
import org.springframework.integration.ftp.dsl.Ftp;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.ftp.session.FtpRemoteFileTemplate;
import org.springframework.integration.ftp.session.FtpSession;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.javaparser.utils.Log;
import com.hdekker.TestProfiles;

@SpringBootTest
@ActiveProfiles({"ftp", TestProfiles.NO_IMAGE_RETRIVAL_PORT})
public class FTPOutboundGatewayTest {
	
	Logger log = LoggerFactory.getLogger(FTPOutboundGatewayTest.class);
	
	public DefaultFtpSessionFactory sessionFactory() {
	    DefaultFtpSessionFactory sessionFactory = new DefaultFtpSessionFactory();
	    sessionFactory.setHost("localhost");
	    sessionFactory.setPort(21);
	    sessionFactory.setUsername("hayden");
	    sessionFactory.setPassword("hayden");
	    return sessionFactory;
	}
	
	@Autowired
	IntegrationFlowContext flowCtx;
	
	FtpSession closable;

	@Test
	public void whenGatewayTriggered_ExpectFileReturned() throws InterruptedException {
		
		//FtpRemoteFileTemplate template = new FtpRemoteFileTemplate(sessionFactory());
		DirectChannel channel = new DirectChannel();
		
//		IntegrationFlow.from(Ftp.outboundGateway(sessionFactory(), Command.GET, "payload")
//					.options(AbstractRemoteFileOutboundGateway.Option.STREAM)
//					.);
		IntegrationFlow flow = IntegrationFlow.from(channel)
			.handle(Ftp.outboundGateway(sessionFactory(), Command.GET, "payload")
					.options(AbstractRemoteFileOutboundGateway.Option.STREAM)
			)
			.handle(message->{
				ObjectMapper om = new ObjectMapper();
				om.registerModule(new JavaTimeModule());
				om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
				try {
					log.info("received message " + om.writeValueAsString(message.getHeaders()) );
				} catch (JsonProcessingException e1) {
					e1.printStackTrace();
				}
				closable = (FtpSession) new IntegrationMessageHeaderAccessor(message).getCloseableResource();
				InputStream is = (InputStream) message.getPayload();
//			    try {
//			    	//is.close();
//			    } catch (IOException e) {
//			        // Handle close error
//			    }
//			    
			    log.info("closed");

			})
			.get();
		
		IntegrationFlowRegistration reg = flowCtx.registration(flow)
			.register();
		
		reg.start();
		
		channel.send(MessageBuilder.withPayload("test.png").build());
		
		log.info("sleeping");
		Thread.sleep(1000);
		
		channel.send(MessageBuilder.withPayload("shoud_fail.png").build());
		
		
		reg.stop();
		
		if(closable.isOpen()) {
			log.error("should be closed.");
			//closable.close();
		}
		
	
	}
	
}
