package Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.jta.JtaTransactionManager;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import Dao.UserDao;
import Model.Level;
import Model.User;

public class UserService {

	@Autowired
	DataSource dataSource;
	
	@Autowired
	UserDao userDao;

	@Autowired
	UserLevelUpgradePolicy userLevelUpgradePolicy;

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public void setUserLevelUpgradePolicy(
			UserLevelUpgradePolicy userLevelUpgradePolicy) {
		this.userLevelUpgradePolicy = userLevelUpgradePolicy;
	}

	void upgradeLevels() throws SQLException {
//		PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
		PlatformTransactionManager transactionManager = new JtaTransactionManager();
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
