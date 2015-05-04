import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Test;

import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import Dao.UserDao;
import Model.User;

public class UserDaoTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException {

		UserDao userDao = new UserDao();
		DataSource dataSource = new SingleConnectionDataSource("jdbc:mysql://localhost/springbook", "spring_developer", "1111", true);
		userDao.setDataSource(dataSource);
		User user = new User();
		
		userDao.create(user);
		userDao.recieve(user);
	}

}
