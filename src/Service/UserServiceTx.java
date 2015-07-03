package Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import Model.User;

public class UserServiceTx implements UserService {
	UserService userService;
	@Autowired
	PlatformTransactionManager transactionManager;

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public void add(User user) {
		userService.add(user);
	}

	@Override
	public void upgradeLevels() {
		TransactionStatus status = this.transactionManager
				.getTransaction(new DefaultTransactionDefinition());
		try {
			userService.upgradeLevels();
			this.transactionManager.commit(status);
		} catch (RuntimeException e) {
			this.transactionManager.rollback(status);
			throw e;
		}
	}

	@Override
	public void setUserLevelUpgradePolicy(
			UserLevelUpgradePolicy userLevelUpgradePolicy) {
		userService.setUserLevelUpgradePolicy(userLevelUpgradePolicy);
	}

}
