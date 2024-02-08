package com.hdekker.ftp;

import static org.assertj.core.api.Assertions.assertThat;

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
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
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

import reactor.util.function.Tuple2;

@SpringBootTest
@ActiveProfiles({"ftp", TestProfiles.NO_IMAGE_RETRIVAL_PORT})
public class FTPOutboundGatewayTest {
	
	Logger log = LoggerFactory.getLogger(FTPOutboundGatewayTest.class);
	
	@Autowired
	FTPFileGetter ftpFileGetter;

	@Test
	public void whenGatewayTriggered_ExpectFileReturned() throws InterruptedException, IOException {
		
		Tuple2<String, InputStream> resp = ftpFileGetter.get("test.png")
			.blockFirst();
		
		assertThat(resp.getT1()).isEqualTo("test.png");
		assertThat(resp.getT2().available()).isGreaterThan(1);
		
	
	}
	
}
