package Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import Dao.UserDao;
import Model.Level;
import Model.User;

public class UserService {

	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;
	
	@Autowired
	UserDao userDao;

	void upgradeLevels() {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if (canUpgradeLevel(user))
				upgradeLevel(user);
		}

	}

	private boolean canUpgradeLevel(User user) {
		Level currentLevel = user.getLevel();
		switch (currentLevel) {
		case BASIC:
			return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
		case SILVER:
			return (user.getRecommend() >= MIN_RECOMMEND_FOR_GOLD);
		case GOLD:
			return false;
		default:
			throw new IllegalArgumentException("Unknown Level:" + currentLevel);
		}
	}

	private void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		
	}

	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.create(user);
	}

}
