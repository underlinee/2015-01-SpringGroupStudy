package Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import Dao.UserDao;
import Model.Level;
import Model.User;

public class UserService {

	@Autowired
	UserDao userDao;
	
	@Autowired
	UserLevelUpgradePolicy userLevelUpgradePolicy;

	void upgradeLevels() {
		List<User> users = userDao.getAll();
		for (User user : users) {
			if (userLevelUpgradePolicy.canUpgradeLevel(user))
				userLevelUpgradePolicy.upgradeLevel(user);
		}

	}

	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.create(user);
	}

}
