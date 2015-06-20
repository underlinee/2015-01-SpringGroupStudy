package Service;

import static Service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static Service.UserService.MIN_RECOMMEND_FOR_GOLD;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import Dao.UserDao;
import Model.Level;
import Model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "/test-applicationContext.xml")
public class UserServiceTest {
	@Autowired
	UserService userService;
	@Autowired
	UserDao userDao;

	List<User> users;

	@Before
	public void setup() {
		users = Arrays.asList(
				new User("erin", "이경륜", "1234", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER-1, 0), // BASIC
				new User("erin1", "이경륜", "1234", Level.BASIC, MIN_LOGCOUNT_FOR_SILVER, 0), // SILVER
				new User("erin2", "이경륜", "1234", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD-1), // SILVER
				new User("erin3", "이경륜", "1234", Level.SILVER, 60, MIN_RECOMMEND_FOR_GOLD), // GOLD
				new User("erin4", "이경륜", "1234", Level.GOLD, 100, 100) // GOLD
				);

	}

	@Test
	public void beanClass() throws Exception {
		assertNotNull(userService);
	}

	@Test
	public void upgradeLevels() throws Exception {
		userDao.deleteAll();
		for (User user : users)
			userDao.create(user);

		userService.upgradeLevels();

		checkLevel(users.get(0), Level.BASIC);
		checkLevel(users.get(1), users.get(1).getLevel().nextLevel());
		checkLevel(users.get(2), Level.SILVER);
		checkLevel(users.get(3), users.get(3).getLevel().nextLevel());
		checkLevel(users.get(4), Level.GOLD);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void cannotUpgrade() throws Exception {
		userDao.deleteAll();
		User user = users.get(0);
		userDao.create(user);
		
		Level[] levels = Level.values();
		for(Level level : levels){
			if(level.nextLevel() != null) continue;
			user.setLevel(level);
			user.upgradeLevel();
		}
	}


	@Test
	public void add() throws Exception {
		userDao.deleteAll();
		User userWithLevel = users.get(4); // gold -> gold
		User userWithoutLevel = users.get(3); // BASIC
		userWithoutLevel.setLevel(null);

		userService.add(userWithLevel);
		userService.add(userWithoutLevel);

		checkLevel(userWithLevel, userWithLevel.getLevel());
		checkLevel(userWithoutLevel, Level.BASIC);
	}
	
	private void checkLevel(User user, Level expectedLevel) {
		User userUpdate = userDao.recieve(user.getId());
		assertEquals(userUpdate.getLevel(), expectedLevel);
	}
}
