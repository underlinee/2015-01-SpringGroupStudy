package Service;

import static Service.UserLevelUpgradePolicySimple.MIN_LOGCOUNT_FOR_SILVER;
import static Service.UserLevelUpgradePolicySimple.MIN_RECOMMEND_FOR_GOLD;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import javax.sql.DataSource;

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
	@Autowired
	DataSource dataSource;
	@Autowired
	UserLevelUpgradePolicy userLevelUpgradePolicy;

	List<User> users;
	
	static class TestUserLevelUpgradePolicy extends UserLevelUpgradePolicySimple{
		private String id;
		
		private TestUserLevelUpgradePolicy(String id){
			this.id = id;
		}
		
		private void setUserDao(UserDao userDao){
			this.userDao = userDao;
		}
		
		@Override
		public void upgradeLevel(User user) {
			if (user.getId().equals(this.id)) {
				throw new TestUserServiceException();
			}
			super.upgradeLevel(user);
		}
		
	}
	static class TestUserServiceException extends RuntimeException{
		private static final long serialVersionUID = 1L;
	}
	
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
		userService.setUserLevelUpgradePolicy(userLevelUpgradePolicy);
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
		assertEquals(expectedLevel, userUpdate.getLevel());
	}
	
	@Test
	public void upgradeAllOrNothing() throws Exception {
		UserService userService = this.userService;
		TestUserLevelUpgradePolicy testPolicy = new TestUserLevelUpgradePolicy(users.get(3).getId());
		testPolicy.setUserDao(userDao);
		userService.setDataSource(dataSource);
		userService.setUserLevelUpgradePolicy(testPolicy);
		userDao.deleteAll();
		for(User user : users) userDao.create(user);
		
		try {
			userService.upgradeLevels();
			fail("testuserService exception expected");
		} catch (TestUserServiceException e) {
			
		} finally{
			userService.setUserLevelUpgradePolicy(new UserLevelUpgradePolicySimple());
		}
		checkLevel(users.get(3), users.get(3).getLevel());
		checkLevel(users.get(1), users.get(1).getLevel());
//		checkLevel(users.get(3), Level.SILVER);
//		checkLevel(users.get(1), Level.BASIC);
	}
}
