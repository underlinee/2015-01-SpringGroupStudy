import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import Dao.CountingConnectionMaker;
import Dao.CountingDaoFactory;
import Dao.DaoFactory;
import Dao.ProductDBConnectionMaker;
import Dao.UserDao;
import Model.User;


public class UserDaoTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException {
		ApplicationContext context = new AnnotationConfigApplicationContext(DaoFactory.class);
		UserDao userDao = context.getBean("userDao", UserDao.class);
		User user = new User();
		
		
		userDao.create(user);
		userDao.recieve(user);
	}
	@Test
	public void countingConnectionMaker() throws Exception {
		ApplicationContext context = new AnnotationConfigApplicationContext(CountingDaoFactory.class);
		UserDao userDao = context.getBean("userDao", UserDao.class);
		CountingConnectionMaker c = context.getBean("connectionMaker", CountingConnectionMaker.class);
		
		User user = new User();
		System.out.println(c.getCounter());
		userDao.create(user);
		System.out.println(c.getCounter());
		userDao.recieve(user);
		System.out.println(c.getCounter());
	}


}
