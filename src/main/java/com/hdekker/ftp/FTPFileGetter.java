package com.hdekker.ftp;

import java.io.InputStream;
import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.context.IntegrationFlowContext;
import org.springframework.integration.dsl.context.IntegrationFlowContext.IntegrationFlowRegistration;
import org.springframework.integration.file.FileHeaders;
import org.springframework.integration.file.remote.gateway.AbstractRemoteFileOutboundGateway;
import org.springframework.integration.file.remote.gateway.AbstractRemoteFileOutboundGateway.Command;
import org.springframework.integration.ftp.dsl.Ftp;
import org.springframework.integration.ftp.session.DefaultFtpSessionFactory;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.publisher.Sinks.Many;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

@Component
public class FTPFileGetter {
	
	Logger log = LoggerFactory.getLogger(FTPFileGetter.class);
	
	@Autowired
	FTPServerConfiguration config;
	
	public DefaultFtpSessionFactory sessionFactory() {
	    DefaultFtpSessionFactory sessionFactory = new DefaultFtpSessionFactory();
	    sessionFactory.setHost("localhost");
	    sessionFactory.setPort(config.ftpPort);
	    sessionFactory.setUsername("hayden");
	    sessionFactory.setPassword("hayden");
	    return sessionFactory;
	}
	
	@Autowired
	IntegrationFlowContext flowCtx;
	

	DirectChannel channel = new DirectChannel();
	Flux<Tuple2<String,InputStream>> outputFlux;

	private Scheduler scheduler;
	
	
	public FTPFileGetter(FTPServerConfiguration config, IntegrationFlowContext flowCtx) {
		
		this.config = config;
		this.flowCtx = flowCtx;
		
		scheduler = Schedulers.newBoundedElastic(4, 10, "ftp_getter");
		
		Many<Tuple2<String,InputStream>> sink = Sinks.many()
				.multicast()
				.directAllOrNothing();
		
		outputFlux = sink.asFlux()
				.doOnNext(t->log.info("Received inputStream for " + t.getT1()));
		
		IntegrationFlow flow = IntegrationFlow.from(channel)
			.handle(Ftp.outboundGateway(sessionFactory(), Command.GET, "payload") 
					.options(AbstractRemoteFileOutboundGateway.Option.STREAM)
					
			)
			.handle(message->{
				InputStream is = (InputStream) message.getPayload();
				String fileName = (String) message.getHeaders()
										.get(FileHeaders.REMOTE_FILE);
				sink.tryEmitNext(Tuples.of(fileName, is));
			})
			.get();

		
		IntegrationFlowRegistration reg = flowCtx.registration(flow)
			.register();
		
		reg.start();
		
	}
	
	
	public Flux<Tuple2<String, InputStream>> get(String fileName) {
		
		Mono.delay(Duration.ofMillis(10))
			.publishOn(scheduler)
			.subscribe(c->{
				channel.send(MessageBuilder.withPayload(fileName).build());
			},(err)->{
				log.info("couldn't open " + fileName + ". Check if it exists on ftp.");
			});
		return outputFlux;
	}


	public Flux<Tuple2<String, InputStream>> getFlux(){
		return outputFlux;
	}
	
}
