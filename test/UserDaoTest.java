import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import Dao.UserDao;
import Model.User;


public class UserDaoTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException {
		ApplicationContext context = new GenericXmlApplicationContext("/applicationContext.xml");
		UserDao userDao = context.getBean("userDao", UserDao.class);
		User user = new User();
		
		
		userDao.create(user);
		userDao.recieve(user);
	}


}
