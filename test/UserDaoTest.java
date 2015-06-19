import static org.junit.Assert.*;

import java.sql.SQLException;

import org.junit.Before;
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
import Model.Level;
import Model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserDaoTest {

	@Autowired
	private ApplicationContext context;
	@Autowired
	private UserDao userDao;
	private User user1;
	private User user2;
	
	@Before
	public void setUp(){
		user1 = new User();
		user1.setId("jojo1");
		user1.setName("jobi");
		user1.setPassword("1111");
		user1.setLevel(Level.BASIC);
		user1.setLogin(10);
		user1.setRecommend(20);
		
		user2 = new User();
		user2.setId("jojo2");
		user2.setName("jobi");
		user2.setPassword("1111");
		user2.setLevel(Level.BASIC);
		user2.setLogin(10);
		user2.setRecommend(20);
	}

	@Test
	public void test() throws ClassNotFoundException, SQLException {

		int count;
		userDao.deleteAll();
		count = userDao.getCount();
		assertEquals(count, 0);

		userDao.create(user1);
		count = userDao.getCount();
		assertEquals(count, 1);

		User dbUser = userDao.recieve(user1.getId());
		assertEquals(count, 1);

		assertEquals(user1, dbUser);

	}
	
	@Test
	public void update() throws Exception {
		userDao.deleteAll();
		userDao.create(user1);
		userDao.create(user2);
		
		user1.setName("hatao");
		user1.setPassword("1234");
		user1.setLevel(Level.GOLD);
	
		userDao.update(user1);
		
		User user1update = userDao.recieve(user1.getId());
		checkSameUser(user1, user1update);
		User user2update = userDao.recieve(user2.getId());
		checkSameUser(user2, user2update);
	}

	private void checkSameUser(User user1, User user2) {
		assertEquals(user1.getId(), user2.getId());
		assertEquals(user1.getName(), user2.getName());
		assertEquals(user1.getPassword(), user2.getPassword());
		assertEquals(user1.getLevel(), user2.getLevel());
		assertEquals(user1.getLogin(), user2.getLogin());
		assertEquals(user1.getRecommend(), user2.getRecommend());
		
	}

	@Test(expected = EmptyResultDataAccessException.class)
	public void getUserFailure() throws Exception {

		userDao.deleteAll();
		assertEquals(userDao.getCount(), 0);

		userDao.create(user1);
		assertEquals(userDao.getCount(), 1);

		User dbUser = userDao.recieve("unknown_id");
		assertEquals(userDao.getCount(), 1);

		assertEquals(user1, dbUser);
	}
	
	@Test
	public void getAll() throws Exception {
		userDao.deleteAll();
		assertEquals(userDao.getAll().size(), 0);
	}
	
//	@Test(expected = DuplicateKeyException.class)
	@Test(expected = DataAccessException.class)
	public void duplicateKey() throws Exception {		
		userDao.deleteAll();
		userDao.create(user1);
		userDao.create(user1);
	}
	
}
