import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import Dao.UserDao;
import Model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserDaoTest {

	@Autowired
	private ApplicationContext context;
	@Autowired
	private UserDao userDao;

	@Test
	public void test() throws ClassNotFoundException, SQLException {

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

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws Exception {
		User user = new User();
		user.setId("jojo1");
		user.setName("jobi");
		user.setPassword("1111");

		userDao.deleteAll();
		assertEquals(userDao.getCount(), 0);

		userDao.create(user);
		assertEquals(userDao.getCount(), 1);

		User dbUser = userDao.recieve("unknown_id");
		assertEquals(userDao.getCount(), 1);

		assertEquals(user, dbUser);
	}
	
	@Test
	public void getAll() throws Exception {
		userDao.deleteAll();
		assertEquals(userDao.getAll().size(), 0);
	}
	
//	@Test(expected = DuplicateKeyException.class)
	@Test(expected = DataAccessException.class)
	public void duplicateKey() throws Exception {
		User user = new User();
		user.setId("jojo1");
		user.setName("jobi");
		user.setPassword("1111");
		
		
		userDao.deleteAll();
		userDao.create(user);
		userDao.create(user);
	}

}
