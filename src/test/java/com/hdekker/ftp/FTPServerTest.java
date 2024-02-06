package com.hdekker.ftp;

import java.io.File;
import java.time.Duration;
import java.util.List;

import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.Pollers;
import org.springframework.integration.dsl.StandardIntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.ftp.dsl.Ftp;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.handler.advice.RequestHandlerRetryAdvice;
import org.springframework.messaging.Message;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.TestProfiles;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@SpringBootTest
@ActiveProfiles({"ftp",
		TestProfiles.NO_IMAGE_RETRIVAL_PORT})
public class FTPServerTest {
	
	Logger log = LoggerFactory.getLogger(FTPServerTest.class);

	@Autowired
	private IntegrationFlowContext flowContext;
	
	@Autowired
	TestFTPConfig testConfig;
	
	@Autowired
	FTPUserConfig userConfig;
	
	@Test
	public void canStartServer() throws InterruptedException {
		
		log.info("Test localDirectory set to " + testConfig.getTestLocalDirectory());
		log.info("Number of users found is " + userConfig.getUsers().size());
		
		DefaultFtpSessionFactory sf = new DefaultFtpSessionFactory();
	    sf.setHost(testConfig.getHost());
	    sf.setPort(testConfig.getPort());
	    sf.setUsername(testConfig.getUserName());
	    sf.setPassword(testConfig.getPassword());
	    
	    Mono<Message<?>> ftpMono = Mono.create(s->{
	    	
	    	StandardIntegrationFlow flow = IntegrationFlow.from(
		    		Ftp.inboundAdapter(sf)
		    			.localDirectory(
		    				new File(testConfig.getTestLocalDirectory()))
		    			.remoteDirectory("/"),
		    		e -> e.poller(Pollers.fixedDelay(Duration.ofSeconds(1))
		    					//.advice(retryAdvice())
		    			.errorHandler(er->{
							log.info("error" + er.getLocalizedMessage()); 
							er.printStackTrace();
						}))
		    	)
		    	.handle(m->{
		    		log.info("message received");
		    		s.success(m);
		    	})
		    	.get();
		    
		    flowContext.registration(flow)
		    	.register()
		    	.start();
		    
		    log.info("started client integration flow.");
	    	
	    });
	    
	 StepVerifier.create(ftpMono)
	 	.expectSubscription()
	 	.assertNext(m->{
	 		log.info("message received at assertion " + 
	 				((File) m.getPayload()).getAbsolutePath());
	 		
	 	})
	 	.expectComplete()
	 	.verify();
	    
		
	}
	
	public RequestHandlerRetryAdvice retryAdvice()
	 
	{
	    SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
	    retryPolicy.setMaxAttempts(3); // Retry up to 3 times
	    RequestHandlerRetryAdvice ad = new RequestHandlerRetryAdvice();
	    RetryTemplate temp = new RetryTemplate();
	    temp.setRetryPolicy(retryPolicy);
	    ad.setRetryTemplate(temp);
	    return ad;
	}
	
}
