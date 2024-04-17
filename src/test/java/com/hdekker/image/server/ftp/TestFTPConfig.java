package com.hdekker.image.server.ftp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@ConfigurationProperties("ftp.test")
@Profile("ftp")
public class TestFTPConfig {
	
	@Value("${ftp.test.local-directory}")
	String testLocalDirectory;
	
	public String getTestLocalDirectory() {
		return testLocalDirectory;
	}

	public void setTestLocalDirectory(String testLocalDirectory) {
		this.testLocalDirectory = testLocalDirectory;
	}
	
	String host;
	Integer port;
	String userName;
	String password;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
