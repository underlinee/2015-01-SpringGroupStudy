package Service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
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
		TransactionSynchronizationManager.initSynchronization();
		Connection c = DataSourceUtils.getConnection(dataSource);
		c.setAutoCommit(false);
		
		try {
			List<User> users = userDao.getAll();
			for (User user : users) {
				if (userLevelUpgradePolicy.canUpgradeLevel(user))
					userLevelUpgradePolicy.upgradeLevel(user);
			}
			c.commit();
			
		} catch (Exception e) {
			c.rollback();
			throw e;
		}
		
		DataSourceUtils.releaseConnection(c, dataSource);
		TransactionSynchronizationManager.unbindResource(this.dataSource);
		TransactionSynchronizationManager.clearSynchronization();
		
	}

	public void add(User user) {
		if (user.getLevel() == null)
			user.setLevel(Level.BASIC);
		userDao.create(user);
	}

}
