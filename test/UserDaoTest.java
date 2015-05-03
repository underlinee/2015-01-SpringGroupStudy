import java.sql.SQLException;

import org.junit.Test;

import Dao.ConnectionMaker;
import Dao.NConnectionMaker;
import Dao.UserDao;
import Model.User;


public class UserDaoTest {

	@Test
	public void test() throws ClassNotFoundException, SQLException {
		ConnectionMaker connectionMaker = new NConnectionMaker();
		UserDao userDao = new UserDao(connectionMaker);
		User user = new User();
		
		
		userDao.create(user);
		userDao.recieve(user);
	}

}
