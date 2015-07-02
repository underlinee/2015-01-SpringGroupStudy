package Service;

import java.sql.SQLException;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import Dao.UserDao;
import Model.Level;
import Model.User;

public class UserService {

	@Autowired
	UserDao userDao;

	@Autowired
	UserLevelUpgradePolicy userLevelUpgradePolicy;
	
	@Autowired
	PlatformTransactionManager transactionManager;

	public void setUserLevelUpgradePolicy(
			UserLevelUpgradePolicy userLevelUpgradePolicy) {
		this.userLevelUpgradePolicy = userLevelUpgradePolicy;
	}

	void upgradeLevels() throws SQLException {
		
		TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
		
		try {
			List<User> users = userDao.getAll();
			for (User user : users) {
				if (userLevelUpgradePolicy.canUpgradeLevel(user))
					userLevelUpgradePolicy.upgradeLevel(user);
			}
			transactionManager.commit(status);
			
			
		} catch (Exception e) {
			transactionManager.rollback(status);
			throw e;
		}
		
	}

	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.create(user);
	}

}
