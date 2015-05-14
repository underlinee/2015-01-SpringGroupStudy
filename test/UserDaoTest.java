import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import Dao.UserDao;
import Model.User;

public class UserDaoTest {

	private ApplicationContext context;

	@Test
	public void test() throws ClassNotFoundException, SQLException {

		context = new GenericXmlApplicationContext("/applicationContext.xml");
		UserDao userDao = context.getBean("userDao", UserDao.class);
		User user = new User();
		user.setId("jojo1");
		user.setName("jobi");
		user.setPassword("1111");

		int count;
		userDao.deleteAll();
		count = userDao.getCount();
		assertEquals(count, 0);
		
		userDao.create(user);
		count = userDao.getCount();
		assertEquals(count, 1);
		
		User dbUser = userDao.recieve(user.getId());
		assertEquals(count, 1);
		
		assertEquals(user, dbUser);
		
		
		
	}

}
