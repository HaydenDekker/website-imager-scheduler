package com.hdekker.ftp;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("ftp")
public class FTPUserConfig {
	
	public static class UserConfig{
		String name;
		String password;
		String homeDirectory;
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getHomeDirectory() {
			return homeDirectory;
		}
		public void setHomeDirectory(String homeDirectory) {
			this.homeDirectory = homeDirectory;
		}
		
	}
	
	List<UserConfig> users;

	public List<UserConfig> getUsers() {
		return users;
	}

	public void setUsers(List<UserConfig> users) {
		this.users = users;
	}	

}
