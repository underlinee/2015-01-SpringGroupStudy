import static org.junit.Assert.*;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import Dao.UserDao;
import Model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/applicationContext.xml")
@DirtiesContext
public class UserDaoTest {

	@Autowired
	private ApplicationContext context;
	@Autowired
	private UserDao userDao;

	@Before
	public void setUp() {
		DataSource dataSource = new SingleConnectionDataSource
				("jdbc:mysql://localhost/spring_test", "spring_developer", "1111", true);
		userDao.setDataSource(dataSource);

	}

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

}
