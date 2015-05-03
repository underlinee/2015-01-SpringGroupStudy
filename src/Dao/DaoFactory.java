package Dao;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DaoFactory {
	
	@Bean
	public UserDao userDao() {
		ConnectionMaker connectionMaker = new NConnectionMaker();
		UserDao userDao = new UserDao(connectionMaker);
		return userDao;
	}
}
