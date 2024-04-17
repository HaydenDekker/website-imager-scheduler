package com.hdekker.image.server.ftp;

import java.util.List;
import java.util.stream.Collectors;

import org.apache.ftpserver.FtpServer;
import org.apache.ftpserver.FtpServerFactory;
import org.apache.ftpserver.ftplet.FtpException;
import org.apache.ftpserver.listener.ListenerFactory;
import org.apache.ftpserver.usermanager.PropertiesUserManagerFactory;
import org.apache.ftpserver.usermanager.impl.BaseUser;
import org.apache.ftpserver.usermanager.impl.WritePermission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hdekker.image.server.ftp.FTPUserConfig.UserConfig;

import jakarta.annotation.PreDestroy;

@Configuration
public class FTPServerConfiguration{
	
	Logger log = LoggerFactory.getLogger(FTPServerConfiguration.class);
	
	FtpServer server;
	
	@Autowired
	FTPUserConfig userConfig;
	
	@Value("${ftp.port}")
	Integer ftpPort;
	
	public List<BaseUser> convert(List<UserConfig> users){
		return userConfig.getUsers()
			.stream()
			.map(uc-> {
			
				BaseUser user = new BaseUser();
				user.setName(uc.getName());
				user.setPassword(uc.getPassword());
				user.setHomeDirectory(uc.getHomeDirectory());
				WritePermission wp = new WritePermission();
				user.setAuthorities(List.of(wp));
				
				return user;
				
			})
			.collect(Collectors.toList());
					
	}

	@Bean
	public FtpServer configureFTP() {
		
		FtpServerFactory serverFactory = new FtpServerFactory();
		PropertiesUserManagerFactory userManagerFactory = new PropertiesUserManagerFactory();
		
		
		serverFactory.setUserManager(userManagerFactory.createUserManager());
		
		convert(userConfig.getUsers())
			.forEach(user->{
				try {
					serverFactory.getUserManager()
						.save(user);
				} catch (FtpException e) {
					e.printStackTrace();
				}
			});
		
		ListenerFactory listenerFactory = new ListenerFactory();
		listenerFactory.setPort(ftpPort);
		serverFactory.addListener("default", listenerFactory.createListener());
		
		server = serverFactory.createServer();
		try {
			server.start();
		} catch (Exception e) {
			log.error("Could not start ftp server.");
		}
		
		return server;
		
	}
	
	@PreDestroy
	public void shutDown() {
		log.info("Shutting down ftp.");
		server.stop();
	}
	
}
