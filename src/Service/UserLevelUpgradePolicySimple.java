package Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import Dao.UserDao;
import Model.Level;
import Model.User;

@Component
public class UserLevelUpgradePolicySimple implements UserLevelUpgradePolicy{

	@Autowired
	UserDao userDao;
	
	public static final int MIN_LOGCOUNT_FOR_SILVER = 50;
	public static final int MIN_RECOMMEND_FOR_GOLD = 30;
	
	public boolean canUpgradeLevel(User user) {
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
	
	public void upgradeLevel(User user) {
		user.upgradeLevel();
		userDao.update(user);
		
	}

}
