package Service;

import org.springframework.mail.MailSender;

import Model.User;

public interface UserLevelUpgradePolicy {
	boolean canUpgradeLevel(User user);
	void upgradeLevel(User user);
}
