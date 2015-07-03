package Service;


import Model.User;

public interface UserService {
	void add(User user);
	void upgradeLevels();
	void setUserLevelUpgradePolicy(UserLevelUpgradePolicy userLevelUpgradePolicy);

}
